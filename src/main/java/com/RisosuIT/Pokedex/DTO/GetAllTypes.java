package com.RisosuIT.Pokedex.DTO;

import java.util.List;

public class GetAllTypes {

    private int count;
    private String next;
    private String previous;
    private List<NamedAPIResource> results;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }

    public String getPrevious() {
        return previous;
    }

    public void setPrevious(String previous) {
        this.previous = previous;
    }

    public List<NamedAPIResource> getResults() {
        return results;
    }

    public void setResults(List<NamedAPIResource> results) {
        this.results = results;
    }
    
    
}
