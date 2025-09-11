package com.RisosuIT.Pokedex.DAO;

import java.util.List;

public class Past_Abilities {

    private List<Abilities> abilities;
    private Generation generation;

    public Past_Abilities() {
    }

    public Past_Abilities(List<Abilities> abilities, Generation generation) {
        this.abilities = abilities;
        this.generation = generation;
    }

    public List<Abilities> getAbilities() {
        return abilities;
    }

    public void setAbilities(List<Abilities> abilities) {
        this.abilities = abilities;
    }

    public Generation getGeneration() {
        return generation;
    }

    public void setGeneration(Generation generation) {
        this.generation = generation;
    }

}
