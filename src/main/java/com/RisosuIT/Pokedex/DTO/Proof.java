package com.RisosuIT.Pokedex.DTO;

import java.util.List;


public class Proof<T> {
    public boolean correct;
    public String errorMessage;
    public Exception ex;
    public T object;
    public List<T> objects;

}
