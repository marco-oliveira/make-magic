package com.marco.makemagic.api;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

import com.marco.makemagic.api.model.Character;
import com.marco.makemagic.api.repository.CharacterRepository;
import com.marco.makemagic.api.util.DataBaseCleaner;
import com.marco.makemagic.api.util.ResourceUtils;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * Implementação teste de integração referente aos serviços do {@link Character}.
 *
 * @author Marco Antônio
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties")
class CharacterRegisterIT {

    private static final int INCORRECT_CHARACTER_ID = 100;
    private static final String CORRECT_HOUSE = "5a05e2b252f721a3cf2ea33f";
    private static final String KATIE_BELL_NAME = "Katie Bell";

    @Autowired
    private CharacterRepository characterRepository;

    @Autowired
    private DataBaseCleaner dataBaseCleaner;

    @LocalServerPort
    private int port;
    private String jsonCharacter;
    private Character harryPotter;
    private Character siriusBlack;
    private int sizeCharactersRegistered;

    /**
     * Configuração inicial.
     */
    @BeforeEach
    public void setUp() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.port = port;
        RestAssured.basePath = "/api/characters";
        this.jsonCharacter = ResourceUtils.getContentFromResource(
                "/json/character.json");
        this.dataBaseCleaner.clearTables();
        insertData();
    }

    /**
     * Teste que deve retornar status 200, quando buscar todos personagens.
     */
    @Test
    void shouldReturnStatus200WhenConsultCharacters() {
        given()
            .accept(ContentType.JSON)
        .when()
            .get()
        .then()
            .statusCode(HttpStatus.OK.value());
    }

    /**
     * Teste que deve retornar status 200, quando buscar um personagem por 'id'.
     */
    @Test
    void shouldReturnStatus200AndCorrectResponseWhenConsulCharactersById() {
        given()
            .pathParam("characterId", this.harryPotter.getId())
            .accept(ContentType.JSON)
        .when()
            .get("/{characterId}")
        .then()
            .statusCode(HttpStatus.OK.value())
            .body("name", equalTo(this.harryPotter.getName()));
    }

    /**
     * Teste que deve retornar status 404, quando buscar um personagem por 'id'.
     */
    @Test
    void shouldReturnStatus404WhenConsulCharactersById() {
        given()
            .pathParam("characterId", INCORRECT_CHARACTER_ID)
            .accept(ContentType.JSON)
        .when()
            .get("/{characterId}")
        .then()
            .statusCode(HttpStatus.NOT_FOUND.value());
    }

    /**
     * Teste que deve retornar status 200 e quantidade de registros corretos, quando buscar uma lista personagem por 'house'.
     */
    @Test
    void shouldReturnStatus200AndCorrectResponseSizeWhenConsulCharactersByHouse() {
        given()
            .queryParam("house", CORRECT_HOUSE)
            .accept(ContentType.JSON)
        .when()
            .get("/filter")
        .then()
            .statusCode(HttpStatus.OK.value())
            .body("", hasSize(sizeCharactersRegistered));
    }

    /**
     * Teste que deve retornar status 201, quando salvar um personagem.
     */
    @Test
    void shouldReturnStatus201WhenRegisterCharacter() {
        given()
            .body(jsonCharacter)
            .contentType(ContentType.JSON)
        .accept(ContentType.JSON)
            .when()
            .post()
        .then()
            .statusCode(HttpStatus.CREATED.value());
    }

    /**
     * Teste que deve retornar status 200 e resposta correta, quando alterar um personagem por 'id'.
     */
    @Test
    void shouldReturnStatus200AndReturnCorrectResponseWhenUpdateCharacter() {
        given()
            .pathParam("characterId", this.harryPotter.getId())
            .body(jsonCharacter)
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
        .when()
            .put("/{characterId}")
        .then()
            .statusCode(HttpStatus.OK.value())
            .body("name", equalTo(KATIE_BELL_NAME));
    }

    /**
     * Teste que deve retornar status 204, quando remover um personagem por 'id'.
     */
    @Test
    void shouldReturnStatus204WhenDeleteCharacter() {
        given()
            .pathParam("characterId", this.siriusBlack.getId())
            .body(jsonCharacter)
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
        .when()
            .delete("/{characterId}")
            .then()
        .statusCode(HttpStatus.NO_CONTENT.value());
    }

    /**
     * Insere dados iniciais no banco de dados.
     */
    private void insertData() {
        this.harryPotter = new Character();
        this.harryPotter.setName("Harry Potter");
        this.harryPotter.setRole("student");
        this.harryPotter.setSchool("Hogwarts School of Witchcraft and Wizardry");
        this.harryPotter.setHouse(CORRECT_HOUSE);
        this.harryPotter.setPatronus("stag");
        this.characterRepository.save(harryPotter);

        this.siriusBlack = new Character();
        this.siriusBlack.setName("Sirius Black");
        this.siriusBlack.setRole("student");
        this.siriusBlack.setSchool("Hogwarts School of Witchcraft and Wizardry");
        this.siriusBlack.setHouse(CORRECT_HOUSE);
        this.siriusBlack.setPatronus("stag");
        this.characterRepository.save(siriusBlack);

        sizeCharactersRegistered = (int) this.characterRepository.count();
    }

}
