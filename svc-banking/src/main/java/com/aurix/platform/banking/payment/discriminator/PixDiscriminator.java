package com.aurix.platform.banking.payment.discriminator;

import com.aurix.platform.banking.core.entity.ProcessingCode;
import com.aurix.platform.shared.dto.TransacaoDTO;
import com.aurix.platform.shared.entity.Transacao;
import org.springframework.stereotype.Component;

@Component
public class PixDiscriminator implements PaymentDiscriminator {

    @Override
    public boolean supports(ProcessingCode code, TransacaoDTO dto) {
        return "PIX".equalsIgnoreCase(code.getPaymentType())
            || dto.getTipoTransacao() == Transacao.TipoTransacao.PIX;
    }

    @Override
    public PaymentResult process(ProcessingCode code, TransacaoDTO dto) {
        if (dto.getValor() == null || dto.getValor().compareTo(java.math.BigDecimal.ZERO) <= 0) {
            return PaymentResult.failed("Invalid PIX amount");
        }
        if (dto.getContaOrigemId() == null) {
            return PaymentResult.failed("PIX requires source account");
        }
        String txCode = "PIX-" + dto.getContaOrigemId() + "-" + System.currentTimeMillis();
        return PaymentResult.ok(txCode);
    }
}
