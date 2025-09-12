package com.RisosuIT.Pokedex.DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BlackWhite {

    private Animated animated;

    public Animated getAnimated() {
        return animated;
    }

    public void setAnimated(Animated animated) {
        this.animated = animated;
    }

}
