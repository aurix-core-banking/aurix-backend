package com.aurix.platform.shared.event;

public class LgpdDadosExcluidosEvent extends BaseEvent {

    private Long clienteId;

    public LgpdDadosExcluidosEvent() {
        super("lgpd.dados.excluidos", "svc-compliance");
    }

    public LgpdDadosExcluidosEvent(Long clienteId) {
        super("lgpd.dados.excluidos", "svc-compliance");
        this.clienteId = clienteId;
    }

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }
}