package com.RisosuIT.Pokedex.Controller;

import com.RisosuIT.Pokedex.DAO.GetAllPokemon;
import com.RisosuIT.Pokedex.DAO.GetAllTypes;
import com.RisosuIT.Pokedex.DAO.NamedAPIResource;
import com.RisosuIT.Pokedex.DAO.Pokemon;
import com.RisosuIT.Pokedex.DAO.Type;
import com.RisosuIT.Pokedex.DAO.Types;
import com.RisosuIT.Pokedex.Service.ServicePokemon;
import com.RisosuIT.Pokedex.Service.ServiceType;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import reactor.core.publisher.Mono;

@Controller
public class ControllerPokemon {

    private final ServicePokemon servicePokemon;
    private final ServiceType serviceType;

    public ControllerPokemon(ServicePokemon servicePokemon,
            ServiceType serviceType) {
        this.servicePokemon = servicePokemon;
        this.serviceType = serviceType;
    }

    @GetMapping("/pokemons")
    public String GetAllPokemons(Model model) {
        try {
            GetAllPokemon pokemos = servicePokemon.GetAllPokemons().block();
            model.addAttribute("pokemons", pokemos);
            return "pokemons";
        } catch (Exception ex) {
            model.addAttribute("errores", ex.getLocalizedMessage());
            return "errores";
        }
    }

    @GetMapping("/pokemon")
    public String GetOnePokemon(@PathVariable(required = false) int id, Model model) {
        Mono<Pokemon> pokemon = servicePokemon.getOnePokemon(id);
        model.addAttribute("pokemon", pokemon.block());
        return "pokemon";
    }

    @GetMapping("/allTypes")
    public GetAllTypes GetAllTypesPokemons(Model model) {
        GetAllTypes tiposPokemon = serviceType.GetAllTypes().block();
        return tiposPokemon;
    }

    @GetMapping("/pokemonXNTipo")
    public List<Pokemon> getPokemonXNTipo(@RequestParam List<String> types) {
        GetAllPokemon pokemos = servicePokemon.GetAllPokemons().block();
        List<NamedAPIResource> listaDePokemones = pokemos.getResults();
        List<Pokemon> listaPokemones = new ArrayList<>();

        for (NamedAPIResource pokemon : listaDePokemones) {
            Pokemon descripcionPokemon = servicePokemon.getPokemonByName(pokemon.getName()).block();
            listaPokemones.add(descripcionPokemon);
        }

        List<Pokemon> listaFiltradaXTipo = listaPokemones.stream()
                .filter(poke -> poke.getTypes().stream()
                .anyMatch(typeSlot -> types.contains(typeSlot.getType().getName())))
                .collect(Collectors.toList());

        return listaFiltradaXTipo;
    }

}
