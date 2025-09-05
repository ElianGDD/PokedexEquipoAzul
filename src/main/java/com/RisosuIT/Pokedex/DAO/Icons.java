package com.RisosuIT.Pokedex.DAO;

public class Icons {

    private String frontDefault;
    private String frontFemale;

    public Icons() {
    }

    public Icons(String frontDefault, String frontFemale) {
        this.frontDefault = frontDefault;
        this.frontFemale = frontFemale;
    }

    public String getFrontDefault() {
        return frontDefault;
    }

    public void setFrontDefault(String frontDefault) {
        this.frontDefault = frontDefault;
    }

    public String getFrontFemale() {
        return frontFemale;
    }

    public void setFrontFemale(String frontFemale) {
        this.frontFemale = frontFemale;
    }
}
