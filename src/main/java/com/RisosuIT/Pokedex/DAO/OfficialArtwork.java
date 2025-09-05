package com.RisosuIT.Pokedex.DAO;

public class OfficialArtwork {

    public String front_default;
    public String front_shiny;

    public OfficialArtwork() {
    }

    public OfficialArtwork(String front_defaul, String front_shiny) {
        this.front_default = front_defaul;
        this.front_shiny = front_shiny;
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

}
