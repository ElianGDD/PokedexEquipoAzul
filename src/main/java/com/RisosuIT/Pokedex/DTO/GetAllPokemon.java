package com.RisosuIT.Pokedex.DTO;

import java.util.List;

public class GetAllPokemon {

    private int count;
    private String next;
    private String previous;
    private List<NamedAPIResource> results;

    public GetAllPokemon() {
    }

    public GetAllPokemon(int count, String next, String previous, List<NamedAPIResource> results) {
        this.count = count;
        this.next = next;
        this.previous = previous;
        this.results = results;
    }

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
