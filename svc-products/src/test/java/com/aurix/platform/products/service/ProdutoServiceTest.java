package com.aurix.platform.products.service;

import com.aurix.platform.products.dto.ProdutoRequest;
import com.aurix.platform.products.dto.VersaoProdutoResponse;
import com.aurix.platform.products.entity.Produto;
import com.aurix.platform.products.entity.VersaoProduto;
import com.aurix.platform.products.exception.ProdutoNaoEncontradoException;
import com.aurix.platform.products.repository.ProdutoRepository;
import com.aurix.platform.products.repository.VersaoProdutoRepository;
import com.aurix.platform.shared.event.BaseEvent;
import com.aurix.platform.shared.event.EventPublisher;
import com.aurix.platform.shared.event.ProdutoCriadoEvent;
import com.aurix.platform.shared.event.Topics;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProdutoServiceTest {

    @Mock
    private ProdutoRepository produtoRepository;

    @Mock
    private VersaoProdutoRepository versaoRepository;

    @Mock
    private EventPublisher eventPublisher;

    private ProdutoService service;

    @BeforeEach
    void setUp() {
        service = new ProdutoService(produtoRepository, versaoRepository, eventPublisher,
            new ObjectMapper().findAndRegisterModules());
    }

    private ProdutoRequest request() {
        return new ProdutoRequest("CONSIGNADO_AUX", "Consignado Auxílio",
            "Empréstimo consignado em folha", Produto.TipoProduto.CONSIGNADO,
            "Servidores públicos", "Vínculo ativo com a fonte pagadora", null,
            LocalDate.now(), null);
    }

    @Test
    void criar_devePersistirProdutoEVersionar() {
        when(produtoRepository.existsByCodigo("CONSIGNADO_AUX")).thenReturn(false);
        when(produtoRepository.save(any(Produto.class))).thenAnswer(inv -> {
            Produto p = inv.getArgument(0);
            p.setId(10L);
            return p;
        });

        Produto produto = service.criar(request());

        assertThat(produto.getId()).isEqualTo(10L);
        assertThat(produto.getCodigo()).isEqualTo("CONSIGNADO_AUX");
        assertThat(produto.getStatus()).isEqualTo(Produto.StatusProduto.RASCUNHO);
        assertThat(produto.getNumeroVersao()).isEqualTo(1);
        assertThat(produto.getAtivo()).isTrue();
        verify(versaoRepository).save(any(VersaoProduto.class));
        verify(eventPublisher).publish(anyString(), any(ProdutoCriadoEvent.class));
    }

    @Test
    void criar_deveRejeitarCodigoDuplicado() {
        when(produtoRepository.existsByCodigo("CONSIGNADO_AUX")).thenReturn(true);

        assertThatThrownBy(() -> service.criar(request()))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("Já existe produto com o código");
        verify(produtoRepository, never()).save(any(Produto.class));
    }

    @Test
    void publicar_deveAtivarProdutoJaPublicado() {
        Produto produto = new Produto();
        produto.setId(5L);
        produto.setCodigo("CONTACORRENTE_PF");
        produto.setStatus(Produto.StatusProduto.RASCUNHO);
        when(produtoRepository.findById(5L)).thenReturn(Optional.of(produto));
        when(produtoRepository.save(any(Produto.class))).thenAnswer(inv -> inv.getArgument(0));

        Produto publicado = service.publicar(5L);

        assertThat(publicado.getStatus()).isEqualTo(Produto.StatusProduto.ATIVO);
        assertThat(publicado.getAtivo()).isTrue();
    }

    @Test
    void descontinuar_deveMarcarProdutoComoDescontinuado() {
        Produto produto = new Produto();
        produto.setId(7L);
        produto.setCodigo("CONSIGNADO_AUX");
        produto.setTipoProduto(Produto.TipoProduto.CONSIGNADO);
        produto.setNumeroVersao(1);
        produto.setStatus(Produto.StatusProduto.ATIVO);
        when(produtoRepository.findById(7L)).thenReturn(Optional.of(produto));
        when(produtoRepository.save(any(Produto.class))).thenAnswer(inv -> inv.getArgument(0));

        Produto descontinuado = service.descontinuar(7L);

        assertThat(descontinuado.getStatus()).isEqualTo(Produto.StatusProduto.DESCONTINUADO);
        assertThat(descontinuado.getAtivo()).isFalse();
        verify(eventPublisher).publish(eq(Topics.PRODUTO_DESCONTINUADO), any(BaseEvent.class));
    }

    @Test
    void buscarInexistente_deveLancarExcecao() {
        when(produtoRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.buscarEntidade(99L))
            .isInstanceOf(ProdutoNaoEncontradoException.class);
    }

    @Test
    void listarVersoes_deveRetornarHistoricoOrdenado() {
        VersaoProduto versao = new VersaoProduto();
        versao.setId(1L);
        versao.setProdutoId(3L);
        versao.setNumeroVersao(2);
        versao.setStatus(VersaoProduto.StatusVersao.PUBLICADA);
        versao.setChangelog("Atualização");
        when(produtoRepository.existsById(3L)).thenReturn(true);
        when(versaoRepository.findByProdutoIdOrderByNumeroVersaoDesc(3L))
            .thenReturn(List.of(versao));

        List<VersaoProdutoResponse> versoes = service.listarVersoes(3L);

        assertThat(versoes).hasSize(1);
        assertThat(versoes.get(0).numeroVersao()).isEqualTo(2);
    }

    @Test
    void listarPorTipo_deveFiltrarPeloTipo() {
        Produto produto = new Produto();
        produto.setId(1L);
        when(produtoRepository.findByTipoProduto(Produto.TipoProduto.SEGURO))
            .thenReturn(List.of(produto));

        List<Produto> produtos = service.listar(Produto.TipoProduto.SEGURO, null, null);

        assertThat(produtos).hasSize(1);
        assertThat(produtos.get(0).getId()).isEqualTo(1L);
    }

    @Test
    void criar_devePreencherRequisitosJson() {
        ProdutoRequest comRequisitos = new ProdutoRequest("INVESTIMENTO_CDB", "CDB",
            "Renda fixa", Produto.TipoProduto.INVESTIMENTO, null, null,
            "{\"aplicacaoMinima\":100}", LocalDate.now(), null);
        when(produtoRepository.existsByCodigo("INVESTIMENTO_CDB")).thenReturn(false);
        when(produtoRepository.save(any(Produto.class))).thenAnswer(inv -> inv.getArgument(0));

        Produto produto = service.criar(comRequisitos);

        assertThat(produto.getRequisitos()).contains("aplicacaoMinima");
    }
}
