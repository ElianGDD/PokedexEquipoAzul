package com.RisosuIT.Pokedex.Controlador;

import com.RisosuIT.Pokedex.DTO.GetAllTypes;
import com.RisosuIT.Pokedex.DTO.NamedAPIResource;
import com.RisosuIT.Pokedex.DTO.Pokemon;
import com.RisosuIT.Pokedex.DTO.PokemonVistaDto;
import com.RisosuIT.Pokedex.Servicio.ServicioPokemon;
import com.RisosuIT.Pokedex.Servicio.ServicioTipo;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

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
    public String listarPokemons(Model modelo) {
        List<NamedAPIResource> pagina = servicioPokemon.buscarPokemons("", null, 0, 24);

        List<PokemonVistaDto> vistaPokemons = pagina.stream()
                .map(p -> {
                    Integer id = extraerIdDesdeUrl(p.getUrl());
                    Pokemon detalle = id != null ? servicioPokemon.obtenerDetallePokemonPorId(id) : null;
                    return detalle != null ? servicioPokemon.convertirAPokemonVista(detalle) : null;
                })
                .filter(p -> p != null)
                .collect(Collectors.toList());

        modelo.addAttribute("pokemons", vistaPokemons);

        GetAllTypes tipos = servicioTipo.obtenerTodosLosTiposCacheados();
        modelo.addAttribute("types", tipos.getResults());

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
