package com.RisosuIT.Pokedex.Controller;

import com.RisosuIT.Pokedex.DAO.NamedAPIResource;
import com.RisosuIT.Pokedex.DAO.Pokemon;
import com.RisosuIT.Pokedex.Service.ServicePokemon;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/pokemons")
public class PokemonApiController {

    private final ServicePokemon pokemonService;

    public PokemonApiController(ServicePokemon pokemonService) {
        this.pokemonService = pokemonService;
    }

    @GetMapping
    public List<NamedAPIResource> search(
            @RequestParam(defaultValue = "") String name,
            @RequestParam(required = false) String type,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "12") int size) {
        return pokemonService.searchCatalog(name, type, page, size);
    }

    @GetMapping("/{id}")
    public Pokemon detail(@PathVariable int id) {
        return pokemonService.getPokemonDetailById(id);
    }
}
