package com.aurix.platform.products.service;

import com.aurix.platform.products.dto.AvaliacaoElegibilidadeResponse;
import com.aurix.platform.products.dto.PerfilClienteRequest;
import com.aurix.platform.products.dto.RegraElegibilidadeRequest;
import com.aurix.platform.products.dto.RegraElegibilidadeResponse;
import com.aurix.platform.products.entity.Produto;
import com.aurix.platform.products.entity.RegraElegibilidade;
import com.aurix.platform.products.exception.ProdutoNaoEncontradoException;
import com.aurix.platform.products.repository.RegraElegibilidadeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ElegibilidadeService {

    private final RegraElegibilidadeRepository regraRepository;
    private final ProdutoService produtoService;

    public ElegibilidadeService(RegraElegibilidadeRepository regraRepository,
                                ProdutoService produtoService) {
        this.regraRepository = regraRepository;
        this.produtoService = produtoService;
    }

    @Transactional
    public RegraElegibilidade adicionarRegra(Long produtoId, RegraElegibilidadeRequest request) {
        Produto produto = produtoService.buscarEntidade(produtoId);
        if (!RegraElegibilidade.TipoRegra.NAO_NEGATIVADO.equals(request.tipoRegra())) {
            if (request.valorNumerico() == null && request.valorTexto() == null) {
                throw new IllegalArgumentException("A regra exige valorNumerico ou valorTexto");
            }
        }
        RegraElegibilidade regra = new RegraElegibilidade();
        regra.setProdutoId(produto.getId());
        regra.setTipoRegra(request.tipoRegra());
        regra.setComparador(request.comparador());
        regra.setValorNumerico(request.valorNumerico());
        regra.setValorTexto(request.valorTexto());
        regra.setDescricao(request.descricao());
        regra.setAtiva(true);
        return regraRepository.save(regra);
    }

    @Transactional
    public void removerRegra(Long produtoId, Long regraId) {
        Produto produto = produtoService.buscarEntidade(produtoId);
        RegraElegibilidade regra = regraRepository.findById(regraId)
            .filter(r -> r.getProdutoId().equals(produto.getId()))
            .orElseThrow(() -> new IllegalArgumentException("Regra de elegibilidade não encontrada: " + regraId));
        regraRepository.delete(regra);
    }

    @Transactional(readOnly = true)
    public List<RegraElegibilidadeResponse> listarRegras(Long produtoId) {
        produtoService.buscarEntidade(produtoId);
        return regraRepository.findByProdutoId(produtoId).stream()
            .map(RegraElegibilidadeResponse::de)
            .toList();
    }

    @Transactional(readOnly = true)
    public AvaliacaoElegibilidadeResponse avaliar(Long produtoId, PerfilClienteRequest perfil) {
        Produto produto = produtoService.buscarEntidade(produtoId);
        List<RegraElegibilidade> regras = regraRepository.findByProdutoIdAndAtivaTrue(produtoId);

        List<AvaliacaoElegibilidadeResponse.ResultadoRegra> resultados = regras.stream()
            .map(regra -> avaliarRegra(regra, perfil))
            .toList();

        boolean apto = resultados.stream().allMatch(AvaliacaoElegibilidadeResponse.ResultadoRegra::atendida);
        return new AvaliacaoElegibilidadeResponse(
            produto.getId(), produto.getCodigo(), produto.getNome(), apto, resultados);
    }

    private AvaliacaoElegibilidadeResponse.ResultadoRegra avaliarRegra(RegraElegibilidade regra, PerfilClienteRequest perfil) {
        String esperado;
        String informado;
        boolean atendida;
        switch (regra.getTipoRegra()) {
            case RENDA_MINIMA -> {
                esperado = regra.getValorNumerico() == null ? null : "renda >= " + regra.getValorNumerico();
                informado = perfil.rendaMensal() == null ? null : String.valueOf(perfil.rendaMensal());
                atendida = perfil.rendaMensal() != null && comparar(perfil.rendaMensal(), regra.getValorNumerico(), regra.getComparador());
            }
            case IDADE_MINIMA -> {
                esperado = "idade >= " + regra.getValorNumerico();
                informado = perfil.idade() == null ? null : String.valueOf(perfil.idade());
                atendida = perfil.idade() != null && comparar(BigDecimal.valueOf(perfil.idade()), regra.getValorNumerico(), regra.getComparador());
            }
            case IDADE_MAXIMA -> {
                esperado = "idade <= " + regra.getValorNumerico();
                informado = perfil.idade() == null ? null : String.valueOf(perfil.idade());
                atendida = perfil.idade() != null && comparar(BigDecimal.valueOf(perfil.idade()), regra.getValorNumerico(), regra.getComparador());
            }
            case SCORE_MINIMO -> {
                esperado = "score >= " + regra.getValorNumerico();
                informado = perfil.score() == null ? null : String.valueOf(perfil.score());
                atendida = perfil.score() != null && comparar(BigDecimal.valueOf(perfil.score()), regra.getValorNumerico(), regra.getComparador());
            }
            case SEGMENTO -> {
                esperado = regra.getValorTexto();
                informado = perfil.segmento();
                atendida = compararTexto(perfil.segmento(), regra.getValorTexto(), regra.getComparador());
            }
            case TIPO_PESSOA -> {
                esperado = regra.getValorTexto();
                informado = perfil.tipoPessoa();
                atendida = compararTexto(perfil.tipoPessoa(), regra.getValorTexto(), regra.getComparador());
            }
            case NAO_NEGATIVADO -> {
                esperado = "negativado = false";
                informado = perfil.negativado() == null ? null : String.valueOf(perfil.negativado());
                atendida = Boolean.FALSE.equals(perfil.negativado());
            }
            default -> throw new IllegalStateException("Tipo de regra não suportado: " + regra.getTipoRegra());
        }
        return new AvaliacaoElegibilidadeResponse.ResultadoRegra(
            regra.getId(), regra.getTipoRegra().name(), regra.getDescricao(), atendida, esperado, informado);
    }

    private boolean comparar(BigDecimal valor, BigDecimal esperado, RegraElegibilidade.Comparador comparador) {
        if (valor == null || esperado == null) {
            return false;
        }
        int cmp = valor.compareTo(esperado);
        return switch (comparador) {
            case MAIOR_IGUAL -> cmp >= 0;
            case MENOR_IGUAL -> cmp <= 0;
            case IGUAL -> cmp == 0;
            case DIFERENTE -> cmp != 0;
        };
    }

    private boolean compararTexto(String valor, String esperado, RegraElegibilidade.Comparador comparador) {
        if (valor == null || esperado == null) {
            return false;
        }
        boolean igual = valor.equalsIgnoreCase(esperado);
        return switch (comparador) {
            case IGUAL -> igual;
            case DIFERENTE -> !igual;
            default -> false;
        };
    }
}
