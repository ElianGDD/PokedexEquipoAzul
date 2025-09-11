package com.RisosuIT.Pokedex.DAO;

public class GenerationIII {

    private Emerald emerald;
    private FireredLeafgreen fireredLeafgreen;
    private RubySapphire rubySapphire;

    public GenerationIII() {
    }

    public GenerationIII(Emerald emerald, FireredLeafgreen fireredLeafgreen, RubySapphire rubySapphire) {
        this.emerald = emerald;
        this.fireredLeafgreen = fireredLeafgreen;
        this.rubySapphire = rubySapphire;
    }

    public Emerald getEmerald() {
        return emerald;
    }

    public void setEmerald(Emerald emerald) {
        this.emerald = emerald;
    }

    public FireredLeafgreen getFireredLeafgreen() {
        return fireredLeafgreen;
    }

    public void setFireredLeafgreen(FireredLeafgreen fireredLeafgreen) {
        this.fireredLeafgreen = fireredLeafgreen;
    }

    public RubySapphire getRubySapphire() {
        return rubySapphire;
    }

    public void setRubySapphire(RubySapphire rubySapphire) {
        this.rubySapphire = rubySapphire;
    }
}
