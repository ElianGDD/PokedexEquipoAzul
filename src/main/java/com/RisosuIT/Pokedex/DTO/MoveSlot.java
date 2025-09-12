package com.RisosuIT.Pokedex.DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MoveSlot {

    private NamedAPIResource move;
    private List<MoveVersionDetail> version_group_details;

    public NamedAPIResource getMove() {
        return move;
    }

    public void setMove(NamedAPIResource move) {
        this.move = move;
    }

    public List<MoveVersionDetail> getVersion_group_details() {
        return version_group_details;
    }

    public void setVersion_group_details(List<MoveVersionDetail> version_group_details) {
        this.version_group_details = version_group_details;
    }

    
    
}
