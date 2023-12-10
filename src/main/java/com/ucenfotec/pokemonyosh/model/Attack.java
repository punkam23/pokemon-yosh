package com.ucenfotec.pokemonyosh.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Attack {


    @JsonProperty("type")
    PokemonTypeEnum type;

    @JsonProperty("power")
    int power;
}
