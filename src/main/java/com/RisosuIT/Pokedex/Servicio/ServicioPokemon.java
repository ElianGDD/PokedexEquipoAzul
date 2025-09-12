package com.RisosuIT.Pokedex.Servicio;

import com.RisosuIT.Pokedex.DTO.GetAllPokemon;
import com.RisosuIT.Pokedex.DTO.GetAllTypes;
import com.RisosuIT.Pokedex.DTO.NamedAPIResource;
import com.RisosuIT.Pokedex.DTO.Pokemon;
import com.RisosuIT.Pokedex.DTO.PokemonVistaDto;
import com.RisosuIT.Pokedex.DTO.TypeDetailDto;
import com.RisosuIT.Pokedex.Estado.EstadoCargaAplicacion;
import com.RisosuIT.Pokedex.Util.ColoresTiposPokemon;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import jakarta.annotation.PostConstruct;
import java.time.Duration;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class ServicioPokemon {

    private final WebClient clienteWebPokemon;
    private final Cache<Integer, Pokemon> cacheDetallesPokemon;
    private final EstadoCargaAplicacion estadoCarga;
    private final ConcurrentMap<Integer, NamedAPIResource> catalogoPokemons = new ConcurrentHashMap<>();
    private final ConcurrentMap<String, Set<Integer>> indiceTiposAPokemons = new ConcurrentHashMap<>();
    private final ConcurrentMap<String, Integer> indiceNombreAPokemonId = new ConcurrentHashMap<>();
    private static final Pattern patronIdUrl = Pattern.compile(".*/(\\d+)/?$");

    private final ThreadPoolTaskExecutor ejecutorTareas;

    public ServicioPokemon(@Qualifier("pokemonWebClient") WebClient clienteWebPokemon,
            EstadoCargaAplicacion estadoCarga,
            ThreadPoolTaskExecutor ejecutorTareas) {
        this.clienteWebPokemon = clienteWebPokemon;
        this.estadoCarga = estadoCarga;
        this.ejecutorTareas = ejecutorTareas;
        this.cacheDetallesPokemon = Caffeine.newBuilder()
                .maximumSize(2000)
                .expireAfterWrite(Duration.ofHours(8))
                .build();
    }

    // =======================
    // === PRECARGA
    // =======================
    @PostConstruct
    public void init() {
        // la precarga se dispara desde LoaderController vía precargarDatos()
    }

    public void precargarDatos() {
        cargarCatalogoPokemons();
        construirIndiceTipos();

        estadoCarga.reiniciar(catalogoPokemons.size());
        precargarDetallesPokemonAsincrono();
    }

    private void cargarCatalogoPokemons() {
        GetAllPokemon respuesta = clienteWebPokemon.get()
                .uri("/pokemon?limit=2000")
                .retrieve()
                .bodyToMono(GetAllPokemon.class)
                .block();

        if (respuesta == null || respuesta.getResults() == null) {
            return;
        }

        for (NamedAPIResource pokemonRecurso : respuesta.getResults()) {
            Integer id = extraerIdDesdeUrl(pokemonRecurso.getUrl());
            if (id != null) {
                catalogoPokemons.put(id, pokemonRecurso);
                indiceNombreAPokemonId.put(pokemonRecurso.getName().toLowerCase(), id);
            }
        }
    }

    private void construirIndiceTipos() {
        GetAllTypes tipos = clienteWebPokemon.get()
                .uri("/type")
                .retrieve()
                .bodyToMono(GetAllTypes.class)
                .block();

        if (tipos == null || tipos.getResults() == null) {
            return;
        }

        for (NamedAPIResource tipo : tipos.getResults()) {
            TypeDetailDto detalleTipo = clienteWebPokemon.get()
                    .uri("/type/{name}", tipo.getName())
                    .retrieve()
                    .bodyToMono(TypeDetailDto.class)
                    .block();

            if (detalleTipo == null || detalleTipo.getPokemon() == null) {
                continue;
            }

            Set<Integer> listaIds = detalleTipo.getPokemon().stream()
                    .map(p -> extraerIdDesdeUrl(p.getPokemon().getUrl()))
                    .filter(Objects::nonNull)
                    .collect(Collectors.toSet());

            indiceTiposAPokemons.put(tipo.getName().toLowerCase(), listaIds);
        }
    }

    private void precargarDetallesPokemonAsincrono() {
        int total = catalogoPokemons.size();
        estadoCarga.reiniciar(total);

        CountDownLatch latch = new CountDownLatch(total);
        Semaphore limiteConcurrente = new Semaphore(16); // 🔽 reducimos concurrencia para estabilidad

        for (Integer id : catalogoPokemons.keySet()) {
            ejecutorTareas.submit(() -> {
                try {
                    limiteConcurrente.acquire();
                    cargarPokemonConReintentos(id, 3); // 🔄 reintentos
                } catch (InterruptedException ignored) {
                    Thread.currentThread().interrupt();
                } finally {
                    limiteConcurrente.release();
                    estadoCarga.incrementar();
                    latch.countDown();
                }
            });
        }

        ejecutorTareas.submit(() -> {
            try {
                latch.await();
                estadoCarga.marcarTerminado();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
    }

    private void cargarPokemonConReintentos(int id, int maxIntentos) {
        int intento = 0;
        while (intento < maxIntentos) {
            try {
                obtenerDetallePokemonPorId(id); // cachea en memoria
                return; // éxito
            } catch (Exception e) {
                intento++;
                System.err.printf("❌ Error cargando Pokémon %d (intento %d/%d): %s%n",
                        id, intento, maxIntentos, e.getMessage());
                try {
                    Thread.sleep(200L * intento); // backoff
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                    return;
                }
            }
        }
        System.err.printf("⚠️ Pokémon %d no pudo cargarse tras %d intentos%n", id, maxIntentos);
    }

    // =======================
    // === CONSULTAS
    // =======================
    public List<PokemonVistaDto> buscarPokemons(String nombre, List<String> tipos, int pagina, int tamanio) {
        Stream<Integer> flujoIds = catalogoPokemons.keySet().stream();

        // ✅ Filtrado por tipos múltiples (intersección de conjuntos)
        if (tipos != null && !tipos.isEmpty()) {
            Set<Integer> idsFiltrados = null;
            for (String tipo : tipos) {
                Set<Integer> idsPorTipo = indiceTiposAPokemons.getOrDefault(tipo.toLowerCase(), Collections.emptySet());
                if (idsFiltrados == null) {
                    idsFiltrados = idsPorTipo;
                } else {
                    idsFiltrados.retainAll(idsPorTipo); // intersección
                }
            }
            if (idsFiltrados == null || idsFiltrados.isEmpty()) {
                return List.of(); // ningún resultado
            }
            flujoIds = idsFiltrados.stream();
        }

        // ✅ Filtrado por nombre
        if (nombre != null && !nombre.isBlank()) {
            String consulta = nombre.toLowerCase();
            flujoIds = flujoIds.filter(id -> {
                NamedAPIResource recurso = catalogoPokemons.get(id);
                return recurso != null && recurso.getName().toLowerCase().contains(consulta);
            });
        }

        // ✅ Paginación
        List<Integer> listaIds = flujoIds.sorted().toList();
        int inicio = Math.min(pagina * tamanio, listaIds.size());
        int fin = Math.min(inicio + tamanio, listaIds.size());

        return listaIds.subList(inicio, fin).stream()
                .map(this::obtenerDetalleVistaPorId)
                .filter(Objects::nonNull)
                .toList();

    }

    private PokemonVistaDto obtenerDetalleVistaPorId(Integer id) {
        Pokemon p = obtenerDetallePokemonPorId(id);
        return convertirAPokemonVista(p);
    }

    public List<PokemonVistaDto> buscarPokemonsPorTipos(List<String> listaTipos) {
        if (listaTipos == null || listaTipos.isEmpty()) {
            return List.of();
        }

        // Intersección de sets
        Set<Integer> idsCoincidentes = null;
        for (String tipo : listaTipos) {
            Set<Integer> ids = indiceTiposAPokemons.getOrDefault(tipo.toLowerCase(), Collections.emptySet());
            if (idsCoincidentes == null) {
                idsCoincidentes = new HashSet<>(ids);
            } else {
                idsCoincidentes.retainAll(ids);
            }
        }

        if (idsCoincidentes == null || idsCoincidentes.isEmpty()) {
            return List.of();
        }

        return idsCoincidentes.stream()
                .sorted()
                .map(this::obtenerDetalleVistaPorId)
                .filter(Objects::nonNull)
                .toList();
    }

    public PokemonVistaDto obtenerDetalleVistaPorId(int id) {
        Pokemon pokemon = obtenerDetallePokemonPorId(id);
        return convertirAPokemonVista(pokemon);
    }

    public PokemonVistaDto convertirAPokemonVista(Pokemon pokemon) {
        if (pokemon == null) {
            return null;
        }

        List<String> tipos = pokemon.getTypes() != null
                ? pokemon.getTypes().stream()
                        .map(t -> t.getType().getName())
                        .toList()
                : List.of();

        String colorPrimario = !tipos.isEmpty() ? ColoresTiposPokemon.obtenerColor(tipos.get(0)) : "#ccc";
        String colorSecundario = tipos.size() > 1 ? ColoresTiposPokemon.obtenerColor(tipos.get(1)) : colorPrimario;

        return new PokemonVistaDto(
                pokemon.getId(),
                pokemon.getName(),
                pokemon.getSprites() != null ? pokemon.getSprites().getFront_default() : "",
                tipos,
                colorPrimario,
                colorSecundario,
                tipos.stream().collect(Collectors.toMap(t -> t, ColoresTiposPokemon::obtenerColor))
        );
    }

    public Pokemon obtenerDetallePokemonPorId(int id) {
        return cacheDetallesPokemon.get(id, clave
                -> clienteWebPokemon.get()
                        .uri("/pokemon/{id}", clave)
                        .retrieve()
                        .bodyToMono(Pokemon.class)
                        .timeout(Duration.ofSeconds(10))
                        .block()
        );
    }

    // =======================
    // === UTILS
    // =======================
    public Integer extraerIdDesdeUrl(String url) {
        if (url == null) {
            return null;
        }
        Matcher matcher = patronIdUrl.matcher(url);
        if (matcher.matches()) {
            try {
                return Integer.valueOf(matcher.group(1));
            } catch (NumberFormatException ignored) {
                return null;
            }
        }
        return null;
    }

    public EstadoCargaAplicacion getEstadoCarga() {
        return estadoCarga;
    }

    public int contarTotalPokemons() {
        return catalogoPokemons.size();
    }
}
