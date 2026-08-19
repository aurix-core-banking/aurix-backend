package com.aurix.platform.credit.seguro.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public class AbrirSinistroRequest {

    @NotNull(message = "Tipo do sinistro é obrigatório")
    private String tipoSinistro;

    @NotBlank(message = "Descrição do sinistro é obrigatória")
    private String descricao;

    @NotNull(message = "Data da ocorrência é obrigatória")
    private LocalDate dataOcorrencia;

    private String documentos;

    public AbrirSinistroRequest() {}

    public AbrirSinistroRequest(String tipoSinistro, String descricao, LocalDate dataOcorrencia, String documentos) {
        this.tipoSinistro = tipoSinistro;
        this.descricao = descricao;
        this.dataOcorrencia = dataOcorrencia;
        this.documentos = documentos;
    }

    public String getTipoSinistro() { return tipoSinistro; }
    public void setTipoSinistro(String tipoSinistro) { this.tipoSinistro = tipoSinistro; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public LocalDate getDataOcorrencia() { return dataOcorrencia; }
    public void setDataOcorrencia(LocalDate dataOcorrencia) { this.dataOcorrencia = dataOcorrencia; }
    public String getDocumentos() { return documentos; }
    public void setDocumentos(String documentos) { this.documentos = documentos; }
}
