package com.marco.makemagic.api.dto;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;

/**
 * Classe de transferência referente a entidade {@link Character}.
 *
 * @author Marco Antônio
 */
public class CharacterDTO implements Serializable {

    private static final long serialVersionUID = -6612009885576934568L;

    @ApiModelProperty(value = "Id do Personagem")
    private Long id;

    @ApiModelProperty(value = "Nome do Personagem")
    @NotBlank
    @Size(max = 100, min = 1)
    private String name;

    @ApiModelProperty(value = "Função do Personagem")
    @NotBlank
    @Size(max = 100, min = 1)
    private String role;

    @ApiModelProperty(value = "Escola do Personagem")
    @NotBlank
    @Size(max = 100, min = 1)
    private String school;

    @NotBlank
    @Size(max = 100, min = 1)
    @ApiModelProperty(value = "Casa do Personagem")
    private String house;

    @NotBlank
    @Size(max = 100, min = 1)
    @ApiModelProperty(value = "Patrono do Personagem")
    private String patronus;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getHouse() {
        return house;
    }

    public void setHouse(String house) {
        this.house = house;
    }

    public String getPatronus() {
        return patronus;
    }

    public void setPatronus(String patronus) {
        this.patronus = patronus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CharacterDTO that = (CharacterDTO) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
