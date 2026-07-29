package com.aurix.platform.finance.service;

import com.aurix.platform.finance.entity.Imposto;
import com.aurix.platform.finance.repository.ImpostoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ImpostoServiceTest {

    @Mock
    private ImpostoRepository impostoRepository;
    @Mock
    private KafkaTemplate<String, Object> kafkaTemplate;

    private ImpostoService impostoService;

    @BeforeEach
    void setUp() {
        impostoService = new ImpostoService(impostoRepository, kafkaTemplate);
    }

    @Test
    void calcularResumoCompetenciaContaApenasImpostosPagosDaPropriaCompetencia() {
        // Há mais impostos pagos no sistema inteiro (em outras competências) do
        // que impostos totais nesta competência específica. Se "impostosPagos"
        // não for filtrado pela competência, o resumo fica inconsistente
        // (impostosPendentes pode até ficar negativo).
        when(impostoRepository.somaValorPorCompetencia("2026-06")).thenReturn(BigDecimal.valueOf(500));
        when(impostoRepository.countByCompetencia("2026-06")).thenReturn(3L);
        when(impostoRepository.countByCompetenciaAndStatus("2026-06", Imposto.StatusImposto.PAGO)).thenReturn(1L);

        ImpostoService.ResumoCompetencia resumo = impostoService.calcularResumoCompetencia("2026-06");

        assertEquals(3L, resumo.getTotalImpostos());
        assertEquals(1L, resumo.getImpostosPagos());
        assertEquals(2L, resumo.getImpostosPendentes());
        verify(impostoRepository, never()).countByStatus(any());
    }

    @Test
    void pagamentoComAtrasoCalculaMultaEJurosArredondadosEmCentavos() {
        // valor_multa/valor_juros têm scale=2 na coluna do banco; sem
        // arredondamento explícito, o cálculo (valorImposto * taxa) produz
        // BigDecimals com escala maior, o que não corresponde a um valor real
        // de centavos numa guia DARF.
        Imposto imposto = new Imposto();
        imposto.setStatus(Imposto.StatusImposto.APURADO);
        imposto.setDataVencimento(LocalDate.of(2026, 1, 10));
        imposto.setValorImposto(BigDecimal.valueOf(1000.00));
        when(impostoRepository.findById(1L)).thenReturn(Optional.of(imposto));
        when(impostoRepository.save(imposto)).thenReturn(imposto);

        impostoService.pagarImposto(1L, LocalDate.of(2026, 1, 20), "DARF-1");

        assertEquals(2, imposto.getValorMulta().scale());
        assertEquals(2, imposto.getValorJuros().scale());
    }
}
