package com.RisosuIT.Pokedex.Service;

import com.RisosuIT.Pokedex.DAO.GetAllPokemon;
import com.RisosuIT.Pokedex.DAO.GetAllTypes;
import com.RisosuIT.Pokedex.DAO.Type;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class ServiceType {
    private WebClient webClient;
    
    public ServiceType(@Qualifier("typeClient") WebClient webClient){
        this.webClient = webClient;
    }
    
    public Mono<GetAllTypes> GetAllTypes(){
        return webClient.get()
                .retrieve()
                .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(),
                        response -> Mono.error(new RuntimeException("Error al obtener el Pokémon")))
                .bodyToMono(GetAllTypes.class);
    }

}
