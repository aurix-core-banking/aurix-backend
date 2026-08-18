package com.aurix.platform.compliance.aml.entity;

import com.aurix.platform.shared.entity.BaseEntity;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "aml_regras", schema = "aurix")
public class AmlRegra extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String codigoRegra;

    @Column(nullable = false, length = 255)
    private String nomeRegra;

    @Column(columnDefinition = "TEXT")
    private String descricaoRegra;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoRegraAml tipoRegra;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusRegraAml status = StatusRegraAml.ATIVA;

    @Column(nullable = false)
    private Integer pesoScore = 10;

    @Column(nullable = false)
    private Integer limiteAtivacao;

    @Column(columnDefinition = "TEXT")
    private String parametrosRegra;

    @Column(nullable = false)
    private Boolean executarAutomaticamente = true;

    @Column(nullable = false)
    private Boolean notificarAlerta = true;

    @Column
    private LocalDateTime dataVigenciaInicio;

    @Column
    private LocalDateTime dataVigenciaFim;

    public enum TipoRegraAml {
        VALOR_LIMITE, FREQUENCIA_DIARIA, PARAISO_FISCAL, PAD_DEPOSITO,
        STRUCTURING, PAIS_ALTO_RISCO, MOVIMENTACAO_INCOMPATIVEL,
        LISTAS_PEP, ANALISE_REDES, ALERTA_MANUAL, CONCENTRACAO_PIX,
        TRANSACAO_NOTURNA, CONTA_INATIVA_REATIVADA, TERCERO_NAO_CADASTRADO
    }

    public enum StatusRegraAml {
        ATIVA, INATIVA, SUSPENSA, EM_REVISAO
    }

    @SuppressWarnings("all")
    public AmlRegra() {
    }

    public String getCodigoRegra() {
        return this.codigoRegra;
    }

    public String getNomeRegra() {
        return this.nomeRegra;
    }

    public String getDescricaoRegra() {
        return this.descricaoRegra;
    }

    public TipoRegraAml getTipoRegra() {
        return this.tipoRegra;
    }

    public StatusRegraAml getStatus() {
        return this.status;
    }

    public Integer getPesoScore() {
        return this.pesoScore;
    }

    public Integer getLimiteAtivacao() {
        return this.limiteAtivacao;
    }

    public String getParametrosRegra() {
        return this.parametrosRegra;
    }

    public Boolean getExecutarAutomaticamente() {
        return this.executarAutomaticamente;
    }

    public Boolean getNotificarAlerta() {
        return this.notificarAlerta;
    }

    public LocalDateTime getDataVigenciaInicio() {
        return this.dataVigenciaInicio;
    }

    public LocalDateTime getDataVigenciaFim() {
        return this.dataVigenciaFim;
    }

    public void setCodigoRegra(final String codigoRegra) {
        this.codigoRegra = codigoRegra;
    }

    public void setNomeRegra(final String nomeRegra) {
        this.nomeRegra = nomeRegra;
    }

    public void setDescricaoRegra(final String descricaoRegra) {
        this.descricaoRegra = descricaoRegra;
    }

    public void setTipoRegra(final TipoRegraAml tipoRegra) {
        this.tipoRegra = tipoRegra;
    }

    public void setStatus(final StatusRegraAml status) {
        this.status = status;
    }

    public void setPesoScore(final Integer pesoScore) {
        this.pesoScore = pesoScore;
    }

    public void setLimiteAtivacao(final Integer limiteAtivacao) {
        this.limiteAtivacao = limiteAtivacao;
    }

    public void setParametrosRegra(final String parametrosRegra) {
        this.parametrosRegra = parametrosRegra;
    }

    public void setExecutarAutomaticamente(final Boolean executarAutomaticamente) {
        this.executarAutomaticamente = executarAutomaticamente;
    }

    public void setNotificarAlerta(final Boolean notificarAlerta) {
        this.notificarAlerta = notificarAlerta;
    }

    public void setDataVigenciaInicio(final LocalDateTime dataVigenciaInicio) {
        this.dataVigenciaInicio = dataVigenciaInicio;
    }

    public void setDataVigenciaFim(final LocalDateTime dataVigenciaFim) {
        this.dataVigenciaFim = dataVigenciaFim;
    }
}
