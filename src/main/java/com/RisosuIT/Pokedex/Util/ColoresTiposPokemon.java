package com.RisosuIT.Pokedex.Util;

import java.util.Map;

public class ColoresTiposPokemon {

    private static final Map<String, String> mapaColores = Map.ofEntries(
            Map.entry("fire", "#EE8130"),
            Map.entry("water", "#6390F0"),
            Map.entry("grass", "#7AC74C"),
            Map.entry("electric", "#F7D02C"),
            Map.entry("ice", "#96D9D6"),
            Map.entry("fighting", "#C22E28"),
            Map.entry("poison", "#A33EA1"),
            Map.entry("ground", "#E2BF65"),
            Map.entry("flying", "#A98FF3"),
            Map.entry("psychic", "#F95587"),
            Map.entry("bug", "#A6B91A"),
            Map.entry("rock", "#B6A136"),
            Map.entry("ghost", "#735797"),
            Map.entry("dragon", "#6F35FC"),
            Map.entry("dark", "#705746"),
            Map.entry("steel", "#B7B7CE"),
            Map.entry("fairy", "#D685AD"),
            Map.entry("normal", "#A8A77A")
    );

    public static String obtenerColor(String tipo) {
        return mapaColores.getOrDefault(tipo, "#777");
    }
}
