package com.RisosuIT.Pokedex.Controller;

import com.RisosuIT.Pokedex.DAO.NamedAPIResource;
import com.RisosuIT.Pokedex.DAO.Pokemon;
import com.RisosuIT.Pokedex.Service.ServicePokemon;
import com.RisosuIT.Pokedex.Service.ServiceType;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/pokemons")
public class PokemonApiController {

    private final ServicePokemon pokemonService;
    private final ServiceType serviceType;

    public PokemonApiController(ServicePokemon pokemonService, ServiceType serviceType) {
        this.pokemonService = pokemonService;
        this.serviceType = serviceType;
    }

    @GetMapping
    public List<NamedAPIResource> search(
            @RequestParam(defaultValue = "") String name,
            @RequestParam(required = false) String type,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "12") int size) {
        return pokemonService.searchPokemonCatalog(name, type, page, size);
    }

    @GetMapping("/by-types")
    public ResponseEntity<List<NamedAPIResource>> byTypes(@RequestParam("types") List<String> types) {
        if (types == null || types.size() < 2) {
            return ResponseEntity.ok(List.of());
        }
        List<NamedAPIResource> result = serviceType.getPokemonsByTwoTypes(types.get(0), types.get(1));
        return ResponseEntity.ok(result);
    }

}
