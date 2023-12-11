package com.ucenfotec.pokemonyosh.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AttackInformation {
    @NotNull
    @JsonProperty("attackId")
    Integer attackId;

    @NotNull
    @JsonProperty("playerName")
    String playerName;
}
