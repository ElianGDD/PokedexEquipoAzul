package com.RisosuIT.Pokedex.Controlador;

import com.RisosuIT.Pokedex.DTO.GetAllTypes;
import com.RisosuIT.Pokedex.DTO.NamedAPIResource;
import com.RisosuIT.Pokedex.DTO.Pokemon;
import com.RisosuIT.Pokedex.DTO.PokemonVistaDto;
import com.RisosuIT.Pokedex.Servicio.ServicioPokemon;
import com.RisosuIT.Pokedex.Servicio.ServicioTipo;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ControladorWebPokemon {

    private final ServicioPokemon servicioPokemon;
    private final ServicioTipo servicioTipo;

    public ControladorWebPokemon(ServicioPokemon servicioPokemon, ServicioTipo servicioTipo) {
        this.servicioPokemon = servicioPokemon;
        this.servicioTipo = servicioTipo;
    }

    @GetMapping("/")
    public String inicio() {
        return "pantalla-carga";
    }

    @GetMapping("/pokemons")
    public String listarPokemons(
            @RequestParam(defaultValue = "") String nombre,
            @RequestParam(required = false) List<String> tipos,
            @RequestParam(defaultValue = "0") int pagina,
            @RequestParam(defaultValue = "12") int tamanio,
            Model modelo) {

        // Obtener resultados del servicio
        List<PokemonVistaDto> paginaPokemons = servicioPokemon.buscarPokemons(nombre,
                tipos,
                pagina, tamanio);

        // Convertir a DTO de vista
        List<PokemonVistaDto> vistaPokemons = paginaPokemons.stream()
                .map(p -> {
                    Integer id = extraerIdDesdeUrl(String.valueOf(p.id()));
                    Pokemon detalle = (id != null) ? servicioPokemon.obtenerDetallePokemonPorId(id) : null;
                    return (detalle != null) ? servicioPokemon.convertirAPokemonVista(detalle) : null;
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        // Tipos para filtros
        GetAllTypes tiposDisponibles = servicioTipo.obtenerTodosLosTiposCacheados();

        // Atributos para la vista
        modelo.addAttribute("pokemons", vistaPokemons);
        modelo.addAttribute("total", servicioPokemon.contarTotalPokemons()); // total global
        modelo.addAttribute("pagina", pagina);
        modelo.addAttribute("tamanio", tamanio);
        modelo.addAttribute("nombre", nombre);
        modelo.addAttribute("types", tiposDisponibles != null ? tiposDisponibles.getResults() : List.of());
        modelo.addAttribute("tiposSeleccionados", tipos != null ? tipos : List.of());

        return "pokemons";
    }

    @GetMapping("/pokemon/{id}")
    public String detallePokemon(@PathVariable int id, Model modelo) {
        Pokemon detalle = servicioPokemon.obtenerDetallePokemonPorId(id);
        PokemonVistaDto dto = servicioPokemon.convertirAPokemonVista(detalle);
        modelo.addAttribute("pokemon", dto);
        return "pokemon-detail";
    }

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
}
