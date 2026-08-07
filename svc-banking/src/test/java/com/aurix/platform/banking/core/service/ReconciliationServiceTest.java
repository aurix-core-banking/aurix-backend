package com.aurix.platform.banking.core.service;

import com.aurix.platform.banking.core.entity.ConciliacaoBancaria;
import com.aurix.platform.banking.core.entity.ItemConciliacao;
import com.aurix.platform.banking.core.entity.Liquidacao;
import com.aurix.platform.banking.core.entity.Reconciliacao;
import com.aurix.platform.banking.core.repository.ConciliacaoBancariaRepository;
import com.aurix.platform.shared.repository.ContaRepository;
import com.aurix.platform.banking.core.repository.ItemConciliacaoRepository;
import com.aurix.platform.banking.core.repository.LiquidacaoRepository;
import com.aurix.platform.banking.core.repository.ReconciliacaoRepository;
import com.aurix.platform.shared.entity.Conta;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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
class ReconciliationServiceTest {

    @Mock
    private ReconciliacaoRepository reconciliacaoRepository;
    @Mock
    private ConciliacaoBancariaRepository conciliacaoRepository;
    @Mock
    private ItemConciliacaoRepository itemRepository;
    @Mock
    private LiquidacaoRepository liquidacaoRepository;
    @Mock
    private ContaRepository contaRepository;

    private ReconciliationService service;

    @BeforeEach
    void setUp() {
        service = new ReconciliationService(reconciliacaoRepository, conciliacaoRepository,
            itemRepository, liquidacaoRepository, contaRepository);
        lenient().when(conciliacaoRepository.save(any(ConciliacaoBancaria.class)))
            .thenAnswer(i -> i.getArgument(0));
        lenient().when(reconciliacaoRepository.save(any(Reconciliacao.class)))
            .thenAnswer(i -> i.getArgument(0));
        lenient().when(itemRepository.save(any(ItemConciliacao.class)))
            .thenAnswer(i -> i.getArgument(0));
    }

    @Test
    void criarConciliacao_deveCriarComStatusPendente() {
        Conta conta = new Conta();
        conta.setId(1L);
        when(contaRepository.findById(1L)).thenReturn(Optional.of(conta));

        ConciliacaoBancaria result = service.criarConciliacao(1L, "DIARIA", "extrato-1.csv");

        assertNotNull(result);
        assertTrue(result.getCodigoConciliacao().startsWith("CONC-"));
        assertEquals(ConciliacaoBancaria.TipoConciliacao.DIARIA, result.getTipoConciliacao());
        assertEquals(ConciliacaoBancaria.StatusConciliacao.PENDENTE, result.getStatus());
        assertEquals("extrato-1.csv", result.getArquivoExtrato());
        verify(conciliacaoRepository).save(any(ConciliacaoBancaria.class));
    }

    @Test
    void criarConciliacao_deveLancarExcecaoQuandoContaNaoEncontrada() {
        when(contaRepository.findById(999L)).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class,
            () -> service.criarConciliacao(999L, "DIARIA", null));
    }

    @Test
    void processarConciliacao_deveConciliarLiquidacoes() {
        ConciliacaoBancaria c = new ConciliacaoBancaria();
        c.setId(10L);
        c.setStatus(ConciliacaoBancaria.StatusConciliacao.PENDENTE);
        when(conciliacaoRepository.findById(10L)).thenReturn(Optional.of(c));

        Liquidacao l1 = new Liquidacao();
        l1.setId(100L);
        l1.setCodigoLiquidacao("LIQ-001");
        l1.setValorLiquidacao(new BigDecimal("1000.00"));
        l1.setDataLiquidacao(LocalDateTime.now());
        l1.setStatus(Liquidacao.StatusLiquidacao.LIQUIDADA);

        when(liquidacaoRepository.findByStatus(Liquidacao.StatusLiquidacao.LIQUIDADA))
            .thenReturn(List.of(l1));
        when(itemRepository.findByLiquidacaoId(100L)).thenReturn(List.of());

        ConciliacaoBancaria result = service.processarConciliacao(10L);

        assertEquals(ConciliacaoBancaria.StatusConciliacao.CONCLUIDA, result.getStatus());
        assertEquals(1, result.getQuantidadeMovimentosSistema());
        assertEquals(1, result.getQuantidadeConciliados());
        assertEquals(0, result.getQuantidadeDivergencias());
        verify(itemRepository).save(any(ItemConciliacao.class));
    }

    @Test
    void processarConciliacao_deveIgnorarLiquidacoesJaConciliadas() {
        ConciliacaoBancaria c = new ConciliacaoBancaria();
        c.setId(10L);
        c.setStatus(ConciliacaoBancaria.StatusConciliacao.PENDENTE);
        when(conciliacaoRepository.findById(10L)).thenReturn(Optional.of(c));

        Liquidacao l1 = new Liquidacao();
        l1.setId(100L);
        l1.setValorLiquidacao(new BigDecimal("1000.00"));
        l1.setDataLiquidacao(LocalDateTime.now());
        l1.setStatus(Liquidacao.StatusLiquidacao.LIQUIDADA);

        when(liquidacaoRepository.findByStatus(Liquidacao.StatusLiquidacao.LIQUIDADA))
            .thenReturn(List.of(l1));
        when(itemRepository.findByLiquidacaoId(100L))
            .thenReturn(List.of(new ItemConciliacao()));

        ConciliacaoBancaria result = service.processarConciliacao(10L);

        assertEquals(1, result.getQuantidadeMovimentosSistema());
        assertEquals(0, result.getQuantidadeConciliados());
        verify(itemRepository, never()).save(any(ItemConciliacao.class));
    }

    @Test
    void criarReconciliacaoGeral_deveCriarComStatusPendente() {
        Reconciliacao result = service.criarReconciliacaoGeral("PIX_DIARIA");

        assertNotNull(result);
        assertTrue(result.getCodigoReconciliacao().startsWith("RECONC-"));
        assertEquals(Reconciliacao.TipoReconciliacao.PIX_DIARIA, result.getTipoReconciliacao());
        assertEquals(Reconciliacao.StatusReconciliacao.PENDENTE, result.getStatus());
        assertTrue(result.getProcessamentoAutomatico());
        verify(reconciliacaoRepository).save(any(Reconciliacao.class));
    }

    @Test
    void processarReconciliacaoGeral_deveAgregarConciliacoes() {
        Reconciliacao r = new Reconciliacao();
        r.setId(20L);
        r.setDataReferencia(LocalDateTime.now());
        r.setStatus(Reconciliacao.StatusReconciliacao.PENDENTE);
        when(reconciliacaoRepository.findById(20L)).thenReturn(Optional.of(r));

        ConciliacaoBancaria c1 = new ConciliacaoBancaria();
        c1.setStatus(ConciliacaoBancaria.StatusConciliacao.CONCLUIDA);
        c1.setQuantidadeMovimentosSistema(5);
        c1.setQuantidadeConciliados(5);
        c1.setQuantidadeDivergencias(0);

        when(conciliacaoRepository.findConciliacoesPorPeriodo(any(), any()))
            .thenReturn(List.of(c1));

        Reconciliacao result = service.processarReconciliacaoGeral(20L);

        assertEquals(Reconciliacao.StatusReconciliacao.CONCLUIDA, result.getStatus());
        assertEquals(5, result.getQuantidadeTransacoes());
        assertEquals(5, result.getQuantidadeSucesso());
        assertEquals(0, result.getQuantidadeFalha());
    }

    @Test
    void processarReconciliacaoGeral_deveDetectarDivergencia() {
        Reconciliacao r = new Reconciliacao();
        r.setId(20L);
        r.setDataReferencia(LocalDateTime.now());
        r.setStatus(Reconciliacao.StatusReconciliacao.PENDENTE);
        when(reconciliacaoRepository.findById(20L)).thenReturn(Optional.of(r));

        ConciliacaoBancaria c1 = new ConciliacaoBancaria();
        c1.setStatus(ConciliacaoBancaria.StatusConciliacao.DIVERGENCIA);
        c1.setQuantidadeMovimentosSistema(10);
        c1.setQuantidadeConciliados(8);
        c1.setQuantidadeDivergencias(2);

        when(conciliacaoRepository.findConciliacoesPorPeriodo(any(), any()))
            .thenReturn(List.of(c1));

        Reconciliacao result = service.processarReconciliacaoGeral(20L);

        assertEquals(Reconciliacao.StatusReconciliacao.DIVERGENCIA, result.getStatus());
        assertEquals(10, result.getQuantidadeTransacoes());
        assertEquals(8, result.getQuantidadeSucesso());
        assertEquals(2, result.getQuantidadeFalha());
    }

    @Test
    void listarConciliacoesPendentes_deveChamarRepository() {
        service.listarConciliacoesPendentes();
        verify(conciliacaoRepository).findConciliacoesPendentesParaProcessamento();
    }

    @Test
    void listarConciliacoesComDivergencia_deveChamarRepository() {
        service.listarConciliacoesComDivergencia();
        verify(conciliacaoRepository).findConciliacoesComDivergencia();
    }

    @Test
    void buscarConciliacaoPorId_deveRetornarOptional() {
        when(conciliacaoRepository.findById(1L)).thenReturn(Optional.of(new ConciliacaoBancaria()));
        assertTrue(service.buscarConciliacaoPorId(1L).isPresent());
    }
}
