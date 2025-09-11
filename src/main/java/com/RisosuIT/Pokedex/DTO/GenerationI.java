package com.RisosuIT.Pokedex.DTO;

public class GenerationI {

    private RedBlue redBlue;
    private Yellow yellow;

    public GenerationI() {
    }

    public GenerationI(RedBlue redBlue, Yellow yellow) {
        this.redBlue = redBlue;
        this.yellow = yellow;
    }

    public RedBlue getRedBlue() {
        return redBlue;
    }

    public void setRedBlue(RedBlue redBlue) {
        this.redBlue = redBlue;
    }

    public Yellow getYellow() {
        return yellow;
    }

    public void setYellow(Yellow yellow) {
        this.yellow = yellow;
    }

}
