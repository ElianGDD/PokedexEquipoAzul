    package com.RisosuIT.Pokedex.DTO;

import java.util.List;
import java.util.Map;

public record PokemonVistaDto(
        int id,
        String nombre,
        String spriteUrl,
        List<String> tipos,
        String colorPrimario,
        String colorSecundario,
        Map<String, String> coloresTipos) {

}
