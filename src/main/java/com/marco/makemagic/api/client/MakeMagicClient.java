package com.marco.makemagic.api.client;

import com.marco.makemagic.api.dto.HouseClientDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;
import reactor.core.publisher.Flux;

import java.net.URI;
import java.util.function.Function;

/**
 * Classe Client responsável por fazer a conexão com a api potterapi.
 *
 *  @author Marco Antônio
 */
@Component
public class MakeMagicClient {

    private final WebClient webClient;

    @Value("${client.api.key}")
    private String apiKey;

    private static final Long TIMEOUT = 10000L;

    public MakeMagicClient(WebClient webClient) {
        this.webClient = webClient;
    }

    /**
     * Método responsável em fazer a busca de uma Casa no potterapi por 'houseId'.
     *
     * @param houseId -
     * @return -
     */
    public Flux<HouseClientDTO> getHouseClientByHouseId(final String houseId) {
       return webClient
            .get()
            .uri(getUriBuilder(houseId, "/v1/houses/{houseId}"))
            .retrieve()
            .bodyToFlux(HouseClientDTO.class);
    }

    /**
     * Método responsável em buildar a uri para a consulta na api.
     *
     * @param pathVariable -
     * @param path -
     * @return -
     */
    private Function<UriBuilder, URI> getUriBuilder(final String pathVariable, final String path) {
        return uriBuilder -> uriBuilder
            .path(path)
            .queryParam("key", apiKey)
            .build(pathVariable);
    }
}
