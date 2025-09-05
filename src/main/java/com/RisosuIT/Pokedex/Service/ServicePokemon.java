package com.RisosuIT.Pokedex.Service;

import com.RisosuIT.Pokedex.DAO.GetAllPokemon;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class ServicePokemon {

    private final WebClient webClient;

    public ServicePokemon(WebClient webClient) {
        this.webClient = webClient;
    }

    public Mono<GetAllPokemon> GetAllPokemons() {
        return webClient.get()
                .retrieve()
                .bodyToMono(GetAllPokemon.class);
    }

}
