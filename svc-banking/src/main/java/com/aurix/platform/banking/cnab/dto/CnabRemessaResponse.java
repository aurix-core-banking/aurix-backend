package com.aurix.platform.banking.cnab.dto;

import com.aurix.platform.banking.cnab.entity.CnabRemessa.StatusRemessa;
import com.aurix.platform.banking.cnab.entity.CnabRemessa.TipoCnab;
import java.time.LocalDateTime;

public class CnabRemessaResponse {
    private Long id;
    private TipoCnab tipo;
    private String arquivoNome;
    private StatusRemessa status;
    private Integer totalRegistros;
    private LocalDateTime dataGeracao;
    private LocalDateTime dataEnvio;

    public CnabRemessaResponse() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public TipoCnab getTipo() { return tipo; }
    public void setTipo(TipoCnab tipo) { this.tipo = tipo; }
    public String getArquivoNome() { return arquivoNome; }
    public void setArquivoNome(String arquivoNome) { this.arquivoNome = arquivoNome; }
    public StatusRemessa getStatus() { return status; }
    public void setStatus(StatusRemessa status) { this.status = status; }
    public Integer getTotalRegistros() { return totalRegistros; }
    public void setTotalRegistros(Integer totalRegistros) { this.totalRegistros = totalRegistros; }
    public LocalDateTime getDataGeracao() { return dataGeracao; }
    public void setDataGeracao(LocalDateTime dataGeracao) { this.dataGeracao = dataGeracao; }
    public LocalDateTime getDataEnvio() { return dataEnvio; }
    public void setDataEnvio(LocalDateTime dataEnvio) { this.dataEnvio = dataEnvio; }
}
