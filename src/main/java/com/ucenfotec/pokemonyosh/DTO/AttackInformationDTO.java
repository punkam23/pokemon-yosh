package com.ucenfotec.pokemonyosh.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AttackInformationDTO {
    @JsonProperty("attackId")
    Integer attackId;
    @JsonProperty("targetPlayerName")
    String targetPlayerName;
    @JsonProperty("sourcePlayerName")
    String sourcePlayerName;
}
