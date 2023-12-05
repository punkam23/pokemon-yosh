package com.ucenfotec.pokemonyosh.controller;

import com.ucenfotec.pokemonyosh.DTO.ResponseDTO;
import com.ucenfotec.pokemonyosh.model.AttackInformation;
import com.ucenfotec.pokemonyosh.model.BatallaResponse;
import com.ucenfotec.pokemonyosh.model.PlayerInformation;
import com.ucenfotec.pokemonyosh.service.PokemonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController()
@RequestMapping("/api/pokemon")
public class PokemonController {

    @Autowired
    public PokemonService pokemonService;

    @PostMapping("/iniciar-pokemon")
    public ResponseEntity<ResponseDTO> iniciarPokemon(@RequestBody PlayerInformation playerInformation) {
        ResponseDTO mensaje = pokemonService.iniciarPokemon(playerInformation);
        return getResponseEntity(mensaje);
    }

    @PostMapping("/unirse-batalla")
    public ResponseEntity<ResponseDTO> unirseBatalla() {
        ResponseDTO mensaje = pokemonService.unirseBatalla();
        return getResponseEntity(mensaje);
    }

    @PostMapping("/iniciar-batalla")
    public ResponseEntity<ResponseDTO> iniciarBatalla() {
        ResponseDTO mensaje = pokemonService.iniciarBatalla();
        return getResponseEntity(mensaje);
    }

    @PostMapping("/atacar-pokemon")
    public ResponseEntity<ResponseDTO> unirseBatalla(@RequestBody AttackInformation attackInformation) {
        ResponseDTO attackResponse = pokemonService.atacarPokemon(attackInformation);
        return getResponseEntity(attackResponse);
    }

    @GetMapping("/obtener-informacion-batalla")
    public ResponseEntity<ResponseDTO> obtenerEstadoBatalla() {
        ResponseDTO batallaResponse = pokemonService.obtenerInformacionBatalla();
        return getResponseEntity(batallaResponse);
    }

    private ResponseEntity<Object> createResponse(String message) {
        Map<String, Object> response = new HashMap<>();
        response.put("message", message);
        response.put("success", true);
        return ResponseEntity.ok(response);
    }

    private static ResponseEntity<ResponseDTO> getResponseEntity(ResponseDTO mensaje) {
        if(mensaje.isSuccess()){
            return ResponseEntity.ok(mensaje);
        }else {
            return ResponseEntity.badRequest().body(mensaje);
        }
    }
}
