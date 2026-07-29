package com.aurix.platform.cards.service;

import com.aurix.platform.cards.dto.AuditMetaDTO;
import com.aurix.platform.cards.dto.ProdutoCartaoRequest;
import com.aurix.platform.cards.dto.ProdutoCartaoResponse;
import com.aurix.platform.cards.entity.ProdutoCartao;
import com.aurix.platform.cards.repository.ProdutoCartaoRepository;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class ProdutoCartaoService {

    private static final Logger log = LoggerFactory.getLogger(ProdutoCartaoService.class);

    private final ProdutoCartaoRepository produtoCartaoRepository;

    public ProdutoCartaoService(ProdutoCartaoRepository produtoCartaoRepository) {
        this.produtoCartaoRepository = produtoCartaoRepository;
    }

    public ProdutoCartaoResponse criar(ProdutoCartaoRequest request) {
        ProdutoCartao produto = new ProdutoCartao();
        produto.setNome(request.getNome());
        produto.setBandeira(request.getBandeira());
        produto.setAdquirente(request.getAdquirente());
        produto.setAnuidade(request.getAnuidade());
        produto.setTaxaJuros(request.getTaxaJuros());
        produto.setTaxaMora(request.getTaxaMora());
        produto.setLimiteMinimo(request.getLimiteMinimo());
        produto.setLimiteMaximo(request.getLimiteMaximo());
        produto.setProgramaPontos(request.getProgramaPontos());
        produto.setAtivo(request.getAtivo() != null ? request.getAtivo() : true);
        produto = produtoCartaoRepository.save(produto);

        log.info("ProdutoCartao criado: id={}, nome={}", produto.getId(), produto.getNome());
        return toResponse(produto);
    }

    @Transactional(readOnly = true)
    public List<ProdutoCartaoResponse> listar() {
        return produtoCartaoRepository.findByAtivoTrue().stream()
            .map(this::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public ProdutoCartaoResponse buscarPorId(Long id) {
        ProdutoCartao produto = produtoCartaoRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("ProdutoCartao nao encontrado: " + id));
        return toResponse(produto);
    }

    private ProdutoCartaoResponse toResponse(ProdutoCartao p) {
        ProdutoCartaoResponse r = new ProdutoCartaoResponse();
        r.setId(p.getId());
        r.setNome(p.getNome());
        r.setBandeira(p.getBandeira());
        r.setAdquirente(p.getAdquirente());
        r.setAnuidade(p.getAnuidade());
        r.setTaxaJuros(p.getTaxaJuros());
        r.setTaxaMora(p.getTaxaMora());
        r.setLimiteMinimo(p.getLimiteMinimo());
        r.setLimiteMaximo(p.getLimiteMaximo());
        r.setProgramaPontos(p.getProgramaPontos());
        r.setAtivo(p.getAtivo());
        AuditMetaDTO audit = new AuditMetaDTO();
        audit.setDataCriacao(p.getDataCriacao());
        audit.setDataAtualizacao(p.getDataAtualizacao());
        r.setAuditoria(audit);
        return r;
    }
}
