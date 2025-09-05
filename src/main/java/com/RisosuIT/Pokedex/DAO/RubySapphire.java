package com.RisosuIT.Pokedex.DAO;

public class RubySapphire {

    private String backDefault;
    private String backShiny;
    private String frontDefault;
    private String frontShiny;

    public RubySapphire() {
    }

    public RubySapphire(String backDefault, String backShiny, String frontDefault, String frontShiny) {
        this.backDefault = backDefault;
        this.backShiny = backShiny;
        this.frontDefault = frontDefault;
        this.frontShiny = frontShiny;
    }

    public String getBackDefault() {
        return backDefault;
    }

    public void setBackDefault(String backDefault) {
        this.backDefault = backDefault;
    }

    public String getBackShiny() {
        return backShiny;
    }

    public void setBackShiny(String backShiny) {
        this.backShiny = backShiny;
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
