package com.RisosuIT.Pokedex.DAO;

public class Move {

    private String name;
    private String url;

    public Move() {
    }

    public Move(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
