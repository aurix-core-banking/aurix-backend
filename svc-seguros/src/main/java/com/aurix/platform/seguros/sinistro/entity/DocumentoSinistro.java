package com.aurix.platform.seguros.sinistro.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "sinistro_documentos")
public class DocumentoSinistro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long sinistroId;

    @Column(nullable = false, length = 100)
    private String tipoDocumento;

    @Column(nullable = false, length = 500)
    private String nomeArquivo;

    @Column(length = 1000)
    private String caminhoArquivo;

    @Column(nullable = false, length = 200)
    private String descricao;

    private LocalDateTime dataUpload;

    public DocumentoSinistro() {}

    public DocumentoSinistro(Long sinistroId, String tipoDocumento, String nomeArquivo,
                             String caminhoArquivo, String descricao) {
        this.sinistroId = sinistroId;
        this.tipoDocumento = tipoDocumento;
        this.nomeArquivo = nomeArquivo;
        this.caminhoArquivo = caminhoArquivo;
        this.descricao = descricao;
    }

    @PrePersist
    protected void onCreate() {
        dataUpload = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getSinistroId() { return sinistroId; }
    public void setSinistroId(Long sinistroId) { this.sinistroId = sinistroId; }
    public String getTipoDocumento() { return tipoDocumento; }
    public void setTipoDocumento(String tipoDocumento) { this.tipoDocumento = tipoDocumento; }
    public String getNomeArquivo() { return nomeArquivo; }
    public void setNomeArquivo(String nomeArquivo) { this.nomeArquivo = nomeArquivo; }
    public String getCaminhoArquivo() { return caminhoArquivo; }
    public void setCaminhoArquivo(String caminhoArquivo) { this.caminhoArquivo = caminhoArquivo; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public LocalDateTime getDataUpload() { return dataUpload; }
}
