package com.aurix.platform.investimentos.indice.service;

import com.aurix.platform.investimentos.indice.entity.IndiceMercado;
import com.aurix.platform.investimentos.indice.entity.TipoIndice;
import com.aurix.platform.investimentos.indice.repository.IndiceMercadoRepository;
import java.math.BigDecimal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestClient;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@ExtendWith(MockitoExtension.class)
class IndiceMercadoServiceTest {

    @Mock
    private IndiceMercadoRepository indiceMercadoRepository;

    private IndiceMercadoService service;
    private MockRestServiceServer server;

    @BeforeEach
    void setUp() {
        RestClient.Builder builder = RestClient.builder().baseUrl("http://localhost:8096");
        server = MockRestServiceServer.bindTo(builder).build();
        service = new IndiceMercadoService(indiceMercadoRepository, builder);
        ReflectionTestUtils.setField(service, "urlBaseBacen",
                "https://api.bcb.gov.br/dados/serie/bcdata.sgs");
    }

    @Test
    void atualizarIndiceDoBacenUsaOCdiRetornadoPelaApi() {
        server.expect(requestTo(
                "https://api.bcb.gov.br/dados/serie/bcdata.sgs.12/dados/ultimos/1?formato=json"))
            .andRespond(withSuccess("""
                [{"data":"01/08/2026","valor":"1.05"}]
                """, MediaType.APPLICATION_JSON));
        when(indiceMercadoRepository.save(any(IndiceMercado.class)))
            .thenAnswer(inv -> inv.getArgument(0));

        IndiceMercado resultado = service.atualizarIndiceDoBacen(TipoIndice.CDI);

        assertThat(resultado).isNotNull();
        assertThat(resultado.getValor()).isEqualByComparingTo("1.05");
        assertThat(resultado.getCodigoSerieBacen()).isEqualTo("12");
        verify(indiceMercadoRepository).save(any(IndiceMercado.class));
    }

    @Test
    void atualizarIndiceRetornaUltimoLocalQuandApiNaoRetornaDados() {
        server.expect(requestTo(
                "https://api.bcb.gov.br/dados/serie/bcdata.sgs.11/dados/ultimos/1?formato=json"))
            .andRespond(withSuccess("[]", MediaType.APPLICATION_JSON));
        IndiceMercado ultimo = new IndiceMercado(
                TipoIndice.SELIC, new BigDecimal("1.20"), java.time.LocalDate.now().minusDays(1),
                "11", "API BACEN SGS");
        when(indiceMercadoRepository.findTopByTipoIndiceOrderByDataReferenciaDesc(TipoIndice.SELIC))
            .thenReturn(java.util.Optional.of(ultimo));

        IndiceMercado resultado = service.atualizarIndiceDoBacen(TipoIndice.SELIC);

        assertThat(resultado).isSameAs(ultimo);
    }
}
