package com.aurix.platform.banking.core.service;

import com.aurix.platform.banking.core.dto.MovimentoContaDTO;
import com.aurix.platform.banking.core.entity.MovimentoConta;
import com.aurix.platform.banking.core.repository.TransacaoRepository;
import com.aurix.platform.banking.core.repository.TransactionLegRepository;
import com.aurix.platform.banking.core.validation.TransactionValidator;
import com.aurix.platform.shared.dto.ml.FraudAnalysisRequestDTO;
import com.aurix.platform.shared.entity.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class NtoNTransactionService {

    private static final Logger log = LoggerFactory.getLogger(NtoNTransactionService.class);

    private final TransactionValidator validator;
    private final ControleSaldoService saldoService;
    private final TransacaoRepository transacaoRepository;
    private final TransactionLegRepository legRepository;
    private final TracerService tracerService;
    private final FraudCheckService fraudCheckService;

    public NtoNTransactionService(TransactionValidator validator, ControleSaldoService saldoService,
                                  TransacaoRepository transacaoRepository, TransactionLegRepository legRepository,
                                  TracerService tracerService, FraudCheckService fraudCheckService) {
        this.validator = validator;
        this.saldoService = saldoService;
        this.transacaoRepository = transacaoRepository;
        this.legRepository = legRepository;
        this.tracerService = tracerService;
        this.fraudCheckService = fraudCheckService;
    }

    @Transactional
    public Transacao createNtoNTransaction(NtoNTransactionRequest request) {
        try (TracerService.Span span = tracerService.createSpan("NtoNTransactionService.create")) {
            List<TransactionLeg> legs = buildLegs(request);
            Transacao transaction = buildTransaction(request, legs);
            List<Conta> accounts = resolveAccounts(legs);

            transaction.setLegs(legs);

            performFraudCheck(transaction, request);

            TransactionValidator.ValidationResult result = validator.validate(transaction, accounts);

            if (!result.isValid()) {
                String msg = String.join("; ", result.getMessages());
                span.tag("error", msg);
                throw new IllegalArgumentException("Transaction validation failed: " + msg);
            }

            transaction.setStatus(Transacao.StatusTransacao.PROCESSADA);
            transaction.setDataProcessamento(LocalDateTime.now());
            transaction = transacaoRepository.save(transaction);

            for (TransactionLeg leg : legs) {
                leg.setTransaction(transaction);
                legRepository.save(leg);
            }

            processBalances(legs, transaction);

            span.tag("transactionId", String.valueOf(transaction.getId()))
                .tag("codigo", transaction.getCodigoTransacao())
                .tag("sources", String.valueOf(result.getNormalized().getSources().size()))
                .tag("destinations", String.valueOf(result.getNormalized().getDestinations().size()));

            log.info("n:n transaction created: {} with {} sources and {} destinations",
                    transaction.getCodigoTransacao(),
                    result.getNormalized().getSources().size(),
                    result.getNormalized().getDestinations().size());

            return transaction;
        }
    }

    private void performFraudCheck(Transacao transaction, NtoNTransactionRequest request) {
        FraudAnalysisRequestDTO fraudRequest = new FraudAnalysisRequestDTO();
        fraudRequest.setTransactionId(transaction.getCodigoTransacao());
        fraudRequest.setAmount(transaction.getValor());
        fraudRequest.setCurrency(request.currency());
        fraudRequest.setTransactionType(transaction.getTipoTransacao().name());
        if (!request.sources().isEmpty()) {
            fraudRequest.setAccountId(String.valueOf(request.sources().get(0).account().getId()));
        }
        if (request.metadata() != null) {
            fraudRequest.setChannel(request.metadata().getOrDefault("channel", "API"));
            fraudRequest.setIpAddress(request.metadata().get("ipAddress"));
        }
        if (fraudCheckService.isBlocked(fraudRequest)) {
            throw new SecurityException("Transaction blocked by fraud detection");
        }
    }

    private List<TransactionLeg> buildLegs(NtoNTransactionRequest request) {
        List<TransactionLeg> legs = new ArrayList<>();
        int order = 0;
        for (NtoNTransactionRequest.LegData src : request.sources()) {
            legs.add(createLeg(null, src.account(), src.amount(), src.currency(),
                    TransactionLeg.LegType.SOURCE, src.description(), order++));
        }
        for (NtoNTransactionRequest.LegData dst : request.destinations()) {
            legs.add(createLeg(null, dst.account(), dst.amount(), dst.currency(),
                    TransactionLeg.LegType.DESTINATION, dst.description(), order++));
        }
        return legs;
    }

    private TransactionLeg createLeg(Transacao transaction, Conta account, BigDecimal amount,
                                     String currency, TransactionLeg.LegType type,
                                     String description, int order) {
        TransactionLeg leg = new TransactionLeg();
        leg.setTransaction(transaction);
        leg.setLegType(type);
        leg.setAccount(account);
        leg.setAmount(amount);
        leg.setCurrency(currency != null ? currency : "BRL");
        leg.setDescription(description != null ? description : (type == TransactionLeg.LegType.SOURCE ? "source" : "destination"));
        leg.setOrderIndex(order);
        leg.setLegStatus(TransactionLeg.LegStatus.PENDING);
        return leg;
    }

    private Transacao buildTransaction(NtoNTransactionRequest request, List<TransactionLeg> legs) {
        Transacao t = new Transacao();
        t.setTipoTransacao(request.tipoTransacao());
        t.setValor(request.totalAmount());
        t.setDescricao(request.description());
        t.setCodigoTransacao("TXN-NN-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        t.setDataTransacao(LocalDateTime.now());
        t.setStatus(Transacao.StatusTransacao.PENDENTE);

        Optional<TransactionLeg> firstSource = legs.stream()
                .filter(l -> l.getLegType() == TransactionLeg.LegType.SOURCE).findFirst();
        Optional<TransactionLeg> firstDest = legs.stream()
                .filter(l -> l.getLegType() == TransactionLeg.LegType.DESTINATION).findFirst();
        firstSource.ifPresent(l -> t.setContaOrigem(l.getAccount()));
        firstDest.ifPresent(l -> t.setContaDestino(l.getAccount()));

        return t;
    }

    private List<Conta> resolveAccounts(List<TransactionLeg> legs) {
        Set<Conta> accounts = new LinkedHashSet<>();
        for (TransactionLeg leg : legs) {
            accounts.add(leg.getAccount());
        }
        return List.copyOf(accounts);
    }

    private void processBalances(List<TransactionLeg> legs, Transacao transaction) {
        for (TransactionLeg leg : legs) {
            MovimentoContaDTO dto = new MovimentoContaDTO();
            dto.setContaId(leg.getAccount().getId());
            dto.setTransacaoId(transaction.getId());
            dto.setValorMovimento(leg.getAmount());
            dto.setDescricaoMovimento(leg.getDescription());
            if (leg.getLegType() == TransactionLeg.LegType.SOURCE) {
                dto.setTipoMovimento(MovimentoConta.TipoMovimento.DEBITO.name());
            } else {
                dto.setTipoMovimento(MovimentoConta.TipoMovimento.CREDITO.name());
            }
            saldoService.processarMovimento(dto);
        }
    }

    public record NtoNTransactionRequest(
            List<LegData> sources,
            List<LegData> destinations,
            BigDecimal totalAmount,
            Transacao.TipoTransacao tipoTransacao,
            String description,
            String currency,
            Map<String, String> metadata
    ) {
        public NtoNTransactionRequest {
            sources = List.copyOf(sources);
            destinations = List.copyOf(destinations);
            currency = currency != null ? currency : "BRL";
        }

        public record LegData(
                Conta account,
                BigDecimal amount,
                String currency,
                String description
        ) {
            public LegData {
                currency = currency != null ? currency : "BRL";
            }
        }
    }
}
