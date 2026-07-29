package com.aurix.platform.banking.core.service;

import com.aurix.platform.banking.core.entity.*;
import com.aurix.platform.banking.core.repository.*;
import com.aurix.platform.banking.core.dto.*;
import com.aurix.platform.shared.entity.Conta;
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
class SistemaRemuneracaoServiceTest {

    @Mock
    private ProdutoFinanceiroRepository produtoFinanceiroRepository;

    @Mock
    private AplicacaoFinanceiraRepository aplicacaoFinanceiraRepository;

    @Mock
    private ContaRepository contaRepository;

    @Mock
    private RemuneracaoRepository remuneracaoRepository;

    @Mock
    private CalculoRemuneracaoRepository calculoRemuneracaoRepository;

    @Mock
    private HistoricoRemuneracaoRepository historicoRemuneracaoRepository;

    @InjectMocks
    private SistemaRemuneracaoService sistemaRemuneracaoService;

    private Conta conta;
    private ProdutoFinanceiro produto;
    private AplicacaoFinanceira aplicacao;

    @BeforeEach
    void setUp() {
        // Setup conta
        conta = new Conta();
        conta.setId(1L);
        conta.setSaldoAtual(BigDecimal.valueOf(10000.00));
        conta.setNumeroConta("12345-6");

        // Setup produto financeiro
        produto = new ProdutoFinanceiro();
        produto.setId(1L);
        produto.setCodigoProduto("CDB-001");
        produto.setNomeProduto("CDB 100% CDI");
        produto.setTipoProduto(ProdutoFinanceiro.TipoProduto.CDB);
        produto.setCategoriaProduto(ProdutoFinanceiro.CategoriaProduto.RENDA_FIXA);
        produto.setValorMinimoAplicacao(BigDecimal.valueOf(1000.00));
        produto.setValorMaximoAplicacao(BigDecimal.valueOf(10000000.00));
        produto.setTaxaRemuneracao(BigDecimal.valueOf(0.0100));
        produto.setTipoRemuneracao(ProdutoFinanceiro.TipoRemuneracao.INDEXADA);
        produto.setPeriodicidadeRemuneracao(ProdutoFinanceiro.PeriodicidadeRemuneracao.DIARIA);
        produto.setPrazoMinimoDias(30);
        produto.setPrazoMaximoDias(1095);
        produto.setPermiteResgateAntecipado(true);
        produto.setTaxaResgateAntecipado(BigDecimal.valueOf(0.0050));
        produto.setAtivo(true);
        produto.setDisponivelPublico(true);

        // Setup aplicação
        aplicacao = new AplicacaoFinanceira();
        aplicacao.setId(1L);
        aplicacao.setCodigoAplicacao("APL-001");
        aplicacao.setConta(conta);
        aplicacao.setProdutoFinanceiro(produto);
        aplicacao.setStatus(AplicacaoFinanceira.StatusAplicacao.ATIVA);
        aplicacao.setValorAplicacao(BigDecimal.valueOf(1000.00));
        aplicacao.setValorAtual(BigDecimal.valueOf(1000.00));
        aplicacao.setTaxaRemuneracao(BigDecimal.valueOf(0.0100));
        aplicacao.setDataAplicacao(LocalDateTime.now().minusDays(1));
        aplicacao.setDataVencimento(LocalDateTime.now().plusDays(29));
        aplicacao.setPrazoDias(30);
        aplicacao.setDiasDecorridos(1);
        aplicacao.setDiasRestantes(29);
    }

    @Test
    void testCriarAplicacao_Sucesso() {
        // Arrange
        when(contaRepository.findById(1L)).thenReturn(Optional.of(conta));
        when(produtoFinanceiroRepository.findById(1L)).thenReturn(Optional.of(produto));
        when(aplicacaoFinanceiraRepository.save(any(AplicacaoFinanceira.class))).thenReturn(aplicacao);
        when(contaRepository.save(any(Conta.class))).thenReturn(conta);

        // Act
        AplicacaoFinanceiraDTO resultado = sistemaRemuneracaoService.criarAplicacao(1L, 1L, BigDecimal.valueOf(1000.00), "USUARIO_TESTE");

        // Assert
        assertNotNull(resultado);
        assertEquals("APL-001", resultado.getCodigoAplicacao());
        assertEquals(1L, resultado.getContaId());
        assertEquals(1L, resultado.getProdutoFinanceiroId());
        assertEquals(AplicacaoFinanceira.StatusAplicacao.ATIVA, resultado.getStatus());
        assertEquals(BigDecimal.valueOf(1000.00), resultado.getValorAplicado());

        verify(contaRepository).findById(1L);
        verify(produtoFinanceiroRepository).findById(1L);
        verify(aplicacaoFinanceiraRepository).save(any(AplicacaoFinanceira.class));
        verify(contaRepository).save(any(Conta.class));
    }

    @Test
    void testCriarAplicacao_ContaNaoEncontrada() {
        // Arrange
        when(contaRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            sistemaRemuneracaoService.criarAplicacao(1L, 1L, BigDecimal.valueOf(1000.00), "USUARIO_TESTE");
        });

        verify(contaRepository).findById(1L);
        verify(produtoFinanceiroRepository, never()).findById(any());
    }

    @Test
    void testCriarAplicacao_ProdutoNaoEncontrado() {
        // Arrange
        when(contaRepository.findById(1L)).thenReturn(Optional.of(conta));
        when(produtoFinanceiroRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            sistemaRemuneracaoService.criarAplicacao(1L, 1L, BigDecimal.valueOf(1000.00), "USUARIO_TESTE");
        });

        verify(contaRepository).findById(1L);
        verify(produtoFinanceiroRepository).findById(1L);
    }

    @Test
    void testCriarAplicacao_ProdutoInativo() {
        // Arrange
        produto.setAtivo(false);
        when(contaRepository.findById(1L)).thenReturn(Optional.of(conta));
        when(produtoFinanceiroRepository.findById(1L)).thenReturn(Optional.of(produto));

        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            sistemaRemuneracaoService.criarAplicacao(1L, 1L, BigDecimal.valueOf(1000.00), "USUARIO_TESTE");
        });

        verify(contaRepository).findById(1L);
        verify(produtoFinanceiroRepository).findById(1L);
    }

    @Test
    void testCriarAplicacao_ValorMenorQueMinimo() {
        // Arrange
        when(contaRepository.findById(1L)).thenReturn(Optional.of(conta));
        when(produtoFinanceiroRepository.findById(1L)).thenReturn(Optional.of(produto));

        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            sistemaRemuneracaoService.criarAplicacao(1L, 1L, BigDecimal.valueOf(500.00), "USUARIO_TESTE");
        });

        verify(contaRepository).findById(1L);
        verify(produtoFinanceiroRepository).findById(1L);
    }

    @Test
    void testCriarAplicacao_SaldoInsuficiente() {
        // Arrange
        conta.setSaldoAtual(BigDecimal.valueOf(500.00));
        when(contaRepository.findById(1L)).thenReturn(Optional.of(conta));
        when(produtoFinanceiroRepository.findById(1L)).thenReturn(Optional.of(produto));

        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            sistemaRemuneracaoService.criarAplicacao(1L, 1L, BigDecimal.valueOf(1000.00), "USUARIO_TESTE");
        });

        verify(contaRepository).findById(1L);
        verify(produtoFinanceiroRepository).findById(1L);
    }

    @Test
    void testResgatarAplicacao_Sucesso() {
        // Arrange
        when(aplicacaoFinanceiraRepository.findById(1L)).thenReturn(Optional.of(aplicacao));
        when(aplicacaoFinanceiraRepository.save(any(AplicacaoFinanceira.class))).thenReturn(aplicacao);
        when(contaRepository.save(any(Conta.class))).thenReturn(conta);

        // Act
        AplicacaoFinanceiraDTO resultado = sistemaRemuneracaoService.resgatarAplicacao(1L, BigDecimal.valueOf(1000.00), "USUARIO_TESTE");

        // Assert
        assertNotNull(resultado);
        assertEquals(AplicacaoFinanceira.StatusAplicacao.RESGATADA, resultado.getStatus());
        assertEquals(BigDecimal.valueOf(1000.00), resultado.getValorResgate());

        verify(aplicacaoFinanceiraRepository).findById(1L);
        verify(aplicacaoFinanceiraRepository).save(any(AplicacaoFinanceira.class));
        verify(contaRepository).save(any(Conta.class));
    }

    @Test
    void testResgatarAplicacao_AplicacaoNaoEncontrada() {
        // Arrange
        when(aplicacaoFinanceiraRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            sistemaRemuneracaoService.resgatarAplicacao(1L, BigDecimal.valueOf(1000.00), "USUARIO_TESTE");
        });

        verify(aplicacaoFinanceiraRepository).findById(1L);
    }

    @Test
    void testResgatarAplicacao_AplicacaoNaoAtiva() {
        // Arrange
        aplicacao.setStatus(AplicacaoFinanceira.StatusAplicacao.RESGATADA);
        when(aplicacaoFinanceiraRepository.findById(1L)).thenReturn(Optional.of(aplicacao));

        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            sistemaRemuneracaoService.resgatarAplicacao(1L, BigDecimal.valueOf(1000.00), "USUARIO_TESTE");
        });

        verify(aplicacaoFinanceiraRepository).findById(1L);
    }

    @Test
    void testResgatarAplicacao_ValorMaiorQueAtual() {
        // Arrange
        when(aplicacaoFinanceiraRepository.findById(1L)).thenReturn(Optional.of(aplicacao));

        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            sistemaRemuneracaoService.resgatarAplicacao(1L, BigDecimal.valueOf(2000.00), "USUARIO_TESTE");
        });

        verify(aplicacaoFinanceiraRepository).findById(1L);
    }

    @Test
    void testListarAplicacoesPorConta() {
        // Arrange
        when(aplicacaoFinanceiraRepository.findByContaId(1L)).thenReturn(List.of(aplicacao));

        // Act
        List<AplicacaoFinanceiraDTO> resultado = sistemaRemuneracaoService.listarAplicacoesPorConta(1L);

        // Assert
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("APL-001", resultado.get(0).getCodigoAplicacao());

        verify(aplicacaoFinanceiraRepository).findByContaId(1L);
    }

    @Test
    void testListarAplicacoesAtivas() {
        // Arrange
        when(aplicacaoFinanceiraRepository.findByStatus(AplicacaoFinanceira.StatusAplicacao.ATIVA)).thenReturn(List.of(aplicacao));

        // Act
        List<AplicacaoFinanceiraDTO> resultado = sistemaRemuneracaoService.listarAplicacoesAtivas();

        // Assert
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("APL-001", resultado.get(0).getCodigoAplicacao());

        verify(aplicacaoFinanceiraRepository).findByStatus(AplicacaoFinanceira.StatusAplicacao.ATIVA);
    }

    @Test
    void testListarProdutosDisponiveis() {
        // Arrange
        when(produtoFinanceiroRepository.findProdutosDisponiveisPublico(true)).thenReturn(List.of(produto));

        // Act
        List<ProdutoFinanceiroDTO> resultado = sistemaRemuneracaoService.listarProdutosDisponiveis();

        // Assert
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("CDB-001", resultado.get(0).getCodigoProduto());

        verify(produtoFinanceiroRepository).findProdutosDisponiveisPublico(true);
    }
}
