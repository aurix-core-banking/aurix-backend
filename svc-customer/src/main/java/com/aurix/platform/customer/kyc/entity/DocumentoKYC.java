package com.aurix.platform.customer.kyc.entity;

import com.aurix.platform.shared.entity.BaseEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "documentos_kyc", schema = "aurix")
public class DocumentoKYC extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "solicitacao_id", nullable = false)
    private SolicitacaoKYC solicitacao;

    @Column(nullable = false, length = 30)
    private String tipo;

    @Column(name = "arquivo_ref", length = 500)
    private String arquivoRef;

    @Column(nullable = false, length = 20)
    private String status;

    @Column(length = 500)
    private String motivoRejeicao;

    public SolicitacaoKYC getSolicitacao() { return solicitacao; }
    public void setSolicitacao(SolicitacaoKYC solicitacao) { this.solicitacao = solicitacao; }
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public String getArquivoRef() { return arquivoRef; }
    public void setArquivoRef(String arquivoRef) { this.arquivoRef = arquivoRef; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getMotivoRejeicao() { return motivoRejeicao; }
    public void setMotivoRejeicao(String motivoRejeicao) { this.motivoRejeicao = motivoRejeicao; }
}
