package com.aurix.platform.shared.event;

import java.time.LocalDateTime;

public class ClienteCriadoEvent extends BaseEvent {
    private Long clienteId;
    private String documento;
    private String nome;
    private String tipoPessoa;
    private String segmento;

    public static ClienteCriadoEvent criado(Long clienteId, String documento, String nome, String tipoPessoa, String segmento) {
        ClienteCriadoEvent event = new ClienteCriadoEvent();
        event.setEventId(java.util.UUID.randomUUID().toString());
        event.setEventType("CLIENTE_CRIADO");
        event.setSource("aurix-customer");
        event.setTimestamp(LocalDateTime.now());
        event.setCorrelationId(java.util.UUID.randomUUID().toString());
        event.clienteId = clienteId;
        event.documento = documento;
        event.nome = nome;
        event.tipoPessoa = tipoPessoa;
        event.segmento = segmento;
        return event;
    }

    public ClienteCriadoEvent() {}

    public Long getClienteId() { return clienteId; }
    public void setClienteId(Long clienteId) { this.clienteId = clienteId; }
    public String getDocumento() { return documento; }
    public void setDocumento(String documento) { this.documento = documento; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getTipoPessoa() { return tipoPessoa; }
    public void setTipoPessoa(String tipoPessoa) { this.tipoPessoa = tipoPessoa; }
    public String getSegmento() { return segmento; }
    public void setSegmento(String segmento) { this.segmento = segmento; }
}
