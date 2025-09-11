package com.RisosuIT.Pokedex.DTO;

public class OtherSprites {

    public DreamWorld dream_world;
    public Home home;
    public OfficialArtwork official_artwork;
    public Showdown showdown;

    public OtherSprites() {
    }

    public OtherSprites(DreamWorld dream_world, Home home, OfficialArtwork official_artwork, Showdown showdown) {
        this.dream_world = dream_world;
        this.home = home;
        this.official_artwork = official_artwork;
        this.showdown = showdown;
    }

    public DreamWorld getDream_world() {
        return dream_world;
    }

    public void setDream_world(DreamWorld dream_world) {
        this.dream_world = dream_world;
    }

    public Home getHome() {
        return home;
    }

    public void setHome(Home home) {
        this.home = home;
    }

    public OfficialArtwork getOfficial_artwork() {
        return official_artwork;
    }

    public void setOfficial_artwork(OfficialArtwork official_artwork) {
        this.official_artwork = official_artwork;
    }

    public Showdown getShowdown() {
        return showdown;
    }

    public void setShowdown(Showdown showdown) {
        this.showdown = showdown;
    }

}
