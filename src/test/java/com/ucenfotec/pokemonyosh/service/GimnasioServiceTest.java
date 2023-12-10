package com.ucenfotec.pokemonyosh.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ucenfotec.pokemonyosh.DTO.ResponseDTO;
import com.ucenfotec.pokemonyosh.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import javax.net.ssl.SSLSession;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

@SpringBootTest
public class GimnasioServiceTest {
    @InjectMocks
    private GimnasioService gimnasioService;

    @Mock
    private HttpClient httpClient;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private Pokemon pokemonPlayer1 = new Pokemon("bulbasor", PokemonTypeEnum.normal,1000, List.of());
    private PlayerInformation player1 = new PlayerInformation("player1", PlayerStateEnum.EN_BATALLA.name(), pokemonPlayer1);
    private Pokemon pokemonPlayer2 = new Pokemon("bulbasor2", PokemonTypeEnum.normal,1000, List.of());
    private PlayerInformation player2 = new PlayerInformation("player2", PlayerStateEnum.EN_BATALLA.name(), pokemonPlayer1);
    private Pokemon pokemonPlayer3 = new Pokemon("bulbasor3", PokemonTypeEnum.normal,1000, List.of());
    private PlayerInformation player3 = new PlayerInformation("player3", PlayerStateEnum.ATACANDO.name(), pokemonPlayer1);
    private BatallaResponse batallaResponse = new BatallaResponse(1L, "EN_BATALLA",
            List.of(player1, player2, player3));

    @BeforeEach
    public void setUp() {
        gimnasioService = spy(new GimnasioService());
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void iniciarBatalla_Success_Test() throws IOException, InterruptedException {

        //given
        ResponseDTO expectedResponseBody = new ResponseDTO();
        expectedResponseBody.setSuccess(true);
        expectedResponseBody.setMessage("La Batalla ha iniciado");


        HttpResponse<Object> mockResponse = createMockHttpResponse(200,
                objectMapper.writeValueAsString(expectedResponseBody));

        when(gimnasioService.getHttpClient()).thenReturn(httpClient);
        when(httpClient.send(any(), any())).thenReturn(mockResponse);

        //when
        ResponseDTO responseDTO = this.gimnasioService.iniciarBatalla();

        //then
        assertNotNull(responseDTO);
    }

    @Test
    void iniciarBatalla_Fail_Test() throws IOException, InterruptedException {

        //given

        when(gimnasioService.getHttpClient()).thenReturn(httpClient);
        when(httpClient.send(any(), any())).thenThrow(new RuntimeException("Test Exception"));

        // When
        //Then
        assertThrows(RuntimeException.class, gimnasioService::iniciarBatalla);
    }

    @Test
    void unirseBatalla_Success_Test() throws IOException, InterruptedException {

        //given
        PlayerInformation playerInformation = new PlayerInformation();
        playerInformation.setPlayerName("playerName");
        playerInformation.setPokemon(pokemonPlayer1);
        ResponseDTO expectedResponseBody = new ResponseDTO();
        expectedResponseBody.setSuccess(true);
        expectedResponseBody.setMessage("El jugador se ha unido a la batalla");


        HttpResponse<Object> mockResponse = createMockHttpResponse(200,
                objectMapper.writeValueAsString(expectedResponseBody));

        when(gimnasioService.getHttpClient()).thenReturn(httpClient);
        when(httpClient.send(any(), any())).thenReturn(mockResponse);

        //when
        ResponseDTO responseDTO = this.gimnasioService.unirseAGimnasio(playerInformation);

        //then
        assertNotNull(responseDTO);
    }

    @Test
    void unirseGimnasio_Fail_Test() throws IOException, InterruptedException {

        //given

        when(gimnasioService.getHttpClient()).thenReturn(httpClient);
        when(httpClient.send(any(), any())).thenThrow(new RuntimeException("Test Exception"));

        // When
        //Then
        assertThrows(RuntimeException.class, () -> gimnasioService.unirseAGimnasio(new PlayerInformation()));
    }

    @Test
    void atacarPokemon_Success_Test() throws IOException, InterruptedException {

        //given
        String sourcerPlayerName = "sourcerPlayerName";
        String targetPlayerName = "targetPlayerName";
        int attackId = 1;
        ResponseDTO expectedResponseBody = new ResponseDTO();
        expectedResponseBody.setSuccess(true);
        expectedResponseBody.setMessage("El ataque ha sido realizado exitosamente");


        HttpResponse<Object> mockResponse = createMockHttpResponse(200,
                objectMapper.writeValueAsString(expectedResponseBody));

        when(gimnasioService.getHttpClient()).thenReturn(httpClient);
        when(httpClient.send(any(), any())).thenReturn(mockResponse);

        //when
        ResponseDTO responseDTO = this.gimnasioService.atacarPokemon(sourcerPlayerName, targetPlayerName, attackId);

        //then
        assertNotNull(responseDTO);
    }

    @Test
    void atacarPokemon_Fail_Test() throws IOException, InterruptedException {

        //given
        String targetPlayerName = "targetPlayerName";
        String sourcePlayerName = "sourcePlayerName";

        when(gimnasioService.getHttpClient()).thenReturn(httpClient);
        when(httpClient.send(any(), any())).thenThrow(new RuntimeException("Test Exception"));

        // When
        //Then
        assertThrows(RuntimeException.class, () -> gimnasioService.atacarPokemon(targetPlayerName, sourcePlayerName, 1));
    }

    @Test
    void obtenerInformacionBatalla_Success_Test() throws IOException, InterruptedException {

        //given

        HttpResponse<Object> mockResponse = createMockHttpResponse(200,
                objectMapper.writeValueAsString(batallaResponse));

        when(gimnasioService.getHttpClient()).thenReturn(httpClient);
        when(httpClient.send(any(), any())).thenReturn(mockResponse);

        //when
        ResponseDTO responseDTO = this.gimnasioService.obtenerBatallaInformation();

        //then
        assertNotNull(responseDTO);
    }

    @Test
    void obtenerInformacionBatalla_Fail_FromServer_Test() throws IOException, InterruptedException {

        //given
        ResponseDTO responseFromServerDTO = new ResponseDTO();
        responseFromServerDTO.setSuccess(false);
        responseFromServerDTO.setMessage("The Battle is not initialized");
        HttpResponse<Object> mockResponse = createMockHttpResponse(400,
                objectMapper.writeValueAsString(responseFromServerDTO));

        when(gimnasioService.getHttpClient()).thenReturn(httpClient);
        when(httpClient.send(any(), any())).thenReturn(mockResponse);

        //when
        ResponseDTO responseDTO = this.gimnasioService.obtenerBatallaInformation();

        //Then
        assertNotNull(responseDTO);
    }

    @Test
    void obtenerInformacionBatalla_Fail_Test() throws IOException, InterruptedException {

        //given

        when(gimnasioService.getHttpClient()).thenReturn(httpClient);
        when(httpClient.send(any(), any())).thenThrow(new RuntimeException("Test Exception"));

        // When
        //Then
        assertThrows(RuntimeException.class, gimnasioService::obtenerBatallaInformation);
    }

    private HttpResponse<Object> createMockHttpResponse(int statusCode, String body) {
        return new HttpResponse<>() {
            @Override
            public int statusCode() {
                return statusCode;
            }

            @Override
            public HttpRequest request() {
                return null;
            }

            @Override
            public Optional<HttpResponse<Object>> previousResponse() {
                return Optional.empty();
            }

            @Override
            public HttpHeaders headers() {
                return HttpHeaders.of(Map.of("Content-Type", List.of("application/json")), (s1, s2) -> true);
            }

            @Override
            public String body() {
                return body;
            }

            @Override
            public Optional<SSLSession> sslSession(){
                return Optional.empty();
            }

            @Override
            public URI uri(){
                return null;
            }
            @Override
            public HttpClient.Version version(){
                return null;
            }


        };
    }
}
