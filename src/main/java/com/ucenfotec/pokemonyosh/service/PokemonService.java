package com.ucenfotec.pokemonyosh.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ucenfotec.pokemonyosh.DTO.ResponseDTO;
import com.ucenfotec.pokemonyosh.model.AttackInformation;
import com.ucenfotec.pokemonyosh.model.BatallaResponse;
import com.ucenfotec.pokemonyosh.model.PlayerInformation;
import com.ucenfotec.pokemonyosh.model.Pokemon;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

@Service
public class PokemonService {

    @Autowired
    public GimnasioService gimnasioService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Value("${app.player-information.file}")
    private String playerInformationFilePath;

    public ResponseDTO iniciarPokemon(PlayerInformation playerInformation) {
        ResponseDTO response = new ResponseDTO();
        try {
            if(!this.gimnasioService.obtenerBatallaInformation().isSuccess()){
                Path outputFilePath = Path.of(playerInformationFilePath);
                String objectJson = objectMapper.writeValueAsString(playerInformation);
                Files.write(outputFilePath, objectJson.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
                response.setSuccess(true);
                response.setMessage("The player has been added or modified.");
            } else {
                response.setSuccess(false);
                response.setMessage("The Battle is in progress.");
            }
            return response;
        } catch (IOException e) {
            e.printStackTrace();
            response.setSuccess(false);
            response.setMessage("Error trying to updating the player information");
        }
        return response;
    }

    public ResponseDTO unirseBatalla() {
        try {
            PlayerInformation pokemonFromFile = objectMapper.readValue(new File(playerInformationFilePath), PlayerInformation.class);
            return this.gimnasioService.unirseAGimnasio(pokemonFromFile);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    public ResponseDTO atacarPokemon(AttackInformation attackInformation) {
        PlayerInformation playerInformation;
        try {
            playerInformation = objectMapper.readValue(new File(playerInformationFilePath), PlayerInformation.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return this.gimnasioService.atacarPokemon(playerInformation.getPlayerName(), attackInformation.getPlayerName(), attackInformation.getAttackId());
    }

    public ResponseDTO obtenerInformacionBatalla() {
        return this.gimnasioService.obtenerBatallaInformation();
    }

    public ResponseDTO iniciarBatalla() {
        return this.gimnasioService.iniciarBatalla();
    }
}
