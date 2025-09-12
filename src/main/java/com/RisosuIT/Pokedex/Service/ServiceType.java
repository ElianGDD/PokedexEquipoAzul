package com.RisosuIT.Pokedex.Service;

import com.RisosuIT.Pokedex.DAO.GetAllTypes;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import java.time.Duration;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class ServiceType {

    private static final String CACHE_KEY = "all-types";

    private final WebClient webClient;
    private final Cache<String, GetAllTypes> cache;

    public ServiceType(@Qualifier("typeWebClient") WebClient webClient) {
        this.webClient = webClient;
        this.cache = Caffeine.newBuilder()
                .maximumSize(2)
                .expireAfterWrite(Duration.ofMinutes(30))
                .build();
    }

    public Mono<GetAllTypes> fetchAllTypesReactive() {
        return webClient.get()
                .uri("/type")
                .retrieve()
                .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(),
                        resp -> Mono.error(new RuntimeException("Error al obtener tipos desde PokeAPI: " + resp.statusCode())))
                .bodyToMono(GetAllTypes.class);
    }

    public GetAllTypes fetchAllTypes() {
        return cache.get(CACHE_KEY, k -> fetchAllTypesReactive().block());
    }

    public void invalidateCache() {
        cache.invalidate(CACHE_KEY);
    }
}
