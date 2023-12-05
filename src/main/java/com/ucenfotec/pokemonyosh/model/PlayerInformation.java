package com.ucenfotec.pokemonyosh.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlayerInformation {
    @JsonProperty("playerName")
    String playerName;

    @JsonProperty("state")
    String state;

    @JsonProperty("pokemon")
    Pokemon pokemon;
}
