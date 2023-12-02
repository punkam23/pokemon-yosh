package com.ucenfotec.pokemonyosh.service;

import com.ucenfotec.pokemonyosh.model.*;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class GimnasioService {
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
        return this.batallaResponse;
    }
}
