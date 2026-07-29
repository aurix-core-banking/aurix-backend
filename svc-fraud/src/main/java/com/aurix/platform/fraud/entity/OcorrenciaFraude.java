package com.aurix.platform.fraud.entity;

import com.aurix.platform.shared.entity.BaseEntity;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "ocorrencias_fraude", schema = "aurix")
public class OcorrenciaFraude extends BaseEntity {
    @Column(name = "cliente_id", nullable = false)
    private Long clienteId;

    @Column(name = "transacao_ref", length = 100)
    private String transacaoRef;

    @Column(nullable = false, length = 50)
    private String tipo;

    @Column(nullable = false, length = 30)
    private String status;

    @Column(length = 1000)
    private String descricao;

    @Column(name = "data_ocorrencia", nullable = false)
    private LocalDateTime dataOcorrencia;

    public Long getClienteId() { return clienteId; }
    public void setClienteId(Long clienteId) { this.clienteId = clienteId; }
    public String getTransacaoRef() { return transacaoRef; }
    public void setTransacaoRef(String transacaoRef) { this.transacaoRef = transacaoRef; }
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public LocalDateTime getDataOcorrencia() { return dataOcorrencia; }
    public void setDataOcorrencia(LocalDateTime dataOcorrencia) { this.dataOcorrencia = dataOcorrencia; }
}
