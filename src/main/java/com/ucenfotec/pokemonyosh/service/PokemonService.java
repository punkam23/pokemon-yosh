package com.ucenfotec.pokemonyosh.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ucenfotec.pokemonyosh.model.AttackInformation;
import com.ucenfotec.pokemonyosh.model.BatallaResponse;
import com.ucenfotec.pokemonyosh.model.PlayerInformation;
import com.ucenfotec.pokemonyosh.model.Pokemon;
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

    public String iniciarPokemon(PlayerInformation playerInformation) {
        try {
            // Escribir el JSON en el archivo

            Path outputFilePath = Path.of(playerInformationFilePath);
            String objectJson = objectMapper.writeValueAsString(playerInformation);
            Files.write(outputFilePath, objectJson.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.WRITE);

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

    public String atacarPokemon(AttackInformation attackInformation) {
        int attackPower = 0;
        try {
            PlayerInformation pokemonFromFile = objectMapper.readValue(new File(playerInformationFilePath), PlayerInformation.class);
            attackPower = pokemonFromFile.getPokemon().getAttacks().get(attackInformation.getAttackId()).getPower();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return this.gimnasioService.atacarPokemon(attackInformation.getPlayerName(), attackPower);
    }

    public BatallaResponse obtenerInformacionBatalla() {
        return this.gimnasioService.obtenerBatallaInformation();
    }
}
