package com.ucenfotec.pokemonyosh.controller;

import com.ucenfotec.pokemonyosh.model.AttackInformation;
import com.ucenfotec.pokemonyosh.model.BatallaResponse;
import com.ucenfotec.pokemonyosh.model.PlayerInformation;
import com.ucenfotec.pokemonyosh.service.PokemonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("/api/pokemon")
public class PokemonController {

    @Autowired
    public PokemonService pokemonService;

    @PostMapping("/iniciar")
    public ResponseEntity<String> iniciarPokemon(@RequestBody PlayerInformation playerInformation) {
        String mensaje = pokemonService.iniciarPokemon(playerInformation);
        return ResponseEntity.ok(mensaje);
    }

    @PostMapping("/unirse-batalla")
    public ResponseEntity<String> unirseBatalla() {
        String mensaje = pokemonService.unirseBatalla();
        return ResponseEntity.ok(mensaje);
    }

    @PostMapping("/atacar")
    public ResponseEntity<String> unirseBatalla(@RequestBody AttackInformation attackInformation) {
        String attackResponse = pokemonService.atacarPokemon(attackInformation);
        return ResponseEntity.ok(attackResponse);
    }

    @GetMapping("/obtener-estado-batalla")
    public ResponseEntity<BatallaResponse> obtenerEstadoBatalla() {
        BatallaResponse batallaResponse = pokemonService.obtenerInformacionBatalla();
        return ResponseEntity.ok(batallaResponse);
    }
}
