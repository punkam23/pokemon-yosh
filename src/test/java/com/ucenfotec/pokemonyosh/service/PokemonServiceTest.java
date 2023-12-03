package com.ucenfotec.pokemonyosh.service;

import com.ucenfotec.pokemonyosh.model.Attack;
import com.ucenfotec.pokemonyosh.model.PlayerInformation;
import com.ucenfotec.pokemonyosh.model.Pokemon;
import com.ucenfotec.pokemonyosh.model.PokemonTypeEnum;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

@SpringBootTest
public class PokemonServiceTest {

    @Autowired
    PokemonService pokemonService;

    @Test
    void unirseBatallaTest() {
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
        String response = this.pokemonService.iniciarPokemon(playerInformation);

        //then
        String expectedResponse = "El jugador ha sido registrado para la batalla";
        assertNotNull(response);
        assertEquals(expectedResponse, response);

    }
}
