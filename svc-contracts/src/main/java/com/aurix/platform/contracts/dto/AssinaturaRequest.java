package com.aurix.platform.contracts.dto;

public record AssinaturaRequest(
    String hashDocumento,
    String ip,
    String userAgent
) {
}
