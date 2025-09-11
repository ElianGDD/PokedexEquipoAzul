package com.RisosuIT.Pokedex.DAO;

import java.util.List;

public class Pokemon {

    private int id;
    private List<Abilitie> abilities;
    private int base_experience;
    private Cries cries;
    private List<Forms> forms;
    private List<Game_Indice> game_indices;
    private int height;
    private boolean is_default;
    private List<Moves> moves;
    private String name;
    private int order;
    private List<Past_Abilities> past_abilities;
    private Specie species;
    private Sprite sprites;
    private List<Stats> stats;
    private List<Types> types;

    public Pokemon() {
    }

    public Pokemon(int id, List<Abilitie> abilities, int base_experience, Cries cries,
            List<Forms> forms, List<Game_Indice> game_indices, int height, boolean is_default,
            List<Moves> moves, String name, int order, List<Past_Abilities> past_abilities,
            Specie species, Sprite sprites, List<Stats> stats, List<Types> types) {
        this.id = id;
        this.abilities = abilities;
        this.base_experience = base_experience;
        this.cries = cries;
        this.forms = forms;
        this.game_indices = game_indices;
        this.height = height;
        this.is_default = is_default;
        this.moves = moves;
        this.name = name;
        this.order = order;
        this.past_abilities = past_abilities;
        this.species = species;
        this.sprites = sprites;
        this.stats = stats;
        this.types = types;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Abilitie> getAbilities() {
        return abilities;
    }

    public void setAbilities(List<Abilitie> abilities) {
        this.abilities = abilities;
    }

    public int getBase_experience() {
        return base_experience;
    }

    public void setBase_experience(int base_experience) {
        this.base_experience = base_experience;
    }

    public Cries getCries() {
        return cries;
    }

    public void setCries(Cries cries) {
        this.cries = cries;
    }

    public List<Forms> getForms() {
        return forms;
    }

    public void setForms(List<Forms> forms) {
        this.forms = forms;
    }

    public List<Game_Indice> getGame_indices() {
        return game_indices;
    }

    public void setGame_indices(List<Game_Indice> game_indices) {
        this.game_indices = game_indices;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public boolean isIs_default() {
        return is_default;
    }

    public void setIs_default(boolean is_default) {
        this.is_default = is_default;
    }

    public List<Moves> getMoves() {
        return moves;
    }

    public void setMoves(List<Moves> moves) {
        this.moves = moves;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public List<Past_Abilities> getPast_abilities() {
        return past_abilities;
    }

    public void setPast_abilities(List<Past_Abilities> past_abilities) {
        this.past_abilities = past_abilities;
    }

    public Specie getSpecies() {
        return species;
    }

    public void setSpecies(Specie species) {
        this.species = species;
    }

    public Sprite getSprites() {
        return sprites;
    }

    public void setSprites(Sprite sprites) {
        this.sprites = sprites;
    }

    public List<Stats> getStats() {
        return stats;
    }

    public void setStats(List<Stats> stats) {
        this.stats = stats;
    }

    public List<Types> getTypes() {
        return types;
    }

    public void setTypes(List<Types> types) {
        this.types = types;
    }

}
