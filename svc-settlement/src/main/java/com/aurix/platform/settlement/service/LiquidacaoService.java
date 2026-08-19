package com.aurix.platform.settlement.service;

import com.aurix.platform.settlement.dto.LiquidacaoRequest;
import com.aurix.platform.settlement.dto.LiquidacaoResponse;
import com.aurix.platform.shared.entity.Transacao;
import com.aurix.platform.shared.repository.TransacaoRepository;
import com.aurix.platform.shared.tenant.TenantContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@Transactional
public class LiquidacaoService {

    private static final Logger log = LoggerFactory.getLogger(LiquidacaoService.class);
    private static final int MAX_RANDOM_CODE = 1000000;
    private static final int MAX_RANDOM_SHORT = 1000;

    private final TransacaoRepository transacaoRepository;
    private final Random random = new Random();

    public LiquidacaoService(TransacaoRepository transacaoRepository) {
        this.transacaoRepository = transacaoRepository;
    }

    public LiquidacaoResponse criarLiquidacao(LiquidacaoRequest request) {
        log.info("Criando liquidacao para transacao: {}", request.getTransacaoId());

        Transacao transacao = transacaoRepository.findById(request.getTransacaoId())
            .orElseThrow(() -> new RuntimeException("Transacao nao encontrada"));

        LiquidacaoResponse resp = new LiquidacaoResponse();
        resp.setCodigoLiquidacao(gerarCodigoLiquidacao());
        resp.setTransacaoId(request.getTransacaoId());
        resp.setTipoLiquidacao(request.getTipoLiquidacao());
        resp.setStatus("PENDENTE");
        resp.setValorLiquidacao(transacao.getValor());
        resp.setValorTaxa(BigDecimal.ZERO);
        resp.setValorIOF(BigDecimal.ZERO);
        resp.setValorTotal(transacao.getValor());
        resp.setContaOrigem(request.getContaOrigem());
        resp.setContaDestino(request.getContaDestino());
        resp.setDataLiquidacao(LocalDateTime.now());

        log.info("Liquidacao criada: {}", resp.getCodigoLiquidacao());
        return resp;
    }

    @Transactional(readOnly = true)
    public LiquidacaoResponse buscarPorId(Long id) {
        log.info("Buscando liquidacao ID: {}", id);
        LiquidacaoResponse resp = new LiquidacaoResponse();
        resp.setId(id);
        resp.setStatus("PENDENTE");
        return resp;
    }

    @Transactional(readOnly = true)
    public List<LiquidacaoResponse> listarPendentes() {
        log.info("Listando liquidacoes pendentes");
        return List.of();
    }

    public LiquidacaoResponse processar(Long id) {
        log.info("Processando liquidacao ID: {}", id);
        LiquidacaoResponse resp = new LiquidacaoResponse();
        resp.setId(id);
        resp.setStatus("LIQUIDADA");
        resp.setDataProcessamento(LocalDateTime.now());
        resp.setDataConfirmacao(LocalDateTime.now());
        log.info("Liquidacao {} processada", id);
        return resp;
    }

    public LiquidacaoResponse estornar(Long id) {
        log.info("Estornando liquidacao ID: {}", id);
        LiquidacaoResponse resp = new LiquidacaoResponse();
        resp.setId(id);
        resp.setStatus("ESTORNADA");
        resp.setDataProcessamento(LocalDateTime.now());
        log.info("Liquidacao {} estornada", id);
        return resp;
    }

    @Transactional(readOnly = true)
    public List<LiquidacaoResponse> listarPorTipo(String tipo) {
        log.info("Listando liquidacoes por tipo: {}", tipo);
        return List.of();
    }

    private String gerarCodigoLiquidacao() {
        return "LIQ-" + random.nextInt(MAX_RANDOM_CODE);
    }
}
