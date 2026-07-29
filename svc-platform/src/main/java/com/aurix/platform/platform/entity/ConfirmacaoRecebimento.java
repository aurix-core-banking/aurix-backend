package com.aurix.platform.platform.entity;

import com.aurix.platform.shared.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "confirmacoes_recebimento", schema = "aurix")
public class ConfirmacaoRecebimento extends BaseEntity {
    @Column(name = "fila_notificacao_id", nullable = false)
    private Long filaNotificacaoId;

    @Column(name = "cliente_id", nullable = false)
    private Long clienteId;

    @Column(name = "canal", nullable = false, length = 30)
    private String canal;

    @Column(name = "recebida_em", nullable = false)
    private LocalDateTime recebidaEm;

    @Column(name = "lida_em")
    private LocalDateTime lidaEm;

    @Column(name = "clicou_link")
    private Boolean clicouLink = false;

    public Long getFilaNotificacaoId() { return filaNotificacaoId; }
    public void setFilaNotificacaoId(Long filaNotificacaoId) { this.filaNotificacaoId = filaNotificacaoId; }
    public Long getClienteId() { return clienteId; }
    public void setClienteId(Long clienteId) { this.clienteId = clienteId; }
    public String getCanal() { return canal; }
    public void setCanal(String canal) { this.canal = canal; }
    public LocalDateTime getRecebidaEm() { return recebidaEm; }
    public void setRecebidaEm(LocalDateTime recebidaEm) { this.recebidaEm = recebidaEm; }
    public LocalDateTime getLidaEm() { return lidaEm; }
    public void setLidaEm(LocalDateTime lidaEm) { this.lidaEm = lidaEm; }
    public Boolean getClicouLink() { return clicouLink; }
    public void setClicouLink(Boolean clicouLink) { this.clicouLink = clicouLink; }
}
