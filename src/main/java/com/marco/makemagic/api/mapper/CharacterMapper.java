package com.marco.makemagic.api.mapper;

import com.marco.makemagic.api.dto.CharacterDTO;
import com.marco.makemagic.api.model.Character;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CharacterMapper {

    CharacterDTO toDTO(final Character character);

    Character toEntity(final CharacterDTO characterDTO);
}
