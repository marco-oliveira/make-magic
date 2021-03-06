package com.marco.makemagic.api.controller;

import com.marco.makemagic.api.dto.CharacterDTO;
import com.marco.makemagic.api.mapper.CharacterMapper;
import com.marco.makemagic.api.model.Character;
import com.marco.makemagic.api.service.CharacterService;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * Classe de controle referente a entidade {@link Character}.
 *
 *  @author Marco Antônio
 */
@RestController
@RequestMapping(path = "characters", produces = MediaType.APPLICATION_JSON_VALUE)
public class CharacterController implements CharacterControllerOpenApi {

    private final CharacterService characterService;
    private final CharacterMapper characterMapper;

    public CharacterController(CharacterService characterService, CharacterMapper characterMapper) {
        this.characterService = characterService;
        this.characterMapper = characterMapper;
    }

    /**
     * Salva um Personagem conforme {@link CharacterDTO} informado.
     *
     * @param characterDTO -
     * @return -
     */
    @Override
    @PostMapping
    public ResponseEntity<CharacterDTO> save(@RequestBody @Valid final CharacterDTO characterDTO) {
        Character character = this.characterMapper.toEntity(characterDTO);
        character = this.characterService.save(character);
        return ResponseEntity.status(HttpStatus.CREATED).body(this.characterMapper.toDTO(character));
    }

    /**
     * Altera um Personagem conforme {@link CharacterDTO} informado por 'characterId'.
     *
     * @param characterId -
     * @param characterDTO -
     * @return -
     */
    @Override
    @PutMapping("{characterId}")
    public ResponseEntity<CharacterDTO> update(@PathVariable final Long characterId, @RequestBody @Valid final CharacterDTO characterDTO) {
        Character character = this.characterMapper.toEntity(characterDTO);
        Character updatedCharacter = this.characterService.update(characterId, character);
        return ResponseEntity.ok(this.characterMapper.toDTO(updatedCharacter));
    }

    /**
     * Remove um Personagem conforme o 'characterId' informado.
     *
     * @param characterId -
     */
    @Override
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("{characterId}")
    public void remove(@PathVariable final Long characterId) {
        this.characterService.remove(characterId);
    }

    /**
     * Retorna uma lista de {@link CharacterDTO}.
     *
     * @return -
     */
    @Override
    @GetMapping
    public ResponseEntity<List<CharacterDTO>> getAllCharacters() {
        List<Character> characters = this.characterService.findAll();
        List<CharacterDTO> charactersDTO = characters.stream()
            .map(this.characterMapper::toDTO)
            .collect(Collectors.toList());
        return ResponseEntity.ok()
                .cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS))
                .body(charactersDTO);
    }

    /**
     * Retorna uma instância de {@link CharacterDTO} conforme o 'id' do personagem informado.
     *
     * @param characterId -
     * @return -
     */
    @Override
    @GetMapping("{characterId}")
    public ResponseEntity<CharacterDTO> getCharacterById(@PathVariable final Long characterId) {
        Character character = this.characterService.findByIdCharacter(characterId);
        CharacterDTO characterDTO = this.characterMapper.toDTO(character);
        return ResponseEntity.ok()
                .cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS))
                .body(characterDTO);
    }

    /**
     * Retorna uma lista de {@link CharacterDTO} conforme a 'house' do personagem informado.
     *
     * @param house -
     * @return -
     */
    @Override
    @GetMapping("/filter")
    public ResponseEntity<List<CharacterDTO>> getCharactersByHouse(@RequestParam final String house) {
        List<Character> characters = this.characterService.findCharactersByHouse(house);
        List<CharacterDTO> charactersDTO = characters.stream()
            .map(this.characterMapper::toDTO)
            .collect(Collectors.toList());
        return ResponseEntity.ok()
                .cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS))
                .body(charactersDTO);
    }
}
