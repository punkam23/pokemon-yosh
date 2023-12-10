package com.ucenfotec.pokemonyosh.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ucenfotec.pokemonyosh.DTO.ResponseDTO;
import com.ucenfotec.pokemonyosh.controller.PokemonController;
import com.ucenfotec.pokemonyosh.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class PokemonControllerTest {

    @Mock
    private PokemonService pokemonService;

    @InjectMocks
    private PokemonController pokemonController;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        reset(pokemonService);
    }

    private Pokemon pokemonPlayer1 = new Pokemon("bulbasor", PokemonTypeEnum.normal,1000, List.of());
    private PlayerInformation player1 = new PlayerInformation("player1", PlayerStateEnum.EN_BATALLA.name(), pokemonPlayer1);
    private Pokemon pokemonPlayer2 = new Pokemon("bulbasor2", PokemonTypeEnum.normal,1000, List.of());
    private PlayerInformation player2 = new PlayerInformation("player2", PlayerStateEnum.EN_BATALLA.name(), pokemonPlayer2);
    private Pokemon pokemonPlayer3 = new Pokemon("bulbasor3", PokemonTypeEnum.normal,1000, List.of());
    private PlayerInformation player3 = new PlayerInformation("player3", PlayerStateEnum.ATACANDO.name(), pokemonPlayer3);
    private BatallaResponse batallaResponse = new BatallaResponse(1L, "EN_BATALLA",
            List.of(player1, player2, player3));

    @Test
    void iniciarPokemon_Test() {
        // then
        when(pokemonService.iniciarPokemon(any(PlayerInformation.class)))
                .thenReturn(new ResponseDTO(true, "The player has been added or modified."));

        // when
        ResponseEntity<ResponseDTO> response = pokemonController.iniciarPokemon(new PlayerInformation());

        // then
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertTrue(response.getBody().isSuccess());
        assertEquals("The player has been added or modified.", response.getBody().getMessage());

        verify(pokemonService, times(1)).iniciarPokemon(any(PlayerInformation.class));
    }

    @Test
    void iniciarBatalla_Test() {
        // then
        when(pokemonService.iniciarBatalla())
                .thenReturn(new ResponseDTO(true, "The Battle has been Initialized."));

        // when
        ResponseEntity<ResponseDTO> response = pokemonController.iniciarBatalla();

        // then
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertTrue(response.getBody().isSuccess());

        verify(pokemonService, times(1)).iniciarBatalla();
    }

    @Test
    void unirseBatalla_Test() {
        // then
        when(pokemonService.unirseBatalla())
                .thenReturn(new ResponseDTO(true, "The player has been added or modified."));

        // when
        ResponseEntity<ResponseDTO> response = pokemonController.unirseBatalla();

        // then
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertTrue(response.getBody().isSuccess());

        verify(pokemonService, times(1)).unirseBatalla();
    }

    @Test
    void atacarPokemon_Test() {
        // then
        when(pokemonService.atacarPokemon(any(AttackInformation.class)))
                .thenReturn(new ResponseDTO(true, "The attack has been delivered"));

        // when
        ResponseEntity<ResponseDTO> response = pokemonController.atacarPokemon(new AttackInformation());

        // then
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertTrue(response.getBody().isSuccess());

        verify(pokemonService, times(1)).atacarPokemon(any(AttackInformation.class));
    }

    @Test
    void obtenerEstadoBatalla_Test() throws JsonProcessingException {
        // given
        String responseFromServer = objectMapper.writeValueAsString(batallaResponse);

        ResponseDTO mockResponseDTO = new ResponseDTO(true, responseFromServer);
        when(pokemonService.obtenerInformacionBatalla())
                .thenReturn(mockResponseDTO);

        // when
        ResponseEntity<ResponseDTO> response = pokemonController.obtenerEstadoBatalla();

        // then
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertTrue(response.getBody().isSuccess());

        verify(pokemonService, times(1)).obtenerInformacionBatalla();
    }
}
