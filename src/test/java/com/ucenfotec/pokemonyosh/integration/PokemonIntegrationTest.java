package com.ucenfotec.pokemonyosh.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ucenfotec.pokemonyosh.model.Attack;
import com.ucenfotec.pokemonyosh.model.PlayerInformation;
import com.ucenfotec.pokemonyosh.model.Pokemon;
import com.ucenfotec.pokemonyosh.model.PokemonTypeEnum;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class PokemonIntegrationTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Value("${app.player-information.file}")
    private String appTitle;

    private HttpClient httpClient = HttpClient.newHttpClient();

    private static final String BASE_URL = "http://localhost:8081"; // Replace with your actual base URL

    @Test
    public void shouldInitiatePlayerInformationTest() throws Exception {
        //given
        Attack attack1 = new Attack();
        attack1.setPower(100);
        attack1.setType(PokemonTypeEnum.NORMAL);

        Attack attack2 = new Attack();
        attack1.setPower(75);
        attack1.setType(PokemonTypeEnum.NORMAL);

        Attack attack3 = new Attack();
        attack1.setPower(50);
        attack1.setType(PokemonTypeEnum.NORMAL);
        Pokemon pokemon = new Pokemon(
                "pokemonName",
                PokemonTypeEnum.NORMAL,
                1000,
                List.of(attack1, attack2, attack3)
        );

        PlayerInformation playerInformation = new PlayerInformation(
                "playerName",
                pokemon
        );

        //when

        HttpRequest.BodyPublisher requestBodyPublisher = HttpRequest.BodyPublishers.ofString(
                objectMapper.writeValueAsString(playerInformation));

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/api/pokemon/iniciar"))
                .header("Content-Type", "application/json")
                .POST(requestBodyPublisher)
                .build();

        // Send the request and get the response
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        // then
        assertEquals(200, response.statusCode());
        assertEquals("El jugador ha sido registrado para la batalla", response.body());

    }
}
