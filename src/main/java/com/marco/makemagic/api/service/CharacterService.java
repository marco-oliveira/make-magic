package com.marco.makemagic.api.service;

import com.marco.makemagic.api.client.MakeMagicClient;
import com.marco.makemagic.api.dto.HouseClientDTO;
import com.marco.makemagic.api.exception.ValidationHouseIdException;
import com.marco.makemagic.api.model.Character;
import com.marco.makemagic.api.repository.CharacterRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.Objects;

@Service
public class CharacterService {

    private final CharacterRepository characterRepository;
    private final MakeMagicClient makeMagicClient;

    public CharacterService(CharacterRepository characterRepository, MakeMagicClient makeMagicClient) {
        this.characterRepository = characterRepository;
        this.makeMagicClient = makeMagicClient;
    }

    public List<Character> findAll() {
        return this.characterRepository.findAll();
    }

    public Character save(final Character character) {
        validateExistingHouse(character.getHouse());
        return this.characterRepository.save(character);
    }

    private void validateExistingHouse(final String house) {
        Flux<HouseClientDTO> houseClientDTOFlux = this.makeMagicClient.getHouseClientByHouseId(house);
        HouseClientDTO houseClientDTO = houseClientDTOFlux.blockFirst();

        assert houseClientDTO != null;
        if (Objects.isNull(houseClientDTO.get_id())) {
            throw new ValidationHouseIdException();
        }
    }

    public Character findByIdCharacter(final Long id) {
        return this.characterRepository.findById(id)
                .orElseThrow(() -> new EmptyResultDataAccessException(1));
    }

    public void remove(final Long id) {
        Character character = this.findByIdCharacter(id);
        this.characterRepository.delete(character);
    }

    public Character update(final Long id, final Character character) {
        Character existingCharacter = findByIdCharacter(id);
        BeanUtils.copyProperties(character, existingCharacter, "id");
        return this.characterRepository.save(existingCharacter);
    }

    public List<Character> findCharactersByHouse(final String house) {
        return this.characterRepository.findAllByHouse(house);
    }
}
