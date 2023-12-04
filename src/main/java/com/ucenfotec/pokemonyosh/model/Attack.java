package com.ucenfotec.pokemonyosh.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Attack {


    @JsonProperty("type")
    PokemonTypeEnum type;

    @JsonProperty("power")
    int power;
}
