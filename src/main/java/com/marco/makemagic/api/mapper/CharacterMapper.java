package com.marco.makemagic.api.mapper;

import com.marco.makemagic.api.dto.CharacterDTO;
import com.marco.makemagic.api.model.Character;
import org.mapstruct.Mapper;

/**
 * Classe adapter referente a entidade {@link Character}.
 *
 * @author Marco Antônio
 */
@Mapper(componentModel = "spring")
public interface CharacterMapper {

    /**
     * Converte a entidade {@link Character} em DTO {@link CharacterDTO}
     *
     * @param character -
     * @return -
     */
    CharacterDTO toDTO(final Character character);

    /**
     * Converte a entidade {@link CharacterDTO} em DTO {@link Character}
     *
     * @param characterDTO -
     * @return -
     */
    Character toEntity(final CharacterDTO characterDTO);
}
