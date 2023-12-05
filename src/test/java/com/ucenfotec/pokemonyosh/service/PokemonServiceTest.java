package com.ucenfotec.pokemonyosh.service;

import com.ucenfotec.pokemonyosh.DTO.ResponseDTO;
import com.ucenfotec.pokemonyosh.model.Attack;
import com.ucenfotec.pokemonyosh.model.PlayerInformation;
import com.ucenfotec.pokemonyosh.model.Pokemon;
import com.ucenfotec.pokemonyosh.model.PokemonTypeEnum;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;

@SpringBootTest
public class PokemonServiceTest {

    @Autowired
    PokemonService pokemonService;

    @Test
    void unirseBatallaTest() throws IOException {
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

        PlayerInformation playerInformation = new PlayerInformation(
                "playerName",
                pokemon
        );

        //when
        ResponseDTO response = this.pokemonService.iniciarPokemon(playerInformation);

        //then
        ResponseDTO expectedResponse = new ResponseDTO(true, "El jugador ha sido registrado para la batalla");
        assertNotNull(response);
        assertEquals(expectedResponse, response);

    }
}
