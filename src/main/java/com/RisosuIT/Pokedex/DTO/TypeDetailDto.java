package com.RisosuIT.Pokedex.DTO;

import java.util.List;

public class TypeDetailDto {

    private Integer id;
    private String name;
    private List<TypePokemonEntryDto> pokemon;

    public TypeDetailDto() {
    }

    public TypeDetailDto(Integer id, String name, List<TypePokemonEntryDto> pokemon) {
        this.id = id;
        this.name = name;
        this.pokemon = pokemon;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<TypePokemonEntryDto> getPokemon() {
        return pokemon;
    }

    public void setPokemon(List<TypePokemonEntryDto> pokemon) {
        this.pokemon = pokemon;
    }

    // entry para cada elemento en "pokemon" del API
    public static class TypePokemonEntryDto {

        private NamedAPIResource pokemon;
        private Integer slot; // en la API puede o no venir; lo incluimos por completitud

        public TypePokemonEntryDto() {
        }

        public TypePokemonEntryDto(NamedAPIResource pokemon, Integer slot) {
            this.pokemon = pokemon;
            this.slot = slot;
        }

        public NamedAPIResource getPokemon() {
            return pokemon;
        }

        public void setPokemon(NamedAPIResource pokemon) {
            this.pokemon = pokemon;
        }

        public Integer getSlot() {
            return slot;
        }

        public void setSlot(Integer slot) {
            this.slot = slot;
        }
    }
}
