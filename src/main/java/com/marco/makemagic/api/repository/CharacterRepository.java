package com.marco.makemagic.api.repository;

import com.marco.makemagic.api.model.Character;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Classe de persistência referente a entidade {@link Character}.
 *
 * @author Marco Antônio
 */
@Repository
public interface CharacterRepository extends JpaRepository<Character, Long> {

    /**
     * Retorna a lista de {@link Character} conforme a'house' informada.
     *
     * @param filter -
     * @return -
     */
    List<Character> findAllByHouse(final String filter);

}
