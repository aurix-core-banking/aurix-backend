package com.aurix.platform.banking.payment.discriminator;

import com.aurix.platform.banking.core.entity.ProcessingCode;
import com.aurix.platform.shared.dto.TransacaoDTO;
import com.aurix.platform.shared.entity.Transacao;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class DocDiscriminator implements PaymentDiscriminator {

    private static final BigDecimal DOC_LIMIT = BigDecimal.valueOf(5000);

    @Override
    public boolean supports(ProcessingCode code, TransacaoDTO dto) {
        return "DOC".equalsIgnoreCase(code.getPaymentType())
            || dto.getTipoTransacao() == Transacao.TipoTransacao.DOC;
    }

    @Override
    public PaymentResult process(ProcessingCode code, TransacaoDTO dto) {
        if (dto.getValor() == null || dto.getValor().compareTo(BigDecimal.ZERO) <= 0) {
            return PaymentResult.failed("Invalid DOC amount");
        }
        if (dto.getValor().compareTo(DOC_LIMIT) > 0) {
            return PaymentResult.failed("DOC amount exceeds limit of " + DOC_LIMIT);
        }
        if (dto.getContaOrigemId() == null || dto.getContaDestinoId() == null) {
            return PaymentResult.failed("DOC requires source and destination accounts");
        }
        String txCode = "DOC-" + dto.getContaOrigemId() + "-" + System.currentTimeMillis();
        return PaymentResult.ok(txCode);
    }
}
