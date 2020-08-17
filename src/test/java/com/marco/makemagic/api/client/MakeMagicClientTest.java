package com.marco.makemagic.api.client;

import static org.mockito.Mockito.when;

import org.mockito.InjectMocks;
import org.mockito.internal.util.reflection.FieldSetter;
import com.marco.makemagic.api.dto.HouseClientDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
class MakeMagicClientTest {

    private static final String CORRECT_HOUSE_ID = "1231213211212";
    private static final String API_KEY = "2312132132121231";

    @InjectMocks
    MakeMagicClient makeMagicClient;
    
    @Mock
    private WebClient webClientMock;
    @Mock
    private WebClient.RequestHeadersSpec requestHeadersMock;
    @Mock
    private WebClient.RequestHeadersUriSpec requestHeadersUriMock;
    @Mock
    private WebClient.RequestBodySpec requestBodySpecMock;
    @Mock
    private WebClient.ResponseSpec responseMock;
    
    private HouseClientDTO houseClientMock;

    @BeforeEach
    void setUp()  {
        initData();
    }

   @Test
    void shouldReturnHouseWhenGetHouseByhouseId() throws NoSuchFieldException{
        when(webClientMock.get()).thenReturn(requestHeadersUriMock);
        FieldSetter.setField(this.makeMagicClient, this.makeMagicClient.getClass().getDeclaredField("apiKey"), API_KEY);
        when(requestHeadersUriMock.uri(uriBuilder -> uriBuilder)).thenReturn(requestHeadersUriMock);
        when(requestHeadersMock.retrieve()).thenReturn(responseMock);
        when(responseMock.bodyToFlux(HouseClientDTO.class)).thenReturn(Flux.just(this.houseClientMock));

        Flux<HouseClientDTO> houseClientFlux = this.makeMagicClient.getHouseClientByHouseId(CORRECT_HOUSE_ID);
        StepVerifier.create(houseClientFlux)
           .expectNextMatches(house -> house.get_id().equals(CORRECT_HOUSE_ID))
           .verifyComplete();
   }
   
   private void initData() {
        this.houseClientMock = new HouseClientDTO();
        this.houseClientMock.set_id(CORRECT_HOUSE_ID);
        this.houseClientMock.setName("Harry Potter");
   }
}