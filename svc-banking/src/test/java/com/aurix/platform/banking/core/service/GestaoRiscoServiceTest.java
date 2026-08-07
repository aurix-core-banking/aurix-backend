package com.aurix.platform.banking.core.service;

import com.aurix.platform.banking.core.entity.*;
import com.aurix.platform.banking.core.repository.*;
import com.aurix.platform.shared.repository.*;
import com.aurix.platform.shared.entity.Conta;
import com.aurix.platform.shared.entity.Transacao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GestaoRiscoServiceTest {

    @Mock
    private PerfilRiscoRepository perfilRiscoRepository;

    @Mock
    private RegraRiscoRepository regraRiscoRepository;

    @Mock
    private AvaliacaoRiscoRepository avaliacaoRiscoRepository;

    @Mock
    private AlertaRiscoRepository alertaRiscoRepository;

    @Mock
    private EventoRiscoRepository eventoRiscoRepository;

    @Mock
    private ContaRepository contaRepository;

    @Mock
    private TransacaoRepository transacaoRepository;

    @Mock
    private LiquidacaoRepository liquidacaoRepository;

    @InjectMocks
    private GestaoRiscoService gestaoRiscoService;

    private Conta conta;
    private Transacao transacao;
    private PerfilRisco perfilRisco;
    private RegraRisco regraRisco;
    private AvaliacaoRisco avaliacao;

    @BeforeEach
    void setUp() {
        // Setup conta
        conta = new Conta();
        conta.setId(1L);
        conta.setNumeroConta("12345-6");
        conta.setSaldoAtual(BigDecimal.valueOf(10000.00));

        // Setup transação
        transacao = new Transacao();
        transacao.setId(1L);
        transacao.setCodigoTransacao("TXN-001");
        transacao.setContaOrigem(conta);
        transacao.setValor(BigDecimal.valueOf(1000.00));
        transacao.setTipoTransacao(Transacao.TipoTransacao.PIX);
        transacao.setStatus(Transacao.StatusTransacao.PENDENTE);

        // Setup perfil de risco
        perfilRisco = new PerfilRisco();
        perfilRisco.setId(1L);
        perfilRisco.setCodigoPerfil("PERFIL-MEDIO");
        perfilRisco.setNomePerfil("Perfil de Risco Médio");
        perfilRisco.setNivelRisco(PerfilRisco.NivelRisco.MEDIO);
        perfilRisco.setValorLimiteDiario(BigDecimal.valueOf(5000.00));
        perfilRisco.setValorLimiteMensal(BigDecimal.valueOf(50000.00));
        perfilRisco.setAtivo(true);

        // Setup regra de risco
        regraRisco = new RegraRisco();
        regraRisco.setId(1L);
        regraRisco.setCodigoRegra("REGRA-VALOR-ALTO");
        regraRisco.setNomeRegra("Regra de Valor Alto");
        regraRisco.setTipoRegra(RegraRisco.TipoRegra.VALOR);
        regraRisco.setCategoriaRegra(RegraRisco.CategoriaRegra.DETECTIVA);
        regraRisco.setNivelPrioridade(RegraRisco.NivelPrioridade.ALTA);
        regraRisco.setValorLimite(BigDecimal.valueOf(10000.00));
        regraRisco.setPesoRegra(10);
        regraRisco.setAtiva(true);

        // Setup avaliação de risco
        avaliacao = new AvaliacaoRisco();
        avaliacao.setId(1L);
        avaliacao.setCodigoAvaliacao("AVAL-001");
        avaliacao.setConta(conta);
        avaliacao.setTransacao(transacao);
        avaliacao.setTipoAvaliacao(AvaliacaoRisco.TipoAvaliacao.TRANSACAO);
        avaliacao.setStatus(AvaliacaoRisco.StatusAvaliacao.PENDENTE);
        avaliacao.setScoreRisco(25);
        avaliacao.setNivelRisco(AvaliacaoRisco.NivelRisco.BAIXO);
        avaliacao.setValorTransacao(BigDecimal.valueOf(1000.00));
        avaliacao.setDataAvaliacao(LocalDateTime.now());
    }

    @Test
    void testAvaliarRiscoTransacao_Sucesso() {
        // Arrange
        when(transacaoRepository.findById(1L)).thenReturn(Optional.of(transacao));
        when(contaRepository.findById(1L)).thenReturn(Optional.of(conta));
        when(regraRiscoRepository.findRegrasVigentes(any(LocalDateTime.class))).thenReturn(List.of(regraRisco));
        when(avaliacaoRiscoRepository.save(any(AvaliacaoRisco.class))).thenAnswer(inv -> inv.getArgument(0));

        // Act
        var resultado = gestaoRiscoService.avaliarRiscoTransacao(1L);

        // Assert
        assertNotNull(resultado);
        assertNotNull(resultado.getCodigoAvaliacao());
        assertTrue(resultado.getCodigoAvaliacao().startsWith("AVAL-"));
        assertEquals(1L, resultado.getContaId());
        assertEquals(1L, resultado.getTransacaoId());
        assertEquals("TRANSACAO", resultado.getTipoAvaliacao());
        assertEquals("PENDENTE", resultado.getStatus());

        verify(transacaoRepository).findById(1L);
        verify(contaRepository).findById(1L);
        verify(regraRiscoRepository, times(2)).findRegrasVigentes(any(LocalDateTime.class));
        verify(avaliacaoRiscoRepository).save(any(AvaliacaoRisco.class));
    }

    @Test
    void testAvaliarRiscoTransacao_TransacaoNaoEncontrada() {
        // Arrange
        when(transacaoRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            gestaoRiscoService.avaliarRiscoTransacao(1L);
        });

        verify(transacaoRepository).findById(1L);
        verify(contaRepository, never()).findById(any());
    }

    @Test
    void testAvaliarRiscoTransacao_ContaNaoEncontrada() {
        // Arrange
        when(transacaoRepository.findById(1L)).thenReturn(Optional.of(transacao));
        when(contaRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            gestaoRiscoService.avaliarRiscoTransacao(1L);
        });

        verify(transacaoRepository).findById(1L);
        verify(contaRepository).findById(1L);
    }

    @Test
    void testAvaliarRiscoConta_Sucesso() {
        // Arrange
        when(contaRepository.findById(1L)).thenReturn(Optional.of(conta));
        when(regraRiscoRepository.findRegrasVigentes(any(LocalDateTime.class))).thenReturn(List.of(regraRisco));
        when(avaliacaoRiscoRepository.save(any(AvaliacaoRisco.class))).thenAnswer(inv -> inv.getArgument(0));

        // Act
        var resultado = gestaoRiscoService.avaliarRiscoConta(1L);

        // Assert
        assertNotNull(resultado);
        assertNotNull(resultado.getCodigoAvaliacao());
        assertTrue(resultado.getCodigoAvaliacao().startsWith("AVAL-"));
        assertEquals(1L, resultado.getContaId());
        assertEquals("CONTA", resultado.getTipoAvaliacao());
        assertEquals("PENDENTE", resultado.getStatus());

        verify(contaRepository).findById(1L);
        verify(regraRiscoRepository, times(2)).findRegrasVigentes(any(LocalDateTime.class));
        verify(avaliacaoRiscoRepository).save(any(AvaliacaoRisco.class));
    }

    @Test
    void testAvaliarRiscoConta_ContaNaoEncontrada() {
        // Arrange
        when(contaRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            gestaoRiscoService.avaliarRiscoConta(1L);
        });

        verify(contaRepository).findById(1L);
    }

    @Test
    void testAprovarAvaliacao_Sucesso() {
        // Arrange
        when(avaliacaoRiscoRepository.findById(1L)).thenReturn(Optional.of(avaliacao));
        when(avaliacaoRiscoRepository.save(any(AvaliacaoRisco.class))).thenReturn(avaliacao);

        // Act
        var resultado = gestaoRiscoService.aprovarAvaliacao(1L, "USUARIO_APROVADOR");

        // Assert
        assertNotNull(resultado);
        assertEquals("APROVADA", resultado.getStatus());
        assertTrue(resultado.getAprovada());

        verify(avaliacaoRiscoRepository).findById(1L);
        verify(avaliacaoRiscoRepository).save(any(AvaliacaoRisco.class));
    }

    @Test
    void testAprovarAvaliacao_AvaliacaoNaoEncontrada() {
        // Arrange
        when(avaliacaoRiscoRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            gestaoRiscoService.aprovarAvaliacao(1L, "USUARIO_APROVADOR");
        });

        verify(avaliacaoRiscoRepository).findById(1L);
    }

    @Test
    void testRejeitarAvaliacao_Sucesso() {
        // Arrange
        when(avaliacaoRiscoRepository.findById(1L)).thenReturn(Optional.of(avaliacao));
        when(avaliacaoRiscoRepository.save(any(AvaliacaoRisco.class))).thenReturn(avaliacao);

        // Act
        var resultado = gestaoRiscoService.rejeitarAvaliacao(1L, "USUARIO_REJEITADOR", "Justificativa de teste");

        // Assert
        assertNotNull(resultado);
        assertEquals("REJEITADA", resultado.getStatus());
        assertTrue(resultado.getRejeitada());
        assertEquals("Justificativa de teste", resultado.getJustificativa());

        verify(avaliacaoRiscoRepository).findById(1L);
        verify(avaliacaoRiscoRepository).save(any(AvaliacaoRisco.class));
    }

    @Test
    void testRejeitarAvaliacao_AvaliacaoNaoEncontrada() {
        // Arrange
        when(avaliacaoRiscoRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            gestaoRiscoService.rejeitarAvaliacao(1L, "USUARIO_REJEITADOR", "Justificativa de teste");
        });

        verify(avaliacaoRiscoRepository).findById(1L);
    }

    @Test
    void testListarAvaliacoesPorConta() {
        // Arrange
        when(avaliacaoRiscoRepository.findByContaId(1L)).thenReturn(List.of(avaliacao));

        // Act
        var resultado = gestaoRiscoService.listarAvaliacoesPorConta(1L);

        // Assert
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("AVAL-001", resultado.get(0).getCodigoAvaliacao());

        verify(avaliacaoRiscoRepository).findByContaId(1L);
    }

    @Test
    void testListarAvaliacoesPendentes() {
        // Arrange
        when(avaliacaoRiscoRepository.findByStatus(AvaliacaoRisco.StatusAvaliacao.PENDENTE)).thenReturn(List.of(avaliacao));

        // Act
        var resultado = gestaoRiscoService.listarAvaliacoesPendentes();

        // Assert
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("AVAL-001", resultado.get(0).getCodigoAvaliacao());

        verify(avaliacaoRiscoRepository).findByStatus(AvaliacaoRisco.StatusAvaliacao.PENDENTE);
    }

    @Test
    void testDetectarLavagemDinheiro_TransacoesGrandesConcentradasEm24hSaoSuspeitas() {
        // Arrange: transação atual acima do limite de lavagem (10.000) e um
        // histórico com 5 transações grandes nas últimas 24h.
        transacao.setValor(BigDecimal.valueOf(15000.00));
        RegraRisco regraLavagem = new RegraRisco();
        regraLavagem.setTipoRegra(RegraRisco.TipoRegra.LAVAGEM_DINHEIRO);
        regraLavagem.setPesoRegra(50);
        regraLavagem.setScoreMinimo(200);
        regraLavagem.setScoreMaximo(300);

        List<Transacao> historico = transacoesGrandes(5, LocalDateTime.now().minusHours(2));

        when(transacaoRepository.findById(1L)).thenReturn(Optional.of(transacao));
        when(contaRepository.findById(1L)).thenReturn(Optional.of(conta));
        when(regraRiscoRepository.findRegrasVigentes(any(LocalDateTime.class))).thenReturn(List.of(regraLavagem));
        when(transacaoRepository.findByContaId(1L)).thenReturn(historico);
        when(avaliacaoRiscoRepository.save(any(AvaliacaoRisco.class))).thenAnswer(inv -> inv.getArgument(0));

        var resultado = gestaoRiscoService.avaliarRiscoTransacao(1L);

        // 60 pontos vêm do valor da transação (>1k, >5k e >10k = 10+20+30) e mais
        // 50 do peso da regra de lavagem de dinheiro, que deve disparar aqui.
        assertEquals(110, resultado.getScoreRisco());
    }

    @Test
    void testDetectarLavagemDinheiro_TransacoesGrandesAntigasNaoSaoSuspeitas() {
        // Mesmas 5 transações grandes do teste anterior, mas espalhadas ao longo
        // de anos (uma a cada ~6 meses) em vez de concentradas em 24h. Um padrão
        // de estruturação/lavagem exige concentração temporal: sem a janela de
        // tempo, qualquer cliente antigo com histórico de grandes valores seria
        // marcado como suspeito para sempre.
        transacao.setValor(BigDecimal.valueOf(15000.00));
        RegraRisco regraLavagem = new RegraRisco();
        regraLavagem.setTipoRegra(RegraRisco.TipoRegra.LAVAGEM_DINHEIRO);
        regraLavagem.setPesoRegra(50);
        regraLavagem.setScoreMinimo(200);
        regraLavagem.setScoreMaximo(300);

        List<Transacao> historico = transacoesGrandesEspalhadas(5);

        when(transacaoRepository.findById(1L)).thenReturn(Optional.of(transacao));
        when(contaRepository.findById(1L)).thenReturn(Optional.of(conta));
        when(regraRiscoRepository.findRegrasVigentes(any(LocalDateTime.class))).thenReturn(List.of(regraLavagem));
        when(transacaoRepository.findByContaId(1L)).thenReturn(historico);
        when(avaliacaoRiscoRepository.save(any(AvaliacaoRisco.class))).thenAnswer(inv -> inv.getArgument(0));

        var resultado = gestaoRiscoService.avaliarRiscoTransacao(1L);

        // Apenas os 60 pontos do valor da transação; a regra de lavagem de
        // dinheiro não deve disparar porque as transações grandes não estão
        // concentradas em uma janela de tempo suspeita.
        assertEquals(60, resultado.getScoreRisco());
    }

    private List<Transacao> transacoesGrandes(int quantidade, LocalDateTime dataBase) {
        List<Transacao> historico = new java.util.ArrayList<>();
        for (int i = 0; i < quantidade; i++) {
            Transacao t = new Transacao();
            t.setValor(BigDecimal.valueOf(15000.00));
            t.setDataTransacao(dataBase.minusMinutes(i));
            historico.add(t);
        }
        return historico;
    }

    private List<Transacao> transacoesGrandesEspalhadas(int quantidade) {
        List<Transacao> historico = new java.util.ArrayList<>();
        for (int i = 1; i <= quantidade; i++) {
            Transacao t = new Transacao();
            t.setValor(BigDecimal.valueOf(15000.00));
            t.setDataTransacao(LocalDateTime.now().minusMonths(6L * i));
            historico.add(t);
        }
        return historico;
    }

    @Test
    void testListarAvaliacoesCriticas() {
        // Arrange
        avaliacao.setNivelRisco(AvaliacaoRisco.NivelRisco.CRITICO);
        when(avaliacaoRiscoRepository.findByNivelRisco(AvaliacaoRisco.NivelRisco.CRITICO)).thenReturn(List.of(avaliacao));

        // Act
        var resultado = gestaoRiscoService.listarAvaliacoesCriticas();

        // Assert
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("AVAL-001", resultado.get(0).getCodigoAvaliacao());
        assertEquals("CRITICO", resultado.get(0).getNivelRisco());

        verify(avaliacaoRiscoRepository).findByNivelRisco(AvaliacaoRisco.NivelRisco.CRITICO);
    }
}
