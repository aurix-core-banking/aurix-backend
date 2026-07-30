package com.aurix.platform.shared.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

/**
 * Entidade AuditoriaCompliance do Aurix.
 * Representa uma auditoria de conformidade.
 */
@Entity
@Table(name = "auditorias_compliance", schema = "aurix")
public class AuditoriaCompliance extends BaseEntity {
    /**
     * Comprimento padrão para campos longos.
     */
    private static final int LENGTH_LONG = 1000;
    /**
     * Título da auditoria.
     */
    @NotBlank(message = "Título da auditoria é obrigatório")
    @Column(nullable = false)
    private String titulo;
    /**
     * Descrição detalhada da auditoria.
     */
    @Column(length = LENGTH_LONG)
    private String descricao;
    /**
     * Regulamentação associada à auditoria.
     */
    @NotNull(message = "Regulamentação é obrigatória")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "regulacao_id", nullable = false)
    private Regulacao regulacao;
    /**
     * Nome do auditor responsável.
     */
    @Column(name = "auditor_responsavel")
    private String auditorResponsavel;
    /**
     * Data e hora de início da auditoria.
     */
    @Column(name = "data_inicio", nullable = false)
    private LocalDateTime dataInicio;
    /**
     * Data e hora previstas para o fim da auditoria.
     */
    @Column(name = "data_fim")
    private LocalDateTime dataFim;
    /**
     * Status atual da auditoria.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private StatusAuditoria status = StatusAuditoria.PLANEJADA;
    /**
     * Tipo técnico da auditoria.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_auditoria", nullable = false)
    private TipoAuditoria tipoAuditoria;
    /**
     * Escopo definido para a auditoria.
     */
    @Column(name = "escopo", length = LENGTH_LONG)
    private String escopo;
    /**
     * Metodologia aplicada na auditoria.
     */
    @Column(name = "metodologia", length = LENGTH_LONG)
    private String metodologia;
    /**
     * Critérios de avaliação utilizados (JSON).
     */
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "criterios", columnDefinition = "jsonb")
    private String criterios;
    /**
     * Inventário de evidências coletadas (JSON).
     */
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "evidencias_coletadas", columnDefinition = "jsonb")
    private String evidenciasColetadas;
    /**
     * Lista de não conformidades encontradas (JSON).
     */
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "nao_conformidades_identificadas", columnDefinition = "jsonb")
    private String naoConformidadesIdentificadas;
    /**
     * Recomendações do auditor (JSON).
     */
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "recomendacoes", columnDefinition = "jsonb")
    private String recomendacoes;
    /**
     * Plano de correção proposto (JSON).
     */
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "plano_correcao", columnDefinition = "jsonb")
    private String planoCorrecao;
    /**
     * Conteúdo final do relatório de auditoria.
     */
    @Column(name = "relatorio_final", columnDefinition = "text")
    private String relatorioFinal;

    /**
     * Verifica se a auditoria está em andamento.
     *
     * @return true se em andamento, false caso contrário.
     */
    public boolean isEmAndamento() {
        return status == StatusAuditoria.EM_ANDAMENTO;
    }

    /**
     * Verifica se a auditoria foi concluída.
     *
     * @return true se concluída, false caso contrário.
     */
    public boolean isConcluida() {
        return status == StatusAuditoria.CONCLUIDA;
    }

    /**
     * Verifica se a auditoria está atrasada.
     *
     * @return true se atrasada, false caso contrário.
     */
    public boolean isAtrasada() {
        return dataFim != null && LocalDateTime.now().isAfter(dataFim) && !isConcluida();
    }


    /**
     * Enum para status da auditoria.
     */
    public enum StatusAuditoria {
        /**
         * Planejada.
         */
        PLANEJADA("Planejada"), /**
         * Em andamento.
         */
        EM_ANDAMENTO("Em Andamento"), /**
         * Concluída.
         */
        CONCLUIDA("Concluída"), /**
         * Cancelada.
         */
        CANCELADA("Cancelada"), /**
         * Suspensa.
         */
        SUSPENSA("Suspensa");
        /**
         * Descrição do status.
         */
        private final String descricao;

        StatusAuditoria(final String desc) {
            this.descricao = desc;
        }

        /**
         * Retorna a descrição do status.
         * 
         * @return a descrição.
         */
        public String getDescricao() {
            return descricao;
        }
    }


    /**
     * Enum para tipo de auditoria.
     */
    public enum TipoAuditoria {
        /**
         * Auditoria Interna.
         */
        INTERNA("Auditoria Interna"), /**
         * Auditoria Externa.
         */
        EXTERNA("Auditoria Externa"), /**
         * Auditoria Regulatória.
         */
        REGULATORIA("Auditoria Regulatória"), /**
         * Auditoria de Certificação.
         */
        CERTIFICACAO("Auditoria de Certificação"), /**
         * Auditoria de Seguimento.
         */
        SEGUIMENTO("Auditoria de Seguimento");
        /**
         * Descrição do tipo.
         */
        private final String descricao;

        TipoAuditoria(final String desc) {
            this.descricao = desc;
        }

        /**
         * Retorna a descrição do tipo.
         * 
         * @return a descrição.
         */
        public String getDescricao() {
            return descricao;
        }
    }

    /**
     * Título da auditoria.
     */
    @java.lang.SuppressWarnings("all")
    public String getTitulo() {
        return this.titulo;
    }

    /**
     * Descrição detalhada da auditoria.
     */
    @java.lang.SuppressWarnings("all")
    public String getDescricao() {
        return this.descricao;
    }

    /**
     * Regulamentação associada à auditoria.
     */
    @java.lang.SuppressWarnings("all")
    public Regulacao getRegulacao() {
        return this.regulacao;
    }

    /**
     * Nome do auditor responsável.
     */
    @java.lang.SuppressWarnings("all")
    public String getAuditorResponsavel() {
        return this.auditorResponsavel;
    }

    /**
     * Data e hora de início da auditoria.
     */
    @java.lang.SuppressWarnings("all")
    public LocalDateTime getDataInicio() {
        return this.dataInicio;
    }

    /**
     * Data e hora previstas para o fim da auditoria.
     */
    @java.lang.SuppressWarnings("all")
    public LocalDateTime getDataFim() {
        return this.dataFim;
    }

    /**
     * Status atual da auditoria.
     */
    @java.lang.SuppressWarnings("all")
    public StatusAuditoria getStatus() {
        return this.status;
    }

    /**
     * Tipo técnico da auditoria.
     */
    @java.lang.SuppressWarnings("all")
    public TipoAuditoria getTipoAuditoria() {
        return this.tipoAuditoria;
    }

    /**
     * Escopo definido para a auditoria.
     */
    @java.lang.SuppressWarnings("all")
    public String getEscopo() {
        return this.escopo;
    }

    /**
     * Metodologia aplicada na auditoria.
     */
    @java.lang.SuppressWarnings("all")
    public String getMetodologia() {
        return this.metodologia;
    }

    /**
     * Critérios de avaliação utilizados (JSON).
     */
    @java.lang.SuppressWarnings("all")
    public String getCriterios() {
        return this.criterios;
    }

    /**
     * Inventário de evidências coletadas (JSON).
     */
    @java.lang.SuppressWarnings("all")
    public String getEvidenciasColetadas() {
        return this.evidenciasColetadas;
    }

    /**
     * Lista de não conformidades encontradas (JSON).
     */
    @java.lang.SuppressWarnings("all")
    public String getNaoConformidadesIdentificadas() {
        return this.naoConformidadesIdentificadas;
    }

    /**
     * Recomendações do auditor (JSON).
     */
    @java.lang.SuppressWarnings("all")
    public String getRecomendacoes() {
        return this.recomendacoes;
    }

    /**
     * Plano de correção proposto (JSON).
     */
    @java.lang.SuppressWarnings("all")
    public String getPlanoCorrecao() {
        return this.planoCorrecao;
    }

    /**
     * Conteúdo final do relatório de auditoria.
     */
    @java.lang.SuppressWarnings("all")
    public String getRelatorioFinal() {
        return this.relatorioFinal;
    }

    /**
     * Título da auditoria.
     */
    @java.lang.SuppressWarnings("all")
    public void setTitulo(final String titulo) {
        this.titulo = titulo;
    }

    /**
     * Descrição detalhada da auditoria.
     */
    @java.lang.SuppressWarnings("all")
    public void setDescricao(final String descricao) {
        this.descricao = descricao;
    }

    /**
     * Regulamentação associada à auditoria.
     */
    @java.lang.SuppressWarnings("all")
    public void setRegulacao(final Regulacao regulacao) {
        this.regulacao = regulacao;
    }

    /**
     * Nome do auditor responsável.
     */
    @java.lang.SuppressWarnings("all")
    public void setAuditorResponsavel(final String auditorResponsavel) {
        this.auditorResponsavel = auditorResponsavel;
    }

    /**
     * Data e hora de início da auditoria.
     */
    @java.lang.SuppressWarnings("all")
    public void setDataInicio(final LocalDateTime dataInicio) {
        this.dataInicio = dataInicio;
    }

    /**
     * Data e hora previstas para o fim da auditoria.
     */
    @java.lang.SuppressWarnings("all")
    public void setDataFim(final LocalDateTime dataFim) {
        this.dataFim = dataFim;
    }

    /**
     * Status atual da auditoria.
     */
    @java.lang.SuppressWarnings("all")
    public void setStatus(final StatusAuditoria status) {
        this.status = status;
    }

    /**
     * Tipo técnico da auditoria.
     */
    @java.lang.SuppressWarnings("all")
    public void setTipoAuditoria(final TipoAuditoria tipoAuditoria) {
        this.tipoAuditoria = tipoAuditoria;
    }

    /**
     * Escopo definido para a auditoria.
     */
    @java.lang.SuppressWarnings("all")
    public void setEscopo(final String escopo) {
        this.escopo = escopo;
    }

    /**
     * Metodologia aplicada na auditoria.
     */
    @java.lang.SuppressWarnings("all")
    public void setMetodologia(final String metodologia) {
        this.metodologia = metodologia;
    }

    /**
     * Critérios de avaliação utilizados (JSON).
     */
    @java.lang.SuppressWarnings("all")
    public void setCriterios(final String criterios) {
        this.criterios = criterios;
    }

    /**
     * Inventário de evidências coletadas (JSON).
     */
    @java.lang.SuppressWarnings("all")
    public void setEvidenciasColetadas(final String evidenciasColetadas) {
        this.evidenciasColetadas = evidenciasColetadas;
    }

    /**
     * Lista de não conformidades encontradas (JSON).
     */
    @java.lang.SuppressWarnings("all")
    public void setNaoConformidadesIdentificadas(final String naoConformidadesIdentificadas) {
        this.naoConformidadesIdentificadas = naoConformidadesIdentificadas;
    }

    /**
     * Recomendações do auditor (JSON).
     */
    @java.lang.SuppressWarnings("all")
    public void setRecomendacoes(final String recomendacoes) {
        this.recomendacoes = recomendacoes;
    }

    /**
     * Plano de correção proposto (JSON).
     */
    @java.lang.SuppressWarnings("all")
    public void setPlanoCorrecao(final String planoCorrecao) {
        this.planoCorrecao = planoCorrecao;
    }

    /**
     * Conteúdo final do relatório de auditoria.
     */
    @java.lang.SuppressWarnings("all")
    public void setRelatorioFinal(final String relatorioFinal) {
        this.relatorioFinal = relatorioFinal;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
        return "AuditoriaCompliance(titulo=" + this.getTitulo() + ", descricao=" + this.getDescricao() + ", regulacao=" + this.getRegulacao() + ", auditorResponsavel=" + this.getAuditorResponsavel() + ", dataInicio=" + this.getDataInicio() + ", dataFim=" + this.getDataFim() + ", status=" + this.getStatus() + ", tipoAuditoria=" + this.getTipoAuditoria() + ", escopo=" + this.getEscopo() + ", metodologia=" + this.getMetodologia() + ", criterios=" + this.getCriterios() + ", evidenciasColetadas=" + this.getEvidenciasColetadas() + ", naoConformidadesIdentificadas=" + this.getNaoConformidadesIdentificadas() + ", recomendacoes=" + this.getRecomendacoes() + ", planoCorrecao=" + this.getPlanoCorrecao() + ", relatorioFinal=" + this.getRelatorioFinal() + ")";
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public boolean equals(final java.lang.Object o) {
        if (o == this) return true;
        if (!(o instanceof AuditoriaCompliance)) return false;
        final AuditoriaCompliance other = (AuditoriaCompliance) o;
        if (!other.canEqual((java.lang.Object) this)) return false;
        if (!super.equals(o)) return false;
        final java.lang.Object this$titulo = this.getTitulo();
        final java.lang.Object other$titulo = other.getTitulo();
        if (this$titulo == null ? other$titulo != null : !this$titulo.equals(other$titulo)) return false;
        final java.lang.Object this$descricao = this.getDescricao();
        final java.lang.Object other$descricao = other.getDescricao();
        if (this$descricao == null ? other$descricao != null : !this$descricao.equals(other$descricao)) return false;
        final java.lang.Object this$regulacao = this.getRegulacao();
        final java.lang.Object other$regulacao = other.getRegulacao();
        if (this$regulacao == null ? other$regulacao != null : !this$regulacao.equals(other$regulacao)) return false;
        final java.lang.Object this$auditorResponsavel = this.getAuditorResponsavel();
        final java.lang.Object other$auditorResponsavel = other.getAuditorResponsavel();
        if (this$auditorResponsavel == null ? other$auditorResponsavel != null : !this$auditorResponsavel.equals(other$auditorResponsavel)) return false;
        final java.lang.Object this$dataInicio = this.getDataInicio();
        final java.lang.Object other$dataInicio = other.getDataInicio();
        if (this$dataInicio == null ? other$dataInicio != null : !this$dataInicio.equals(other$dataInicio)) return false;
        final java.lang.Object this$dataFim = this.getDataFim();
        final java.lang.Object other$dataFim = other.getDataFim();
        if (this$dataFim == null ? other$dataFim != null : !this$dataFim.equals(other$dataFim)) return false;
        final java.lang.Object this$status = this.getStatus();
        final java.lang.Object other$status = other.getStatus();
        if (this$status == null ? other$status != null : !this$status.equals(other$status)) return false;
        final java.lang.Object this$tipoAuditoria = this.getTipoAuditoria();
        final java.lang.Object other$tipoAuditoria = other.getTipoAuditoria();
        if (this$tipoAuditoria == null ? other$tipoAuditoria != null : !this$tipoAuditoria.equals(other$tipoAuditoria)) return false;
        final java.lang.Object this$escopo = this.getEscopo();
        final java.lang.Object other$escopo = other.getEscopo();
        if (this$escopo == null ? other$escopo != null : !this$escopo.equals(other$escopo)) return false;
        final java.lang.Object this$metodologia = this.getMetodologia();
        final java.lang.Object other$metodologia = other.getMetodologia();
        if (this$metodologia == null ? other$metodologia != null : !this$metodologia.equals(other$metodologia)) return false;
        final java.lang.Object this$criterios = this.getCriterios();
        final java.lang.Object other$criterios = other.getCriterios();
        if (this$criterios == null ? other$criterios != null : !this$criterios.equals(other$criterios)) return false;
        final java.lang.Object this$evidenciasColetadas = this.getEvidenciasColetadas();
        final java.lang.Object other$evidenciasColetadas = other.getEvidenciasColetadas();
        if (this$evidenciasColetadas == null ? other$evidenciasColetadas != null : !this$evidenciasColetadas.equals(other$evidenciasColetadas)) return false;
        final java.lang.Object this$naoConformidadesIdentificadas = this.getNaoConformidadesIdentificadas();
        final java.lang.Object other$naoConformidadesIdentificadas = other.getNaoConformidadesIdentificadas();
        if (this$naoConformidadesIdentificadas == null ? other$naoConformidadesIdentificadas != null : !this$naoConformidadesIdentificadas.equals(other$naoConformidadesIdentificadas)) return false;
        final java.lang.Object this$recomendacoes = this.getRecomendacoes();
        final java.lang.Object other$recomendacoes = other.getRecomendacoes();
        if (this$recomendacoes == null ? other$recomendacoes != null : !this$recomendacoes.equals(other$recomendacoes)) return false;
        final java.lang.Object this$planoCorrecao = this.getPlanoCorrecao();
        final java.lang.Object other$planoCorrecao = other.getPlanoCorrecao();
        if (this$planoCorrecao == null ? other$planoCorrecao != null : !this$planoCorrecao.equals(other$planoCorrecao)) return false;
        final java.lang.Object this$relatorioFinal = this.getRelatorioFinal();
        final java.lang.Object other$relatorioFinal = other.getRelatorioFinal();
        if (this$relatorioFinal == null ? other$relatorioFinal != null : !this$relatorioFinal.equals(other$relatorioFinal)) return false;
        return true;
    }

    @java.lang.SuppressWarnings("all")
    protected boolean canEqual(final java.lang.Object other) {
        return other instanceof AuditoriaCompliance;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public int hashCode() {
        final int PRIME = 59;
        int result = super.hashCode();
        final java.lang.Object $titulo = this.getTitulo();
        result = result * PRIME + ($titulo == null ? 43 : $titulo.hashCode());
        final java.lang.Object $descricao = this.getDescricao();
        result = result * PRIME + ($descricao == null ? 43 : $descricao.hashCode());
        final java.lang.Object $regulacao = this.getRegulacao();
        result = result * PRIME + ($regulacao == null ? 43 : $regulacao.hashCode());
        final java.lang.Object $auditorResponsavel = this.getAuditorResponsavel();
        result = result * PRIME + ($auditorResponsavel == null ? 43 : $auditorResponsavel.hashCode());
        final java.lang.Object $dataInicio = this.getDataInicio();
        result = result * PRIME + ($dataInicio == null ? 43 : $dataInicio.hashCode());
        final java.lang.Object $dataFim = this.getDataFim();
        result = result * PRIME + ($dataFim == null ? 43 : $dataFim.hashCode());
        final java.lang.Object $status = this.getStatus();
        result = result * PRIME + ($status == null ? 43 : $status.hashCode());
        final java.lang.Object $tipoAuditoria = this.getTipoAuditoria();
        result = result * PRIME + ($tipoAuditoria == null ? 43 : $tipoAuditoria.hashCode());
        final java.lang.Object $escopo = this.getEscopo();
        result = result * PRIME + ($escopo == null ? 43 : $escopo.hashCode());
        final java.lang.Object $metodologia = this.getMetodologia();
        result = result * PRIME + ($metodologia == null ? 43 : $metodologia.hashCode());
        final java.lang.Object $criterios = this.getCriterios();
        result = result * PRIME + ($criterios == null ? 43 : $criterios.hashCode());
        final java.lang.Object $evidenciasColetadas = this.getEvidenciasColetadas();
        result = result * PRIME + ($evidenciasColetadas == null ? 43 : $evidenciasColetadas.hashCode());
        final java.lang.Object $naoConformidadesIdentificadas = this.getNaoConformidadesIdentificadas();
        result = result * PRIME + ($naoConformidadesIdentificadas == null ? 43 : $naoConformidadesIdentificadas.hashCode());
        final java.lang.Object $recomendacoes = this.getRecomendacoes();
        result = result * PRIME + ($recomendacoes == null ? 43 : $recomendacoes.hashCode());
        final java.lang.Object $planoCorrecao = this.getPlanoCorrecao();
        result = result * PRIME + ($planoCorrecao == null ? 43 : $planoCorrecao.hashCode());
        final java.lang.Object $relatorioFinal = this.getRelatorioFinal();
        result = result * PRIME + ($relatorioFinal == null ? 43 : $relatorioFinal.hashCode());
        return result;
    }

    @java.lang.SuppressWarnings("all")
    public AuditoriaCompliance() {
    }

    /**
     * Creates a new {@code AuditoriaCompliance} instance.
     *
     * @param titulo Título da auditoria.
     * @param descricao Descrição detalhada da auditoria.
     * @param regulacao Regulamentação associada à auditoria.
     * @param auditorResponsavel Nome do auditor responsável.
     * @param dataInicio Data e hora de início da auditoria.
     * @param dataFim Data e hora previstas para o fim da auditoria.
     * @param status Status atual da auditoria.
     * @param tipoAuditoria Tipo técnico da auditoria.
     * @param escopo Escopo definido para a auditoria.
     * @param metodologia Metodologia aplicada na auditoria.
     * @param criterios Critérios de avaliação utilizados (JSON).
     * @param evidenciasColetadas Inventário de evidências coletadas (JSON).
     * @param naoConformidadesIdentificadas Lista de não conformidades encontradas (JSON).
     * @param recomendacoes Recomendações do auditor (JSON).
     * @param planoCorrecao Plano de correção proposto (JSON).
     * @param relatorioFinal Conteúdo final do relatório de auditoria.
     */
    @java.lang.SuppressWarnings("all")
    public AuditoriaCompliance(final String titulo, final String descricao, final Regulacao regulacao, final String auditorResponsavel, final LocalDateTime dataInicio, final LocalDateTime dataFim, final StatusAuditoria status, final TipoAuditoria tipoAuditoria, final String escopo, final String metodologia, final String criterios, final String evidenciasColetadas, final String naoConformidadesIdentificadas, final String recomendacoes, final String planoCorrecao, final String relatorioFinal) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.regulacao = regulacao;
        this.auditorResponsavel = auditorResponsavel;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.status = status;
        this.tipoAuditoria = tipoAuditoria;
        this.escopo = escopo;
        this.metodologia = metodologia;
        this.criterios = criterios;
        this.evidenciasColetadas = evidenciasColetadas;
        this.naoConformidadesIdentificadas = naoConformidadesIdentificadas;
        this.recomendacoes = recomendacoes;
        this.planoCorrecao = planoCorrecao;
        this.relatorioFinal = relatorioFinal;
    }
}
