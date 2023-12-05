package com.ucenfotec.pokemonyosh.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class AttackInformationDTO {
    @JsonProperty("attackId")
    Integer attackId;
    @JsonProperty("targetPlayerName")
    String targetPlayerName;
    @JsonProperty("sourcePlayerName")
    String sourcePlayerName;
}
