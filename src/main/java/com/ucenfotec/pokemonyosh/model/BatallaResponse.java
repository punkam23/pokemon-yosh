package com.ucenfotec.pokemonyosh.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class BatallaResponse {

    private Long id;
    private String estadoBatalla;
    private List<PlayerInformation> playerInformationList;

}
