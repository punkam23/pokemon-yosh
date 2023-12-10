package com.ucenfotec.pokemonyosh.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Setter
@Getter
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
