package com.aurix.platform.products.service;

import com.aurix.platform.products.dto.AvaliacaoElegibilidadeResponse;
import com.aurix.platform.products.dto.PerfilClienteRequest;
import com.aurix.platform.products.dto.RegraElegibilidadeRequest;
import com.aurix.platform.products.entity.Produto;
import com.aurix.platform.products.entity.RegraElegibilidade;
import com.aurix.platform.products.repository.RegraElegibilidadeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ElegibilidadeServiceTest {

    @Mock
    private RegraElegibilidadeRepository regraRepository;

    @Mock
    private ProdutoService produtoService;

    private ElegibilidadeService service;

    @BeforeEach
    void setUp() {
        service = new ElegibilidadeService(regraRepository, produtoService);
    }

    private Produto produto() {
        Produto produto = new Produto();
        produto.setId(1L);
        produto.setCodigo("CONSIGNADO_AUX");
        produto.setNome("Consignado Auxílio");
        return produto;
    }

    private RegraElegibilidade regra(RegraElegibilidade.TipoRegra tipo,
                                     RegraElegibilidade.Comparador comparador,
                                     BigDecimal valorNumerico, String valorTexto) {
        RegraElegibilidade regra = new RegraElegibilidade();
        regra.setId(1L);
        regra.setProdutoId(1L);
        regra.setTipoRegra(tipo);
        regra.setComparador(comparador);
        regra.setValorNumerico(valorNumerico);
        regra.setValorTexto(valorTexto);
        regra.setAtiva(true);
        return regra;
    }

    @Test
    void avaliar_deveAprovarPerfilQueAtendeTodasRegras() {
        when(produtoService.buscarEntidade(1L)).thenReturn(produto());
        when(regraRepository.findByProdutoIdAndAtivaTrue(1L)).thenReturn(List.of(
            regra(RegraElegibilidade.TipoRegra.RENDA_MINIMA,
                RegraElegibilidade.Comparador.MAIOR_IGUAL, new BigDecimal("2000"), null),
            regra(RegraElegibilidade.TipoRegra.NAO_NEGATIVADO,
                RegraElegibilidade.Comparador.IGUAL, null, null)
        ));

        PerfilClienteRequest perfil = new PerfilClienteRequest(
            new BigDecimal("3500"), 30, 700, "OURO", "PF", false);

        AvaliacaoElegibilidadeResponse avaliacao = service.avaliar(1L, perfil);

        assertThat(avaliacao.apto()).isTrue();
        assertThat(avaliacao.codigoProduto()).isEqualTo("CONSIGNADO_AUX");
        assertThat(avaliacao.regras()).hasSize(2);
        assertThat(avaliacao.regras()).allMatch(AvaliacaoElegibilidadeResponse.ResultadoRegra::atendida);
    }

    @Test
    void avaliar_deveReprovarPerfilComRendaAbaixoDoMinimo() {
        when(produtoService.buscarEntidade(1L)).thenReturn(produto());
        when(regraRepository.findByProdutoIdAndAtivaTrue(1L)).thenReturn(List.of(
            regra(RegraElegibilidade.TipoRegra.RENDA_MINIMA,
                RegraElegibilidade.Comparador.MAIOR_IGUAL, new BigDecimal("2000"), null)
        ));

        PerfilClienteRequest perfil = new PerfilClienteRequest(
            new BigDecimal("1000"), 30, 700, "OURO", "PF", false);

        AvaliacaoElegibilidadeResponse avaliacao = service.avaliar(1L, perfil);

        assertThat(avaliacao.apto()).isFalse();
        assertThat(avaliacao.regras().get(0).atendida()).isFalse();
        assertThat(avaliacao.regras().get(0).valorEsperado()).isEqualTo("renda >= 2000");
        assertThat(avaliacao.regras().get(0).valorInformado()).isEqualTo("1000");
    }

    @Test
    void avaliar_deveReprovarPerfilNegativado() {
        when(produtoService.buscarEntidade(1L)).thenReturn(produto());
        when(regraRepository.findByProdutoIdAndAtivaTrue(1L)).thenReturn(List.of(
            regra(RegraElegibilidade.TipoRegra.NAO_NEGATIVADO,
                RegraElegibilidade.Comparador.IGUAL, null, null)
        ));

        PerfilClienteRequest perfil = new PerfilClienteRequest(
            new BigDecimal("3500"), 30, 700, "OURO", "PF", true);

        AvaliacaoElegibilidadeResponse avaliacao = service.avaliar(1L, perfil);

        assertThat(avaliacao.apto()).isFalse();
    }

    @Test
    void avaliar_segmento_consideraComparadorIgual() {
        when(produtoService.buscarEntidade(1L)).thenReturn(produto());
        when(regraRepository.findByProdutoIdAndAtivaTrue(1L)).thenReturn(List.of(
            regra(RegraElegibilidade.TipoRegra.SEGMENTO,
                RegraElegibilidade.Comparador.IGUAL, null, "OURO")
        ));

        AvaliacaoElegibilidadeResponse apto = service.avaliar(1L,
            new PerfilClienteRequest(null, null, null, "OURO", null, null));
        AvaliacaoElegibilidadeResponse reprovado = service.avaliar(1L,
            new PerfilClienteRequest(null, null, null, "PRATA", null, null));

        assertThat(apto.apto()).isTrue();
        assertThat(reprovado.apto()).isFalse();
    }

    @Test
    void adicionarRegra_deveExigirValorParaRegraQuantitativa() {
        when(produtoService.buscarEntidade(1L)).thenReturn(produto());
        RegraElegibilidadeRequest request = new RegraElegibilidadeRequest(
            RegraElegibilidade.TipoRegra.RENDA_MINIMA,
            RegraElegibilidade.Comparador.MAIOR_IGUAL, null, null, "Renda mínima");

        assertThatThrownBy(() -> service.adicionarRegra(1L, request))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("exige valorNumerico ou valorTexto");
        verify(regraRepository, never()).save(any(RegraElegibilidade.class));
    }

    @Test
    void adicionarRegra_devePersistirRegraValida() {
        when(produtoService.buscarEntidade(1L)).thenReturn(produto());
        RegraElegibilidadeRequest request = new RegraElegibilidadeRequest(
            RegraElegibilidade.TipoRegra.RENDA_MINIMA,
            RegraElegibilidade.Comparador.MAIOR_IGUAL, new BigDecimal("2000"), null, "Renda mínima");
        when(regraRepository.save(any(RegraElegibilidade.class))).thenAnswer(inv -> {
            RegraElegibilidade r = inv.getArgument(0);
            r.setId(9L);
            return r;
        });

        RegraElegibilidade regra = service.adicionarRegra(1L, request);

        assertThat(regra.getId()).isEqualTo(9L);
        assertThat(regra.getProdutoId()).isEqualTo(1L);
        assertThat(regra.getTipoRegra()).isEqualTo(RegraElegibilidade.TipoRegra.RENDA_MINIMA);
        assertThat(regra.getAtiva()).isTrue();
    }

    @Test
    void removerRegra_deveExigirRegraDoProduto() {
        when(produtoService.buscarEntidade(1L)).thenReturn(produto());
        RegraElegibilidade outraRegra = new RegraElegibilidade();
        outraRegra.setId(2L);
        outraRegra.setProdutoId(999L);
        when(regraRepository.findById(2L)).thenReturn(java.util.Optional.of(outraRegra));

        assertThatThrownBy(() -> service.removerRegra(1L, 2L))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("não encontrada");
    }
}
