package com.RisosuIT.Pokedex.DAO;

public class Abilities {

    private String ability;
    private boolean is_hidden;
    private int slot;

    public Abilities() {
    }

    public Abilities(String ability, boolean is_hidden, int slot) {
        this.ability = ability;
        this.is_hidden = is_hidden;
        this.slot = slot;
    }

    public String getAbility() {
        return ability;
    }

    public void setAbility(String ability) {
        this.ability = ability;
    }

    public boolean isIs_hidden() {
        return is_hidden;
    }

    public void setIs_hidden(boolean is_hidden) {
        this.is_hidden = is_hidden;
    }

    public int getSlot() {
        return slot;
    }

    public void setSlot(int slot) {
        this.slot = slot;
    }

}
