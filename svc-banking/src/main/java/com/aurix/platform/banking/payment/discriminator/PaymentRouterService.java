package com.aurix.platform.banking.payment.discriminator;

import com.aurix.platform.banking.core.entity.ProcessingCode;
import com.aurix.platform.banking.core.repository.ProcessingCodeRepository;
import com.aurix.platform.shared.dto.TransacaoDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentRouterService {

    @java.lang.SuppressWarnings("all")
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(PaymentRouterService.class);
    private final ProcessingCodeRepository processingCodeRepository;
    private final List<PaymentDiscriminator> discriminators;

    public PaymentRouterService(final ProcessingCodeRepository processingCodeRepository,
                                 final List<PaymentDiscriminator> discriminators) {
        this.processingCodeRepository = processingCodeRepository;
        this.discriminators = discriminators;
    }

    public PaymentDiscriminator.PaymentResult route(String processingCodeValue, TransacaoDTO dto) {
        ProcessingCode code = processingCodeRepository.findByCode(processingCodeValue)
            .orElseThrow(() -> new IllegalArgumentException("Processing code not found: " + processingCodeValue));

        if (!code.isActive()) {
            return PaymentDiscriminator.PaymentResult.failed("Processing code is inactive: " + processingCodeValue);
        }

        for (PaymentDiscriminator discriminator : discriminators) {
            if (discriminator.supports(code, dto)) {
                PaymentDiscriminator.PaymentResult result = discriminator.process(code, dto);
                log.info("Payment routed: code={}, type={}, success={}",
                    processingCodeValue, code.getPaymentType(), result.isSuccess());
                return result;
            }
        }

        return PaymentDiscriminator.PaymentResult.failed(
            "No discriminator found for code: " + processingCodeValue
            + " and type: " + dto.getTipoTransacao());
    }

    public PaymentDiscriminator.PaymentResult routeAuto(TransacaoDTO dto) {
        List<ProcessingCode> activeCodes = processingCodeRepository
            .findByPaymentTypeAndActiveTrueOrderByPriorityAsc(
                dto.getTipoTransacao() != null ? dto.getTipoTransacao().name() : "");

        for (ProcessingCode code : activeCodes) {
            for (PaymentDiscriminator discriminator : discriminators) {
                if (discriminator.supports(code, dto)) {
                    PaymentDiscriminator.PaymentResult result = discriminator.process(code, dto);
                    log.info("Payment auto-routed: code={}, success={}", code.getCode(), result.isSuccess());
                    return result;
                }
            }
        }

        return PaymentDiscriminator.PaymentResult.failed(
            "No active processing code found for: " + dto.getTipoTransacao());
    }
}
