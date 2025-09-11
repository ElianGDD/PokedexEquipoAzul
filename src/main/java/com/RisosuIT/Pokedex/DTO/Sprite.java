package com.RisosuIT.Pokedex.DTO;

public class Sprite {

    public String front_default;
    public String front_shiny;
    public String front_female;
    public String front_shiny_female;
    public String back_default;
    public String back_shiny;
    public String back_female;
    public String back_shiny_female;
    public OtherSprites other;
    public VersionSprites versions;

    public Sprite() {
    }

    public Sprite(String backDefault, String backFemale, String backShiny, String backShinyFemale,
            String frontDefault, String frontFemale, String frontShiny, String frontShinyFemale,
            OtherSprites other, VersionSprites versions) {
        this.front_default = frontDefault;
        this.front_shiny = frontShiny;
        this.front_female = frontFemale;
        this.front_shiny_female = frontShinyFemale;
        this.back_default = backDefault;
        this.back_shiny = backShiny;
        this.back_female = backFemale;
        this.back_shiny_female = backShinyFemale;
        this.other = other;
        this.versions = versions;
    }

    public String getFront_default() {
        return front_default;
    }

    public void setFront_default(String front_default) {
        this.front_default = front_default;
    }

    public String getFront_shiny() {
        return front_shiny;
    }

    public void setFront_shiny(String front_shiny) {
        this.front_shiny = front_shiny;
    }

    public String getFront_female() {
        return front_female;
    }

    public void setFront_female(String front_female) {
        this.front_female = front_female;
    }

    public String getFront_shiny_female() {
        return front_shiny_female;
    }

    public void setFront_shiny_female(String front_shiny_female) {
        this.front_shiny_female = front_shiny_female;
    }

    public String getBack_default() {
        return back_default;
    }

    public void setBack_default(String back_default) {
        this.back_default = back_default;
    }

    public String getBack_shiny() {
        return back_shiny;
    }

    public void setBack_shiny(String back_shiny) {
        this.back_shiny = back_shiny;
    }

    public String getBack_female() {
        return back_female;
    }

    public void setBack_female(String back_female) {
        this.back_female = back_female;
    }

    public String getBack_shiny_female() {
        return back_shiny_female;
    }

    public void setBack_shiny_female(String back_shiny_female) {
        this.back_shiny_female = back_shiny_female;
    }

    public OtherSprites getOther() {
        return other;
    }

    public void setOther(OtherSprites other) {
        this.other = other;
    }

    public VersionSprites getVersions() {
        return versions;
    }

    public void setVersions(VersionSprites versions) {
        this.versions = versions;
    }
}
