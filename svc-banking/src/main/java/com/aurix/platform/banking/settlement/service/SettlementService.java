package com.aurix.platform.banking.settlement.service;

import com.aurix.platform.banking.core.dto.MovimentoContaDTO;
import com.aurix.platform.banking.core.service.ControleSaldoService;
import com.aurix.platform.banking.settlement.entity.Liquidez;
import com.aurix.platform.banking.settlement.repository.LiquidezRepository;
import com.aurix.platform.shared.event.EventPublisher;
import com.aurix.platform.shared.event.LiquidezEvent;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class SettlementService {
    @java.lang.SuppressWarnings("all")
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(SettlementService.class);
    private final LiquidezRepository liquidezRepository;
    private final EventPublisher eventPublisher;
    private final ControleSaldoService controleSaldoService;
    private final Clock clock;

    public Liquidez processarLiquidez(Liquidez liquidez) {
        log.info("Processando liquidação: {} - Valor: {}", liquidez.getNumeroLiquidez(), liquidez.getValor());
        try {
            liquidez.setStatus(Liquidez.StatusLiquidez.PROCESSANDO);
            liquidez.setDataProcessamento(LocalDateTime.now());
            liquidez.setTentativasProcessamento(0);
            liquidez.setMaxTentativas(3);
            switch (liquidez.getTipoOperacao()) {
                case PIX -> processarPIX(liquidez);
                case TED -> processarTED(liquidez);
                case DOC -> processarDOC(liquidez);
                case TRANSFERENCIA_INTERNA -> processarTransferenciaInterna(liquidez);
                default -> processarOperacaoGenerica(liquidez);
            }
            if (liquidez.getStatus() == Liquidez.StatusLiquidez.LIQUIDADO) {
                movimentarSaldos(liquidez);
            }
            Liquidez liquidezProcessada = liquidezRepository.save(liquidez);
            log.info("Liquidação processada: {} - Status: {}", liquidez.getNumeroLiquidez(), liquidez.getStatus());
            publicarEventoLiquidez(liquidezProcessada);
            return liquidezProcessada;
        } catch (Exception e) {
            log.error("Erro ao processar liquidação: {} - Erro: {}", liquidez.getNumeroLiquidez(), e.getMessage());
            liquidez.setStatus(Liquidez.StatusLiquidez.FALHA);
            liquidez.setCodigoRetorno("ERRO_PROCESSAMENTO");
            liquidez.setMensagemRetorno("Erro no processamento: " + e.getMessage());
            return liquidezRepository.save(liquidez);
        }
    }

    private void processarPIX(Liquidez liquidez) {
        log.info("Processando PIX: {}", liquidez.getNumeroLiquidez());
        liquidez.setProtocoloSistema(gerarProtocolo("PIX"));
        liquidez.setProtocoloBacen(gerarProtocolo("BACEN"));
        liquidez.setStatus(Liquidez.StatusLiquidez.LIQUIDADO);
        liquidez.setCodigoRetorno("000");
        liquidez.setMensagemRetorno("PIX processado com sucesso");
        liquidez.setDataConfirmacao(LocalDateTime.now());
    }

    private void processarTED(Liquidez liquidez) {
        log.info("Processando TED: {}", liquidez.getNumeroLiquidez());
        if (!verificarHorarioFuncionamentoTED()) {
            liquidez.setStatus(Liquidez.StatusLiquidez.REJEITADO);
            liquidez.setCodigoRetorno("FORA_HORARIO");
            liquidez.setMensagemRetorno("TED fora do horário de funcionamento");
            return;
        }
        liquidez.setProtocoloSistema(gerarProtocolo("TED"));
        liquidez.setProtocoloBacen(gerarProtocolo("BACEN"));
        liquidez.setStatus(Liquidez.StatusLiquidez.LIQUIDADO);
        liquidez.setCodigoRetorno("000");
        liquidez.setMensagemRetorno("TED processado com sucesso");
        liquidez.setDataConfirmacao(LocalDateTime.now());
    }

    private void processarDOC(Liquidez liquidez) {
        log.info("Processando DOC: {}", liquidez.getNumeroLiquidez());
        if (!verificarHorarioFuncionamentoDOC()) {
            liquidez.setStatus(Liquidez.StatusLiquidez.REJEITADO);
            liquidez.setCodigoRetorno("FORA_HORARIO");
            liquidez.setMensagemRetorno("DOC fora do horário de funcionamento");
            return;
        }
        liquidez.setProtocoloSistema(gerarProtocolo("DOC"));
        liquidez.setProtocoloBacen(gerarProtocolo("BACEN"));
        liquidez.setStatus(Liquidez.StatusLiquidez.LIQUIDADO);
        liquidez.setCodigoRetorno("000");
        liquidez.setMensagemRetorno("DOC processado com sucesso");
        liquidez.setDataConfirmacao(LocalDateTime.now());
    }

    private void processarTransferenciaInterna(Liquidez liquidez) {
        log.info("Processando transferência interna: {}", liquidez.getNumeroLiquidez());
        liquidez.setProtocoloSistema(gerarProtocolo("INT"));
        liquidez.setStatus(Liquidez.StatusLiquidez.LIQUIDADO);
        liquidez.setCodigoRetorno("000");
        liquidez.setMensagemRetorno("Transferência interna processada com sucesso");
        liquidez.setDataConfirmacao(LocalDateTime.now());
    }

    private void processarOperacaoGenerica(Liquidez liquidez) {
        log.info("Processando operação genérica: {}", liquidez.getNumeroLiquidez());
        liquidez.setProtocoloSistema(gerarProtocolo("GEN"));
        liquidez.setStatus(Liquidez.StatusLiquidez.LIQUIDADO);
        liquidez.setCodigoRetorno("000");
        liquidez.setMensagemRetorno("Operação processada com sucesso");
        liquidez.setDataConfirmacao(LocalDateTime.now());
    }

    private void movimentarSaldos(Liquidez liquidez) {
        log.info("Movimentando saldos para liquidação: {}", liquidez.getNumeroLiquidez());
        try {
            if (liquidez.getContaDestino() != null && !liquidez.getContaDestino().isBlank()) {
                MovimentoContaDTO credito = new MovimentoContaDTO();
                credito.setContaId(Long.parseLong(liquidez.getContaDestino()));
                credito.setTipoMovimento("CREDITO");
                credito.setValorMovimento(liquidez.getValorLiquido() != null
                    ? liquidez.getValorLiquido() : liquidez.getValor());
                credito.setDescricaoMovimento("Liquidação: " + liquidez.getNumeroLiquidez());
                credito.setDataMovimento(LocalDateTime.now());
                controleSaldoService.processarMovimento(credito);
            }
            if (liquidez.getContaOrigem() != null && !liquidez.getContaOrigem().isBlank()) {
                MovimentoContaDTO debito = new MovimentoContaDTO();
                debito.setContaId(Long.parseLong(liquidez.getContaOrigem()));
                debito.setTipoMovimento("DEBITO");
                debito.setValorMovimento(liquidez.getValor());
                debito.setDescricaoMovimento("Liquidação: " + liquidez.getNumeroLiquidez());
                debito.setDataMovimento(LocalDateTime.now());
                controleSaldoService.processarMovimento(debito);
            }
        } catch (Exception e) {
            log.error("Erro ao movimentar saldos: {}", e.getMessage());
            throw new RuntimeException("Failed to update balances: " + e.getMessage());
        }
    }

    public List<Liquidez> buscarLiquidezPendentes() {
        log.info("Buscando liquidações pendentes");
        List<Liquidez> pendentes = liquidezRepository.findByStatus(Liquidez.StatusLiquidez.PENDENTE);
        log.info("Encontradas {} liquidações pendentes", pendentes.size());
        return pendentes;
    }

    private void publicarEventoLiquidez(Liquidez liquidez) {
        try {
            if (liquidez.getStatus() == Liquidez.StatusLiquidez.LIQUIDADO) {
                eventPublisher.publicarLiquidezProcessada(LiquidezEvent.liquidezProcessada(
                        liquidez.getNumeroLiquidez(), liquidez.getContaOrigem(), liquidez.getContaDestino(),
                        liquidez.getValor(), liquidez.getTipoOperacao().name()));
            } else {
                eventPublisher.publicarLiquidezRejeitada(LiquidezEvent.liquidezRejeitada(
                        liquidez.getNumeroLiquidez(), liquidez.getContaOrigem(), liquidez.getContaDestino(),
                        liquidez.getValor(), liquidez.getTipoOperacao().name(), liquidez.getMensagemRetorno()));
            }
        } catch (Exception e) {
            log.warn("Falha ao publicar evento de liquidez {}: {}", liquidez.getNumeroLiquidez(), e.getMessage());
        }
    }

    private String gerarProtocolo(String prefixo) {
        return prefixo + "-" + System.currentTimeMillis() + "-" + (int) (Math.random() * 1000);
    }

    private boolean verificarHorarioFuncionamentoTED() {
        LocalDateTime agora = LocalDateTime.now(clock);
        int hora = agora.getHour();
        int diaSemana = agora.getDayOfWeek().getValue();
        return diaSemana >= 1 && diaSemana <= 5 && hora >= 6 && hora < 18;
    }

    private boolean verificarHorarioFuncionamentoDOC() {
        return verificarHorarioFuncionamentoTED();
    }

    @java.lang.SuppressWarnings("all")
    public SettlementService(final LiquidezRepository liquidezRepository, final EventPublisher eventPublisher, final ControleSaldoService controleSaldoService, final Clock clock) {
        this.liquidezRepository = liquidezRepository;
        this.eventPublisher = eventPublisher;
        this.controleSaldoService = controleSaldoService;
        this.clock = clock;
    }
}
