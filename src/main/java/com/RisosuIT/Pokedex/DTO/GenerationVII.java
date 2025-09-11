package com.RisosuIT.Pokedex.DTO;

public class GenerationVII {

    private Icons icons;
    private UltraSunUltraMoon ultraSunUltraMoon;

    public GenerationVII() {
    }

    public GenerationVII(Icons icons, UltraSunUltraMoon ultraSunUltraMoon) {
        this.icons = icons;
        this.ultraSunUltraMoon = ultraSunUltraMoon;
    }

    public Icons getIcons() {
        return icons;
    }

    public void setIcons(Icons icons) {
        this.icons = icons;
    }

    public UltraSunUltraMoon getUltraSunUltraMoon() {
        return ultraSunUltraMoon;
    }

    public void setUltraSunUltraMoon(UltraSunUltraMoon ultraSunUltraMoon) {
        this.ultraSunUltraMoon = ultraSunUltraMoon;
    }
}
