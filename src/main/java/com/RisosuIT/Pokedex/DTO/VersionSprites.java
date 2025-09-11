package com.RisosuIT.Pokedex.DTO;

public class VersionSprites {

    private GenerationI generationI;
    private GenerationII generationII;
    private GenerationIII generationIII;
    private GenerationIV generationIV;
    private GenerationV generationV;
    private GenerationVI generationVI;
    private GenerationVII generationVII;
    private GenerationVIII generationVIII;

    public VersionSprites() {
    }

    public VersionSprites(GenerationI generationI, GenerationII generationII, GenerationIII generationIII,
            GenerationIV generationIV, GenerationV generationV, GenerationVI generationVI,
            GenerationVII generationVII, GenerationVIII generationVIII) {
        this.generationI = generationI;
        this.generationII = generationII;
        this.generationIII = generationIII;
        this.generationIV = generationIV;
        this.generationV = generationV;
        this.generationVI = generationVI;
        this.generationVII = generationVII;
        this.generationVIII = generationVIII;
    }

    public GenerationI getGenerationI() {
        return generationI;
    }

    public void setGenerationI(GenerationI generationI) {
        this.generationI = generationI;
    }

    public GenerationII getGenerationII() {
        return generationII;
    }

    public void setGenerationII(GenerationII generationII) {
        this.generationII = generationII;
    }

    public GenerationIII getGenerationIII() {
        return generationIII;
    }

    public void setGenerationIII(GenerationIII generationIII) {
        this.generationIII = generationIII;
    }

    public GenerationIV getGenerationIV() {
        return generationIV;
    }

    public void setGenerationIV(GenerationIV generationIV) {
        this.generationIV = generationIV;
    }

    public GenerationV getGenerationV() {
        return generationV;
    }

    public void setGenerationV(GenerationV generationV) {
        this.generationV = generationV;
    }

    public GenerationVI getGenerationVI() {
        return generationVI;
    }

    public void setGenerationVI(GenerationVI generationVI) {
        this.generationVI = generationVI;
    }

    public GenerationVII getGenerationVII() {
        return generationVII;
    }

    public void setGenerationVII(GenerationVII generationVII) {
        this.generationVII = generationVII;
    }

    public GenerationVIII getGenerationVIII() {
        return generationVIII;
    }

    public void setGenerationVIII(GenerationVIII generationVIII) {
        this.generationVIII = generationVIII;
    }

}
