package com.ucenfotec.pokemonyosh.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ucenfotec.pokemonyosh.DTO.ResponseDTO;
import com.ucenfotec.pokemonyosh.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.util.List;

@SpringBootTest
public class PokemonServiceTest {

    @Mock
    private GimnasioService gimnasioService;

    @InjectMocks
    private PokemonService pokemonService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private Pokemon pokemonPlayer1 = new Pokemon("bulbasor", PokemonTypeEnum.normal,1000, List.of());
    private PlayerInformation player1 = new PlayerInformation("player1", PlayerStateEnum.EN_BATALLA.name(), pokemonPlayer1);
    private Pokemon pokemonPlayer2 = new Pokemon("bulbasor2", PokemonTypeEnum.normal,1000, List.of());
    private PlayerInformation player2 = new PlayerInformation("player2", PlayerStateEnum.EN_BATALLA.name(), pokemonPlayer2);
    private Pokemon pokemonPlayer3 = new Pokemon("bulbasor3", PokemonTypeEnum.normal,1000, List.of());
    private PlayerInformation player3 = new PlayerInformation("player3", PlayerStateEnum.ATACANDO.name(), pokemonPlayer3);
    private BatallaResponse batallaResponse = new BatallaResponse(1L, "EN_BATALLA",
            List.of(player1, player2, player3));

    @BeforeEach
    void setUp() {
        // Initialize mocks before each test method
        pokemonService = spy(new PokemonService(gimnasioService, new ObjectMapper()));
        reset(gimnasioService);
    }

    @Test
    void iniciarPokemon_Fail() throws IOException {
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

        when(gimnasioService.obtenerBatallaInformation()).thenReturn(new ResponseDTO(true, "Battle information"));

        //when
        ResponseDTO response = this.pokemonService.iniciarPokemon(playerInformation);

        //then
        ResponseDTO expectedResponse = new ResponseDTO(false, "The Battle is in progress.");
        assertNotNull(response);
        assertEquals(expectedResponse, response);

    }

    @Test
    void iniciarPokemon_Success() throws IOException {
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

        when(gimnasioService.obtenerBatallaInformation()).thenReturn(new ResponseDTO(false, "Battle no iniciada"));
        when(pokemonService.getPlayerInformationFilePath()).thenReturn("../playerInformation-test.json");

        //when
        ResponseDTO response = this.pokemonService.iniciarPokemon(playerInformation);

        //then
        ResponseDTO expectedResponse = new ResponseDTO(true, "The player has been added or modified.");
        assertNotNull(response);
        assertEquals(expectedResponse, response);

    }

    @Test
    void unirseBatalla_Success_Test() throws IOException {

        // Mock the behavior of gimnasioService.unirseAGimnasio
        ResponseDTO mockResponseDTO = new ResponseDTO(true, "El Jugador se unio a la batalla");

        when(pokemonService.getPlayerInformationFilePath()).thenReturn("../playerInformation-test.json");
        when(gimnasioService.unirseAGimnasio(any())).thenReturn(mockResponseDTO);

        // Act
        ResponseDTO result = this.pokemonService.unirseBatalla();

        // Assert
        assertEquals(mockResponseDTO, result);
    }

    @Test
    void unirseBatalla_Fail_Test() throws Exception {

        //Given
        when(pokemonService.getPlayerInformationFilePath()).thenReturn("../playerInformation-test.json");
        doThrow(new RuntimeException("Test Exception")).when(gimnasioService).unirseAGimnasio(any());

        // When
        //Then
        assertThrows(Exception.class, pokemonService::unirseBatalla);
    }

    @Test
    void atacarPokemon_Success_Test() throws IOException {
        //given
        ResponseDTO mockResponseDTO = new ResponseDTO(true, "El Jugador ha atacado");
        AttackInformation attackInformation = new AttackInformation();
        attackInformation.setAttackId(1);
        attackInformation.setPlayerName("playerName");
        when(pokemonService.getPlayerInformationFilePath()).thenReturn("../playerInformation-test.json");
        when(gimnasioService.atacarPokemon(anyString(), anyString(), anyInt())).thenReturn(mockResponseDTO);


        //when
        ResponseDTO result = this.pokemonService.atacarPokemon(attackInformation);

        //then
        assertEquals(mockResponseDTO, result);
    }

    @Test
    void atacarPokemon_Fail_Test() throws Exception {
        //given
        AttackInformation attackInformation = new AttackInformation();
        attackInformation.setAttackId(1);
        attackInformation.setPlayerName("playerName");
        doThrow(new RuntimeException("Test Exception")).when(pokemonService).getPlayerInformationFilePath();

        //when
        //then
        assertThrows(RuntimeException.class, () -> pokemonService.atacarPokemon(attackInformation));
    }

    @Test
    void iniciarBatalla_Success_Test() throws Exception {
        //given
        ResponseDTO mockResponseDTO = new ResponseDTO(true, "La batalla ha sido iniciada");

        when(gimnasioService.iniciarBatalla()).thenReturn(mockResponseDTO);

        //when
        ResponseDTO responseDTO = this.pokemonService.iniciarBatalla();
        //then
        assertNotNull(responseDTO);
    }

    @Test
    void obtenerInformacionBatalla_Success_Test() throws Exception {
        //given
        String responseFromServer = objectMapper.writeValueAsString(batallaResponse);

        ResponseDTO mockResponseDTO = new ResponseDTO(true, responseFromServer);

        when(gimnasioService.obtenerBatallaInformation()).thenReturn(mockResponseDTO);

        //when
        ResponseDTO responseDTO = this.pokemonService.obtenerInformacionBatalla();
        //then
        assertNotNull(responseDTO);
    }
}
