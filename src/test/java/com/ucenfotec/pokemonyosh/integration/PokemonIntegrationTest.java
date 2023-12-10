package com.ucenfotec.pokemonyosh.integration;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ucenfotec.pokemonyosh.DTO.ResponseDTO;
import com.ucenfotec.pokemonyosh.model.*;
import com.ucenfotec.pokemonyosh.service.GimnasioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

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

    @MockBean
    public GimnasioService gimnasioService;

    @BeforeEach
    void setUp() {
        // Initialize mocks before each test method
        reset(gimnasioService);
    }

    @Test
    public void shouldInitiatePlayerInformationTest() throws Exception {
        //given
        Attack attack1 = new Attack();
        attack1.setPower(100);
        attack1.setType(PokemonTypeEnum.normal);

        Attack attack2 = new Attack();
        attack1.setPower(75);
        attack1.setType(PokemonTypeEnum.normal);

        Attack attack3 = new Attack();
        attack1.setPower(50);
        attack1.setType(PokemonTypeEnum.normal);
        Pokemon pokemon = new Pokemon(
                "pokemonName",
                PokemonTypeEnum.normal,
                1000,
                List.of(attack1, attack2, attack3)
        );

        PlayerInformation playerInformation = new PlayerInformation();
        playerInformation.setPlayerName("playerName");
        playerInformation.setPokemon(pokemon);
        Mockito.when(gimnasioService.obtenerBatallaInformation())
                .thenReturn(new ResponseDTO(false, "Batalla no Iniciado"));

        //when

        HttpRequest.BodyPublisher requestBodyPublisher = HttpRequest.BodyPublishers.ofString(
                objectMapper.writeValueAsString(playerInformation));

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/api/pokemon/iniciar-pokemon"))
                .header("Content-Type", "application/json")
                .POST(requestBodyPublisher)
                .build();

        // Send the request and get the response
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        ResponseDTO responseFromServer = new ResponseDTO();
        responseFromServer.setSuccess(false);
        responseFromServer.setMessage(objectMapper.readValue(response.body(), new TypeReference<>() {}));
        // then
        assertEquals(200, response.statusCode());
        assertEquals(objectMapper.writeValueAsString(responseFromServer.getMessage()), response.body());

    }

    @Test
    public void shouldFailWhenInitiatePlayerInformationTest() throws Exception {
        //given

        PlayerInformation playerInformation = new PlayerInformation();
        Mockito.when(gimnasioService.obtenerBatallaInformation())
                .thenThrow(new RuntimeException("Exception test"));

        //when

        HttpRequest.BodyPublisher requestBodyPublisher = HttpRequest.BodyPublishers.ofString(
                objectMapper.writeValueAsString(playerInformation));

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/api/pokemon/iniciar-pokemon"))
                .header("Content-Type", "application/json")
                .POST(requestBodyPublisher)
                .build();

        // Send the request and get the response
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        ResponseDTO responseFromServer = new ResponseDTO();
        responseFromServer.setSuccess(false);
        responseFromServer.setMessage(objectMapper.readValue(response.body(), new TypeReference<>() {}));
        // then
        assertEquals(400, response.statusCode());
        assertEquals(objectMapper.writeValueAsString(responseFromServer.getMessage()), response.body());

    }
}
