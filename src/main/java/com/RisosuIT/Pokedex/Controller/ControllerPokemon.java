package com.RisosuIT.Pokedex.Controller;

import com.RisosuIT.Pokedex.DAO.GetAllPokemon;
import com.RisosuIT.Pokedex.DAO.Pokemon;
import com.RisosuIT.Pokedex.Service.ServicePokemon;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import reactor.core.publisher.Mono;

@Controller
public class ControllerPokemon {

    private final ServicePokemon servicePokemon;

    public ControllerPokemon(ServicePokemon servicePokemon) {
        this.servicePokemon = servicePokemon;
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

}
