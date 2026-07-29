package com.aurix.platform.customer.onboarding.dto;

import jakarta.validation.constraints.Pattern;

public record CNPJConsultaRequest(
    @Pattern(regexp = "\\d{14}", message = "CNPJ deve conter 14 dígitos") String cnpj
) {
}
