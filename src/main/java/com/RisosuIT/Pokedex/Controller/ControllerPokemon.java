package com.RisosuIT.Pokedex.Controller;

import com.RisosuIT.Pokedex.DAO.GetAllPokemon;
import com.RisosuIT.Pokedex.DAO.Pokemon;
import com.RisosuIT.Pokedex.Service.ServicePokemon;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import reactor.core.publisher.Mono;

@Controller
@RequestMapping("/Pokemon")
public class ControllerPokemon {

    private final ServicePokemon servicePokemon;

    public ControllerPokemon(ServicePokemon servicePokemon) {
        this.servicePokemon = servicePokemon;
    }

    @GetMapping("/pokemons")
    public String GetAllPokemons(Model model, @RequestParam(required = false) int id) {
        try {
            if (id != 0) {
                Mono<Pokemon> pokemon = servicePokemon.getOnePokemon(id);
                model.addAttribute("pokemon", pokemon.block());
                return "pokemon";
            } else {
                Mono<GetAllPokemon> pokemos = servicePokemon.GetAllPokemons();
                model.addAttribute("pokemos", pokemos.block());
                return "pokemons";
            }
        } catch (Exception ex) {
            model.addAttribute("errores", ex.getLocalizedMessage());
            return "errores";
        }
    }

}
