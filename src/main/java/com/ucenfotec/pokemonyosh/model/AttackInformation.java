package com.ucenfotec.pokemonyosh.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AttackInformation {
    @JsonProperty("attackId")
    Integer attackId;
    @JsonProperty("playerName")
    String playerName;
}
