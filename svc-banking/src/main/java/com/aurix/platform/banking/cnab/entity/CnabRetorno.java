package com.aurix.platform.banking.cnab.entity;

import com.aurix.platform.shared.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Entity
@Table(name = "cnab_retornos", schema = "aurix")
public class CnabRetorno extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "remessa_id", nullable = false)
    private CnabRemessa remessa;

    @NotBlank
    @Column(name = "arquivo_nome", nullable = false, length = 255)
    private String arquivoNome;

    @Column(name = "conteudo_arquivo", columnDefinition = "TEXT")
    private String conteudoArquivo;

    @Column(name = "processado", nullable = false)
    private Boolean processado = false;

    @Column(name = "data_processamento")
    private LocalDateTime dataProcessamento;

    @Column(name = "total_registros")
    private Integer totalRegistros;

    @Column(name = "total_erros")
    private Integer totalErros;

    public CnabRetorno() {}

    public CnabRetorno(CnabRemessa remessa, String arquivoNome, String conteudoArquivo) {
        this.remessa = remessa;
        this.arquivoNome = arquivoNome;
        this.conteudoArquivo = conteudoArquivo;
    }

    public CnabRemessa getRemessa() { return remessa; }
    public void setRemessa(CnabRemessa remessa) { this.remessa = remessa; }
    public String getArquivoNome() { return arquivoNome; }
    public void setArquivoNome(String arquivoNome) { this.arquivoNome = arquivoNome; }
    public String getConteudoArquivo() { return conteudoArquivo; }
    public void setConteudoArquivo(String conteudoArquivo) { this.conteudoArquivo = conteudoArquivo; }
    public Boolean getProcessado() { return processado; }
    public void setProcessado(Boolean processado) { this.processado = processado; }
    public LocalDateTime getDataProcessamento() { return dataProcessamento; }
    public void setDataProcessamento(LocalDateTime dataProcessamento) { this.dataProcessamento = dataProcessamento; }
    public Integer getTotalRegistros() { return totalRegistros; }
    public void setTotalRegistros(Integer totalRegistros) { this.totalRegistros = totalRegistros; }
    public Integer getTotalErros() { return totalErros; }
    public void setTotalErros(Integer totalErros) { this.totalErros = totalErros; }
}
