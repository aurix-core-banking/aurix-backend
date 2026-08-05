package com.aurix.platform.fraud.service;

import com.aurix.platform.fraud.entity.RegraFraude;
import com.aurix.platform.fraud.entity.ScoreTransacao;
import com.aurix.platform.fraud.repository.BloqueioPreventivoRepository;
import com.aurix.platform.fraud.repository.OcorrenciaFraudeRepository;
import com.aurix.platform.fraud.repository.RegraFraudeRepository;
import com.aurix.platform.fraud.repository.ScoreTransacaoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FraudScoringServiceTest {
    @Mock private RegraFraudeRepository regraRepository;
    @Mock private ScoreTransacaoRepository scoreRepository;
    @Mock private OcorrenciaFraudeRepository ocorrenciaRepository;
    @Mock private BloqueioPreventivoRepository bloqueioRepository;
    @Mock private FraudProducer fraudProducer;
    @InjectMocks private FraudScoringService fraudScoringService;

    @Test
    void deveCriarRegraComAtivoEPrioridadePadrao() {
        RegraFraude regra = new RegraFraude();
        regra.setNome("Alto Valor");
        regra.setTipo("VALOR");
        regra.setPontuacao(50);

        when(regraRepository.save(any())).thenAnswer(inv -> {
            RegraFraude saved = inv.getArgument(0);
            saved.setId(1L);
            return saved;
        });

        RegraFraude resultado = fraudScoringService.criarRegra(regra);

        assertTrue(resultado.getAtivo());
        assertEquals(0, resultado.getPrioridade());
        assertEquals("Alto Valor", resultado.getNome());
    }

    @Test
    void deveLancarExcecaoQuandoRegraNaoEncontrada() {
        when(regraRepository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> fraudScoringService.buscarRegra(99L));
    }

    @Test
    void deveAvaliarTransacaoComRiscoBaixo() {
        when(regraRepository.findByAtivoTrueOrderByPrioridadeDesc()).thenReturn(List.of());
        when(scoreRepository.save(any())).thenAnswer(inv -> {
            ScoreTransacao saved = inv.getArgument(0);
            saved.setId(1L);
            return saved;
        });

        ScoreTransacao resultado = fraudScoringService.avaliarTransacao(10L, "TXN001");

        assertEquals("BAIXO", resultado.getRisco());
        assertEquals(0, resultado.getScore().intValue());
        assertEquals(10L, resultado.getClienteId());
        assertEquals("TXN001", resultado.getTransacaoRef());
        assertNotNull(resultado.getDataAvaliacao());
    }
}
