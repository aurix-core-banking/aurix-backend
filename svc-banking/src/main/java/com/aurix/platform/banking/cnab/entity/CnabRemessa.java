package com.aurix.platform.banking.cnab.entity;

import com.aurix.platform.shared.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "cnab_remessas", schema = "aurix")
public class CnabRemessa extends BaseEntity {

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", nullable = false, length = 20)
    private TipoCnab tipo;

    @NotBlank
    @Column(name = "arquivo_nome", nullable = false, length = 255)
    private String arquivoNome;

    @Column(name = "conteudo_arquivo", columnDefinition = "TEXT")
    private String conteudoArquivo;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private StatusRemessa status = StatusRemessa.GERADO;

    @Column(name = "total_registros")
    private Integer totalRegistros;

    @Column(name = "data_geracao")
    private LocalDateTime dataGeracao = LocalDateTime.now();

    @Column(name = "data_envio")
    private LocalDateTime dataEnvio;

    public enum TipoCnab {
        PAGAMENTO_TED, PAGAMENTO_BOLETO, PAGAMENTO_FOLHA, LIQUIDACAO
    }

    public enum StatusRemessa {
        GERADO, ENVIADO, PROCESSADO, RETORNADO, ERRO
    }

    public CnabRemessa() {}

    public CnabRemessa(TipoCnab tipo, String arquivoNome, String conteudoArquivo) {
        this.tipo = tipo;
        this.arquivoNome = arquivoNome;
        this.conteudoArquivo = conteudoArquivo;
    }

    public TipoCnab getTipo() { return tipo; }
    public void setTipo(TipoCnab tipo) { this.tipo = tipo; }
    public String getArquivoNome() { return arquivoNome; }
    public void setArquivoNome(String arquivoNome) { this.arquivoNome = arquivoNome; }
    public String getConteudoArquivo() { return conteudoArquivo; }
    public void setConteudoArquivo(String conteudoArquivo) { this.conteudoArquivo = conteudoArquivo; }
    public StatusRemessa getStatus() { return status; }
    public void setStatus(StatusRemessa status) { this.status = status; }
    public Integer getTotalRegistros() { return totalRegistros; }
    public void setTotalRegistros(Integer totalRegistros) { this.totalRegistros = totalRegistros; }
    public LocalDateTime getDataGeracao() { return dataGeracao; }
    public void setDataGeracao(LocalDateTime dataGeracao) { this.dataGeracao = dataGeracao; }
    public LocalDateTime getDataEnvio() { return dataEnvio; }
    public void setDataEnvio(LocalDateTime dataEnvio) { this.dataEnvio = dataEnvio; }
}
