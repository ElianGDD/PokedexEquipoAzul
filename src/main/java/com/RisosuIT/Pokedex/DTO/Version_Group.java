package com.RisosuIT.Pokedex.DTO;

class Version_Group {

    private String name;
    private String url;

    public Version_Group() {
    }

    public Version_Group(String name, String url) {
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
