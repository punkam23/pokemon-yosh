package com.ucenfotec.pokemonyosh.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Attack {


    @JsonProperty("type")
    PokemonTypeEnum type;

    @JsonProperty("power")
    int power;
}
