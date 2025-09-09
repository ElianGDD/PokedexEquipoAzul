package com.RisosuIT.Pokedex.Service;

import com.RisosuIT.Pokedex.DAO.GetAllPokemon;
import com.RisosuIT.Pokedex.DAO.GetAllTypes;
import com.RisosuIT.Pokedex.DAO.NamedAPIResource;
import com.RisosuIT.Pokedex.DAO.Pokemon;
import com.RisosuIT.Pokedex.DAO.TypeDetailDto;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import jakarta.annotation.PostConstruct;
import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class ServicePokemon {

    private final WebClient webClient;
    private final Cache<Integer, Pokemon> detailCache;
    private final ConcurrentMap<Integer, NamedAPIResource> catalog = new ConcurrentHashMap<>();
    private final ConcurrentMap<String, Set<Integer>> typeIndex = new ConcurrentHashMap<>();
    private final ConcurrentMap<String, Integer> nameToId;
    private static final Pattern ID_PATTERN = Pattern.compile(".*/(\\d+)/?$");

    public ServicePokemon(@Qualifier("pokemonWebClient") WebClient pokeWebClient) {
        this.nameToId = new ConcurrentHashMap<>();
        this.webClient = pokeWebClient;
        this.detailCache = Caffeine.newBuilder()
                .maximumSize(2000)
                .expireAfterWrite(Duration.ofMinutes(3600))
                .build();
    }

    @PostConstruct
    public void init() {
        fetchAndIndexCatalog();
        // index de tipos en background para no bloquear el arranque
        CompletableFuture.runAsync(this::buildTypeIndex);
    }

    private void fetchAndIndexCatalog() {
        GetAllPokemon getAllPokemons = webClient.get()
                .uri("/pokemon?limit=2000")
                .retrieve()
                .bodyToMono(GetAllPokemon.class)
                .block();

        if (getAllPokemons == null || getAllPokemons.getResults() == null) {
            return;
        }

        for (NamedAPIResource pokemon : getAllPokemons.getResults()) {
            Integer id = parseIdFromUrl(pokemon.getUrl());
            if (id != null) {
                catalog.put(id, pokemon);
                nameToId.put(pokemon.getName().toLowerCase(), id);
            }
        }
    }

    private void buildTypeIndex() {
        GetAllTypes types = webClient.get()
                .uri("/type")
                .retrieve()
                .bodyToMono(GetAllTypes.class)
                .block();

        if (types == null || types.getResults() == null) {
            return;
        }

        for (NamedAPIResource type : types.getResults()) {
            TypeDetailDto typeDetails = webClient.get()
                    .uri("/type/{name}", type.getName())
                    .retrieve()
                    .bodyToMono(TypeDetailDto.class)
                    .block();
            if (typeDetails == null || typeDetails.getPokemon() == null) {
                continue;
            }
            Set<Integer> ids = typeDetails.getPokemon().stream()
                    .map(pokemon -> parseIdFromUrl(pokemon.getPokemon().getUrl()))
                    .filter(Objects::nonNull)
                    .collect(Collectors.toSet());
            typeIndex.put(type.getName().toLowerCase(), ids);
        }
    }

    private Integer parseIdFromUrl(String url) {
        if (url == null) {
            return null;
        }
        Matcher matcher = ID_PATTERN.matcher(url);
        if (matcher.matches()) {
            try {
                return Integer.valueOf(matcher.group(1));
            } catch (NumberFormatException ex) {
                return null;
            }
        }
        return null;
    }

    public List<NamedAPIResource> searchCatalog(String name, String type, int page, int size) {
        Stream<Integer> idsStream = catalog.keySet().stream();

        if (type != null && !type.isBlank()) {
            Set<Integer> byType = typeIndex.getOrDefault(type.toLowerCase(), Collections.emptySet());
            idsStream = byType.stream();
        }

        if (name != null && !name.isBlank()) {
            String q = name.toLowerCase();
            idsStream = idsStream.filter(id -> {
                NamedAPIResource r = catalog.get(id);
                return r != null && r.getName().toLowerCase().contains(q);
            });
        }

        List<Integer> ids = idsStream.sorted().collect(Collectors.toList());
        int from = Math.min(page * size, ids.size());
        int to = Math.min(from + size, ids.size());

        return ids.subList(from, to).stream()
                .map(catalog::get)
                .collect(Collectors.toList());
    }

    public Pokemon getPokemonDetailById(int id) {
        return detailCache.get(id, pokemon -> webClient.get()
                .uri("/pokemon/{id}", pokemon)
                .retrieve()
                .bodyToMono(Pokemon.class)
                .block());
    }
    
}
