package com.aurix.platform.shared.event;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class CobrancaNegativadaEvent extends BaseEvent {
    private Long cobrancaId;
    private String orgao;
    private LocalDate dataEnvio;

    public static CobrancaNegativadaEvent negativada(Long cobrancaId, String orgao, LocalDate dataEnvio) {
        CobrancaNegativadaEvent e = new CobrancaNegativadaEvent();
        e.setEventId(java.util.UUID.randomUUID().toString());
        e.setEventType("COBRANCA_NEGATIVADA");
        e.setSource("aurix-collections");
        e.setTimestamp(LocalDateTime.now());
        e.setCorrelationId(java.util.UUID.randomUUID().toString());
        e.cobrancaId = cobrancaId;
        e.orgao = orgao;
        e.dataEnvio = dataEnvio;
        return e;
    }

    public CobrancaNegativadaEvent() {}

    public Long getCobrancaId() { return cobrancaId; }
    public void setCobrancaId(Long v) { this.cobrancaId = v; }
    public String getOrgao() { return orgao; }
    public void setOrgao(String v) { this.orgao = v; }
    public LocalDate getDataEnvio() { return dataEnvio; }
    public void setDataEnvio(LocalDate v) { this.dataEnvio = v; }
}
