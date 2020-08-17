package com.marco.makemagic.api.client;

import com.marco.makemagic.api.dto.HouseClientDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;
import reactor.core.publisher.Flux;

import java.net.URI;
import java.util.function.Function;

@Component
public class MakeMagicClient {

    private final WebClient webClient;

    @Value("${client.api.key}")
    private String apiKey;

    public MakeMagicClient(WebClient webClient) {
        this.webClient = webClient;
    }

    public Flux<HouseClientDTO> getHouseClientByHouseId(final String houseId) {
       return webClient
            .method(HttpMethod.GET)
            .uri(getUriBuilder(houseId, "/houses/{houseId}"))
            .retrieve()
            .bodyToFlux(HouseClientDTO.class);
    }

    private Function<UriBuilder, URI> getUriBuilder(final String pathVariable, final String path) {
        return uriBuilder -> uriBuilder
            .path(path)
            .queryParam("key", apiKey)
            .build(pathVariable);
    }
}
