package com.aurix.platform.banking.payment.discriminator;

import com.aurix.platform.banking.core.entity.ProcessingCode;
import com.aurix.platform.shared.dto.TransacaoDTO;
import com.aurix.platform.shared.entity.Transacao;
import org.springframework.stereotype.Component;

@Component
public class TedDiscriminator implements PaymentDiscriminator {

    @Override
    public boolean supports(ProcessingCode code, TransacaoDTO dto) {
        return "TED".equalsIgnoreCase(code.getPaymentType())
            || dto.getTipoTransacao() == Transacao.TipoTransacao.TED;
    }

    @Override
    public PaymentResult process(ProcessingCode code, TransacaoDTO dto) {
        if (dto.getValor() == null || dto.getValor().compareTo(java.math.BigDecimal.ZERO) <= 0) {
            return PaymentResult.failed("Invalid TED amount");
        }
        if (dto.getContaOrigemId() == null || dto.getContaDestinoId() == null) {
            return PaymentResult.failed("TED requires source and destination accounts");
        }
        String txCode = "TED-" + dto.getContaOrigemId() + "-" + System.currentTimeMillis();
        return PaymentResult.ok(txCode);
    }
}
