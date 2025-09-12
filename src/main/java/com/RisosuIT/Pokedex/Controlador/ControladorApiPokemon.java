package com.RisosuIT.Pokedex.Controlador;

import com.RisosuIT.Pokedex.DTO.PokemonVistaDto;
import com.RisosuIT.Pokedex.Servicio.ServicioPokemon;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pokemons")
public class ControladorApiPokemon {

    private final ServicioPokemon servicioPokemon;

    public ControladorApiPokemon(ServicioPokemon servicioPokemon) {
        this.servicioPokemon = servicioPokemon;
    }

    // === LISTADO paginado + búsqueda ===
    @GetMapping
    public List<PokemonVistaDto> buscarPokemons(
            @RequestParam(defaultValue = "") String nombre,
            @RequestParam(required = false) List<String> tipos, // ✅ lista de tipos
            @RequestParam(defaultValue = "0") int pagina,
            @RequestParam(defaultValue = "12") int tamanio) {
        return servicioPokemon.buscarPokemons(nombre, tipos, pagina, tamanio);
    }

    // === FILTRO por múltiples tipos ===
    @GetMapping("/por-tipos")
    public ResponseEntity<List<PokemonVistaDto>> buscarPokemonsPorTipos(
            @RequestParam("tipos") List<String> listaTipos) {
        if (listaTipos == null || listaTipos.isEmpty()) {
            return ResponseEntity.ok(List.of());
        }
        List<PokemonVistaDto> resultado = servicioPokemon.buscarPokemonsPorTipos(listaTipos);
        return ResponseEntity.ok(resultado);
    }

    @GetMapping("/search")
    public ResponseEntity<Map<String, Object>> buscarPokemonsSearch(
            @RequestParam(defaultValue = "") String nombre,
            @RequestParam(required = false) List<String> tipo,
            @RequestParam(defaultValue = "0") int pagina,
            @RequestParam(defaultValue = "12") int tamanio,
            @RequestParam(required = false) List<String> tipos) {

        List<PokemonVistaDto> pokemons;

        if (tipos != null && !tipos.isEmpty()) {
            // si hay múltiples tipos seleccionados
            pokemons = servicioPokemon.buscarPokemonsPorTipos(tipos);
        } else {
            // búsqueda normal con nombre, tipo, paginación
            pokemons = servicioPokemon.buscarPokemons(nombre, tipo, pagina, tamanio);
        }

        Map<String, Object> respuesta = new HashMap<>();
        respuesta.put("pokemons", pokemons);
        respuesta.put("pagina", pagina);
        respuesta.put("tamanio", tamanio);
        respuesta.put("hasNext", pokemons.size() == tamanio);

        return ResponseEntity.ok(respuesta);
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
