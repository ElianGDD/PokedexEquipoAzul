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

@Service
public class ServiceType {

    private static final String CACHE_KEY_ALL_TYPES = "all-types";

    private final WebClient typeWebClient;
    private final Cache<String, GetAllTypes> cache;

    public ServiceType(@Qualifier("typeWebClient") WebClient typeWebClient) {
        this.typeWebClient = typeWebClient;
        this.cache = Caffeine.newBuilder()
                .maximumSize(4)
                .expireAfterWrite(Duration.ofHours(1)) // mismo estándar que ServicePokemon
                .build();
    }

    public GetAllTypes fetchAllTypesCached() {
        return cache.get(CACHE_KEY_ALL_TYPES, key -> typeWebClient.get()
                .uri("/type")
                .retrieve()
                .bodyToMono(GetAllTypes.class)
                .block());
    }

    public void invalidateTypesCache() {
        cache.invalidate(CACHE_KEY_ALL_TYPES);
    }

    public List<NamedAPIResource> getPokemonsByTwoTypes(@Nonnull String typeA, @Nonnull String typeB) {
        TypeDetailResponse detailA = fetchTypeDetailBlocking(typeA);
        TypeDetailResponse detailB = fetchTypeDetailBlocking(typeB);

        if (detailA == null || detailB == null) {
            return Collections.emptyList();
        }

        Set<String> namesA = detailA.getPokemon().stream()
                .map(slot -> slot.getPokemon().getName())
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        return detailB.getPokemon().stream()
                .map(TypePokemonEntry::getPokemon)
                .filter(p -> p != null && namesA.contains(p.getName()))
                .distinct()
                .toList();
    }

    public List<NamedAPIResource> getPokemonsByMultipleTypes(@Nonnull List<String> types) {
        if (types == null || types.isEmpty()) {
            return Collections.emptyList();
        }

        List<TypeDetailResponse> details = types.stream()
                .map(this::fetchTypeDetailBlocking)
                .filter(Objects::nonNull)
                .toList();

        if (details.isEmpty()) {
            return Collections.emptyList();
        }

        Map<String, NamedAPIResource> nameToResource = new HashMap<>();
        Map<String, Integer> count = new HashMap<>();
        int required = details.size();

        for (TypeDetailResponse response : details) {
            Set<String> seen = new HashSet<>();
            for (TypePokemonEntry slot : Optional.ofNullable(response.getPokemon()).orElse(Collections.emptyList())) {
                NamedAPIResource p = slot.getPokemon();
                if (p == null || p.getName() == null) {
                    continue;
                }
                seen.add(p.getName());
                nameToResource.putIfAbsent(p.getName(), p);
            }
            for (String name : seen) {
                count.merge(name, 1, Integer::sum);
            }
        }

        return count.entrySet().stream()
                .filter(e -> e.getValue() == required)
                .map(e -> nameToResource.get(e.getKey()))
                .filter(Objects::nonNull)
                .toList();
    }

    private TypeDetailResponse fetchTypeDetailBlocking(String typeName) {
        if (typeName == null || typeName.isBlank()) {
            return null;
        }
        return typeWebClient.get()
                .uri("/type/{t}", typeName)
                .retrieve()
                .bodyToMono(TypeDetailResponse.class)
                .onErrorReturn(new TypeDetailResponse()) // fallback vacío
                .block();
    }

    // DTOs internos
    public static class TypeDetailResponse {

        private List<TypePokemonEntry> pokemon = new ArrayList<>();

        public List<TypePokemonEntry> getPokemon() {
            return pokemon;
        }

        public void setPokemon(List<TypePokemonEntry> pokemon) {
            this.pokemon = pokemon;
        }
    }

    public static class TypePokemonEntry {

        private NamedAPIResource pokemon;

        public NamedAPIResource getPokemon() {
            return pokemon;
        }

        public void setPokemon(NamedAPIResource pokemon) {
            this.pokemon = pokemon;
        }
    }
}
