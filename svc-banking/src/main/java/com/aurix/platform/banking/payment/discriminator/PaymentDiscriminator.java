package com.aurix.platform.banking.payment.discriminator;

import com.aurix.platform.banking.core.entity.ProcessingCode;
import com.aurix.platform.shared.dto.TransacaoDTO;

public interface PaymentDiscriminator {

    boolean supports(ProcessingCode code, TransacaoDTO dto);

    PaymentResult process(ProcessingCode code, TransacaoDTO dto);

    class PaymentResult {
        private final boolean success;
        private final String message;
        private final String transactionCode;

        public PaymentResult(boolean success, String message, String transactionCode) {
            this.success = success;
            this.message = message;
            this.transactionCode = transactionCode;
        }

        public static PaymentResult ok(String transactionCode) {
            return new PaymentResult(true, "Processed successfully", transactionCode);
        }

        public static PaymentResult failed(String message) {
            return new PaymentResult(false, message, null);
        }

        @java.lang.SuppressWarnings("all")
        public boolean isSuccess() { return this.success; }

        @java.lang.SuppressWarnings("all")
        public String getMessage() { return this.message; }

        @java.lang.SuppressWarnings("all")
        public String getTransactionCode() { return this.transactionCode; }
    }
}
