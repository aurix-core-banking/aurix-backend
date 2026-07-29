package com.aurix.platform.finance.service;

import com.aurix.platform.finance.entity.InstrumentoFinanceiro;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ClassificationServiceTest {

    private final ClassificationService classificationService = new ClassificationService();

    private InstrumentoFinanceiro instrumentoVencidoHaDias(long diasAtraso) {
        InstrumentoFinanceiro instrumento = new InstrumentoFinanceiro();
        instrumento.setTipoInstrumento(InstrumentoFinanceiro.TipoInstrumento.EMPRESTIMO);
        instrumento.setEstagioDeterioracao(InstrumentoFinanceiro.EstagioDeterioracao.ESTAGIO_1);
        instrumento.setValorNominal(BigDecimal.valueOf(1000));
        instrumento.setDataVencimento(LocalDateTime.now().minusDays(diasAtraso));
        return instrumento;
    }

    @Test
    void instrumentoComNoventaOuMaisDiasDeAtrasoDeveIrParaEstagio3SemPrecisarDeMarcadorNoMetadata() {
        InstrumentoFinanceiro instrumento = instrumentoVencidoHaDias(91);

        InstrumentoFinanceiro.EstagioDeterioracao estagio = classificationService.avaliarEstagio(instrumento, LocalDate.now());

        assertEquals(InstrumentoFinanceiro.EstagioDeterioracao.ESTAGIO_3, estagio);
    }

    @Test
    void instrumentoComTrintaOuMaisDiasDeAtrasoDeveIrParaEstagio2SemPrecisarDeMarcadorNoMetadata() {
        InstrumentoFinanceiro instrumento = instrumentoVencidoHaDias(45);

        InstrumentoFinanceiro.EstagioDeterioracao estagio = classificationService.avaliarEstagio(instrumento, LocalDate.now());

        assertEquals(InstrumentoFinanceiro.EstagioDeterioracao.ESTAGIO_2, estagio);
    }

    @Test
    void instrumentoComMenosDeTrintaDiasDeAtrasoEsemIndicadoresPermaneceNoEstagioAtual() {
        InstrumentoFinanceiro instrumento = instrumentoVencidoHaDias(10);

        InstrumentoFinanceiro.EstagioDeterioracao estagio = classificationService.avaliarEstagio(instrumento, LocalDate.now());

        assertEquals(InstrumentoFinanceiro.EstagioDeterioracao.ESTAGIO_1, estagio);
    }

    @Test
    void marcadorInadimplenteNoMetadataAindaLevaAoEstagio3MesmoSemAtraso() {
        InstrumentoFinanceiro instrumento = instrumentoVencidoHaDias(0);
        instrumento.setMetadata("{\"observacao\":\"inadimplente\"}");

        InstrumentoFinanceiro.EstagioDeterioracao estagio = classificationService.avaliarEstagio(instrumento, LocalDate.now());

        assertEquals(InstrumentoFinanceiro.EstagioDeterioracao.ESTAGIO_3, estagio);
    }
}
