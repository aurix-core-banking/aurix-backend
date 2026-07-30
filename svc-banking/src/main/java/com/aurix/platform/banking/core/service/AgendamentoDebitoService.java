package com.aurix.platform.banking.core.service;

import com.aurix.platform.banking.core.dto.LiquidacaoDTO;
import com.aurix.platform.banking.core.entity.AgendamentoDebito;
import com.aurix.platform.banking.core.repository.AgendamentoDebitoRepository;
import com.aurix.platform.banking.core.repository.ContaRepository;
import com.aurix.platform.shared.entity.Conta;
import com.aurix.platform.shared.entity.Transacao;
import com.aurix.platform.shared.tenant.TenantContext;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class AgendamentoDebitoService {
    @java.lang.SuppressWarnings("all")
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(AgendamentoDebitoService.class);
    private final AgendamentoDebitoRepository agendamentoDebitoRepository;
    private final ContaRepository contaRepository;
    private final TransacaoService transacaoService;
    private final LiquidacaoService liquidacaoService;
    private final BoletoService boletoService;

    public AgendamentoDebito agendar(Long contaId, BigDecimal valor, LocalDate dataDebito, String descricao, Long boletoId, Boolean recorrente, String periodicidade) {
        String tenantId = TenantContext.getTenantId();
        Conta conta = contaRepository.findByTenantIdAndId(tenantId, contaId).orElseThrow(() -> new RuntimeException("Conta não encontrada"));
        AgendamentoDebito a = new AgendamentoDebito();
        a.setTenantId(tenantId);
        a.setContaId(conta.getId());
        a.setValor(valor);
        a.setDataDebito(dataDebito);
        a.setDescricao(descricao != null ? descricao : "Débito automático");
        a.setStatus(AgendamentoDebito.StatusAgendamento.AGENDADO);
        a.setBoletoId(boletoId);
        a.setRecorrente(Boolean.TRUE.equals(recorrente));
        a.setPeriodicidade(periodicidade);
        return agendamentoDebitoRepository.save(a);
    }

    public AgendamentoDebito cancelar(Long id) {
        AgendamentoDebito a = agendamentoDebitoRepository.findById(id).orElseThrow(() -> new RuntimeException("Agendamento não encontrado"));
        if (a.getStatus() != AgendamentoDebito.StatusAgendamento.AGENDADO) {
            throw new RuntimeException("Apenas agendamentos pendentes podem ser cancelados");
        }
        a.setStatus(AgendamentoDebito.StatusAgendamento.CANCELADO);
        return agendamentoDebitoRepository.save(a);
    }

    public void executarDebitosPendentes(LocalDate ateData) {
        List<AgendamentoDebito> pendentes = agendamentoDebitoRepository.findPendentesAteData(ateData);
        for (AgendamentoDebito a : pendentes) {
            try {
                if (a.getBoletoId() != null) {
                    boletoService.registrarPagamento(a.getBoletoId(), a.getContaId());
                } else {
                    executarDebitoGenerico(a);
                }
                a.setStatus(AgendamentoDebito.StatusAgendamento.EXECUTADO);
                a.setDataExecucao(LocalDateTime.now());
                agendamentoDebitoRepository.save(a);
            } catch (Exception e) {
                log.warn("Falha ao executar débito agendado id={}: {}", a.getId(), e.getMessage());
                a.setStatus(AgendamentoDebito.StatusAgendamento.FALHOU);
                agendamentoDebitoRepository.save(a);
            }
        }
    }

    private void executarDebitoGenerico(AgendamentoDebito a) {
        com.aurix.platform.shared.dto.TransacaoDTO txDto = new com.aurix.platform.shared.dto.TransacaoDTO();
        txDto.setContaOrigemId(a.getContaId());
        txDto.setContaDestinoId(null);
        txDto.setTipoTransacao(Transacao.TipoTransacao.TRANSFERENCIA_INTERNA);
        txDto.setValor(a.getValor());
        txDto.setDescricao(a.getDescricao());
        com.aurix.platform.shared.dto.TransacaoDTO txCriada = transacaoService.criar(txDto);
        a.setTransacaoId(txCriada.getId());
        LiquidacaoDTO liqDto = new LiquidacaoDTO();
        liqDto.setTransacaoId(txCriada.getId());
        liqDto.setTipoLiquidacao("OUTROS");
        liqDto.setStatus("PENDENTE");
        liqDto.setValorLiquidacao(a.getValor());
        liqDto.setValorTaxa(BigDecimal.ZERO);
        liqDto.setValorTotal(a.getValor());
        liqDto.setProcessamentoAutomatico(true);
        liqDto.setReversivel(true);
        liquidacaoService.criarLiquidacao(liqDto);
    }

    public List<AgendamentoDebito> listarPorConta(Long contaId) {
        return agendamentoDebitoRepository.findByContaId(contaId);
    }

    public List<AgendamentoDebito> listarPendentes() {
        return agendamentoDebitoRepository.findPendentesAteData(LocalDate.now().plusDays(365));
    }

    @Scheduled(cron = "0 0 6 * * *")
    public void executarDebitosAgendados() {
        log.debug("Executando débitos automáticos agendados para hoje");
        executarDebitosPendentes(LocalDate.now());
    }

    @java.lang.SuppressWarnings("all")
    public AgendamentoDebitoService(final AgendamentoDebitoRepository agendamentoDebitoRepository, final ContaRepository contaRepository, final TransacaoService transacaoService, final LiquidacaoService liquidacaoService, final BoletoService boletoService) {
        this.agendamentoDebitoRepository = agendamentoDebitoRepository;
        this.contaRepository = contaRepository;
        this.transacaoService = transacaoService;
        this.liquidacaoService = liquidacaoService;
        this.boletoService = boletoService;
    }
}
