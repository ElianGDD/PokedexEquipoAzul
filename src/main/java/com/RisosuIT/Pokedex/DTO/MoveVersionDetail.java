package com.RisosuIT.Pokedex.DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MoveVersionDetail {

    private int level_learned_at;
    private NamedAPIResource move_learn_method;
    private Integer order;
    private NamedAPIResource version_group;

    public int getLevel_learned_at() {
        return level_learned_at;
    }

    public void setLevel_learned_at(int level_learned_at) {
        this.level_learned_at = level_learned_at;
    }

    public NamedAPIResource getMove_learn_method() {
        return move_learn_method;
    }

    public void setMove_learn_method(NamedAPIResource move_learn_method) {
        this.move_learn_method = move_learn_method;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public NamedAPIResource getVersion_group() {
        return version_group;
    }

    public void setVersion_group(NamedAPIResource version_group) {
        this.version_group = version_group;
    }

}
