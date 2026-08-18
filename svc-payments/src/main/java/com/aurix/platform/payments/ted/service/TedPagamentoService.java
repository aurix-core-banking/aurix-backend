package com.aurix.platform.payments.ted.service;

import com.aurix.platform.payments.ted.repository.PagamentoTedRepository;
import com.aurix.platform.shared.dto.PagamentoTedDTO;
import com.aurix.platform.shared.entity.Conta;
import com.aurix.platform.shared.entity.PagamentoTed;
import com.aurix.platform.shared.repository.ContaRepository;
import com.aurix.platform.shared.tenant.TenantContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Serviço para gestão de pagamentos TED e DOC.
 * TED: valor até R$ 4.999,99, processamento no horário SPB (D+0).
 * DOC: valor igual ou superior a R$ 5.000,00, agendamento D+1.
 */
@Service
@Transactional
public class TedPagamentoService {
    @java.lang.SuppressWarnings("all")
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(TedPagamentoService.class);
    private static final BigDecimal LIMITE_TED = new BigDecimal("4999.99");
    private static final BigDecimal LIMITE_DOC = new BigDecimal("5000.00");

    private final PagamentoTedRepository pagamentoTedRepository;
    private final ContaRepository contaRepository;

    /**
     * Cria um pagamento TED ou DOC.
     * Se valor <= 4999.99 → TED (D+0, horário SPB).
     * Se valor >= 5000.00 → DOC (D+1).
     */
    public PagamentoTedDTO criarPagamento(PagamentoTedDTO dto) {
        log.info("Criando pagamento TED/DOC para conta origem: {}", dto.getContaOrigemId());
        Conta contaOrigem = contaRepository.findById(dto.getContaOrigemId())
                .orElseThrow(() -> new IllegalArgumentException("Conta origem não encontrada: " + dto.getContaOrigemId()));

        PagamentoTed pagamento = new PagamentoTed();
        pagamento.setCodigoTed(gerarCodigoTed());
        pagamento.setContaOrigem(contaOrigem);
        pagamento.setNomeDestinatario(dto.getNomeDestinatario());
        pagamento.setCpfCnpjDestino(dto.getCpfCnpjDestino());
        pagamento.setBancoDestino(dto.getBancoDestino());
        pagamento.setAgenciaDestino(dto.getAgenciaDestino());
        pagamento.setContaDestino(dto.getContaDestino());
        pagamento.setIspbDestino(dto.getIspbDestino());
        pagamento.setValor(dto.getValor());
        pagamento.setDescricao(dto.getDescricao());
        pagamento.setDadosAdicionais(dto.getDadosAdicionais());

        boolean isDoc = dto.getValor().compareTo(LIMITE_DOC) >= 0;
        pagamento.setTipoPagamento(isDoc ? PagamentoTed.TipoPagamento.DOC : PagamentoTed.TipoPagamento.TED);

        if (isDoc) {
            pagamento.setStatus(PagamentoTed.StatusTed.AGENDADO);
            pagamento.setDataAgendamento(proximoDiaUtil(LocalDateTime.now().plusDays(1)));
        } else {
            pagamento.setStatus(PagamentoTed.StatusTed.PENDENTE);
        }

        PagamentoTed salvo = pagamentoTedRepository.save(pagamento);
        log.info("Pagamento {} criado com código: {}", isDoc ? "DOC" : "TED", salvo.getCodigoTed());
        return converterParaDTO(salvo);
    }

    /**
     * Busca pagamento por ID.
     */
    @Transactional(readOnly = true)
    public PagamentoTedDTO buscarPorId(Long id) {
        log.info("Buscando pagamento TED/DOC por ID: {}", id);
        PagamentoTed pagamento = pagamentoTedRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Pagamento TED/DOC não encontrado: " + id));
        return converterParaDTO(pagamento);
    }

    /**
     * Cancela pagamento TED/DOC pendente ou agendado.
     */
    public void cancelarPagamento(Long id) {
        log.info("Cancelando pagamento TED/DOC ID: {}", id);
        PagamentoTed pagamento = pagamentoTedRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Pagamento TED/DOC não encontrado: " + id));
        if (pagamento.getStatus() != PagamentoTed.StatusTed.PENDENTE
                && pagamento.getStatus() != PagamentoTed.StatusTed.AGENDADO) {
            throw new IllegalStateException("Pagamento não pode ser cancelado — status atual: " + pagamento.getStatus().getDescricao());
        }
        pagamento.setStatus(PagamentoTed.StatusTed.CANCELADO);
        pagamento.setDataProcessamento(LocalDateTime.now());
        pagamento.setCodigoRetorno("99");
        pagamento.setMensagemRetorno("Pagamento cancelado pelo usuário");
        pagamentoTedRepository.save(pagamento);
        log.info("Pagamento TED/DOC cancelado com sucesso");
    }

    /**
     * Lista pagamentos por conta.
     */
    @Transactional(readOnly = true)
    public List<PagamentoTedDTO> listarPorConta(Long contaId) {
        return pagamentoTedRepository.findByContaOrigemId(contaId).stream()
                .map(this::converterParaDTO)
                .collect(Collectors.toList());
    }

    /**
     * Lista pagamentos por período.
     */
    @Transactional(readOnly = true)
    public List<PagamentoTedDTO> listarPorPeriodo(LocalDateTime inicio, LocalDateTime fim) {
        return pagamentoTedRepository.findByPeriodo(inicio, fim).stream()
                .map(this::converterParaDTO)
                .collect(Collectors.toList());
    }

    /**
     * Processa pagamentos TED pendentes dentro do horário SPB.
     * Chamado por scheduler.
     */
    public void processarPagamentosPendentes() {
        log.info("Processando pagamentos TED pendentes no horário SPB");
        LocalDateTime agora = LocalDateTime.now();
        List<PagamentoTed> pendentes = pagamentoTedRepository.findPendentesParaProcessar(agora);
        for (PagamentoTed pagamento : pendentes) {
            try {
                processarPagamentoIndividual(pagamento);
            } catch (Exception e) {
                log.error("Erro ao processar pagamento TED ID {}: {}", pagamento.getId(), e.getMessage());
                pagamento.setStatus(PagamentoTed.StatusTed.FALHADO);
                pagamento.setCodigoRetorno("98");
                pagamento.setMensagemRetorno("Erro no processamento: " + e.getMessage());
                pagamento.setDataProcessamento(LocalDateTime.now());
                pagamentoTedRepository.save(pagamento);
            }
        }
    }

    /**
     * Processa um pagamento TED individual: debita a conta de origem.
     */
    private void processarPagamentoIndividual(PagamentoTed pagamento) {
        String tenantId = TenantContext.getTenantId();
        int debitado = contaRepository.debitarSaldoAtomico(
                tenantId, pagamento.getContaOrigem().getId(), pagamento.getValor());
        if (debitado == 0) {
            pagamento.setStatus(PagamentoTed.StatusTed.FALHADO);
            pagamento.setCodigoRetorno("99");
            pagamento.setMensagemRetorno("Saldo insuficiente na conta de origem");
            pagamento.setDataProcessamento(LocalDateTime.now());
            pagamentoTedRepository.save(pagamento);
            throw new IllegalStateException("Saldo insuficiente para pagamento TED/DOC ID: " + pagamento.getId());
        }
        pagamento.setStatus(PagamentoTed.StatusTed.PROCESSADO);
        pagamento.setDataProcessamento(LocalDateTime.now());
        pagamento.setCodigoRetorno("00");
        pagamento.setMensagemRetorno("Pagamento processado com sucesso via SPB");
        pagamentoTedRepository.save(pagamento);
        log.info("Pagamento TED/DOC processado — código: {}", pagamento.getCodigoTed());
    }

    /**
     * Verifica se é horário de processamento SPB (09:00–17:00, dias úteis).
     */
    private boolean isHorarioSPB(LocalDateTime dateTime) {
        if (dateTime.getDayOfWeek().getValue() > 5) {
            return false;
        }
        LocalTime hora = dateTime.toLocalTime();
        return !hora.isBefore(LocalTime.of(9, 0)) && !hora.isAfter(LocalTime.of(17, 0));
    }

    /**
     * Retorna próximo dia útil (simplificado: pula fins de semana).
     */
    private LocalDateTime proximoDiaUtil(LocalDateTime data) {
        LocalDateTime resultado = data.withHour(9).withMinute(0).withSecond(0);
        while (resultado.getDayOfWeek().getValue() > 5) {
            resultado = resultado.plusDays(1);
        }
        return resultado;
    }

    private String gerarCodigoTed() {
        return "TED" + System.currentTimeMillis()
                + UUID.randomUUID().toString().replace("-", "").substring(0, 6).toUpperCase();
    }

    private PagamentoTedDTO converterParaDTO(PagamentoTed entity) {
        PagamentoTedDTO dto = new PagamentoTedDTO();
        dto.setId(entity.getId());
        dto.setCodigoTed(entity.getCodigoTed());
        dto.setContaOrigemId(entity.getContaOrigem() != null ? entity.getContaOrigem().getId() : null);
        dto.setContaOrigemNumero(entity.getContaOrigem() != null ? entity.getContaOrigem().getNumeroConta() : null);
        dto.setTipoPagamento(entity.getTipoPagamento());
        dto.setNomeDestinatario(entity.getNomeDestinatario());
        dto.setCpfCnpjDestino(entity.getCpfCnpjDestino());
        dto.setBancoDestino(entity.getBancoDestino());
        dto.setAgenciaDestino(entity.getAgenciaDestino());
        dto.setContaDestino(entity.getContaDestino());
        dto.setIspbDestino(entity.getIspbDestino());
        dto.setValor(entity.getValor());
        dto.setDescricao(entity.getDescricao());
        dto.setStatus(entity.getStatus());
        dto.setDataAgendamento(entity.getDataAgendamento());
        dto.setDataProcessamento(entity.getDataProcessamento());
        dto.setCodigoRetorno(entity.getCodigoRetorno());
        dto.setMensagemRetorno(entity.getMensagemRetorno());
        dto.setDadosAdicionais(entity.getDadosAdicionais());
        dto.setDataCriacao(entity.getDataCriacao() != null ? entity.getDataCriacao().toString() : null);
        dto.setDataAtualizacao(entity.getDataAtualizacao() != null ? entity.getDataAtualizacao().toString() : null);
        return dto;
    }

    @java.lang.SuppressWarnings("all")
    public TedPagamentoService(final PagamentoTedRepository pagamentoTedRepository, final ContaRepository contaRepository) {
        this.pagamentoTedRepository = pagamentoTedRepository;
        this.contaRepository = contaRepository;
    }
}
