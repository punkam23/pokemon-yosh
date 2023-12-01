package com.ucenfotec.pokemonyosh.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PlayerInformation {
    @JsonProperty("playerName")
    String playerName;

    @JsonProperty("pokemon")
    Pokemon pokemon;
}
