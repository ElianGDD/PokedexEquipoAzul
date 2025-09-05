package com.RisosuIT.Pokedex.DAO;

public class GenerationIV {

    private DiamondPearl diamondPearl;
    private HeartgoldSoulsilver heartgoldSoulsilver;
    private Platinum platinum;

    public GenerationIV() {
    }

    public GenerationIV(DiamondPearl diamondPearl, HeartgoldSoulsilver heartgoldSoulsilver, Platinum platinum) {
        this.diamondPearl = diamondPearl;
        this.heartgoldSoulsilver = heartgoldSoulsilver;
        this.platinum = platinum;
    }

    public DiamondPearl getDiamondPearl() {
        return diamondPearl;
    }

    public void setDiamondPearl(DiamondPearl diamondPearl) {
        this.diamondPearl = diamondPearl;
    }

    public HeartgoldSoulsilver getHeartgoldSoulsilver() {
        return heartgoldSoulsilver;
    }

    public void setHeartgoldSoulsilver(HeartgoldSoulsilver heartgoldSoulsilver) {
        this.heartgoldSoulsilver = heartgoldSoulsilver;
    }

    public Platinum getPlatinum() {
        return platinum;
    }

    public void setPlatinum(Platinum platinum) {
        this.platinum = platinum;
    }
}
