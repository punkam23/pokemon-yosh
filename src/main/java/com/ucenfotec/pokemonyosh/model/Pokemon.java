package com.ucenfotec.pokemonyosh.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
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
