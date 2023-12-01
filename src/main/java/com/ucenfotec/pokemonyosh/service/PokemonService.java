package com.ucenfotec.pokemonyosh.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ucenfotec.pokemonyosh.model.PlayerInformation;
import com.ucenfotec.pokemonyosh.model.Pokemon;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Service
public class PokemonService {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final String playerInformationFilePath = "../playerInformation.json";

    public String iniciarPokemon(PlayerInformation playerInformation) {
        try {
            // Escribir el JSON en el archivo
            objectMapper.writeValue(new File(playerInformationFilePath), playerInformation);

            return "El jugador ha sido registrado para la batalla";
        } catch (IOException e) {
            e.printStackTrace();
            return "Hubo un error al intentar registrar el jugador";
        }
    }

    public String unirseBatalla() {
        try {
            PlayerInformation pokemonFromFile = objectMapper.readValue(new File(playerInformationFilePath), PlayerInformation.class);
            //llamar al gimnasio para unirse a la batalla.
            return "Se ha unido exitosamente a la batalla";
        } catch (IOException e) {
            e.printStackTrace();
            return "Hubo un error al unirse a la batalla";
        }
    }
}
