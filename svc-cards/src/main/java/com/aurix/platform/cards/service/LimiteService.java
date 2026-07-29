package com.aurix.platform.cards.service;

import com.aurix.platform.cards.dto.AjustarLimiteRequest;
import com.aurix.platform.cards.dto.LimiteCartaoResponse;
import com.aurix.platform.cards.entity.Cartao;
import com.aurix.platform.cards.entity.LimiteCartao;
import com.aurix.platform.cards.repository.CartaoRepository;
import com.aurix.platform.cards.repository.LimiteCartaoRepository;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class LimiteService {

    private static final Logger log = LoggerFactory.getLogger(LimiteService.class);

    private final CartaoRepository cartaoRepository;
    private final LimiteCartaoRepository limiteCartaoRepository;

    public LimiteService(CartaoRepository cartaoRepository,
                         LimiteCartaoRepository limiteCartaoRepository) {
        this.cartaoRepository = cartaoRepository;
        this.limiteCartaoRepository = limiteCartaoRepository;
    }

    public LimiteCartaoResponse ajustarLimite(Long cartaoId, AjustarLimiteRequest request) {
        Cartao cartao = cartaoRepository.findById(cartaoId)
            .orElseThrow(() -> new IllegalArgumentException("Cartao nao encontrado: " + cartaoId));
        if (request.getNovoLimite().compareTo(cartao.getLimiteUtilizado()) < 0) {
            throw new IllegalStateException("Novo limite nao pode ser menor que o limite utilizado");
        }

        LimiteCartao limite = limiteCartaoRepository.findByCartaoId(cartaoId)
            .orElseGet(() -> {
                LimiteCartao novo = new LimiteCartao();
                novo.setCartaoId(cartaoId);
                novo.setLimiteUtilizado(BigDecimal.ZERO);
                return novo;
            });

        limite.setLimiteTotal(request.getNovoLimite());
        limite.setLimiteDisponivel(request.getNovoLimite().subtract(limite.getLimiteUtilizado()));
        limite.setDataAtualizacao(LocalDateTime.now());
        limiteCartaoRepository.save(limite);

        cartao.setLimiteCredito(request.getNovoLimite());
        cartao.setLimiteDisponivel(limite.getLimiteDisponivel());
        cartaoRepository.save(cartao);

        log.info("Limite ajustado: cartaoId={}, novoLimite={}", cartaoId, request.getNovoLimite());
        return toResponse(limite);
    }

    @Transactional(readOnly = true)
    public LimiteCartaoResponse consultarLimite(Long cartaoId) {
        LimiteCartao limite = limiteCartaoRepository.findByCartaoId(cartaoId)
            .orElseThrow(() -> new IllegalArgumentException("Limite nao encontrado para cartao: " + cartaoId));
        return toResponse(limite);
    }

    public void bloquearLimite(Long cartaoId) {
        LimiteCartao limite = limiteCartaoRepository.findByCartaoId(cartaoId)
            .orElseThrow(() -> new IllegalArgumentException("Limite nao encontrado para cartao: " + cartaoId));
        limite.setLimiteDisponivel(BigDecimal.ZERO);
        limite.setDataAtualizacao(LocalDateTime.now());
        limiteCartaoRepository.save(limite);
        log.info("Limite bloqueado: cartaoId={}", cartaoId);
    }

    public void desbloquearLimite(Long cartaoId) {
        LimiteCartao limite = limiteCartaoRepository.findByCartaoId(cartaoId)
            .orElseThrow(() -> new IllegalArgumentException("Limite nao encontrado para cartao: " + cartaoId));
        limite.setLimiteDisponivel(limite.getLimiteTotal().subtract(limite.getLimiteUtilizado()));
        limite.setDataAtualizacao(LocalDateTime.now());
        limiteCartaoRepository.save(limite);
        log.info("Limite desbloqueado: cartaoId={}", cartaoId);
    }

    private LimiteCartaoResponse toResponse(LimiteCartao limite) {
        LimiteCartaoResponse r = new LimiteCartaoResponse();
        r.setLimiteTotal(limite.getLimiteTotal());
        r.setLimiteDisponivel(limite.getLimiteDisponivel());
        r.setLimiteUtilizado(limite.getLimiteUtilizado());
        return r;
    }
}
