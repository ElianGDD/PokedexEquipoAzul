package com.RisosuIT.Pokedex.DTO;

public class Crystal {

    private String backDefault;
    private String backShiny;
    private String backShinyTransparent;
    private String backTransparent;
    private String frontDefault;
    private String frontShiny;
    private String frontShinyTransparent;
    private String frontTransparent;

    public Crystal() {
    }

    public Crystal(String backDefault, String backShiny, String backShinyTransparent, String backTransparent,
            String frontDefault, String frontShiny, String frontShinyTransparent, String frontTransparent) {
        this.backDefault = backDefault;
        this.backShiny = backShiny;
        this.backShinyTransparent = backShinyTransparent;
        this.backTransparent = backTransparent;
        this.frontDefault = frontDefault;
        this.frontShiny = frontShiny;
        this.frontShinyTransparent = frontShinyTransparent;
        this.frontTransparent = frontTransparent;
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

    public String getBackShinyTransparent() {
        return backShinyTransparent;
    }

    public void setBackShinyTransparent(String backShinyTransparent) {
        this.backShinyTransparent = backShinyTransparent;
    }

    public String getBackTransparent() {
        return backTransparent;
    }

    public void setBackTransparent(String backTransparent) {
        this.backTransparent = backTransparent;
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

    public String getFrontShinyTransparent() {
        return frontShinyTransparent;
    }

    public void setFrontShinyTransparent(String frontShinyTransparent) {
        this.frontShinyTransparent = frontShinyTransparent;
    }

    public String getFrontTransparent() {
        return frontTransparent;
    }

    public void setFrontTransparent(String frontTransparent) {
        this.frontTransparent = frontTransparent;
    }
}
