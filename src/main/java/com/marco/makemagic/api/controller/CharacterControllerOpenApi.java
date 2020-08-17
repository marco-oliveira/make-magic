package com.marco.makemagic.api.controller;

import com.marco.makemagic.api.dto.CharacterDTO;
import com.marco.makemagic.api.exception.MakeMagicError;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Api(tags = "Personagens")
public interface CharacterControllerOpenApi {

    @ApiOperation("Salva um Personagem informado.")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Created", response = CharacterDTO.class),
            @ApiResponse(code = 400, message = "Bad Request", response = MakeMagicError.class),
            @ApiResponse(code = 404, message = "Not Found", response = MakeMagicError.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = MakeMagicError.class) })
    ResponseEntity<CharacterDTO> save(
            @ApiParam(value = "Representação de um Personagem", required = true) final CharacterDTO characterDTO);

    @ApiOperation("Altera um Personagem por id informado.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = CharacterDTO.class),
            @ApiResponse(code = 400, message = "Bad Request", response = MakeMagicError.class),
            @ApiResponse(code = 404, message = "Not Found", response = MakeMagicError.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = MakeMagicError.class) })
    ResponseEntity<CharacterDTO> update(
            @ApiParam(value = "Id do Personagem", example = "1", required = true) final Long characterId,
            @ApiParam(value = "Representação de um Personagem", required = true) final CharacterDTO characterDTO);

    @ApiOperation("Excluí um Personagem por id informado.")
    @ApiResponses({
            @ApiResponse(code = 204, message = "No Content"),
            @ApiResponse(code = 400, message = "Bad Request", response = MakeMagicError.class),
            @ApiResponse(code = 404, message = "Not Found", response = MakeMagicError.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = MakeMagicError.class) })
    void remove(@ApiParam(value = "Id do Personagem", required = true, example = "1") final Long characterId);

    @ApiOperation("Lista todos os Personagens Cadastrados.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = CharacterDTO.class),
            @ApiResponse(code = 400, message = "Bad Request", response = MakeMagicError.class),
            @ApiResponse(code = 404, message = "Not Found", response = MakeMagicError.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = MakeMagicError.class) })
    ResponseEntity<List<CharacterDTO>> getAllCharacters();

    @ApiOperation("Busca um Personagem por id Informado.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = CharacterDTO.class),
            @ApiResponse(code = 400, message = "Bad Request", response = MakeMagicError.class),
            @ApiResponse(code = 404, message = "Not Found", response = MakeMagicError.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = MakeMagicError.class) })
    ResponseEntity<CharacterDTO> getCharacterById(
            @ApiParam(value = "Id do Personagem", required = true, example = "1") final Long characterId);

    @ApiOperation("Lista todos os Personagens filtrados pela casa informada.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = CharacterDTO.class),
            @ApiResponse(code = 400, message = "Bad Request", response = MakeMagicError.class),
            @ApiResponse(code = 404, message = "Not Found", response = MakeMagicError.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = MakeMagicError.class) })
    ResponseEntity<List<CharacterDTO>> getCharactersByHouse(
            @ApiParam(value = "Casa a ser filtrada", required = true) final String house);
}
