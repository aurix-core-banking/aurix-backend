package com.aurix.platform.cards.service;

import com.aurix.platform.cards.dto.AuditMetaDTO;
import com.aurix.platform.cards.dto.CartaoResponse;
import com.aurix.platform.cards.dto.LimiteCartaoResponse;
import com.aurix.platform.cards.entity.Cartao;
import com.aurix.platform.cards.entity.Cartao.StatusCartao;
import com.aurix.platform.cards.entity.LimiteCartao;
import com.aurix.platform.cards.repository.CartaoRepository;
import com.aurix.platform.cards.repository.LimiteCartaoRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class CartaoQueryService {

    private final CartaoRepository cartaoRepository;
    private final LimiteCartaoRepository limiteCartaoRepository;

    public CartaoQueryService(CartaoRepository cartaoRepository,
                              LimiteCartaoRepository limiteCartaoRepository) {
        this.cartaoRepository = cartaoRepository;
        this.limiteCartaoRepository = limiteCartaoRepository;
    }

    public List<CartaoResponse> buscarPorCliente(Long clienteId) {
        return cartaoRepository.findByContaId(clienteId).stream()
            .map(this::toResponse).toList();
    }

    public CartaoResponse buscarPorId(Long id) {
        Cartao cartao = cartaoRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Cartao nao encontrado: " + id));
        return toResponse(cartao);
    }

    public List<CartaoResponse> listarPorStatus(String status) {
        return cartaoRepository.findByStatus(StatusCartao.valueOf(status)).stream()
            .map(this::toResponse).toList();
    }

    private CartaoResponse toResponse(Cartao c) {
        CartaoResponse r = new CartaoResponse();
        r.setId(c.getId());
        r.setProdutoId(c.getProdutoId());
        r.setContaId(c.getContaId());
        r.setNumeroCartaoMascarado(c.getNumeroCartaoMascarado());
        r.setNomePortador(c.getNomePortador());
        r.setTipoCartao(c.getTipoCartao().name());
        r.setBandeira(c.getBandeira().name());
        r.setStatus(c.getStatus().name());
        r.setLimiteCredito(c.getLimiteCredito());
        r.setLimiteDisponivel(c.getLimiteDisponivel());
        r.setLimiteUtilizado(c.getLimiteUtilizado());
        r.setDiaVencimentoFatura(c.getDiaVencimentoFatura());
        r.setDataEmissao(c.getDataEmissao());
        r.setDataAtivacao(c.getDataAtivacao());
        r.setDataBloqueio(c.getDataBloqueio());
        r.setDataCancelamento(c.getDataCancelamento());
        AuditMetaDTO audit = new AuditMetaDTO();
        audit.setDataCriacao(c.getDataCriacao());
        audit.setDataAtualizacao(c.getDataAtualizacao());
        r.setAuditoria(audit);
        return r;
    }

    @Transactional(readOnly = true)
    public LimiteCartaoResponse consultarLimite(Long cartaoId) {
        LimiteCartao limite = limiteCartaoRepository.findByCartaoId(cartaoId)
            .orElseThrow(() -> new IllegalArgumentException("Limite nao encontrado para cartao: " + cartaoId));
        LimiteCartaoResponse r = new LimiteCartaoResponse();
        r.setLimiteTotal(limite.getLimiteTotal());
        r.setLimiteDisponivel(limite.getLimiteDisponivel());
        r.setLimiteUtilizado(limite.getLimiteUtilizado());
        return r;
    }
}
