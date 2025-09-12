package com.RisosuIT.Pokedex.DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class HeldItem {
    private NamedAPIResource item;
    private List<HeldItemVersionDetail> version_details;

    public NamedAPIResource getItem() {
        return item;
    }

    public void setItem(NamedAPIResource item) {
        this.item = item;
    }

    public List<HeldItemVersionDetail> getVersion_details() {
        return version_details;
    }

    public void setVersion_details(List<HeldItemVersionDetail> version_details) {
        this.version_details = version_details;
    }
   
    
    
}


