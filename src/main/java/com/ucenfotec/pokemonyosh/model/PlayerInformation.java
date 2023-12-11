package com.ucenfotec.pokemonyosh.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PlayerInformation {

    @NotNull
    @JsonProperty("playerName")
    String playerName;

    @JsonProperty("state")
    String state;

    @Valid
    @NotNull(message = "The Pokemon is required.")
    @JsonProperty("pokemon")
    Pokemon pokemon;
}
