package com.ucenfotec.pokemonyosh.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ucenfotec.pokemonyosh.model.*;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class GimnasioService {

    private final HttpClient httpClient;
    private final ObjectMapper objectMapper = new ObjectMapper();

    private static final String BASE_URL = "http://ec2-3-18-23-121.us-east-2.compute.amazonaws.com:8080"; // Replace with your actual base URL


    public GimnasioService(){
        this.httpClient = HttpClient.newBuilder().build();
    }
    private Pokemon pokemonPlayer1 = new Pokemon("bulbasor", PokemonTypeEnum.NORMAL,1000, List.of());
    private PlayerInformation player1 = new PlayerInformation("player1", pokemonPlayer1);
    private Pokemon pokemonPlayer2 = new Pokemon("bulbasor2", PokemonTypeEnum.NORMAL,1000, List.of());
    private PlayerInformation player2 = new PlayerInformation("player2", pokemonPlayer1);
    private Pokemon pokemonPlayer3 = new Pokemon("bulbasor3", PokemonTypeEnum.NORMAL,1000, List.of());
    private PlayerInformation player3 = new PlayerInformation("player3", pokemonPlayer1);
    private BatallaResponse batallaResponse = new BatallaResponse(1L, "EN_BATALLA",
            List.of(player1, player2, player3));

    public String atacarPokemon(String playerId, int attackPower){
        boolean foundPlayer = this.batallaResponse.getPlayerInformationList().stream().anyMatch(playerInformation -> {
            if(playerInformation.getPlayerName().equals(playerId)) {
                if (playerInformation.getPokemon().getVida() <= 0) {
                    Integer vida = playerInformation.getPokemon().getVida();
                    Integer nuevaVida = vida - attackPower;
                    playerInformation.getPokemon().setVida(nuevaVida);
                    return true;
                }
            }
            return false;
        });
        return foundPlayer ? "Pokemon Attack send successfully" : "Pokemon or Attack not valid.";
    }

    public BatallaResponse obtenerBatallaInformation() {
        BatallaResponse batallaResponseFromServer = new BatallaResponse();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/api/gimnasioPokemon/obtener-informacion-batalla"))
                .GET()
                .build();
        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(response.body());
            batallaResponseFromServer = objectMapper.readValue(response.body(), new TypeReference<>() {});

        } catch (Exception e) {
            e.printStackTrace();
        }
        return batallaResponseFromServer;
    }
}
