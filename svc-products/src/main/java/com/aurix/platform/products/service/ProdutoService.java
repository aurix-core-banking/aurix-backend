package com.aurix.platform.products.service;

import com.aurix.platform.products.dto.ProdutoRequest;
import com.aurix.platform.products.dto.ProdutoResponse;
import com.aurix.platform.products.dto.VersaoProdutoResponse;
import com.aurix.platform.products.entity.Produto;
import com.aurix.platform.products.entity.VersaoProduto;
import com.aurix.platform.products.exception.ProdutoNaoEncontradoException;
import com.aurix.platform.products.repository.ProdutoRepository;
import com.aurix.platform.products.repository.VersaoProdutoRepository;
import com.aurix.platform.shared.event.EventPublisher;
import com.aurix.platform.shared.event.ProdutoCriadoEvent;
import com.aurix.platform.shared.event.Topics;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProdutoService {

    private final ProdutoRepository produtoRepository;
    private final VersaoProdutoRepository versaoRepository;
    private final EventPublisher eventPublisher;
    private final ObjectMapper objectMapper;

    public ProdutoService(ProdutoRepository produtoRepository,
                          VersaoProdutoRepository versaoRepository,
                          EventPublisher eventPublisher,
                          ObjectMapper objectMapper) {
        this.produtoRepository = produtoRepository;
        this.versaoRepository = versaoRepository;
        this.eventPublisher = eventPublisher;
        this.objectMapper = objectMapper;
    }

    @Transactional
    public Produto criar(ProdutoRequest request) {
        if (produtoRepository.existsByCodigo(request.codigo())) {
            throw new IllegalArgumentException("Já existe produto com o código: " + request.codigo());
        }
        Produto produto = new Produto();
        produto.setCodigo(request.codigo());
        produto.setNome(request.nome());
        produto.setDescricao(request.descricao());
        produto.setTipoProduto(request.tipoProduto());
        produto.setPublicoAlvo(request.publicoAlvo());
        produto.setExigenciaMinima(request.exigenciaMinima());
        produto.setRequisitos(request.requisitos());
        produto.setVigenciaInicio(request.vigenciaInicio());
        produto.setVigenciaFim(request.vigenciaFim());
        produto.setNumeroVersao(1);
        produto.setStatus(Produto.StatusProduto.RASCUNHO);
        produto.setAtivo(true);
        produto = produtoRepository.save(produto);

        registraVersao(produto, "SISTEMA", "Criação do produto");

        eventPublisher.publish(Topics.PRODUTO_CRIADO, ProdutoCriadoEvent.criado(
            produto.getId(), produto.getCodigo(), produto.getNome(),
            produto.getTipoProduto().name(), produto.getNumeroVersao()));
        return produto;
    }

    @Transactional
    public Produto atualizar(Long id, ProdutoRequest request, String autor, String changelog) {
        Produto produto = buscarEntidade(id);
        produto.setCodigo(request.codigo());
        produto.setNome(request.nome());
        produto.setDescricao(request.descricao());
        produto.setTipoProduto(request.tipoProduto());
        produto.setPublicoAlvo(request.publicoAlvo());
        produto.setExigenciaMinima(request.exigenciaMinima());
        produto.setRequisitos(request.requisitos());
        produto.setVigenciaInicio(request.vigenciaInicio());
        produto.setVigenciaFim(request.vigenciaFim());
        produto.setNumeroVersao(produto.getNumeroVersao() + 1);
        produto = produtoRepository.save(produto);

        registraVersao(produto, autor, changelog);
        eventPublisher.publish(Topics.PRODUTO_ATUALIZADO, ProdutoCriadoEvent.criado(
            produto.getId(), produto.getCodigo(), produto.getNome(),
            produto.getTipoProduto().name(), produto.getNumeroVersao()));
        return produto;
    }

    @Transactional
    public Produto publicar(Long id) {
        Produto produto = buscarEntidade(id);
        if (produto.getStatus() == Produto.StatusProduto.ATIVO) {
            return produto;
        }
        produto.setStatus(Produto.StatusProduto.ATIVO);
        produto.setAtivo(true);
        produto = produtoRepository.save(produto);
        registraVersao(produto, "SISTEMA", "Publicação do produto");
        return produto;
    }

    @Transactional
    public Produto descontinuar(Long id) {
        Produto produto = buscarEntidade(id);
        produto.setStatus(Produto.StatusProduto.DESCONTINUADO);
        produto.setAtivo(false);
        produto = produtoRepository.save(produto);
        registraVersao(produto, "SISTEMA", "Produto descontinuado");
        eventPublisher.publish(Topics.PRODUTO_DESCONTINUADO, ProdutoCriadoEvent.criado(
            produto.getId(), produto.getCodigo(), produto.getNome(),
            produto.getTipoProduto().name(), produto.getNumeroVersao()));
        return produto;
    }

    @Transactional(readOnly = true)
    public Produto buscarEntidade(Long id) {
        return produtoRepository.findById(id)
            .orElseThrow(() -> new ProdutoNaoEncontradoException(id));
    }

    @Transactional(readOnly = true)
    public Produto buscarPorCodigo(String codigo) {
        return produtoRepository.findByCodigo(codigo)
            .orElseThrow(() -> new ProdutoNaoEncontradoException(codigo));
    }

    @Transactional(readOnly = true)
    public List<Produto> listar(Produto.TipoProduto tipo, Produto.StatusProduto status, Boolean ativo) {
        if (tipo != null) {
            return produtoRepository.findByTipoProduto(tipo);
        }
        if (status != null) {
            return produtoRepository.findByStatus(status);
        }
        if (ativo != null) {
            return produtoRepository.findByAtivo(ativo);
        }
        return produtoRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<VersaoProdutoResponse> listarVersoes(Long produtoId) {
        if (!produtoRepository.existsById(produtoId)) {
            throw new ProdutoNaoEncontradoException(produtoId);
        }
        return versaoRepository.findByProdutoIdOrderByNumeroVersaoDesc(produtoId).stream()
            .map(VersaoProdutoResponse::de)
            .toList();
    }

    private void registraVersao(Produto produto, String autor, String changelog) {
        VersaoProduto versao = new VersaoProduto();
        versao.setProdutoId(produto.getId());
        versao.setNumeroVersao(produto.getNumeroVersao());
        versao.setStatus(VersaoProduto.StatusVersao.PUBLICADA);
        versao.setAutor(autor == null ? "SISTEMA" : autor);
        versao.setChangelog(changelog);
        versao.setDadosJson(serializar(produto));
        versaoRepository.save(versao);
    }

    private String serializar(Produto produto) {
        try {
            return objectMapper.writeValueAsString(ProdutoResponse.de(produto));
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("Falha ao serializar snapshot do produto", e);
        }
    }
}
