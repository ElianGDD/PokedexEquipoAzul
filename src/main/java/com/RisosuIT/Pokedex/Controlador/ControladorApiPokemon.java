package com.RisosuIT.Pokedex.Controlador;

import com.RisosuIT.Pokedex.DTO.NamedAPIResource;
import com.RisosuIT.Pokedex.DTO.Pokemon;
import com.RisosuIT.Pokedex.Servicio.ServicioPokemon;
import com.RisosuIT.Pokedex.Servicio.ServicioTipo;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pokemons")
public class ControladorApiPokemon {

    private final ServicioPokemon servicioPokemon;
    private final ServicioTipo servicioTipo;

    public ControladorApiPokemon(ServicioPokemon servicioPokemon, ServicioTipo servicioTipo) {
        this.servicioPokemon = servicioPokemon;
        this.servicioTipo = servicioTipo;
    }

    @GetMapping("/search")
    public Map<String, Object> buscarPokemonsJson(
            @RequestParam(defaultValue = "") String nombre,
            @RequestParam(required = false) String tipo,
            @RequestParam(defaultValue = "0") int pagina,
            @RequestParam(defaultValue = "12") int tamanio) {

        List<NamedAPIResource> recursos = servicioPokemon.buscarPokemons(nombre, tipo, pagina, tamanio);

        // Convertir a DTOs de vista
        List<com.RisosuIT.Pokedex.DTO.PokemonVistaDto> vista = recursos.stream()
                .map(r -> {
                    Integer id = extraerIdDesdeUrl(r.getUrl());
                    Pokemon detalle = id != null ? servicioPokemon.obtenerDetallePokemonPorId(id) : null;
                    return detalle != null ? servicioPokemon.convertirAPokemonVista(detalle) : null;
                })
                .filter(dto -> dto != null)
                .toList();

        boolean hasNext = recursos.size() == tamanio;

        Map<String, Object> response = new HashMap<>();
        response.put("pokemons", vista);
        response.put("pagina", pagina);
        response.put("hasNext", hasNext);

        return response;
    }

// utilitario privado (mismo que en el controlador web)
    private Integer extraerIdDesdeUrl(String url) {
        if (url == null) {
            return null;
        }
        java.util.regex.Matcher matcher = java.util.regex.Pattern.compile(".*/(\\d+)/?$").matcher(url);
        if (matcher.matches()) {
            try {
                return Integer.valueOf(matcher.group(1));
            } catch (NumberFormatException e) {
                return null;
            }
        }
        return null;
    }

    @GetMapping("/por-tipos")
    public ResponseEntity<List<NamedAPIResource>> buscarPokemonsPorTipos(
            @RequestParam("tipos") List<String> listaTipos) {
        if (listaTipos == null || listaTipos.isEmpty()) {
            return ResponseEntity.ok(List.of());
        }
        List<NamedAPIResource> resultado = servicioTipo.obtenerPokemonsPorTipos(listaTipos);
        return ResponseEntity.ok(resultado);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pokemon> obtenerDetalle(@PathVariable int id) {
        Pokemon pokemon = servicioPokemon.obtenerDetallePokemonPorId(id);
        if (pokemon == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(pokemon);
    }

    @GetMapping("/estado-precarga")
    public Map<String, Object> obtenerEstadoPrecarga() {
        Map<String, Object> estado = new HashMap<>();
        estado.put("procesados", servicioPokemon.getEstadoCarga().getProgresoActual());
        estado.put("total", servicioPokemon.getEstadoCarga().getTotal());
        estado.put("terminado", servicioPokemon.getEstadoCarga().isTerminado());
        estado.put("porcentaje", servicioPokemon.getEstadoCarga().getPorcentajeAvance());
        return estado;
    }
}
