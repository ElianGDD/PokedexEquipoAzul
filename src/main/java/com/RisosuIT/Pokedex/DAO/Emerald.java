package com.RisosuIT.Pokedex.DAO;

public class Emerald {

    private String frontDefault;
    private String frontShiny;

    public Emerald() {
    }

    public Emerald(String frontDefault, String frontShiny) {
        this.frontDefault = frontDefault;
        this.frontShiny = frontShiny;
    }

    public String getFrontDefault() {
        return frontDefault;
    }

    public void setFrontDefault(String frontDefault) {
        this.frontDefault = frontDefault;
    }

    public String getFrontShiny() {
        return frontShiny;
    }

    public void setFrontShiny(String frontShiny) {
        this.frontShiny = frontShiny;
    }
}
