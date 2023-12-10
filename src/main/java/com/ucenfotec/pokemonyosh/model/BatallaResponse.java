package com.ucenfotec.pokemonyosh.model;

import lombok.*;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BatallaResponse {
    private Long id;
    private String state;
    private List<PlayerInformation> playerInformationList;

}
