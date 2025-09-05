package com.RisosuIT.Pokedex.Controller;

import com.RisosuIT.Pokedex.DAO.GetAllPokemon;
import com.RisosuIT.Pokedex.Service.ServicePokemon;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import reactor.core.publisher.Mono;

@Controller
public class ControllerPokemon {

    private final ServicePokemon servicePokemon;

    public ControllerPokemon(ServicePokemon servicePokemon) {
        this.servicePokemon = servicePokemon;
    }

    @GetMapping
    public String GetAllPokemons(Model model) {
        try {
            Mono<GetAllPokemon> pokemos = servicePokemon.GetAllPokemons();
            model.addAttribute("pokemos", pokemos.block());
            return "pokemons";
        } catch (Exception ex) {
            model.addAttribute("errores", ex.getLocalizedMessage());
            return "errores";
        }
    }

}
