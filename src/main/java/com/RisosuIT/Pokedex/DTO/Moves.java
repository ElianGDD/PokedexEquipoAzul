package com.RisosuIT.Pokedex.DTO;

import java.util.List;

public class Moves {

    private Move move;
    private List<Version_Group_Details> version_Group_Details;

    public Moves() {
    }

    public Moves(Move move, List<Version_Group_Details> version_Group_Detailses) {
        this.move = move;
        this.version_Group_Details = version_Group_Detailses;
    }

    public Move getMove() {
        return move;
    }

    public void setMove(Move move) {
        this.move = move;
    }

    public List<Version_Group_Details> getVersion_Group_Details() {
        return version_Group_Details;
    }

    public void setVersion_Group_Details(List<Version_Group_Details> version_Group_Details) {
        this.version_Group_Details = version_Group_Details;
    }

}
