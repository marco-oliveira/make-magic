package com.marco.makemagic.api.service;

import static org.mockito.Mockito.when;

import com.marco.makemagic.api.client.MakeMagicClient;
import com.marco.makemagic.api.dto.HouseClientDTO;
import com.marco.makemagic.api.exception.ValidationHouseIdException;
import com.marco.makemagic.api.model.Character;
import com.marco.makemagic.api.repository.CharacterRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import reactor.core.publisher.Flux;

class CharacterServiceTest {

    @InjectMocks
    CharacterService characterService;
    @Mock
    MakeMagicClient makeMagicClient;
    @Mock
    CharacterRepository characterRepository;
    Character characterMock;
    HouseClientDTO houseClientDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        initData();
    }

    @Test
    void shouldPassedInExistingHouseValidation() {
        when(this.makeMagicClient.getHouseClientByHouseId(this.characterMock.getHouse())).thenReturn(Flux.just(this.houseClientDTO));
        when(this.characterRepository.save(this.characterMock)).thenReturn(this.characterMock);
        Assertions.assertNotNull(this.characterService.save(this.characterMock));
    }

    @Test
    void shouldReturnValidationHouseIdExceptionInNotExistingHouse() {
        when(this.makeMagicClient.getHouseClientByHouseId(this.characterMock.getHouse())).thenReturn(Flux.just(new HouseClientDTO()));
        when(this.characterRepository.save(this.characterMock)).thenReturn(this.characterMock);
        Assertions.assertThrows(ValidationHouseIdException.class, () -> this.characterService.save(this.characterMock));
    }

    private void initData() {
        this.characterMock = new Character();
        this.characterMock.setHouse("123121321");

        this.houseClientDTO = new HouseClientDTO();
        this.houseClientDTO.set_id("123121321");
        this.houseClientDTO.setName("Name Test");

    }
}