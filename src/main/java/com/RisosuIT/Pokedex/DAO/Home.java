package com.RisosuIT.Pokedex.DAO;

public class Home {

    public String front_default;
    public String front_shiny;
    public String front_female;
    public String front_shiny_female;

    public Home() {
    }

    public Home(
            String front_default,
            String front_shiny,
            String front_female,
            String front_shiny_female
    ) {
        this.front_default = front_default;
        this.front_shiny = front_shiny;
        this.front_female = front_female;
        this.front_shiny_female = front_shiny_female;
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

}
