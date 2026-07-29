package com.aurix.platform.customer.kyc.entity;

import com.aurix.platform.shared.entity.BaseEntity;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "solicitacoes_kyc", schema = "aurix")
public class SolicitacaoKYC extends BaseEntity {
    @Column(name = "cliente_id", nullable = false)
    private Long clienteId;

    @Column(nullable = false, length = 20)
    private String status;

    @Column(name = "score_risco")
    private Integer scoreRisco;

    @Column(name = "data_solicitacao", nullable = false)
    private LocalDateTime dataSolicitacao;

    @Column(name = "data_conclusao")
    private LocalDateTime dataConclusao;

    @Column(length = 500)
    private String observacao;

    public Long getClienteId() { return clienteId; }
    public void setClienteId(Long clienteId) { this.clienteId = clienteId; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public Integer getScoreRisco() { return scoreRisco; }
    public void setScoreRisco(Integer scoreRisco) { this.scoreRisco = scoreRisco; }
    public LocalDateTime getDataSolicitacao() { return dataSolicitacao; }
    public void setDataSolicitacao(LocalDateTime dataSolicitacao) { this.dataSolicitacao = dataSolicitacao; }
    public LocalDateTime getDataConclusao() { return dataConclusao; }
    public void setDataConclusao(LocalDateTime dataConclusao) { this.dataConclusao = dataConclusao; }
    public String getObservacao() { return observacao; }
    public void setObservacao(String observacao) { this.observacao = observacao; }
}
