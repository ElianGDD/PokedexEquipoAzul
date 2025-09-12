package com.RisosuIT.Pokedex.Controller;

import com.RisosuIT.Pokedex.DAO.GetAllTypes;
import com.RisosuIT.Pokedex.DAO.NamedAPIResource;
import com.RisosuIT.Pokedex.Service.ServicePokemon;
import com.RisosuIT.Pokedex.Service.ServiceType;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PokemonWebController {

    private final ServicePokemon pokemonService;
    private final ServiceType typeService;

    public PokemonWebController(ServicePokemon pokemonService, ServiceType typeService) {
        this.pokemonService = pokemonService;
        this.typeService = typeService;
    }

    @GetMapping("/pokemons")
    public String listPokemons(Model model) {
        List<NamedAPIResource> page = pokemonService.searchCatalog("", null, 0, 24);
        model.addAttribute("pokemons", page);

        // Se asume que TypeService expone un método bloqueante que devuelve GetAllTypes
        GetAllTypes tipos = typeService.fetchAllTypes();
        model.addAttribute("types", tipos != null ? tipos.getResults() : List.of());

        return "pokemons";
    }
}
