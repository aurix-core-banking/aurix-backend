package com.aurix.platform.cards.service;

import com.aurix.platform.cards.dto.EmitirCartaoRequest;
import com.aurix.platform.cards.entity.Cartao;
import com.aurix.platform.cards.entity.Cartao.BandeiraCartao;
import com.aurix.platform.cards.entity.Cartao.StatusCartao;
import com.aurix.platform.cards.entity.Cartao.TipoCartao;
import com.aurix.platform.cards.entity.LimiteCartao;
import com.aurix.platform.cards.entity.ProdutoCartao;
import com.aurix.platform.cards.repository.CartaoRepository;
import com.aurix.platform.cards.repository.LimiteCartaoRepository;
import com.aurix.platform.cards.repository.ProdutoCartaoRepository;
import com.aurix.platform.shared.event.CartaoEmitidoEvent;
import com.aurix.platform.shared.event.Topics;
import com.aurix.platform.shared.tenant.TenantContext;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class EmissaoService {

    private static final Logger log = LoggerFactory.getLogger(EmissaoService.class);

    private final CartaoRepository cartaoRepository;
    private final ProdutoCartaoRepository produtoCartaoRepository;
    private final LimiteCartaoRepository limiteCartaoRepository;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public EmissaoService(CartaoRepository cartaoRepository,
                          ProdutoCartaoRepository produtoCartaoRepository,
                          LimiteCartaoRepository limiteCartaoRepository,
                          KafkaTemplate<String, Object> kafkaTemplate) {
        this.cartaoRepository = cartaoRepository;
        this.produtoCartaoRepository = produtoCartaoRepository;
        this.limiteCartaoRepository = limiteCartaoRepository;
        this.kafkaTemplate = kafkaTemplate;
    }

    public Cartao emitir(EmitirCartaoRequest request) {
        ProdutoCartao produto = produtoCartaoRepository.findById(request.getProdutoId())
            .orElseThrow(() -> new IllegalArgumentException("ProdutoCartao nao encontrado: " + request.getProdutoId()));

        if (Boolean.FALSE.equals(produto.getAtivo())) {
            throw new IllegalStateException("ProdutoCartao nao esta ativo: " + request.getProdutoId());
        }

        String tenantId = TenantContext.getTenantId();

        Cartao cartao = new Cartao();
        cartao.setNumeroCartao(gerarNumeroCartao(produto.getBandeira()));
        cartao.setNumeroCartaoMascarado(mascararNumeroCartao(cartao.getNumeroCartao()));
        cartao.setCvv(gerarCVV());
        cartao.setDataValidade(LocalDate.now().plusYears(5));
        cartao.setNomePortador(request.getNomePortador());
        cartao.setContaId(request.getContaId());
        cartao.setTipoCartao(TipoCartao.valueOf(request.getTipo()));
        cartao.setBandeira(BandeiraCartao.valueOf(produto.getBandeira()));
        cartao.setStatus(StatusCartao.PENDENTE_ATIVACAO);
        cartao.setLimiteCredito(produto.getLimiteMaximo());
        cartao.setLimiteUtilizado(BigDecimal.ZERO);
        cartao.setLimiteDisponivel(produto.getLimiteMaximo());
        cartao.setDataEmissao(LocalDateTime.now());
        cartao.setProdutoId(produto.getId());
        cartao.setTenantId(tenantId);
        cartao = cartaoRepository.save(cartao);

        LimiteCartao limite = new LimiteCartao();
        limite.setCartaoId(cartao.getId());
        limite.setLimiteTotal(produto.getLimiteMaximo());
        limite.setLimiteDisponivel(produto.getLimiteMaximo());
        limite.setLimiteUtilizado(BigDecimal.ZERO);
        limite.setDataAtualizacao(LocalDateTime.now());
        limiteCartaoRepository.save(limite);

        kafkaTemplate.send(Topics.CARTOES_CARTAO_EMITIDO, String.valueOf(cartao.getId()),
                CartaoEmitidoEvent.emitido(cartao.getId(), cartao.getContaId(), cartao.getNomePortador(),
                        cartao.getBandeira().name(), cartao.getTipoCartao().name(), cartao.getId()));

        log.info("Cartao emitido: id={}, contaId={}", cartao.getId(), cartao.getContaId());
        return cartao;
    }

    public Cartao bloquear(Long cartaoId, com.aurix.platform.cards.dto.BloquearCartaoRequest request) {
        Cartao cartao = cartaoRepository.findById(cartaoId)
            .orElseThrow(() -> new IllegalArgumentException("Cartao nao encontrado: " + cartaoId));
        if (cartao.getStatus() == StatusCartao.CANCELADO) {
            throw new IllegalStateException("Cartao ja esta cancelado");
        }
        cartao.setStatus(StatusCartao.BLOQUEADO);
        cartao.setDataBloqueio(LocalDateTime.now());
        cartao.setMotivoBloqueio(request.getMotivo());
        return cartaoRepository.save(cartao);
    }

    public Cartao ativar(Long cartaoId) {
        Cartao cartao = cartaoRepository.findById(cartaoId)
            .orElseThrow(() -> new IllegalArgumentException("Cartao nao encontrado: " + cartaoId));
        if (cartao.getStatus() != StatusCartao.PENDENTE_ATIVACAO && cartao.getStatus() != StatusCartao.BLOQUEADO) {
            throw new IllegalStateException("Cartao nao pode ser ativado: " + cartao.getStatus());
        }
        cartao.setStatus(StatusCartao.ATIVO);
        cartao.setDataAtivacao(LocalDateTime.now());
        return cartaoRepository.save(cartao);
    }

    public Cartao cancelar(Long cartaoId) {
        Cartao cartao = cartaoRepository.findById(cartaoId)
            .orElseThrow(() -> new IllegalArgumentException("Cartao nao encontrado: " + cartaoId));
        if (cartao.getStatus() == StatusCartao.CANCELADO) {
            throw new IllegalStateException("Cartao ja esta cancelado");
        }
        cartao.setStatus(StatusCartao.CANCELADO);
        cartao.setDataCancelamento(LocalDateTime.now());
        return cartaoRepository.save(cartao);
    }

    private String gerarNumeroCartao(String bandeira) {
        String prefixo = switch (bandeira.toUpperCase()) {
            case "VISA" -> "4";
            case "MASTERCARD" -> "5";
            case "ELO" -> "6";
            default -> "4";
        };
        StringBuilder numero = new StringBuilder(prefixo);
        while (numero.length() < 16) {
            numero.append((int) (Math.random() * 10));
        }
        return numero.toString();
    }

    private String mascararNumeroCartao(String numero) {
        return "**** **** **** " + numero.substring(numero.length() - 4);
    }

    private String gerarCVV() {
        return String.format("%03d", (int) (Math.random() * 1000));
    }
}
