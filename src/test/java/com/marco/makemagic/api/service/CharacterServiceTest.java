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

/**
 * Implementação teste referente a classe de serviço {@link CharacterService}.
 *
 * @author Marco Antônio
 */
class CharacterServiceTest {

    @InjectMocks
    CharacterService characterService;
    @Mock
    MakeMagicClient makeMagicClient;
    @Mock
    CharacterRepository characterRepository;
    Character characterMock;
    HouseClientDTO houseClientDTO;

    /**
     * Configuração inicial.
     */
    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        initData();
    }

    /**
     * Teste que válida se uma 'house' informada existe na api 'potterapi'.
     */
    @Test
    void shouldPassedInExistingHouseValidation() {
        when(this.makeMagicClient.getHouseClientByHouseId(this.characterMock.getHouse())).thenReturn(Flux.just(this.houseClientDTO));
        when(this.characterRepository.save(this.characterMock)).thenReturn(this.characterMock);
        Assertions.assertNotNull(this.characterService.save(this.characterMock));
    }

    /**
     * Teste que válida se uma 'house' informada existe na api 'potterapi', e deve retornar a exception {@link ValidationHouseIdException}.
     */
    @Test
    void shouldReturnValidationHouseIdExceptionInNotExistingHouse() {
        when(this.makeMagicClient.getHouseClientByHouseId(this.characterMock.getHouse())).thenReturn(Flux.just(new HouseClientDTO()));
        when(this.characterRepository.save(this.characterMock)).thenReturn(this.characterMock);
        Assertions.assertThrows(ValidationHouseIdException.class, () -> this.characterService.save(this.characterMock));
    }

    /**
     * Insere dados iniciais no banco de dados.
     */
    private void initData() {
        this.characterMock = new Character();
        this.characterMock.setHouse("123121321");

        this.houseClientDTO = new HouseClientDTO();
        this.houseClientDTO.set_id("123121321");
        this.houseClientDTO.setName("Name Test");

    }
}