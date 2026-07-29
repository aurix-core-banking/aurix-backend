package com.aurix.platform.cards.service;

import com.aurix.platform.cards.client.ContaCorrenteClient;
import com.aurix.platform.cards.client.MlFraudClient;
import com.aurix.platform.cards.client.MlFraudClient.FraudResponse;
import com.aurix.platform.cards.dto.AutorizarTransacaoRequest;
import com.aurix.platform.cards.dto.TransacaoResponse;
import com.aurix.platform.cards.entity.Cartao;
import com.aurix.platform.cards.entity.Cartao.StatusCartao;
import com.aurix.platform.cards.entity.LimiteCartao;
import com.aurix.platform.cards.entity.TransacaoCartao;
import com.aurix.platform.cards.entity.TransacaoCartao.StatusTransacao;
import com.aurix.platform.cards.entity.TransacaoCartao.TipoTransacao;
import com.aurix.platform.cards.repository.CartaoRepository;
import com.aurix.platform.cards.repository.LimiteCartaoRepository;
import com.aurix.platform.cards.repository.TransacaoCartaoRepository;
import com.aurix.platform.shared.event.CartaoTransacaoAutorizadaEvent;
import com.aurix.platform.shared.event.CartaoTransacaoEstornadaEvent;
import com.aurix.platform.shared.event.Topics;
import com.aurix.platform.shared.tenant.TenantContext;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class TransacaoService {

    private static final Logger log = LoggerFactory.getLogger(TransacaoService.class);

    private final CartaoRepository cartaoRepository;
    private final LimiteCartaoRepository limiteCartaoRepository;
    private final TransacaoCartaoRepository transacaoCartaoRepository;
    private final MlFraudClient mlFraudClient;
    private final ContaCorrenteClient contaCorrenteClient;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public TransacaoService(CartaoRepository cartaoRepository,
                            LimiteCartaoRepository limiteCartaoRepository,
                            TransacaoCartaoRepository transacaoCartaoRepository,
                            MlFraudClient mlFraudClient,
                            ContaCorrenteClient contaCorrenteClient,
                            KafkaTemplate<String, Object> kafkaTemplate) {
        this.cartaoRepository = cartaoRepository;
        this.limiteCartaoRepository = limiteCartaoRepository;
        this.transacaoCartaoRepository = transacaoCartaoRepository;
        this.mlFraudClient = mlFraudClient;
        this.contaCorrenteClient = contaCorrenteClient;
        this.kafkaTemplate = kafkaTemplate;
    }

    public TransacaoResponse autorizar(AutorizarTransacaoRequest request) {
        Cartao cartao = cartaoRepository.findById(request.getCartaoId())
            .orElseThrow(() -> new IllegalArgumentException("Cartao nao encontrado: " + request.getCartaoId()));

        if (cartao.getStatus() != StatusCartao.ATIVO) {
            throw new IllegalStateException("Cartao nao esta ativo: " + cartao.getStatus());
        }

        String tenantId = TenantContext.getTenantId();
        FraudResponse fraud = mlFraudClient.avaliar(
            new MlFraudClient.FraudRequest(cartao.getId(), request.getValor(),
                request.getEstabelecimento(), request.getModo()));

        boolean aprovado = "APROVADO".equalsIgnoreCase(fraud.resultado());
        StatusTransacao status = aprovado ? StatusTransacao.AUTORIZADA : StatusTransacao.NEGADA;

        if (aprovado) {
            LimiteCartao limite = limiteCartaoRepository.findByCartaoId(cartao.getId())
                .orElseThrow(() -> new IllegalStateException("Limite nao configurado para cartao: " + cartao.getId()));

            if (limite.getLimiteDisponivel().compareTo(request.getValor()) < 0) {
                throw new IllegalStateException("Limite insuficiente para transacao");
            }

            limite.setLimiteUtilizado(limite.getLimiteUtilizado().add(request.getValor()));
            limite.setLimiteDisponivel(limite.getLimiteDisponivel().subtract(request.getValor()));
            limite.setDataAtualizacao(LocalDateTime.now());
            limiteCartaoRepository.save(limite);

            cartao.setLimiteUtilizado(cartao.getLimiteUtilizado().add(request.getValor()));
            cartao.setLimiteDisponivel(cartao.getLimiteDisponivel().subtract(request.getValor()));
            cartaoRepository.save(cartao);
        }

        TransacaoCartao transacao = new TransacaoCartao();
        transacao.setCodigoTransacao("TXN-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        transacao.setCartaoId(cartao.getId());
        transacao.setTipoTransacao(TipoTransacao.COMPRA_CREDITO);
        transacao.setStatus(status);
        transacao.setValor(request.getValor());
        transacao.setDataTransacao(LocalDateTime.now());
        transacao.setEstabelecimento(request.getEstabelecimento());
        transacao.setNsu(gerarNSU());
        transacao.setAutorizacao(aprovado ? gerarAutorizacao() : null);
        transacao.setModo(TransacaoCartao.ModoTransacao.valueOf(request.getModo()));
        transacao.setNumeroParcelas(1);
        transacao.setParcelaAtual(1);
        transacao = transacaoCartaoRepository.save(transacao);

        kafkaTemplate.send(Topics.CARTOES_TRANSACAO_AUTORIZADA, String.valueOf(transacao.getId()),
                CartaoTransacaoAutorizadaEvent.autorizada(
                        transacao.getCodigoTransacao(), transacao.getCartaoId(), transacao.getValor(),
                        transacao.getEstabelecimento(), transacao.getAutorizacao(),
                        transacao.getStatus().name(), transacao.getCartaoId()));

        log.info("Transacao {}: cartaoId={}, valor={}, status={}",
            aprovado ? "autorizada" : "recusada", cartao.getId(), request.getValor(), status);
        return toResponse(transacao);
    }

    public TransacaoResponse capturar(Long transacaoId) {
        TransacaoCartao transacao = transacaoCartaoRepository.findById(transacaoId)
            .orElseThrow(() -> new IllegalArgumentException("Transacao nao encontrada: " + transacaoId));

        if (transacao.getStatus() != StatusTransacao.AUTORIZADA) {
            throw new IllegalStateException("Transacao nao esta autorizada: " + transacao.getStatus());
        }

        transacao.setStatus(StatusTransacao.CONFIRMADA);
        TransacaoCartao saved = transacaoCartaoRepository.save(transacao);

        Cartao cartao = cartaoRepository.findById(saved.getCartaoId())
            .orElseThrow(() -> new IllegalArgumentException("Cartao nao encontrado: " + saved.getCartaoId()));

        contaCorrenteClient.debitar(cartao.getContaId(),
            new ContaCorrenteClient.DebitoRequest(transacao.getValor(),
                "Capturacao transacao " + transacao.getCodigoTransacao()));

        log.info("Transacao capturada: id={}, codigo={}", saved.getId(), saved.getCodigoTransacao());
        return toResponse(saved);
    }

    public TransacaoResponse estornar(Long transacaoId) {
        TransacaoCartao transacao = transacaoCartaoRepository.findById(transacaoId)
            .orElseThrow(() -> new IllegalArgumentException("Transacao nao encontrada: " + transacaoId));

        if (transacao.getStatus() == StatusTransacao.ESTORNADA) {
            throw new IllegalStateException("Transacao ja esta estornada");
        }

        transacao.setStatus(StatusTransacao.ESTORNADA);
        transacao = transacaoCartaoRepository.save(transacao);

        LimiteCartao limite = limiteCartaoRepository.findByCartaoId(transacao.getCartaoId())
            .orElse(null);
        if (limite != null) {
            limite.setLimiteUtilizado(limite.getLimiteUtilizado().subtract(transacao.getValor()));
            limite.setLimiteDisponivel(limite.getLimiteDisponivel().add(transacao.getValor()));
            limite.setDataAtualizacao(LocalDateTime.now());
            limiteCartaoRepository.save(limite);
        }

        kafkaTemplate.send(Topics.CARTOES_TRANSACAO_ESTORNADA, String.valueOf(transacao.getId()),
                CartaoTransacaoEstornadaEvent.estornada(
                        transacao.getCodigoTransacao(), transacao.getCartaoId(),
                        transacao.getValor(), transacao.getCartaoId()));

        log.info("Transacao estornada: id={}, codigo={}", transacao.getId(), transacao.getCodigoTransacao());
        return toResponse(transacao);
    }

    @Transactional(readOnly = true)
    public List<TransacaoResponse> listarPorCartao(Long cartaoId) {
        return transacaoCartaoRepository.findByCartaoId(cartaoId).stream()
            .map(this::toResponse).toList();
    }

    private TransacaoResponse toResponse(TransacaoCartao t) {
        TransacaoResponse r = new TransacaoResponse();
        r.setId(t.getId());
        r.setCodigoTransacao(t.getCodigoTransacao());
        r.setCartaoId(t.getCartaoId());
        r.setValor(t.getValor());
        r.setEstabelecimento(t.getEstabelecimento());
        r.setAutorizacao(t.getAutorizacao());
        r.setModo(t.getModo() != null ? t.getModo().name() : null);
        r.setStatus(t.getStatus().name());
        r.setDataTransacao(t.getDataTransacao());
        return r;
    }

    private String gerarNSU() {
        return String.format("%06d", (int) (Math.random() * 1000000));
    }

    private String gerarAutorizacao() {
        return String.format("%06d", (int) (Math.random() * 1000000));
    }
}
