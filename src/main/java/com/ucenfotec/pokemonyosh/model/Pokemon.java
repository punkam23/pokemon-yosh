package com.ucenfotec.pokemonyosh.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Pokemon {

    @NotNull
    @JsonProperty("name")
    String name;

    @NotNull
    @JsonProperty("type")
    PokemonTypeEnum type;

    @NotNull
    @Min(value = 100, message = "The life must be greater than 100")
    @JsonProperty("life")
    double life;

    @NotNull
    @NotEmpty
    @JsonProperty("attacks")
    List<Attack> attacks;
}
