package com.aurix.platform.credit.credit.service;

import com.aurix.platform.credit.credit.config.RegrasCreditoProperties;
import com.aurix.platform.credit.credit.integration.CreditBureauService;
import com.aurix.platform.shared.repository.SolicitacaoCreditoRepository;
import com.aurix.platform.shared.entity.Cliente;
import com.aurix.platform.shared.entity.SolicitacaoCredito;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.math.BigDecimal;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DecisaoCreditoPJTest {

    @Mock
    private SolicitacaoCreditoRepository repository;
    @Mock
    private CreditBureauService bureauService;
    @Mock
    private RegrasCreditoProperties regras;

    private DecisaoCreditoService service;
    private Cliente clientePJ;
    private SolicitacaoCredito solicitacao;

    @BeforeEach
    void setUp() {
        service = new DecisaoCreditoService(repository, bureauService, regras);
        clientePJ = new Cliente();
        clientePJ.setTipoPessoa(Cliente.TipoPessoa.JURIDICA);
        clientePJ.setCnpj("11222333000181");
        clientePJ.setFaturamentoMensal(BigDecimal.valueOf(1_000_000));
        solicitacao = new SolicitacaoCredito();
        solicitacao.setCliente(clientePJ);
        solicitacao.setValorSolicitado(BigDecimal.valueOf(100_000));
    }

    @Test
    void deveAprovarPJComScoreAltoEFaturamentoSuficiente() {
        when(bureauService.consultarScoreCNPJ(anyString()))
            .thenReturn(CreditBureauService.ScoreCNPJResult.ok(750, BigDecimal.valueOf(500_000), "MEDIO"));

        DecisaoCreditoService.ResultadoDecisao result = service.decidirPJ(solicitacao, "tenant1");

        assertEquals("APROVADA", result.status());
        assertEquals("Aprovado com score 750", result.motivo());
        assertEquals(750, result.score());
    }

    @Test
    void deveReferPJComScoreAltoMasFaturamentoInsuficiente() {
        clientePJ.setFaturamentoMensal(BigDecimal.valueOf(10_000));
        when(bureauService.consultarScoreCNPJ(anyString()))
            .thenReturn(CreditBureauService.ScoreCNPJResult.ok(750, BigDecimal.valueOf(500_000), "MEDIO"));

        DecisaoCreditoService.ResultadoDecisao result = service.decidirPJ(solicitacao, "tenant1");

        assertEquals("REFER", result.status());
        assertEquals("Faturamento insuficiente para o valor solicitado", result.motivo());
        assertEquals(750, result.score());
    }

    @Test
    void deveRejeitarPJComScoreBaixo() {
        when(bureauService.consultarScoreCNPJ(anyString()))
            .thenReturn(CreditBureauService.ScoreCNPJResult.ok(200, BigDecimal.valueOf(100_000), "ALTO"));

        DecisaoCreditoService.ResultadoDecisao result = service.decidirPJ(solicitacao, "tenant1");

        assertEquals("REJEITADA", result.status());
        assertEquals("Score baixo: 200", result.motivo());
        assertEquals(200, result.score());
    }

    @Test
    void deveReferPJComScoreIntermediario() {
        when(bureauService.consultarScoreCNPJ(anyString()))
            .thenReturn(CreditBureauService.ScoreCNPJResult.ok(400, BigDecimal.valueOf(200_000), "MEDIO"));

        DecisaoCreditoService.ResultadoDecisao result = service.decidirPJ(solicitacao, "tenant1");

        assertEquals("REFER", result.status());
        assertEquals("Score intermediario: 400", result.motivo());
        assertEquals(400, result.score());
    }

    @Test
    void deveRejeitarPJComCNPJInvalido() {
        when(bureauService.consultarScoreCNPJ(anyString()))
            .thenReturn(CreditBureauService.ScoreCNPJResult.erro("000", "CNPJ invalido"));

        DecisaoCreditoService.ResultadoDecisao result = service.decidirPJ(solicitacao, "tenant1");

        assertEquals("REJEITADA", result.status());
        assertNull(result.score());
    }
}