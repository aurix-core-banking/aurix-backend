package com.aurix.platform.contracts.assinatura.dto.response;

import java.time.LocalDateTime;

public record AssinaturaDigitalResponse(
    Long id,
    Long contratoId,
    String clienteDocumento,
    String clienteNome,
    String status,
    boolean otpEnviado,
    boolean biometriaConfirmada,
    String biometriaTipo,
    String hashDocumentoSha256,
    String timestampDigital,
    boolean validaJuridicamente,
    LocalDateTime dataAssinatura,
    LocalDateTime dataCriacao
) {}
