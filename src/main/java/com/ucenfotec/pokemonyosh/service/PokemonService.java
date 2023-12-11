package com.ucenfotec.pokemonyosh.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ucenfotec.pokemonyosh.DTO.ResponseDTO;
import com.ucenfotec.pokemonyosh.model.AttackInformation;
import com.ucenfotec.pokemonyosh.model.BatallaResponse;
import com.ucenfotec.pokemonyosh.model.BattleStateEnum;
import com.ucenfotec.pokemonyosh.model.PlayerInformation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;

@Service
public class PokemonService {

    public GimnasioService gimnasioService;

    private final ObjectMapper objectMapper;

    @Value("${app.player-information.file}")
    private String playerInformationFilePath;

    @Autowired
    public PokemonService(GimnasioService gimnasioService, ObjectMapper objectMapper){
        this.gimnasioService = gimnasioService;
        this.objectMapper = new ObjectMapper();
    }

    public String getPlayerInformationFilePath(){
        return this.playerInformationFilePath;
    }

    public ResponseDTO iniciarPokemon(PlayerInformation playerInformation) {
        ResponseDTO response = new ResponseDTO();
        BatallaResponse batallaResponse = new BatallaResponse();
        try {
            ResponseDTO batallaResponseDTO = this.gimnasioService.obtenerBatallaInformation();
            if(batallaResponseDTO.isSuccess()){
                batallaResponse = (BatallaResponse) batallaResponseDTO.getMessage();
            }
            if(!batallaResponseDTO.isSuccess() || (batallaResponseDTO.isSuccess() &&
                    List.of(BattleStateEnum.LOBBY.name(), BattleStateEnum.TERMINADA.name()).contains(batallaResponse.getState()))
            ) {
                Path outputFilePath = Path.of(getPlayerInformationFilePath());
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
            PlayerInformation pokemonFromFile = objectMapper.readValue(new File(getPlayerInformationFilePath()), PlayerInformation.class);
            return this.gimnasioService.unirseAGimnasio(pokemonFromFile);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    public ResponseDTO atacarPokemon(AttackInformation attackInformation) {
        PlayerInformation playerInformation;
        try {
            playerInformation = objectMapper.readValue(new File(getPlayerInformationFilePath()), PlayerInformation.class);
        } catch (Exception e) {
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
