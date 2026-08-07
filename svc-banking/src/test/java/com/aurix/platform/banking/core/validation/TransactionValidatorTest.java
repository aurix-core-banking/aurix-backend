package com.aurix.platform.banking.core.validation;

import com.aurix.platform.banking.core.entity.ControleSaldo;
import com.aurix.platform.banking.core.entity.ValidationRule;
import com.aurix.platform.shared.repository.AssetRateRepository;
import com.aurix.platform.banking.core.repository.ControleSaldoRepository;
import com.aurix.platform.shared.repository.TransacaoRepository;
import com.aurix.platform.banking.core.repository.ValidationRuleRepository;
import com.aurix.platform.shared.entity.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransactionValidatorTest {

    @Mock
    private ControleSaldoRepository controleSaldoRepository;

    @Mock
    private AssetRateRepository assetRateRepository;

    @Mock
    private TransacaoRepository transacaoRepository;

    @Mock
    private ValidationRuleRepository validationRuleRepository;

    private TransactionValidator validator;
    private Conta contaAtiva;
    private Conta contaDestino;

    @BeforeEach
    void setUp() {
        validator = new TransactionValidator(controleSaldoRepository, assetRateRepository,
                transacaoRepository, validationRuleRepository, new BigDecimal("50000"));

        when(validationRuleRepository.findByActiveTrueAndScopeOrderByPriorityAsc(any()))
                .thenReturn(List.of());

        contaAtiva = new Conta();
        contaAtiva.setId(100L);
        contaAtiva.setNumeroConta("00001-0");
        contaAtiva.setStatus(Conta.StatusConta.ATIVA);

        contaDestino = new Conta();
        contaDestino.setId(200L);
        contaDestino.setNumeroConta("00002-0");
        contaDestino.setStatus(Conta.StatusConta.ATIVA);
    }

    private Transacao createSimpleTransaction(Conta origem, Conta destino, BigDecimal valor) {
        Transacao t = new Transacao();
        t.setCodigoTransacao("TXN-TEST-001");
        t.setContaOrigem(origem);
        t.setContaDestino(destino);
        t.setValor(valor);
        t.setTipoTransacao(Transacao.TipoTransacao.PIX);
        t.setDescricao("test transaction");
        t.setStatus(Transacao.StatusTransacao.PENDENTE);
        return t;
    }

    @Test
    void shouldPassForValidTransaction() {
        Transacao t = createSimpleTransaction(contaAtiva, contaDestino, BigDecimal.valueOf(100));
        List<Conta> accounts = List.of(contaAtiva, contaDestino);

        when(controleSaldoRepository.findByContaId(100L))
                .thenReturn(Optional.of(createControleSaldo(BigDecimal.valueOf(1000))));
        when(transacaoRepository.sumDailyDebitsByContaOrigem(eq(100L), any(LocalDateTime.class)))
                .thenReturn(BigDecimal.ZERO);

        TransactionValidator.ValidationResult result = validator.validate(t, accounts);

        assertTrue(result.isValid(), "Expected valid, got: " + result.getMessages());
    }

    @Test
    void shouldFailWhenNoSources() {
        Transacao t = createSimpleTransaction(null, null, BigDecimal.valueOf(100));
        List<Conta> accounts = List.of();

        TransactionValidator.ValidationResult result = validator.validate(t, accounts);

        assertFalse(result.isValid());
        assertTrue(result.getErrors().contains(TransactionValidator.ErrorCode.E0006_NO_SOURCES));
    }

    @Test
    void shouldFailWhenNoDestinations() {
        Transacao t = createSimpleTransaction(contaAtiva, null, BigDecimal.valueOf(100));
        List<Conta> accounts = List.of(contaAtiva);

        when(controleSaldoRepository.findByContaId(100L))
                .thenReturn(Optional.of(createControleSaldo(BigDecimal.valueOf(1000))));
        when(transacaoRepository.sumDailyDebitsByContaOrigem(eq(100L), any(LocalDateTime.class)))
                .thenReturn(BigDecimal.ZERO);

        TransactionValidator.ValidationResult result = validator.validate(t, accounts);

        assertFalse(result.isValid());
        assertTrue(result.getErrors().contains(TransactionValidator.ErrorCode.E0007_NO_DESTINATIONS));
    }

    @Test
    void shouldFailForInvalidAmount() {
        Transacao t = createSimpleTransaction(contaAtiva, contaDestino, BigDecimal.ZERO);
        List<Conta> accounts = List.of(contaAtiva, contaDestino);

        TransactionValidator.ValidationResult result = validator.validate(t, accounts);

        assertFalse(result.isValid());
        assertTrue(result.getErrors().contains(TransactionValidator.ErrorCode.E0001_INVALID_AMOUNT));
    }

    @Test
    void shouldFailForAmountExceedingMax() {
        Transacao t = createSimpleTransaction(contaAtiva, contaDestino, new BigDecimal("1000001"));
        List<Conta> accounts = List.of(contaAtiva, contaDestino);

        TransactionValidator.ValidationResult result = validator.validate(t, accounts);

        assertFalse(result.isValid());
        assertTrue(result.getErrors().contains(TransactionValidator.ErrorCode.E0001_INVALID_AMOUNT));
    }

    @Test
    void shouldFailForInsufficientBalance() {
        Transacao t = createSimpleTransaction(contaAtiva, contaDestino, BigDecimal.valueOf(5000));
        List<Conta> accounts = List.of(contaAtiva, contaDestino);

        when(controleSaldoRepository.findByContaId(100L))
                .thenReturn(Optional.of(createControleSaldo(BigDecimal.valueOf(100))));
        when(transacaoRepository.sumDailyDebitsByContaOrigem(eq(100L), any(LocalDateTime.class)))
                .thenReturn(BigDecimal.ZERO);

        TransactionValidator.ValidationResult result = validator.validate(t, accounts);

        assertFalse(result.isValid());
        assertTrue(result.getErrors().contains(TransactionValidator.ErrorCode.E0002_INSUFFICIENT_BALANCE));
    }

    @Test
    void shouldFailForBlockedAccount() {
        contaAtiva.setStatus(Conta.StatusConta.BLOQUEADA);
        Transacao t = createSimpleTransaction(contaAtiva, contaDestino, BigDecimal.valueOf(100));
        List<Conta> accounts = List.of(contaAtiva, contaDestino);

        when(controleSaldoRepository.findByContaId(100L))
                .thenReturn(Optional.of(createControleSaldo(BigDecimal.valueOf(1000))));
        when(transacaoRepository.sumDailyDebitsByContaOrigem(eq(100L), any(LocalDateTime.class)))
                .thenReturn(BigDecimal.ZERO);

        TransactionValidator.ValidationResult result = validator.validate(t, accounts);

        assertFalse(result.isValid());
        assertTrue(result.getErrors().contains(TransactionValidator.ErrorCode.E0003_ACCOUNT_BLOCKED));
    }

    @Test
    void shouldFailForUnbalancedTransaction() {
        Conta src = new Conta();
        src.setId(1L);
        src.setNumeroConta("11111-1");
        src.setStatus(Conta.StatusConta.ATIVA);
        Conta dst = new Conta();
        dst.setId(2L);
        dst.setNumeroConta("22222-2");
        dst.setStatus(Conta.StatusConta.ATIVA);

        TransactionLeg sourceLeg = new TransactionLeg();
        sourceLeg.setLegType(TransactionLeg.LegType.SOURCE);
        sourceLeg.setAccount(src);
        sourceLeg.setAmount(BigDecimal.valueOf(200));
        sourceLeg.setCurrency("BRL");
        sourceLeg.setOrderIndex(0);

        TransactionLeg destLeg = new TransactionLeg();
        destLeg.setLegType(TransactionLeg.LegType.DESTINATION);
        destLeg.setAccount(dst);
        destLeg.setAmount(BigDecimal.valueOf(100));
        destLeg.setCurrency("BRL");
        destLeg.setOrderIndex(1);

        Transacao t = new Transacao();
        t.setCodigoTransacao("TXN-UNB-001");
        t.setValor(BigDecimal.valueOf(100));
        t.setLegs(List.of(sourceLeg, destLeg));

        when(controleSaldoRepository.findByContaId(1L))
                .thenReturn(Optional.of(createControleSaldo(BigDecimal.valueOf(1000))));
        when(transacaoRepository.sumDailyDebitsByContaOrigem(eq(1L), any(LocalDateTime.class)))
                .thenReturn(BigDecimal.ZERO);

        TransactionValidator.ValidationResult result = validator.validate(t, List.of(src, dst));

        assertFalse(result.isValid());
        assertTrue(result.getErrors().contains(TransactionValidator.ErrorCode.E0008_UNBALANCED_TRANSACTION));
    }

    @Test
    void shouldFailForSourceAndDestinationSameAccount() {
        Transacao t = createSimpleTransaction(contaAtiva, contaAtiva, BigDecimal.valueOf(100));
        List<Conta> accounts = List.of(contaAtiva);

        when(controleSaldoRepository.findByContaId(100L))
                .thenReturn(Optional.of(createControleSaldo(BigDecimal.valueOf(1000))));
        when(transacaoRepository.sumDailyDebitsByContaOrigem(eq(100L), any(LocalDateTime.class)))
                .thenReturn(BigDecimal.ZERO);

        TransactionValidator.ValidationResult result = validator.validate(t, accounts);

        assertFalse(result.isValid());
        assertTrue(result.getErrors().contains(TransactionValidator.ErrorCode.E0005_SOURCE_DESTINATION_SAME));
    }

    @Test
    void shouldFailForDailyLimitExceeded() {
        Transacao t = createSimpleTransaction(contaAtiva, contaDestino, BigDecimal.valueOf(30000));
        List<Conta> accounts = List.of(contaAtiva, contaDestino);

        when(controleSaldoRepository.findByContaId(100L))
                .thenReturn(Optional.of(createControleSaldo(BigDecimal.valueOf(100000))));
        when(transacaoRepository.sumDailyDebitsByContaOrigem(eq(100L), any(LocalDateTime.class)))
                .thenReturn(BigDecimal.valueOf(30000));

        TransactionValidator.ValidationResult result = validator.validate(t, accounts);

        assertFalse(result.isValid());
        assertTrue(result.getErrors().contains(TransactionValidator.ErrorCode.E0012_DAILY_LIMIT_EXCEEDED));
    }

    @Test
    void shouldFailWithMultipleErrors() {
        contaAtiva.setStatus(Conta.StatusConta.BLOQUEADA);
        Transacao t = createSimpleTransaction(contaAtiva, contaDestino, new BigDecimal("-50"));
        List<Conta> accounts = List.of(contaAtiva, contaDestino);

        TransactionValidator.ValidationResult result = validator.validate(t, accounts);

        assertFalse(result.isValid());
        assertTrue(result.getErrors().contains(TransactionValidator.ErrorCode.E0001_INVALID_AMOUNT));
        assertTrue(result.getErrors().contains(TransactionValidator.ErrorCode.E0003_ACCOUNT_BLOCKED));
    }

    @Test
    void shouldHandleNtoNTransactionWithLegs() {
        Conta src1 = new Conta();
        src1.setId(1L);
        src1.setNumeroConta("11111-1");
        src1.setStatus(Conta.StatusConta.ATIVA);

        Conta src2 = new Conta();
        src2.setId(2L);
        src2.setNumeroConta("22222-2");
        src2.setStatus(Conta.StatusConta.ATIVA);

        Conta dst = new Conta();
        dst.setId(3L);
        dst.setNumeroConta("33333-3");
        dst.setStatus(Conta.StatusConta.ATIVA);

        TransactionLeg leg1 = new TransactionLeg();
        leg1.setLegType(TransactionLeg.LegType.SOURCE);
        leg1.setAccount(src1);
        leg1.setAmount(BigDecimal.valueOf(50));
        leg1.setCurrency("BRL");
        leg1.setOrderIndex(0);

        TransactionLeg leg2 = new TransactionLeg();
        leg2.setLegType(TransactionLeg.LegType.SOURCE);
        leg2.setAccount(src2);
        leg2.setAmount(BigDecimal.valueOf(50));
        leg2.setCurrency("BRL");
        leg2.setOrderIndex(1);

        TransactionLeg leg3 = new TransactionLeg();
        leg3.setLegType(TransactionLeg.LegType.DESTINATION);
        leg3.setAccount(dst);
        leg3.setAmount(BigDecimal.valueOf(100));
        leg3.setCurrency("BRL");
        leg3.setOrderIndex(2);

        Transacao t = new Transacao();
        t.setCodigoTransacao("TXN-NN-001");
        t.setValor(BigDecimal.valueOf(100));
        t.setLegs(List.of(leg1, leg2, leg3));

        when(controleSaldoRepository.findByContaId(1L))
                .thenReturn(Optional.of(createControleSaldo(BigDecimal.valueOf(100))));
        when(controleSaldoRepository.findByContaId(2L))
                .thenReturn(Optional.of(createControleSaldo(BigDecimal.valueOf(100))));
        when(transacaoRepository.sumDailyDebitsByContaOrigem(eq(1L), any(LocalDateTime.class)))
                .thenReturn(BigDecimal.ZERO);
        when(transacaoRepository.sumDailyDebitsByContaOrigem(eq(2L), any(LocalDateTime.class)))
                .thenReturn(BigDecimal.ZERO);

        TransactionValidator.ValidationResult result = validator.validate(t, List.of(src1, src2, dst));

        assertTrue(result.isValid(), "Expected valid n:n, got: " + result.getMessages());
    }

    @Test
    void shouldFailForCurrencyMismatchInLegs() {
        Conta src = new Conta();
        src.setId(1L);
        src.setNumeroConta("11111-1");
        src.setStatus(Conta.StatusConta.ATIVA);
        Conta dst = new Conta();
        dst.setId(2L);
        dst.setNumeroConta("22222-2");
        dst.setStatus(Conta.StatusConta.ATIVA);

        TransactionLeg leg1 = new TransactionLeg();
        leg1.setLegType(TransactionLeg.LegType.SOURCE);
        leg1.setAccount(src);
        leg1.setAmount(BigDecimal.valueOf(100));
        leg1.setCurrency("BRL");
        leg1.setOrderIndex(0);

        TransactionLeg leg2 = new TransactionLeg();
        leg2.setLegType(TransactionLeg.LegType.DESTINATION);
        leg2.setAccount(dst);
        leg2.setAmount(BigDecimal.valueOf(100));
        leg2.setCurrency("USD");
        leg2.setOrderIndex(1);

        Transacao t = new Transacao();
        t.setCodigoTransacao("TXN-CUR-001");
        t.setValor(BigDecimal.valueOf(100));
        t.setLegs(List.of(leg1, leg2));

        when(controleSaldoRepository.findByContaId(1L))
                .thenReturn(Optional.of(createControleSaldo(BigDecimal.valueOf(1000))));
        when(transacaoRepository.sumDailyDebitsByContaOrigem(eq(1L), any(LocalDateTime.class)))
                .thenReturn(BigDecimal.ZERO);

        TransactionValidator.ValidationResult result = validator.validate(t, List.of(src, dst));

        assertFalse(result.isValid());
        assertTrue(result.getErrors().contains(TransactionValidator.ErrorCode.E0004_CURRENCY_MISMATCH));
    }

    @Test
    void shouldRejectWhenSpelRuleFails() {
        ValidationRule rule = new ValidationRule();
        rule.setName("Valor maximo PIX");
        rule.setSpelExpression("#valorTotal <= 5000");
        rule.setErrorCode("E0001_INVALID_AMOUNT");
        rule.setErrorMessage("SpEL: valor excede limite");

        when(validationRuleRepository.findByActiveTrueAndScopeOrderByPriorityAsc(
                ValidationRule.RuleScope.TRANSACAO)).thenReturn(List.of(rule));

        Transacao t = createSimpleTransaction(contaAtiva, contaDestino, BigDecimal.valueOf(10000));
        List<Conta> accounts = List.of(contaAtiva, contaDestino);

        when(controleSaldoRepository.findByContaId(100L))
                .thenReturn(Optional.of(createControleSaldo(BigDecimal.valueOf(50000))));
        when(transacaoRepository.sumDailyDebitsByContaOrigem(eq(100L), any(LocalDateTime.class)))
                .thenReturn(BigDecimal.ZERO);

        TransactionValidator.ValidationResult result = validator.validate(t, accounts);

        assertFalse(result.isValid());
        assertTrue(result.getMessages().stream().anyMatch(m -> m.contains("SpEL: valor excede limite")),
                "Expected SpEL error message, got: " + result.getMessages());
    }

    @Test
    void shouldPassWhenSpelRuleSucceeds() {
        ValidationRule rule = new ValidationRule();
        rule.setName("Valor maximo PIX");
        rule.setSpelExpression("#valorTotal <= 100000");
        rule.setErrorCode("E0001_INVALID_AMOUNT");
        rule.setErrorMessage("SpEL: valor excede limite");

        when(validationRuleRepository.findByActiveTrueAndScopeOrderByPriorityAsc(
                ValidationRule.RuleScope.TRANSACAO)).thenReturn(List.of(rule));

        Transacao t = createSimpleTransaction(contaAtiva, contaDestino, BigDecimal.valueOf(100));
        List<Conta> accounts = List.of(contaAtiva, contaDestino);

        when(controleSaldoRepository.findByContaId(100L))
                .thenReturn(Optional.of(createControleSaldo(BigDecimal.valueOf(1000))));
        when(transacaoRepository.sumDailyDebitsByContaOrigem(eq(100L), any(LocalDateTime.class)))
                .thenReturn(BigDecimal.ZERO);

        TransactionValidator.ValidationResult result = validator.validate(t, accounts);

        assertTrue(result.isValid(), "Expected valid, got: " + result.getMessages());
    }

    private ControleSaldo createControleSaldo(BigDecimal saldoDisponivel) {
        ControleSaldo cs = new ControleSaldo();
        cs.setSaldoDisponivel(saldoDisponivel);
        cs.setSaldoBloqueado(BigDecimal.ZERO);
        cs.setSaldoPendente(BigDecimal.ZERO);
        cs.setSaldoTotal(saldoDisponivel);
        return cs;
    }
}
