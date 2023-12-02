package com.ucenfotec.pokemonyosh.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class AttackInformation {
    @JsonProperty("attackId")
    Integer attackId;
    @JsonProperty("playerName")
    String playerName;
}
