package com.RisosuIT.Pokedex.DTO;

public class Cries {

    private String latest;
    private String legacy;

    public Cries() {
    }

    public Cries(String latest, String legacy) {
        this.latest = latest;
        this.legacy = legacy;
    }

    public String getLatest() {
        return latest;
    }

    public void setLatest(String latest) {
        this.latest = latest;
    }

    public String getLegacy() {
        return legacy;
    }

    public void setLegacy(String legacy) {
        this.legacy = legacy;
    }

}
