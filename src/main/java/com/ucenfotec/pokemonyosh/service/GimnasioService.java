package com.ucenfotec.pokemonyosh.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ucenfotec.pokemonyosh.DTO.AttackInformationDTO;
import com.ucenfotec.pokemonyosh.DTO.ResponseDTO;
import com.ucenfotec.pokemonyosh.model.*;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

@Service
public class GimnasioService {

    private final HttpClient httpClient = HttpClient.newBuilder().build();
    private final ObjectMapper objectMapper = new ObjectMapper();

    private static final String BASE_URL = "http://ec2-3-18-23-121.us-east-2.compute.amazonaws.com:8080"; // Replace with your actual base URL

    public HttpClient getHttpClient() {
        return this.httpClient;
    }

    public ResponseDTO iniciarBatalla(){
        ResponseDTO attackResponseFromServer = new ResponseDTO();
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URL + "/api/gimnasio/iniciar"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.noBody())
                    .build();
            HttpResponse<String> response = getHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(response.body());
            setResponseDto(response, attackResponseFromServer);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return attackResponseFromServer;
    }
    public ResponseDTO unirseAGimnasio(PlayerInformation pokemonFromFile){
        ResponseDTO responseFromServer = new ResponseDTO();
        try {
            HttpRequest.BodyPublisher requestBodyPublisher = HttpRequest.BodyPublishers.ofString(
                    objectMapper.writeValueAsString(pokemonFromFile));
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URL + "/api/gimnasio/unirse"))
                    .header("Content-Type", "application/json")
                    .POST(requestBodyPublisher)
                    .build();
            HttpResponse<String> response = getHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(response.body());
            setResponseDto(response, responseFromServer);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return responseFromServer;
    }

    public ResponseDTO atacarPokemon(String sourcePlayerName, String targetPlayerName, int attackId){
        ResponseDTO attackResponseFromServer = new ResponseDTO();
        AttackInformationDTO attackInformationDTO = new AttackInformationDTO();
        attackInformationDTO.setAttackId(attackId);
        attackInformationDTO.setSourcePlayerName(sourcePlayerName);
        attackInformationDTO.setTargetPlayerName(targetPlayerName);
        try {
            HttpRequest.BodyPublisher requestBodyPublisher = HttpRequest.BodyPublishers.ofString(
                    objectMapper.writeValueAsString(attackInformationDTO));
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URL + "/api/gimnasio/atacar"))
                    .header("Content-Type", "application/json")
                    .POST(requestBodyPublisher)
                    .build();
            HttpResponse<String> response = getHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(response.body());
            setResponseDto(response, attackResponseFromServer);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return attackResponseFromServer;
    }

    public ResponseDTO obtenerBatallaInformation() {
        ResponseDTO batallaResponseFromServer = new ResponseDTO();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/api/gimnasio/info"))
                .GET()
                .build();
        try {
            HttpResponse<String> response = getHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(response.body());
            if(response.statusCode() == 400){
                batallaResponseFromServer.setSuccess(false);
                batallaResponseFromServer = objectMapper.readValue(response.body(), new TypeReference<>() {});
            } else {
                BatallaResponse batallaFromServer = objectMapper.readValue(response.body(), new TypeReference<>() {});
                batallaResponseFromServer.setSuccess(true);
                batallaResponseFromServer.setMessage(batallaFromServer);
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return batallaResponseFromServer;
    }

    private void setResponseDto(HttpResponse<String> response, ResponseDTO responseFromServer) throws JsonProcessingException {
        if(response.statusCode() == 400){
            responseFromServer.setSuccess(false);
            responseFromServer.setMessage(objectMapper.readValue(response.body(), new TypeReference<>() {}));
        } else {
            responseFromServer.setSuccess(true);
            responseFromServer.setMessage(objectMapper.readValue(response.body(), new TypeReference<>() {}));
        }
    }
}
