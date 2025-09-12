package com.RisosuIT.Pokedex.DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Pokemon {

    private int id;
    private String name;
    private int base_experience;
    private int height;
    private int weight;
    private boolean is_default;
    private int order;

    private List<AbilitySlot> abilities;
    private List<Form> forms;
    private List<GameIndice> game_indices;
    private List<HeldItem> held_items;
    private List<MoveSlot> moves;
    private List<StatSlot> stats;
    private List<TypeSlot> types;

    private Cries cries;
    private Sprite sprites;
    private NamedAPIResource species;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBase_experience() {
        return base_experience;
    }

    public void setBase_experience(int base_experience) {
        this.base_experience = base_experience;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public boolean isIs_default() {
        return is_default;
    }

    public void setIs_default(boolean is_default) {
        this.is_default = is_default;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public List<AbilitySlot> getAbilities() {
        return abilities;
    }

    public void setAbilities(List<AbilitySlot> abilities) {
        this.abilities = abilities;
    }

    public List<Form> getForms() {
        return forms;
    }

    public void setForms(List<Form> forms) {
        this.forms = forms;
    }

    public List<GameIndice> getGame_indices() {
        return game_indices;
    }

    public void setGame_indices(List<GameIndice> game_indices) {
        this.game_indices = game_indices;
    }

    public List<HeldItem> getHeld_items() {
        return held_items;
    }

    public void setHeld_items(List<HeldItem> held_items) {
        this.held_items = held_items;
    }

    public List<MoveSlot> getMoves() {
        return moves;
    }

    public void setMoves(List<MoveSlot> moves) {
        this.moves = moves;
    }

    public List<StatSlot> getStats() {
        return stats;
    }

    public void setStats(List<StatSlot> stats) {
        this.stats = stats;
    }

    public List<TypeSlot> getTypes() {
        return types;
    }

    public void setTypes(List<TypeSlot> types) {
        this.types = types;
    }

    public Cries getCries() {
        return cries;
    }

    public void setCries(Cries cries) {
        this.cries = cries;
    }

    public Sprite getSprites() {
        return sprites;
    }

    public void setSprites(Sprite sprites) {
        this.sprites = sprites;
    }

    public NamedAPIResource getSpecies() {
        return species;
    }

    public void setSpecies(NamedAPIResource species) {
        this.species = species;
    }

}
