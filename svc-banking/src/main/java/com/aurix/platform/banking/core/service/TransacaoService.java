package com.aurix.platform.banking.core.service;

import com.aurix.platform.banking.core.entity.ControleSaldo;
import com.aurix.platform.shared.repository.ContaRepository;
import com.aurix.platform.banking.core.repository.ControleSaldoRepository;
import com.aurix.platform.shared.repository.TransacaoRepository;
import com.aurix.platform.banking.core.validation.TransactionValidator;
import com.aurix.platform.shared.dto.TransacaoDTO;
import com.aurix.platform.shared.entity.Conta;
import com.aurix.platform.shared.entity.Transacao;
import com.aurix.platform.shared.event.EventPublisher;
import com.aurix.platform.shared.event.TransacaoEvent;
import com.aurix.platform.shared.tenant.TenantContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Serviço responsável pela gestão de transações financeiras.
 */
@Service
@Transactional
public class TransacaoService {
        @java.lang.SuppressWarnings("all")
        private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(TransacaoService.class);
        private static final int UUID_SUBSTRING_END = 8;
        /**
         * Repositório de transações.
         */
        private final TransacaoRepository transacaoRepository;
        private final ContaRepository contaRepository;
        private final ControleSaldoRepository controleSaldoRepository;
        private final EventPublisher eventPublisher;
        private final TracerService tracerService;
        private final TransactionValidator transactionValidator;

        public TransacaoService(final TransacaoRepository transacaoRepo, final ContaRepository contaRepo,
                                final ControleSaldoRepository controleSaldoRepo,
                                final EventPublisher publisher, final TracerService tracer,
                                final TransactionValidator transactionValidator) {
                this.transacaoRepository = transacaoRepo;
                this.contaRepository = contaRepo;
                this.controleSaldoRepository = controleSaldoRepo;
                this.eventPublisher = publisher;
                this.tracerService = tracer;
                this.transactionValidator = transactionValidator;
        }

        /**
         * Cria uma nova transação.
         *
         * @param dto DTO com os dados da transação.
         * @return DTO com os dados da transação criada.
         */
        public TransacaoDTO criar(final TransacaoDTO dto) {
                try (TracerService.Span span = tracerService.createSpan("TransacaoService.criar")) {
                        String tenantId = TenantContext.getTenantId();
                        Conta contaOrigem = null;
                        if (dto.getContaOrigemId() != null) {
                                contaOrigem = contaRepository.findByTenantIdAndId(tenantId, dto.getContaOrigemId()).orElseThrow(() -> new RuntimeException("Conta origem não encontrada"));
                        }
                        Conta contaDestino = null;
                        if (dto.getContaDestinoId() != null) {
                                contaDestino = contaRepository.findByTenantIdAndId(tenantId, dto.getContaDestinoId()).orElseThrow(() -> new RuntimeException("Conta destino não encontrada"));
                        }
                        Transacao t = new Transacao();
                        t.setTenantId(tenantId);
                        t.setContaOrigem(contaOrigem);
                        t.setContaDestino(contaDestino);
                        t.setTipoTransacao(dto.getTipoTransacao() != null ? dto.getTipoTransacao() : Transacao.TipoTransacao.TRANSFERENCIA_INTERNA);
                        t.setValor(dto.getValor());
                        t.setDescricao(dto.getDescricao());
                        t.setStatus(Transacao.StatusTransacao.PENDENTE);
                        t.setCodigoTransacao("TXN-" + UUID.randomUUID().toString().substring(0, UUID_SUBSTRING_END).toUpperCase(java.util.Locale.ROOT));
                        t.setDataTransacao(dto.getDataTransacao() != null ? dto.getDataTransacao() : LocalDateTime.now());
                        t.setDadosPix(dto.getDadosPix());
                        t.setDadosTed(dto.getDadosTed());

                        List<Conta> accounts = new ArrayList<>();
                        if (contaOrigem != null) accounts.add(contaOrigem);
                        if (contaDestino != null) accounts.add(contaDestino);
                        TransactionValidator.ValidationResult vResult = transactionValidator.validate(t, accounts);
                        if (!vResult.isValid()) {
                            String msg = String.join("; ", vResult.getMessages());
                            span.tag("validation-error", msg);
                            throw new IllegalArgumentException("Transaction validation failed: " + msg);
                        }

                        t = transacaoRepository.save(t);

                        span.tag("transactionId", String.valueOf(t.getId()))
                            .tag("tipo", t.getTipoTransacao().name())
                            .tag("valor", t.getValor().toString());

                        try {
                                String contaId = t.getContaOrigem() != null ? String.valueOf(t.getContaOrigem().getId()) : null;
                                boolean hasCliente = t.getContaOrigem() != null && t.getContaOrigem().getCliente() != null;
                                String clienteId = hasCliente ? String.valueOf(t.getContaOrigem().getCliente().getId()) : null;
                                String tipo = t.getTipoTransacao() != null ? t.getTipoTransacao().name() : "TRANSFERENCIA_INTERNA";
                                eventPublisher.publicarTransacaoRealizada(TransacaoEvent.transacaoRealizada(String.valueOf(t.getId()), contaId, clienteId, t.getValor(), tipo, t.getDescricao()));
                        } catch (RuntimeException e) {
                                log.warn("Falha ao publicar evento transacao-realizada" + " (RuntimeException): {}", e.getMessage());
                        } catch (Exception e) {
                                log.warn("Falha ao publicar evento transacao-realizada: {}", e.getMessage());
                        }
                        return toDTO(t);
                }
        }

        /**
         * Busca uma transação pelo seu ID.
         *
         * @param id ID da transação.
         * @return Optional com o DTO da transação, se encontrada.
         */
        public Optional<TransacaoDTO> buscarPorId(final Long id) {
                String tenantId = TenantContext.getTenantId();
                return transacaoRepository.findByTenantIdAndId(tenantId, id).map(this::toDTO);
        }

        /**
         * Busca uma transação pelo seu código único.
         *
         * @param codigoTransacao Código da transação.
         * @return Optional com o DTO da transação, se encontrada.
         */
        public Optional<TransacaoDTO> buscarPorCodigo(final String codigoTransacao) {
                String tenantId = TenantContext.getTenantId();
                return transacaoRepository.findByTenantIdAndCodigoTransacao(tenantId, codigoTransacao).map(this::toDTO);
        }

        /**
         * Lista todas as transações de uma conta.
         *
         * @param contaId ID da conta.
         * @return Lista de DTOs das transações.
         */
        @Transactional(readOnly = true)
        public List<TransacaoDTO> listarPorConta(final Long contaId) {
                String tenantId = TenantContext.getTenantId();
                Pageable p = Pageable.unpaged();
                return transacaoRepository.findByTenantIdAndContaIdOrderByDataTransacaoDesc(tenantId, contaId, p).getContent().stream().map(this::toDTO).collect(Collectors.toList());
        }

        /**
         * Lista as transações de uma conta de forma paginada.
         *
         * @param contaId  ID da conta.
         * @param pageable Configuração de paginação.
         * @return Página de DTOs das transações.
         */
        @Transactional(readOnly = true)
        public Page<TransacaoDTO> listarPorConta(final Long contaId, final Pageable pageable) {
                String tenantId = TenantContext.getTenantId();
                return transacaoRepository.findByTenantIdAndContaIdOrderByDataTransacaoDesc(tenantId, contaId, pageable).map(this::toDTO);
        }

        /**
         * Lista as transações de uma conta em um período específico.
         *
         * @param contaId  ID da conta.
         * @param inicio   Data inicial.
         * @param fim      Data final.
         * @param pageable Configuração de paginação.
         * @return Página de DTOs das transações.
         */
        @Transactional(readOnly = true)
        public Page<TransacaoDTO> listarPorContaEPeriodo(final Long contaId, final LocalDateTime inicio, final LocalDateTime fim, final Pageable pageable) {
                String tenantId = TenantContext.getTenantId();
                return transacaoRepository.findByTenantIdAndContaIdEPeriodo(tenantId, contaId, inicio, fim, pageable).map(this::toDTO);
        }

        /**
         * Lista todas as transações pendentes.
         *
         * @return Lista de DTOs das transações pendentes.
         */
        @Transactional(readOnly = true)
        public List<TransacaoDTO> listarPendentes() {
                String tenantId = TenantContext.getTenantId();
                return transacaoRepository.findTransacoesPendentesByTenantId(tenantId, Pageable.unpaged()).getContent().stream().map(this::toDTO).collect(Collectors.toList());
        }

        /**
         * Lista as transações pendentes de forma paginada.
         *
         * @param pageable Configuração de paginação.
         * @return Página de DTOs das transações pendentes.
         */
        @Transactional(readOnly = true)
        public Page<TransacaoDTO> listarPendentes(final Pageable pageable) {
                String tenantId = TenantContext.getTenantId();
                return transacaoRepository.findTransacoesPendentesByTenantId(tenantId, pageable).map(this::toDTO);
        }

        /**
         * Converte uma entidade Transacao para um DTO.
         *
         * @param t Entidade da transação.
         * @return DTO correspondente.
         */
        private TransacaoDTO toDTO(final Transacao t) {
                TransacaoDTO dto = new TransacaoDTO();
                dto.setId(t.getId());
                Long oriId = t.getContaOrigem() != null ? t.getContaOrigem().getId() : null;
                dto.setContaOrigemId(oriId);
                String oriNum = t.getContaOrigem() != null ? t.getContaOrigem().getNumeroConta() : null;
                dto.setContaOrigemNumero(oriNum);
                Long desId = t.getContaDestino() != null ? t.getContaDestino().getId() : null;
                dto.setContaDestinoId(desId);
                String desNum = t.getContaDestino() != null ? t.getContaDestino().getNumeroConta() : null;
                dto.setContaDestinoNumero(desNum);
                dto.setTipoTransacao(t.getTipoTransacao());
                dto.setValor(t.getValor());
                dto.setDescricao(t.getDescricao());
                dto.setStatus(t.getStatus());
                dto.setCodigoTransacao(t.getCodigoTransacao());
                dto.setDadosPix(t.getDadosPix());
                dto.setDadosTed(t.getDadosTed());
                dto.setDataTransacao(t.getDataTransacao());
                dto.setDataProcessamento(t.getDataProcessamento());
                populateBalanceInfo(dto, t);
                return dto;
        }

        private void populateBalanceInfo(TransacaoDTO dto, Transacao t) {
                if (t.getContaOrigem() != null) {
                        controleSaldoRepository.findByContaId(t.getContaOrigem().getId())
                                .ifPresent(cs -> {
                                        dto.setSaldoPosteriorOrigem(cs.getSaldoDisponivel());
                                        BigDecimal valor = t.getValor() != null ? t.getValor() : BigDecimal.ZERO;
                                        dto.setSaldoAnteriorOrigem(cs.getSaldoDisponivel().add(valor));
                                });
                }
                if (t.getContaDestino() != null) {
                        controleSaldoRepository.findByContaId(t.getContaDestino().getId())
                                .ifPresent(cs -> {
                                        dto.setSaldoPosteriorDestino(cs.getSaldoDisponivel());
                                        BigDecimal valor = t.getValor() != null ? t.getValor() : BigDecimal.ZERO;
                                        dto.setSaldoAnteriorDestino(cs.getSaldoDisponivel().subtract(valor));
                                });
                }
        }
}
