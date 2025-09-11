package com.RisosuIT.Pokedex.Service;

import com.RisosuIT.Pokedex.DAO.GetAllPokemon;
import com.RisosuIT.Pokedex.DAO.GetAllTypes;
import com.RisosuIT.Pokedex.DAO.NamedAPIResource;
import com.RisosuIT.Pokedex.DAO.Pokemon;
import com.RisosuIT.Pokedex.DAO.TypeDetailDto;
import com.RisosuIT.Pokedex.State.ApplicationLoadState;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import jakarta.annotation.PostConstruct;
import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;
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
    private final Cache<Integer, Pokemon> pokemonDetailCache;
    private final ApplicationLoadState loadState;
    private final ConcurrentMap<Integer, NamedAPIResource> pokemonCatalog = new ConcurrentHashMap<>();
    private final ConcurrentMap<String, Set<Integer>> typeToPokemonIndex = new ConcurrentHashMap<>();
    private final ConcurrentMap<String, Integer> pokemonNameToId = new ConcurrentHashMap<>();
    private static final Pattern ID_PATTERN = Pattern.compile(".*/(\\d+)/?$");

    public ServicePokemon(@Qualifier("pokemonWebClient") WebClient pokeWebClient,
            ApplicationLoadState loadState) {
        this.webClient = pokeWebClient;
        this.loadState = loadState;
        this.pokemonDetailCache = Caffeine.newBuilder()
                .maximumSize(2000)
                .expireAfterWrite(Duration.ofHours(1))
                .build();
    }

    @PostConstruct
    public void init() {
        // vacío: la carga inicial la dispara LoaderController vía preloadData()
    }

    public void preloadData() {
        loadState.reset(1302);

        fetchAndIndexPokemonCatalog();
        buildTypeToPokemonIndex();

        loadState.markDone();
    }

    private void fetchAndIndexPokemonCatalog() {
        GetAllPokemon all = webClient.get()
                .uri("/pokemon?limit=2000")
                .retrieve()
                .bodyToMono(GetAllPokemon.class)
                .block();

        if (all == null || all.getResults() == null) {
            return;
        }

        for (NamedAPIResource pokemon : all.getResults()) {
            Integer id = parseIdFromUrl(pokemon.getUrl());
            if (id != null) {
                pokemonCatalog.put(id, pokemon);
                pokemonNameToId.put(pokemon.getName().toLowerCase(), id);
            }
            loadState.increment();
        }
    }

    private void buildTypeToPokemonIndex() {
        GetAllTypes types = webClient.get()
                .uri("/type")
                .retrieve()
                .bodyToMono(GetAllTypes.class)
                .block();

        if (types == null || types.getResults() == null) {
            return;
        }

        for (NamedAPIResource type : types.getResults()) {
            TypeDetailDto details = webClient.get()
                    .uri("/type/{name}", type.getName())
                    .retrieve()
                    .bodyToMono(TypeDetailDto.class)
                    .block();

            if (details == null || details.getPokemon() == null) {
                continue;
            }

            Set<Integer> ids = details.getPokemon().stream()
                    .map(p -> parseIdFromUrl(p.getPokemon().getUrl()))
                    .filter(Objects::nonNull)
                    .collect(Collectors.toSet());

            typeToPokemonIndex.put(type.getName().toLowerCase(), ids);
            loadState.increment();
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
            } catch (NumberFormatException ignored) {
                return null;
            }
        }
        return null;
    }

    public List<NamedAPIResource> searchPokemonCatalog(String name, String type, int page, int size) {
        Stream<Integer> idStream = pokemonCatalog.keySet().stream();

        if (type != null && !type.isBlank()) {
            Set<Integer> byType = typeToPokemonIndex.getOrDefault(type.toLowerCase(), Collections.emptySet());
            idStream = byType.stream();
        }

        if (name != null && !name.isBlank()) {
            String query = name.toLowerCase();
            idStream = idStream.filter(id -> {
                NamedAPIResource resource = pokemonCatalog.get(id);
                return resource != null && resource.getName().toLowerCase().contains(query);
            });
        }

        List<Integer> ids = idStream.sorted().toList();
        int from = Math.min(page * size, ids.size());
        int to = Math.min(from + size, ids.size());

        return ids.subList(from, to).stream()
                .map(pokemonCatalog::get)
                .toList();
    }

    public Pokemon getPokemonDetailById(int id) {
        return pokemonDetailCache.get(id, key -> webClient.get()
                .uri("/pokemon/{id}", key)
                .retrieve()
                .bodyToMono(Pokemon.class)
                .block());
    }
}
