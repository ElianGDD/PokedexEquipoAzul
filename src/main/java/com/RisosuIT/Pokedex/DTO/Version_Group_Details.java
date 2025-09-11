package com.RisosuIT.Pokedex.DTO;

public class Version_Group_Details {

    private int level_learned_at;
    private Move_Learn_Method move_learn_method;
    private String order;
    private Version_Group version_group;

    public Version_Group_Details() {
    }

    public Version_Group_Details(int level_learned_at, Move_Learn_Method move_Learn_Method,
            String order, Version_Group version_Group) {
        this.level_learned_at = level_learned_at;
        this.move_learn_method = move_Learn_Method;
        this.order = order;
        this.version_group = version_Group;
    }

    public int getLevel_learned_at() {
        return level_learned_at;
    }

    public void setLevel_learned_at(int level_learned_at) {
        this.level_learned_at = level_learned_at;
    }

    public Move_Learn_Method getMove_learn_method() {
        return move_learn_method;
    }

    public void setMove_learn_method(Move_Learn_Method move_learn_method) {
        this.move_learn_method = move_learn_method;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public Version_Group getVersion_group() {
        return version_group;
    }

    public void setVersion_group(Version_Group version_group) {
        this.version_group = version_group;
    }

}
