package com.RisosuIT.Pokedex.Servicio;

import com.RisosuIT.Pokedex.DTO.GetAllTypes;
import com.RisosuIT.Pokedex.DTO.NamedAPIResource;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import jakarta.annotation.Nonnull;
import java.time.Duration;
import java.util.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class ServicioTipo {

    private static final String CLAVE_CACHE_TODOS_TIPOS = "todos-los-tipos";

    private final WebClient clienteWebTipo;

    // Cache para lista completa de tipos
    private final Cache<String, GetAllTypes> cacheListaTipos;

    // Cache para detalles de cada tipo individual
    private final Cache<String, RespuestaDetalleTipo> cacheDetallesTipo;

    public ServicioTipo(@Qualifier("typeWebClient") WebClient clienteWebTipo) {
        this.clienteWebTipo = clienteWebTipo;
        this.cacheListaTipos = Caffeine.newBuilder()
                .maximumSize(4)
                .expireAfterWrite(Duration.ofHours(1))
                .build();
        this.cacheDetallesTipo = Caffeine.newBuilder()
                .maximumSize(40)
                .expireAfterWrite(Duration.ofHours(1))
                .build();
    }

    public GetAllTypes obtenerTodosLosTiposCacheados() {
        return cacheListaTipos.get(CLAVE_CACHE_TODOS_TIPOS, clave
                -> clienteWebTipo.get()
                        .uri("/type")
                        .retrieve()
                        .bodyToMono(GetAllTypes.class)
                        .block()
        );
    }

    public void invalidarCaches() {
        cacheListaTipos.invalidate(CLAVE_CACHE_TODOS_TIPOS);
        cacheDetallesTipo.invalidateAll();
    }

    public List<NamedAPIResource> obtenerPokemonsPorTipos(@Nonnull List<String> listaTipos) {
        if (listaTipos == null || listaTipos.isEmpty()) {
            return Collections.emptyList();
        }

        List<RespuestaDetalleTipo> listaDetalles = listaTipos.stream()
                .map(this::obtenerDetalleTipoCacheado)
                .filter(Objects::nonNull)
                .toList();

        if (listaDetalles.isEmpty()) {
            return Collections.emptyList();
        }

        Map<String, NamedAPIResource> mapaNombreAPokemon = new HashMap<>();
        Map<String, Integer> conteoApariciones = new HashMap<>();
        int cantidadTipos = listaDetalles.size();

        for (RespuestaDetalleTipo detalle : listaDetalles) {
            Set<String> nombresVistosEnTipo = new HashSet<>();
            for (EntradaTipoPokemon entrada : Optional.ofNullable(detalle.getPokemons()).orElse(Collections.emptyList())) {
                NamedAPIResource pokemon = entrada.getPokemon();
                if (pokemon == null || pokemon.getName() == null) {
                    continue;
                }
                nombresVistosEnTipo.add(pokemon.getName());
                mapaNombreAPokemon.putIfAbsent(pokemon.getName(), pokemon);
            }
            for (String nombre : nombresVistosEnTipo) {
                conteoApariciones.merge(nombre, 1, Integer::sum);
            }
        }

        return conteoApariciones.entrySet().stream()
                .filter(e -> e.getValue() == cantidadTipos)
                .map(e -> mapaNombreAPokemon.get(e.getKey()))
                .filter(Objects::nonNull)
                .toList();
    }

    /**
     * Obtiene detalle de un tipo desde cache o PokeAPI
     */
    private RespuestaDetalleTipo obtenerDetalleTipoCacheado(String nombreTipo) {
        if (nombreTipo == null || nombreTipo.isBlank()) {
            return null;
        }
        return cacheDetallesTipo.get(nombreTipo.toLowerCase(), clave
                -> clienteWebTipo.get()
                        .uri("/type/{t}", clave)
                        .retrieve()
                        .bodyToMono(RespuestaDetalleTipo.class)
                        .onErrorReturn(new RespuestaDetalleTipo()) // fallback vacío
                        .block()
        );
    }

    // DTOs internos para mapear la API de tipos
    public static class RespuestaDetalleTipo {

        private List<EntradaTipoPokemon> pokemons = new ArrayList<>();

        public List<EntradaTipoPokemon> getPokemons() {
            return pokemons;
        }

        public void setPokemons(List<EntradaTipoPokemon> pokemons) {
            this.pokemons = pokemons;
        }
    }

    public static class EntradaTipoPokemon {

        private NamedAPIResource pokemon;

        public NamedAPIResource getPokemon() {
            return pokemon;
        }

        public void setPokemon(NamedAPIResource pokemon) {
            this.pokemon = pokemon;
        }
    }
}
