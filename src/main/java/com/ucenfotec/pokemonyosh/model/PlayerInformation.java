package com.ucenfotec.pokemonyosh.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PlayerInformation {
    @JsonProperty("playerName")
    String playerName;

    @JsonProperty("pokemon")
    Pokemon pokemon;
}
