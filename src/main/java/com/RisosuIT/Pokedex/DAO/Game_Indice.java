package com.RisosuIT.Pokedex.DAO;

public class Game_Indice {

    private int game_index;
    private Version version;

    public Game_Indice() {
    }

    public Game_Indice(int game_index, Version version) {
        this.game_index = game_index;
        this.version = version;
    }

    public int getGame_index() {
        return game_index;
    }

    public void setGame_index(int game_index) {
        this.game_index = game_index;
    }

    public Version getVersion() {
        return version;
    }

    public void setVersion(Version version) {
        this.version = version;
    }

}
