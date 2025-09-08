package com.RisosuIT.Pokedex.Service;

import com.RisosuIT.Pokedex.DAO.GetAllPokemon;
import com.RisosuIT.Pokedex.DAO.Pokemon;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class ServicePokemon {

    private final WebClient webClient;

    public ServicePokemon(@Qualifier("pokemonWebClient")WebClient webClient) {
        this.webClient = webClient;
    }

    public Mono<GetAllPokemon> GetAllPokemons() {
        return webClient.get()
                .retrieve()
                .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(),
                        response -> Mono.error(new RuntimeException("Error al obtener el Pokémon")))
                .bodyToMono(GetAllPokemon.class);
    }

    public Mono<Pokemon> getOnePokemon(int id) {
        return webClient.get()
                .uri("/" + id)
                .retrieve()
                .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(),
                        response -> Mono.error(new RuntimeException("Error al obtener el Pokémon")))
                .bodyToMono(Pokemon.class);
    }
    public Mono<Pokemon> getPokemonByName(String nombre){
        return webClient.get()
                .uri("/" + nombre)
                .retrieve()
                .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(),
                        response -> Mono.error(new RuntimeException("Error al obtener el Pokémon")))
                .bodyToMono(Pokemon.class);
    }

}
