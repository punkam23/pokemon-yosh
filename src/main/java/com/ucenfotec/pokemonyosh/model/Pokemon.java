package com.ucenfotec.pokemonyosh.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Pokemon {
    @JsonProperty("name")
    String name;

    @JsonProperty("type")
    PokemonTypeEnum type;

    @JsonProperty("life")
    double life;

    @JsonProperty("attacks")
    List<Attack> attacks;
}
