package com.RisosuIT.Pokedex.DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SpriteOther {

    private OfficialArtwork official_artwork;

    public OfficialArtwork getOfficial_artwork() {
        return official_artwork;
    }

    public void setOfficial_artwork(OfficialArtwork official_artwork) {
        this.official_artwork = official_artwork;
    }

}
