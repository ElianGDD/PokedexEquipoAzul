package com.RisosuIT.Pokedex.Service;

import com.RisosuIT.Pokedex.DAO.GetAllTypes;
import com.RisosuIT.Pokedex.DAO.NamedAPIResource;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import jakarta.annotation.Nonnull;
import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service responsable de operaciones relacionadas con "types" (tipos de
 * Pokémon). - cachea la lista global de tipos - permite obtener la intersección
 * entre pokémones de dos tipos (reactivo y sincrono) - ofrece una versión N-way
 * (varios tipos)
 *
 * Nota: expone DTOs mínimos {@link GetTypeResponse} y {@link TypePokemonSlot}
 * que modelan la respuesta de /api/v2/type/{name} (campo "pokemon").
 */
@Service
public class ServiceType {

    private static final String CACHE_KEY_ALL_TYPES = "all-types";

    private final WebClient typeWebClient;
    private final Cache<String, GetAllTypes> cache;

    public ServiceType(@Qualifier("typeWebClient") WebClient typeWebClient) {
        this.typeWebClient = typeWebClient;
        this.cache = Caffeine.newBuilder()
                .maximumSize(4)
                .expireAfterWrite(Duration.ofMinutes(30))
                .build();
    }

    /**
     * Reactive fetch of all types (no cache).
     */
    public Mono<GetAllTypes> fetchAllTypesReactive() {
        return typeWebClient.get()
                .uri("/type")
                .retrieve()
                .onStatus(s -> s.is4xxClientError() || s.is5xxServerError(),
                        resp -> Mono.error(new RuntimeException("Error fetching types: " + resp.statusCode())))
                .bodyToMono(GetAllTypes.class);
    }

    /**
     * Cached blocking fetch (convenience).
     */
    public GetAllTypes fetchAllTypesCached() {
        return cache.get(CACHE_KEY_ALL_TYPES, k -> fetchAllTypesReactive().block());
    }

    public void invalidateTypesCache() {
        cache.invalidate(CACHE_KEY_ALL_TYPES);
    }

    /* ---------------------------
       Reactive: intersection of TWO types
       --------------------------- */
    public Mono<List<NamedAPIResource>> getPokemonsByTwoTypesReactive(@Nonnull String typeA, @Nonnull String typeB) {
        Mono<GetTypeResponse> monoA = fetchTypeDetailReactive(typeA);
        Mono<GetTypeResponse> monoB = fetchTypeDetailReactive(typeB);

        return Mono.zip(monoA, monoB)
                .map(tuple -> {
                    List<TypePokemonSlot> listA = Optional.ofNullable(tuple.getT1()).map(GetTypeResponse::getPokemon).orElse(Collections.emptyList());
                    List<TypePokemonSlot> listB = Optional.ofNullable(tuple.getT2()).map(GetTypeResponse::getPokemon).orElse(Collections.emptyList());

                    Set<String> namesA = listA.stream()
                            .map(slot -> slot.getPokemon().getName())
                            .filter(Objects::nonNull)
                            .collect(Collectors.toSet());

                    return listB.stream()
                            .map(TypePokemonSlot::getPokemon)
                            .filter(p -> p != null && namesA.contains(p.getName()))
                            .distinct()
                            .collect(Collectors.toList());
                });
    }

    /* ---------------------------
       Blocking convenience: intersection of TWO types
       --------------------------- */
    public List<NamedAPIResource> getPokemonsByTwoTypesBlocking(@Nonnull String typeA, @Nonnull String typeB) {
        GetTypeResponse a = fetchTypeDetailReactive(typeA).block();
        GetTypeResponse b = fetchTypeDetailReactive(typeB).block();

        if (a == null || b == null) {
            return Collections.emptyList();
        }

        Set<String> namesA = a.getPokemon().stream()
                .map(slot -> slot.getPokemon().getName())
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        return b.getPokemon().stream()
                .map(TypePokemonSlot::getPokemon)
                .filter(p -> p != null && namesA.contains(p.getName()))
                .distinct()
                .collect(Collectors.toList());
    }

    /* ---------------------------
       Reactive: intersection for N types (returns pokémon que están en *todos* los tipos)
       --------------------------- */
    public Mono<List<NamedAPIResource>> getPokemonsByTypesReactive(@Nonnull List<String> types) {
        if (types == null || types.isEmpty()) {
            return Mono.just(Collections.emptyList());
        }

        // fetch each type detail in parallel
        Flux<GetTypeResponse> flux = Flux.fromIterable(types)
                .flatMap(this::fetchTypeDetailReactive);

        return flux.collectList()
                .map(list -> {
                    if (list.isEmpty()) {
                        return Collections.emptyList();
                    }

                    // map name -> NamedAPIResource (first occurrence)
                    Map<String, NamedAPIResource> nameToResource = new HashMap<>();
                    Map<String, Integer> count = new HashMap<>();
                    int needed = list.size();

                    for (GetTypeResponse tr : list) {
                        // use a set to avoid counting duplicates inside same type
                        Set<String> seenThisType = new HashSet<>();
                        for (TypePokemonSlot slot : Optional.ofNullable(tr.getPokemon()).orElse(Collections.emptyList())) {
                            NamedAPIResource p = slot.getPokemon();
                            if (p == null || p.getName() == null) {
                                continue;
                            }
                            String name = p.getName();
                            seenThisType.add(name);
                            nameToResource.putIfAbsent(name, p);
                        }
                        for (String name : seenThisType) {
                            count.merge(name, 1, Integer::sum);
                        }
                    }

                    return count.entrySet().stream()
                            .filter(e -> e.getValue() == needed)
                            .map(e -> nameToResource.get(e.getKey()))
                            .filter(Objects::nonNull)
                            .collect(Collectors.toList());
                });
    }

    /* ---------------------------
       Helper: fetch type detail (reactive)
       maps response of /api/v2/type/{name}
       --------------------------- */
    private Mono<GetTypeResponse> fetchTypeDetailReactive(String typeName) {
        if (typeName == null || typeName.isBlank()) {
            return Mono.just(new GetTypeResponse());
        }
        return typeWebClient.get()
                .uri("/type/{t}", typeName)
                .retrieve()
                .onStatus(s -> s.is4xxClientError() || s.is5xxServerError(),
                        resp -> Mono.error(new RuntimeException("Error fetching type '" + typeName + "': " + resp.statusCode())))
                .bodyToMono(GetTypeResponse.class)
                .onErrorResume(ex -> {
                    // si un tipo no existe o hay error, devolvemos un objeto vacío en lugar de fallar toda la operación
                    return Mono.just(new GetTypeResponse());
                });
    }

    /* ---------------------------
       Minimal DTOs que modelan la respuesta de /api/v2/type/{name}
       Solo defino los campos que necesitamos: "pokemon" -> lista de slots con { pokemon: { name, url } }
       --------------------------- */
    public static class GetTypeResponse {

        private List<TypePokemonSlot> pokemon = new ArrayList<>();

        public GetTypeResponse() {
        }

        public List<TypePokemonSlot> getPokemon() {
            return pokemon;
        }

        public void setPokemon(List<TypePokemonSlot> pokemon) {
            this.pokemon = pokemon;
        }
    }

    public static class TypePokemonSlot {

        private NamedAPIResource pokemon;

        public TypePokemonSlot() {
        }

        public NamedAPIResource getPokemon() {
            return pokemon;
        }

        public void setPokemon(NamedAPIResource pokemon) {
            this.pokemon = pokemon;
        }
    }
}
