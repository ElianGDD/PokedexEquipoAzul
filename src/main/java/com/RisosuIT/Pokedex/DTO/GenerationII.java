package com.RisosuIT.Pokedex.DTO;

public class GenerationII {

    private Crystal crystal;
    private Gold gold;
    private Silver silver;

    public GenerationII() {
    }

    public GenerationII(Crystal crystal, Gold gold, Silver silver) {
        this.crystal = crystal;
        this.gold = gold;
        this.silver = silver;
    }

    public Crystal getCrystal() {
        return crystal;
    }

    public void setCrystal(Crystal crystal) {
        this.crystal = crystal;
    }

    public Gold getGold() {
        return gold;
    }

    public void setGold(Gold gold) {
        this.gold = gold;
    }

    public Silver getSilver() {
        return silver;
    }

    public void setSilver(Silver silver) {
        this.silver = silver;
    }
}
