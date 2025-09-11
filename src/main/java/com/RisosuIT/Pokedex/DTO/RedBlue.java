package com.RisosuIT.Pokedex.DTO;

public class RedBlue {

    private String backDefault;
    private String backGray;
    private String backTransparent;
    private String frontDefault;
    private String frontGray;
    private String frontTransparent;

    public RedBlue() {
    }

    public RedBlue(String backDefault, String backGray, String backTransparent,
            String frontDefault, String frontGray, String frontTransparent) {
        this.backDefault = backDefault;
        this.backGray = backGray;
        this.backTransparent = backTransparent;
        this.frontDefault = frontDefault;
        this.frontGray = frontGray;
        this.frontTransparent = frontTransparent;
    }

    public String getBackDefault() {
        return backDefault;
    }

    public void setBackDefault(String backDefault) {
        this.backDefault = backDefault;
    }

    public String getBackGray() {
        return backGray;
    }

    public void setBackGray(String backGray) {
        this.backGray = backGray;
    }

    public String getBackTransparent() {
        return backTransparent;
    }

    public void setBackTransparent(String backTransparent) {
        this.backTransparent = backTransparent;
    }

    public String getFrontDefault() {
        return frontDefault;
    }

    public void setFrontDefault(String frontDefault) {
        this.frontDefault = frontDefault;
    }

    public String getFrontGray() {
        return frontGray;
    }

    public void setFrontGray(String frontGray) {
        this.frontGray = frontGray;
    }

    public String getFrontTransparent() {
        return frontTransparent;
    }

    public void setFrontTransparent(String frontTransparent) {
        this.frontTransparent = frontTransparent;
    }
}
