package com.RisosuIT.Pokedex.DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Sprite {

    private String front_default;
    private String back_default;
    private String front_shiny;
    private String back_shiny;
    private SpriteOther other;
    private SpriteVersions versions;

    public String getFront_default() {
        return front_default;
    }

    public void setFront_default(String front_default) {
        this.front_default = front_default;
    }

    public String getBack_default() {
        return back_default;
    }

    public void setBack_default(String back_default) {
        this.back_default = back_default;
    }

    public String getFront_shiny() {
        return front_shiny;
    }

    public void setFront_shiny(String front_shiny) {
        this.front_shiny = front_shiny;
    }

    public String getBack_shiny() {
        return back_shiny;
    }

    public void setBack_shiny(String back_shiny) {
        this.back_shiny = back_shiny;
    }

    public SpriteOther getOther() {
        return other;
    }

    public void setOther(SpriteOther other) {
        this.other = other;
    }

    public SpriteVersions getVersions() {
        return versions;
    }

    public void setVersions(SpriteVersions versions) {
        this.versions = versions;
    }

}
