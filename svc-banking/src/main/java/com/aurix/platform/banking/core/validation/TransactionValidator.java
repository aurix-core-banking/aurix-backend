package com.aurix.platform.banking.core.validation;

import com.aurix.platform.banking.core.entity.ControleSaldo;
import com.aurix.platform.banking.core.entity.ValidationRule;
import com.aurix.platform.banking.core.repository.AssetRateRepository;
import com.aurix.platform.banking.core.repository.ControleSaldoRepository;
import com.aurix.platform.banking.core.repository.TransacaoRepository;
import com.aurix.platform.banking.core.repository.ValidationRuleRepository;
import com.aurix.platform.shared.entity.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class TransactionValidator {

    private static final Logger log = LoggerFactory.getLogger(TransactionValidator.class);

    private static final BigDecimal MAX_TRANSACTION_AMOUNT = new BigDecimal("1000000");
    private static final BigDecimal SHARE_TOLERANCE = new BigDecimal("0.01");
    private static final int SCALE = 4;

    private final ControleSaldoRepository controleSaldoRepository;
    private final AssetRateRepository assetRateRepository;
    private final TransacaoRepository transacaoRepository;
    private final ValidationRuleRepository validationRuleRepository;
    private final ExpressionParser spelParser;
    private final BigDecimal defaultDailyLimit;

    public TransactionValidator(ControleSaldoRepository controleSaldoRepository,
                                AssetRateRepository assetRateRepository,
                                TransacaoRepository transacaoRepository,
                                ValidationRuleRepository validationRuleRepository,
                                @Value("${transaction.daily-limit.default:50000}") BigDecimal defaultDailyLimit) {
        this.controleSaldoRepository = controleSaldoRepository;
        this.assetRateRepository = assetRateRepository;
        this.transacaoRepository = transacaoRepository;
        this.validationRuleRepository = validationRuleRepository;
        this.spelParser = new SpelExpressionParser();
        this.defaultDailyLimit = defaultDailyLimit;
    }

    public enum ErrorCode {
        E0001_INVALID_AMOUNT,
        E0002_INSUFFICIENT_BALANCE,
        E0003_ACCOUNT_BLOCKED,
        E0004_CURRENCY_MISMATCH,
        E0005_SOURCE_DESTINATION_SAME,
        E0006_NO_SOURCES,
        E0007_NO_DESTINATIONS,
        E0008_UNBALANCED_TRANSACTION,
        E0009_RATE_NOT_FOUND,
        E0010_ACCOUNT_NOT_FOUND,
        E0011_INVALID_SHARE_PERCENTAGE,
        E0012_DAILY_LIMIT_EXCEEDED,
        E0013_AMOUNT_MISMATCH,
    }

    public static class ValidationResult {
        private final boolean valid;
        private final List<ErrorCode> errors;
        private final List<String> messages;
        private final NormalizedTransaction normalized;

        public ValidationResult(boolean valid, List<ErrorCode> errors, List<String> messages, NormalizedTransaction normalized) {
            this.valid = valid;
            this.errors = errors;
            this.messages = messages;
            this.normalized = normalized;
        }

        public boolean isValid() { return valid; }
        public List<ErrorCode> getErrors() { return errors; }
        public List<String> getMessages() { return messages; }
        public NormalizedTransaction getNormalized() { return normalized; }

        public static ValidationResult valid(NormalizedTransaction normalized) {
            return new ValidationResult(true, List.of(), List.of(), normalized);
        }

        public static ValidationResult invalid(ErrorCode code, String message, NormalizedTransaction normalized) {
            return new ValidationResult(false, List.of(code), List.of(message), normalized);
        }

        public static ValidationResult invalidMultiple(List<ErrorCode> codes, List<String> messages, NormalizedTransaction normalized) {
            return new ValidationResult(false, codes, messages, normalized);
        }
    }

    public static class NormalizedTransaction {
        private final String transactionId;
        private final String description;
        private final List<Leg> sources;
        private final List<Leg> destinations;
        private final String currency;
        private final Map<String, BigDecimal> rates;
        private final LocalDateTime timestamp;

        public NormalizedTransaction(String transactionId, String description,
                                      List<Leg> sources, List<Leg> destinations,
                                      String currency, Map<String, BigDecimal> rates,
                                      LocalDateTime timestamp) {
            this.transactionId = transactionId;
            this.description = description;
            this.sources = List.copyOf(sources);
            this.destinations = List.copyOf(destinations);
            this.currency = currency;
            this.rates = rates;
            this.timestamp = timestamp;
        }

        public String getTransactionId() { return transactionId; }
        public String getDescription() { return description; }
        public List<Leg> getSources() { return sources; }
        public List<Leg> getDestinations() { return destinations; }
        public String getCurrency() { return currency; }
        public Map<String, BigDecimal> getRates() { return rates; }
        public LocalDateTime getTimestamp() { return timestamp; }
    }

    public static class Leg {
        private final Conta account;
        private final BigDecimal amount;
        private final String currency;
        private final BigDecimal convertedAmount;
        private final BigDecimal sharePercentage;
        private final String description;
        private final int order;

        public Leg(Conta account, BigDecimal amount, String currency,
                   BigDecimal convertedAmount, BigDecimal sharePercentage,
                   String description, int order) {
            this.account = account;
            this.amount = amount;
            this.currency = currency;
            this.convertedAmount = convertedAmount;
            this.sharePercentage = sharePercentage;
            this.description = description;
            this.order = order;
        }

        public Conta getAccount() { return account; }
        public BigDecimal getAmount() { return amount; }
        public String getCurrency() { return currency; }
        public BigDecimal getConvertedAmount() { return convertedAmount; }
        public BigDecimal getSharePercentage() { return sharePercentage; }
        public String getDescription() { return description; }
        public int getOrder() { return order; }
    }

    public ValidationResult validate(Transacao transaction, List<Conta> accounts) {
        NormalizedTransaction normalized = normalize(transaction, accounts);
        return executeValidation(normalized);
    }

    public NormalizedTransaction normalize(Transacao transaction, List<Conta> accounts) {
        String currency = "BRL";
        LocalDateTime now = LocalDateTime.now();

        List<TransactionLeg> txLegs = transaction.getLegs();
        if (txLegs != null && !txLegs.isEmpty()) {
            return normalizeFromLegs(transaction, txLegs, now);
        }

        return normalizeFromSimple(transaction, accounts, currency, now);
    }

    private NormalizedTransaction normalizeFromLegs(Transacao transaction, List<TransactionLeg> txLegs, LocalDateTime now) {
        List<Leg> sources = new ArrayList<>();
        List<Leg> destinations = new ArrayList<>();
        String currency = "BRL";

        for (TransactionLeg txLeg : txLegs) {
            Leg leg = new Leg(
                    txLeg.getAccount(),
                    txLeg.getAmount(),
                    txLeg.getCurrency() != null ? txLeg.getCurrency() : currency,
                    txLeg.getConvertedAmount(),
                    txLeg.getSharePercentage(),
                    txLeg.getDescription(),
                    txLeg.getOrderIndex() != null ? txLeg.getOrderIndex() : 0
            );
            if (txLeg.getLegType() == TransactionLeg.LegType.SOURCE) {
                sources.add(leg);
            } else {
                destinations.add(leg);
            }
        }

        String detectedCurrency = detectCurrency(sources, destinations, currency);
        return new NormalizedTransaction(
                transaction.getCodigoTransacao(),
                transaction.getDescricao(),
                sources, destinations, detectedCurrency,
                Map.of(), now
        );
    }

    private NormalizedTransaction normalizeFromSimple(Transacao transaction, List<Conta> accounts, String currency, LocalDateTime now) {
        List<Leg> sources = new ArrayList<>();
        List<Leg> destinations = new ArrayList<>();

        if (transaction.getContaOrigem() != null) {
            Conta src = transaction.getContaOrigem();
            sources.add(new Leg(src, transaction.getValor(), "BRL", transaction.getValor(),
                    BigDecimal.valueOf(100), "source", 0));
        }
        if (transaction.getContaDestino() != null) {
            Conta dst = transaction.getContaDestino();
            destinations.add(new Leg(dst, transaction.getValor(), "BRL", transaction.getValor(),
                    BigDecimal.valueOf(100), "destination", 0));
        }

        return new NormalizedTransaction(
                transaction.getCodigoTransacao(),
                transaction.getDescricao(),
                sources, destinations, currency,
                Map.of(), now
        );
    }

    private String detectCurrency(List<Leg> sources, List<Leg> destinations, String defaultCurrency) {
        for (Leg leg : sources) {
            if (leg.getCurrency() != null) return leg.getCurrency();
        }
        for (Leg leg : destinations) {
            if (leg.getCurrency() != null) return leg.getCurrency();
        }
        return defaultCurrency;
    }

    private ValidationResult executeValidation(NormalizedTransaction normalized) {
        List<ErrorCode> errors = new ArrayList<>();
        List<String> messages = new ArrayList<>();

        checkNoSources(normalized, errors, messages);
        checkNoDestinations(normalized, errors, messages);
        checkInvalidAmounts(normalized, errors, messages);
        checkBalance(normalized, errors, messages);
        checkBlockedAccounts(normalized, errors, messages);
        checkCurrencyMismatch(normalized, errors, messages);
        checkSourceDestinationSame(normalized, errors, messages);
        checkUnbalanced(normalized, errors, messages);
        checkRateExists(normalized, errors, messages);
        checkAccountsExist(normalized, errors, messages);
        checkSharePercentages(normalized, errors, messages);
        checkDailyLimit(normalized, errors, messages);
        checkAmountMatch(normalized, errors, messages);
        checkSpelRules(normalized, errors, messages, true, false);

        return errors.isEmpty()
                ? ValidationResult.valid(normalized)
                : ValidationResult.invalidMultiple(errors, messages, normalized);
    }

    private void checkNoSources(NormalizedTransaction normalized, List<ErrorCode> errors, List<String> messages) {
        if (normalized.getSources().isEmpty()) {
            errors.add(ErrorCode.E0006_NO_SOURCES);
            messages.add("Transaction must have at least one source");
        }
    }

    private void checkNoDestinations(NormalizedTransaction normalized, List<ErrorCode> errors, List<String> messages) {
        if (normalized.getDestinations().isEmpty()) {
            errors.add(ErrorCode.E0007_NO_DESTINATIONS);
            messages.add("Transaction must have at least one destination");
        }
    }

    private void checkInvalidAmounts(NormalizedTransaction normalized, List<ErrorCode> errors, List<String> messages) {
        Set<String> invalidLegs = new LinkedHashSet<>();
        for (Leg leg : normalized.getSources()) {
            if (leg.getAmount() == null || leg.getAmount().compareTo(BigDecimal.ZERO) <= 0
                    || leg.getAmount().compareTo(MAX_TRANSACTION_AMOUNT) > 0) {
                invalidLegs.add(leg.getDescription() != null ? leg.getDescription() : "source-" + leg.getOrder());
            }
        }
        for (Leg leg : normalized.getDestinations()) {
            if (leg.getAmount() == null || leg.getAmount().compareTo(BigDecimal.ZERO) <= 0
                    || leg.getAmount().compareTo(MAX_TRANSACTION_AMOUNT) > 0) {
                invalidLegs.add(leg.getDescription() != null ? leg.getDescription() : "dest-" + leg.getOrder());
            }
        }
        if (!invalidLegs.isEmpty()) {
            errors.add(ErrorCode.E0001_INVALID_AMOUNT);
            messages.add(String.format("Invalid amounts (must be > 0 and <= %s): %s",
                    MAX_TRANSACTION_AMOUNT, String.join(", ", invalidLegs)));
        }
    }

    private void checkBalance(NormalizedTransaction normalized, List<ErrorCode> errors, List<String> messages) {
        Map<Long, BigDecimal> sourceSumByAccount = new HashMap<>();
        for (Leg source : normalized.getSources()) {
            if (source.getAmount() != null && source.getAmount().compareTo(BigDecimal.ZERO) > 0) {
                Long accountId = source.getAccount().getId();
                sourceSumByAccount.merge(accountId, source.getAmount(), BigDecimal::add);
            }
        }
        for (Map.Entry<Long, BigDecimal> entry : sourceSumByAccount.entrySet()) {
            Optional<ControleSaldo> opt = controleSaldoRepository.findByContaId(entry.getKey());
            if (opt.isEmpty()) {
                errors.add(ErrorCode.E0010_ACCOUNT_NOT_FOUND);
                messages.add(String.format("Balance control not found for account %d", entry.getKey()));
                continue;
            }
            BigDecimal available = opt.get().getSaldoDisponivel();
            if (available.compareTo(entry.getValue()) < 0) {
                errors.add(ErrorCode.E0002_INSUFFICIENT_BALANCE);
                messages.add(String.format("Insufficient balance for account %d: available=%s required=%s",
                        entry.getKey(), available, entry.getValue()));
            }
        }
    }

    private void checkBlockedAccounts(NormalizedTransaction normalized, List<ErrorCode> errors, List<String> messages) {
        for (Leg source : normalized.getSources()) {
            if (source.getAccount().getStatus() != Conta.StatusConta.ATIVA) {
                errors.add(ErrorCode.E0003_ACCOUNT_BLOCKED);
                messages.add(String.format("Source account %s is not active: %s",
                        source.getAccount().getNumeroConta(), source.getAccount().getStatus()));
            }
        }
    }

    private void checkCurrencyMismatch(NormalizedTransaction normalized, List<ErrorCode> errors, List<String> messages) {
        Set<String> currencies = new HashSet<>();
        for (Leg leg : normalized.getSources()) {
            if (leg.getCurrency() != null) currencies.add(leg.getCurrency());
        }
        for (Leg leg : normalized.getDestinations()) {
            if (leg.getCurrency() != null) currencies.add(leg.getCurrency());
        }
        if (currencies.size() > 1) {
            errors.add(ErrorCode.E0004_CURRENCY_MISMATCH);
            messages.add(String.format("Currency mismatch across legs: %s", String.join(", ", currencies)));
        }
    }

    private void checkSourceDestinationSame(NormalizedTransaction normalized, List<ErrorCode> errors, List<String> messages) {
        Set<Long> sourceIds = normalized.getSources().stream()
                .map(l -> l.getAccount().getId())
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
        Set<Long> destIds = normalized.getDestinations().stream()
                .map(l -> l.getAccount().getId())
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
        sourceIds.retainAll(destIds);
        if (!sourceIds.isEmpty()) {
            errors.add(ErrorCode.E0005_SOURCE_DESTINATION_SAME);
            messages.add(String.format("Account(s) cannot be both source and destination: %s", sourceIds));
        }
    }

    private void checkUnbalanced(NormalizedTransaction normalized, List<ErrorCode> errors, List<String> messages) {
        BigDecimal totalSource = normalized.getSources().stream()
                .map(Leg::getAmount)
                .filter(a -> a != null && a.compareTo(BigDecimal.ZERO) > 0)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalDestination = normalized.getDestinations().stream()
                .map(Leg::getAmount)
                .filter(a -> a != null && a.compareTo(BigDecimal.ZERO) > 0)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        if (totalSource.compareTo(totalDestination) != 0) {
            errors.add(ErrorCode.E0008_UNBALANCED_TRANSACTION);
            messages.add(String.format("Transaction is unbalanced: sources=%s destinations=%s",
                    totalSource, totalDestination));
        }
    }

    private void checkRateExists(NormalizedTransaction normalized, List<ErrorCode> errors, List<String> messages) {
        if (normalized.getSources().isEmpty() || normalized.getDestinations().isEmpty()) return;

        String srcCurrency = normalized.getSources().get(0).getCurrency();
        String dstCurrency = normalized.getDestinations().get(0).getCurrency();
        if (srcCurrency == null || dstCurrency == null || srcCurrency.equals(dstCurrency)) return;

        if (normalized.getRates() == null || normalized.getRates().isEmpty()) {
            Optional<com.aurix.platform.shared.entity.AssetRate> rateOpt =
                    assetRateRepository.findValidRate(srcCurrency, dstCurrency, LocalDateTime.now());
            if (rateOpt.isEmpty()) {
                errors.add(ErrorCode.E0009_RATE_NOT_FOUND);
                messages.add(String.format("No valid exchange rate found for %s -> %s", srcCurrency, dstCurrency));
            }
        }
    }

    private void checkAccountsExist(NormalizedTransaction normalized, List<ErrorCode> errors, List<String> messages) {
        for (Leg leg : normalized.getSources()) {
            if (leg.getAccount() == null || leg.getAccount().getId() == null) {
                errors.add(ErrorCode.E0010_ACCOUNT_NOT_FOUND);
                messages.add("Source account reference is null or has no ID");
            }
        }
        for (Leg leg : normalized.getDestinations()) {
            if (leg.getAccount() == null || leg.getAccount().getId() == null) {
                errors.add(ErrorCode.E0010_ACCOUNT_NOT_FOUND);
                messages.add("Destination account reference is null or has no ID");
            }
        }
    }

    private void checkSharePercentages(NormalizedTransaction normalized, List<ErrorCode> errors, List<String> messages) {
        checkShareSum("source", normalized.getSources(), errors, messages);
        checkShareSum("destination", normalized.getDestinations(), errors, messages);
    }

    private void checkShareSum(String label, List<Leg> legs, List<ErrorCode> errors, List<String> messages) {
        boolean hasShares = legs.stream()
                .anyMatch(l -> l.getSharePercentage() != null && l.getSharePercentage().compareTo(BigDecimal.ZERO) > 0);
        if (!hasShares) return;

        BigDecimal sum = legs.stream()
                .filter(l -> l.getSharePercentage() != null)
                .map(Leg::getSharePercentage)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal expected = BigDecimal.valueOf(100);
        BigDecimal diff = sum.subtract(expected).abs();
        if (diff.compareTo(SHARE_TOLERANCE) > 0) {
            errors.add(ErrorCode.E0011_INVALID_SHARE_PERCENTAGE);
            messages.add(String.format("Invalid share percentage sum for %s legs: %s (expected ~100)", label, sum));
        }
    }

    private void checkDailyLimit(NormalizedTransaction normalized, List<ErrorCode> errors, List<String> messages) {
        LocalDateTime startOfDay = LocalDateTime.of(LocalDate.now(), LocalTime.MIDNIGHT);

        Map<Long, BigDecimal> sourceSumByAccount = new HashMap<>();
        for (Leg source : normalized.getSources()) {
            if (source.getAmount() != null && source.getAmount().compareTo(BigDecimal.ZERO) > 0
                    && source.getAccount() != null && source.getAccount().getId() != null) {
                sourceSumByAccount.merge(source.getAccount().getId(), source.getAmount(), BigDecimal::add);
            }
        }

        for (Map.Entry<Long, BigDecimal> entry : sourceSumByAccount.entrySet()) {
            BigDecimal todayTotal = transacaoRepository.sumDailyDebitsByContaOrigem(entry.getKey(), startOfDay);
            if (todayTotal == null) todayTotal = BigDecimal.ZERO;
            BigDecimal projected = todayTotal.add(entry.getValue());
            if (projected.compareTo(defaultDailyLimit) > 0) {
                errors.add(ErrorCode.E0012_DAILY_LIMIT_EXCEEDED);
                messages.add(String.format("Daily limit exceeded for account %d: today=%s transaction=%s limit=%s",
                        entry.getKey(), todayTotal, entry.getValue(), defaultDailyLimit));
            }
        }
    }

    private void checkAmountMatch(NormalizedTransaction normalized, List<ErrorCode> errors, List<String> messages) {
        for (Leg leg : normalized.getSources()) {
            checkLegAmountVsShare(leg, normalized.getSources(), "source", errors, messages);
        }
        for (Leg leg : normalized.getDestinations()) {
            checkLegAmountVsShare(leg, normalized.getDestinations(), "destination", errors, messages);
        }
    }

    private void checkLegAmountVsShare(Leg leg, List<Leg> allLegs, String label,
                                        List<ErrorCode> errors, List<String> messages) {
        if (leg.getSharePercentage() == null || leg.getSharePercentage().compareTo(BigDecimal.ZERO) <= 0) return;
        if (leg.getAmount() == null || leg.getAmount().compareTo(BigDecimal.ZERO) <= 0) return;

        BigDecimal total = allLegs.stream()
                .filter(l -> l.getAmount() != null && l.getAmount().compareTo(BigDecimal.ZERO) > 0)
                .map(Leg::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        if (total.compareTo(BigDecimal.ZERO) <= 0) return;

        BigDecimal expected = total.multiply(leg.getSharePercentage())
                .divide(BigDecimal.valueOf(100), SCALE, RoundingMode.HALF_UP);
        BigDecimal diff = leg.getAmount().subtract(expected).abs();
        if (diff.compareTo(BigDecimal.valueOf(0.01)) > 0) {
            errors.add(ErrorCode.E0013_AMOUNT_MISMATCH);
            messages.add(String.format("Amount mismatch for %s leg %d: amount=%s expected=%s (share=%s%%)",
                    label, leg.getOrder(), leg.getAmount(), expected, leg.getSharePercentage()));
        }
    }

    private void checkSpelRules(NormalizedTransaction normalized, List<ErrorCode> errors,
                                 List<String> messages, boolean warnOnly, boolean stopOnFirstError) {
        List<ValidationRule> rules;
        try {
            rules = validationRuleRepository.findByActiveTrueAndScopeOrderByPriorityAsc(
                    ValidationRule.RuleScope.TRANSACAO);
        } catch (Exception e) {
            log.warn("Could not load SpEL rules, skipping dynamic validation: {}", e.getMessage());
            return;
        }
        if (rules.isEmpty()) return;

        StandardEvaluationContext ctx = new StandardEvaluationContext();
        ctx.setVariable("transactionId", normalized.getTransactionId());
        ctx.setVariable("description", normalized.getDescription());
        ctx.setVariable("currency", normalized.getCurrency());
        ctx.setVariable("valorTotal", normalized.getSources().stream()
                .map(Leg::getAmount).filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add));
        ctx.setVariable("quantidadePernas", normalized.getSources().size() + normalized.getDestinations().size());
        ctx.setVariable("sources", normalized.getSources());
        ctx.setVariable("destinations", normalized.getDestinations());
        ctx.setVariable("rates", normalized.getRates());

        if (!normalized.getSources().isEmpty()) {
            ctx.setVariable("contaOrigem", normalized.getSources().get(0).getAccount().getNumeroConta());
        }
        if (!normalized.getDestinations().isEmpty()) {
            ctx.setVariable("contaDestino", normalized.getDestinations().get(0).getAccount().getNumeroConta());
        }

        for (ValidationRule rule : rules) {
            try {
                Boolean result = spelParser.parseExpression(rule.getSpelExpression())
                        .getValue(ctx, Boolean.class);
                if (result == null || !result) {
                    errors.add(ErrorCode.valueOf(rule.getErrorCode()));
                    messages.add(rule.getErrorMessage() != null
                            ? rule.getErrorMessage()
                            : "Dynamic rule failed: " + rule.getName());
                    if (stopOnFirstError) break;
                }
            } catch (Exception e) {
                log.warn("SpEL rule '{}' evaluation error: {}", rule.getName(), e.getMessage());
                errors.add(ErrorCode.valueOf(rule.getErrorCode()));
                messages.add("SpEL rule error (" + rule.getName() + "): " + e.getMessage());
                if (stopOnFirstError) break;
            }
        }
    }
}
