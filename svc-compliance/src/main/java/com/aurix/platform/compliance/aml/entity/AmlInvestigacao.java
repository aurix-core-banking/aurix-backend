package com.aurix.platform.compliance.aml.entity;

import com.aurix.platform.shared.entity.BaseEntity;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "aml_investigacoes", schema = "aurix")
public class AmlInvestigacao extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String codigoInvestigacao;

    @Column(name = "alerta_id", nullable = false)
    private Long alertaId;

    @Column(nullable = false)
    private String codigoAlerta;

    @Column(name = "cliente_id", nullable = false)
    private Long clienteId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusInvestigacao status = StatusInvestigacao.INICIADA;

    @Column(nullable = false)
    private LocalDateTime dataInicio;

    @Column
    private LocalDateTime dataPrazo;

    @Column
    private LocalDateTime dataConclusao;

    @Column(nullable = false, length = 100)
    private String investigadorResponsavel;

    @Column(length = 100)
    private String supervisorRevisor;

    @Column(columnDefinition = "TEXT")
    private String descricaoInvestigacao;

    @Column(columnDefinition = "TEXT")
    private String evidenciasColetadas;

    @Column(columnDefinition = "TEXT")
    private String parecerInvestigador;

    @Column(columnDefinition = "TEXT")
    private String parecerSupervisor;

    @Enumerated(EnumType.STRING)
    private DecisaoInvestigacao decisaoFinal;

    @Column(columnDefinition = "TEXT")
    private String justificativaDecisao;

    @Column
    private Boolean reportadoCoaf = false;

    @Column
    private LocalDateTime dataReporteCoaf;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "JSONB")
    private String timelineEventos;

    @Column(columnDefinition = "TEXT")
    private String observacoes;

    public enum StatusInvestigacao {
        INICIADA, EM_ANDAMENTO, AGUARDANDO_REVISAO, CONCLUIDA, CANCELADA
    }

    public enum DecisaoInvestigacao {
        CONFIRMADO_SUSPEITA, FALSA_SUSPEITA, ENCAMINHADO_COAF, ARQUIVADO
    }

    @SuppressWarnings("all")
    public AmlInvestigacao() {
    }

    public String getCodigoInvestigacao() {
        return this.codigoInvestigacao;
    }

    public Long getAlertaId() {
        return this.alertaId;
    }

    public String getCodigoAlerta() {
        return this.codigoAlerta;
    }

    public Long getClienteId() {
        return this.clienteId;
    }

    public StatusInvestigacao getStatus() {
        return this.status;
    }

    public LocalDateTime getDataInicio() {
        return this.dataInicio;
    }

    public LocalDateTime getDataPrazo() {
        return this.dataPrazo;
    }

    public LocalDateTime getDataConclusao() {
        return this.dataConclusao;
    }

    public String getInvestigadorResponsavel() {
        return this.investigadorResponsavel;
    }

    public String getSupervisorRevisor() {
        return this.supervisorRevisor;
    }

    public String getDescricaoInvestigacao() {
        return this.descricaoInvestigacao;
    }

    public String getEvidenciasColetadas() {
        return this.evidenciasColetadas;
    }

    public String getParecerInvestigador() {
        return this.parecerInvestigador;
    }

    public String getParecerSupervisor() {
        return this.parecerSupervisor;
    }

    public DecisaoInvestigacao getDecisaoFinal() {
        return this.decisaoFinal;
    }

    public String getJustificativaDecisao() {
        return this.justificativaDecisao;
    }

    public Boolean getReportadoCoaf() {
        return this.reportadoCoaf;
    }

    public LocalDateTime getDataReporteCoaf() {
        return this.dataReporteCoaf;
    }

    public String getTimelineEventos() {
        return this.timelineEventos;
    }

    public String getObservacoes() {
        return this.observacoes;
    }

    public void setCodigoInvestigacao(final String codigoInvestigacao) {
        this.codigoInvestigacao = codigoInvestigacao;
    }

    public void setAlertaId(final Long alertaId) {
        this.alertaId = alertaId;
    }

    public void setCodigoAlerta(final String codigoAlerta) {
        this.codigoAlerta = codigoAlerta;
    }

    public void setClienteId(final Long clienteId) {
        this.clienteId = clienteId;
    }

    public void setStatus(final StatusInvestigacao status) {
        this.status = status;
    }

    public void setDataInicio(final LocalDateTime dataInicio) {
        this.dataInicio = dataInicio;
    }

    public void setDataPrazo(final LocalDateTime dataPrazo) {
        this.dataPrazo = dataPrazo;
    }

    public void setDataConclusao(final LocalDateTime dataConclusao) {
        this.dataConclusao = dataConclusao;
    }

    public void setInvestigadorResponsavel(final String investigadorResponsavel) {
        this.investigadorResponsavel = investigadorResponsavel;
    }

    public void setSupervisorRevisor(final String supervisorRevisor) {
        this.supervisorRevisor = supervisorRevisor;
    }

    public void setDescricaoInvestigacao(final String descricaoInvestigacao) {
        this.descricaoInvestigacao = descricaoInvestigacao;
    }

    public void setEvidenciasColetadas(final String evidenciasColetadas) {
        this.evidenciasColetadas = evidenciasColetadas;
    }

    public void setParecerInvestigador(final String parecerInvestigador) {
        this.parecerInvestigador = parecerInvestigador;
    }

    public void setParecerSupervisor(final String parecerSupervisor) {
        this.parecerSupervisor = parecerSupervisor;
    }

    public void setDecisaoFinal(final DecisaoInvestigacao decisaoFinal) {
        this.decisaoFinal = decisaoFinal;
    }

    public void setJustificativaDecisao(final String justificativaDecisao) {
        this.justificativaDecisao = justificativaDecisao;
    }

    public void setReportadoCoaf(final Boolean reportadoCoaf) {
        this.reportadoCoaf = reportadoCoaf;
    }

    public void setDataReporteCoaf(final LocalDateTime dataReporteCoaf) {
        this.dataReporteCoaf = dataReporteCoaf;
    }

    public void setTimelineEventos(final String timelineEventos) {
        this.timelineEventos = timelineEventos;
    }

    public void setObservacoes(final String observacoes) {
        this.observacoes = observacoes;
    }
}
