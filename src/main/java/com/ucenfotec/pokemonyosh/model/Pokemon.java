package com.ucenfotec.pokemonyosh.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class Pokemon {
    @JsonProperty("name")
    String name;

    @JsonProperty("type")
    PokemonTypeEnum type;

    @JsonProperty("vida")
    int vida;

    @JsonProperty("attacks")
    List<Attack> attacks;
}
