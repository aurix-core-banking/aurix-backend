package com.aurix.platform.cambio.service;

import com.aurix.platform.cambio.entity.TaxaSelic;
import com.aurix.platform.cambio.repository.TaxaSelicRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BacenIntegrationServiceTest {

    @Mock
    private TaxaSelicRepository taxaSelicRepository;

    @Mock
    private WebClient.Builder webClientBuilder;

    private BacenIntegrationService bacenIntegrationService;

    @BeforeEach
    void setUp() {
        bacenIntegrationService = new BacenIntegrationService(taxaSelicRepository, webClientBuilder);
        ReflectionTestUtils.setField(bacenIntegrationService, "urlBaseBacen", "https://api.bcb.gov.br/dados/serie/bcdata.sgs");
        ReflectionTestUtils.setField(bacenIntegrationService, "codigoSerieSelic", "11");
    }

    private void stubBacenResponse(String json) {
        ExchangeFunction exchangeFunction = request -> Mono.just(ClientResponse.create(HttpStatus.OK)
                .header("Content-Type", "application/json")
                .body(json)
                .build());
        WebClient stubbedClient = WebClient.builder().exchangeFunction(exchangeFunction).build();
        when(webClientBuilder.build()).thenReturn(stubbedClient);
    }

    @Test
    void atualizarTaxaSelicDoBacenDeveUsarOValorRealRetornadoPelaApi() {
        stubBacenResponse("[{\"data\":\"18/06/2026\",\"valor\":\"0.04\"}]");
        when(taxaSelicRepository.save(any(TaxaSelic.class))).thenAnswer(inv -> inv.getArgument(0));

        TaxaSelic resultado = bacenIntegrationService.atualizarTaxaSelicDoBacen();

        assertNotNull(resultado);
        assertEquals(new BigDecimal("0.04"), resultado.getValorTaxa());

        ArgumentCaptor<TaxaSelic> captor = ArgumentCaptor.forClass(TaxaSelic.class);
        verify(taxaSelicRepository).save(captor.capture());
        assertEquals("11", captor.getValue().getCodigoSerieBacen());
    }

    @Test
    void atualizarTaxaSelicDoBacenRetornaNullQuandoApiNaoRetornaDados() {
        stubBacenResponse("[]");

        TaxaSelic resultado = bacenIntegrationService.atualizarTaxaSelicDoBacen();

        assertEquals(null, resultado);
    }
}
