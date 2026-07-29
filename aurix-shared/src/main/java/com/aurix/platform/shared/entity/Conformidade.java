package com.aurix.platform.shared.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

/**
 * Entidade Conformidade do Aurix.
 * Representa o status de conformidade de uma entidade com uma regulamentação.
 */
@Entity
@Table(name = "conformidades", schema = "aurix")
public class Conformidade extends BaseEntity {
    /**
     * Comprimento padrão para campos longos.
     */
    private static final int LENGTH_LONG = 1000;
    /**
     * Regulamentação associada à conformidade.
     */
    @NotNull(message = "Regulamentação é obrigatória")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "regulacao_id", nullable = false)
    private Regulacao regulacao;
    /**
     * ID da entidade técnica sendo avaliada.
     */
    @Column(name = "entidade_id")
    private Long entidadeId;
    /**
     * Tipo da entidade avaliada (ex: CLIENTE, CONTA).
     */
    @Column(name = "tipo_entidade")
    private String tipoEntidade;
    /**
     * Status atual da conformidade.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private StatusConformidade status = StatusConformidade.EM_ANALISE;
    /**
     * Data e hora da última verificação realizada.
     */
    @Column(name = "data_verificacao", nullable = false)
    private LocalDateTime dataVerificacao = LocalDateTime.now();
    /**
     * Data e hora previstas para a próxima verificação.
     */
    @Column(name = "data_proxima_verificacao")
    private LocalDateTime dataProximaVerificacao;
    /**
     * Observações técnicas do analista de conformidade.
     */
    @Column(name = "observacoes", length = LENGTH_LONG)
    private String observacoes;
    /**
     * Evidências coletadas para suportar o status (JSON).
     */
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "evidencias", columnDefinition = "jsonb")
    private String evidencias;
    /**
     * Detalhamento das não conformidades (JSON).
     */
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "nao_conformidades", columnDefinition = "jsonb")
    private String naoConformidades;
    /**
     * Plano de ação para correção (JSON).
     */
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "plano_acao", columnDefinition = "jsonb")
    private String planoAcao;

    /**
     * Verifica se a conformidade está vencida.
     *
     * @return true se vencida, false caso contrário.
     */
    public boolean isVencida() {
        return dataProximaVerificacao != null && LocalDateTime.now().isAfter(dataProximaVerificacao);
    }

    /**
     * Verifica se a conformidade está em conformidade.
     *
     * @return true se conforme, false caso contrário.
     */
    public boolean isConforme() {
        return status == StatusConformidade.CONFORME;
    }

    /**
     * Verifica se há não conformidades.
     *
     * @return true se houver não conformidades, false caso contrário.
     */
    public boolean hasNaoConformidades() {
        return status == StatusConformidade.NAO_CONFORME || status == StatusConformidade.NAO_CONFORME_CRITICO;
    }


    /**
     * Enum para status de conformidade.
     */
    public enum StatusConformidade {
        /**
         * Em análise.
         */
        EM_ANALISE("Em Análise"), /**
         * Conforme.
         */
        CONFORME("Conforme"), /**
         * Não conforme.
         */
        NAO_CONFORME("Não Conforme"), /**
         * Não conforme crítico.
         */
        NAO_CONFORME_CRITICO("Não Conforme Crítico"), /**
         * Pendente correção.
         */
        PENDENTE_CORRECAO("Pendente Correção"), /**
         * Corrigido.
         */
        CORRIGIDO("Corrigido");
        /**
         * Descrição do status.
         */
        private final String descricao;

        StatusConformidade(final String desc) {
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
     * Regulamentação associada à conformidade.
     */
    @java.lang.SuppressWarnings("all")
    public Regulacao getRegulacao() {
        return this.regulacao;
    }

    /**
     * ID da entidade técnica sendo avaliada.
     */
    @java.lang.SuppressWarnings("all")
    public Long getEntidadeId() {
        return this.entidadeId;
    }

    /**
     * Tipo da entidade avaliada (ex: CLIENTE, CONTA).
     */
    @java.lang.SuppressWarnings("all")
    public String getTipoEntidade() {
        return this.tipoEntidade;
    }

    /**
     * Status atual da conformidade.
     */
    @java.lang.SuppressWarnings("all")
    public StatusConformidade getStatus() {
        return this.status;
    }

    /**
     * Data e hora da última verificação realizada.
     */
    @java.lang.SuppressWarnings("all")
    public LocalDateTime getDataVerificacao() {
        return this.dataVerificacao;
    }

    /**
     * Data e hora previstas para a próxima verificação.
     */
    @java.lang.SuppressWarnings("all")
    public LocalDateTime getDataProximaVerificacao() {
        return this.dataProximaVerificacao;
    }

    /**
     * Observações técnicas do analista de conformidade.
     */
    @java.lang.SuppressWarnings("all")
    public String getObservacoes() {
        return this.observacoes;
    }

    /**
     * Evidências coletadas para suportar o status (JSON).
     */
    @java.lang.SuppressWarnings("all")
    public String getEvidencias() {
        return this.evidencias;
    }

    /**
     * Detalhamento das não conformidades (JSON).
     */
    @java.lang.SuppressWarnings("all")
    public String getNaoConformidades() {
        return this.naoConformidades;
    }

    /**
     * Plano de ação para correção (JSON).
     */
    @java.lang.SuppressWarnings("all")
    public String getPlanoAcao() {
        return this.planoAcao;
    }

    /**
     * Regulamentação associada à conformidade.
     */
    @java.lang.SuppressWarnings("all")
    public void setRegulacao(final Regulacao regulacao) {
        this.regulacao = regulacao;
    }

    /**
     * ID da entidade técnica sendo avaliada.
     */
    @java.lang.SuppressWarnings("all")
    public void setEntidadeId(final Long entidadeId) {
        this.entidadeId = entidadeId;
    }

    /**
     * Tipo da entidade avaliada (ex: CLIENTE, CONTA).
     */
    @java.lang.SuppressWarnings("all")
    public void setTipoEntidade(final String tipoEntidade) {
        this.tipoEntidade = tipoEntidade;
    }

    /**
     * Status atual da conformidade.
     */
    @java.lang.SuppressWarnings("all")
    public void setStatus(final StatusConformidade status) {
        this.status = status;
    }

    /**
     * Data e hora da última verificação realizada.
     */
    @java.lang.SuppressWarnings("all")
    public void setDataVerificacao(final LocalDateTime dataVerificacao) {
        this.dataVerificacao = dataVerificacao;
    }

    /**
     * Data e hora previstas para a próxima verificação.
     */
    @java.lang.SuppressWarnings("all")
    public void setDataProximaVerificacao(final LocalDateTime dataProximaVerificacao) {
        this.dataProximaVerificacao = dataProximaVerificacao;
    }

    /**
     * Observações técnicas do analista de conformidade.
     */
    @java.lang.SuppressWarnings("all")
    public void setObservacoes(final String observacoes) {
        this.observacoes = observacoes;
    }

    /**
     * Evidências coletadas para suportar o status (JSON).
     */
    @java.lang.SuppressWarnings("all")
    public void setEvidencias(final String evidencias) {
        this.evidencias = evidencias;
    }

    /**
     * Detalhamento das não conformidades (JSON).
     */
    @java.lang.SuppressWarnings("all")
    public void setNaoConformidades(final String naoConformidades) {
        this.naoConformidades = naoConformidades;
    }

    /**
     * Plano de ação para correção (JSON).
     */
    @java.lang.SuppressWarnings("all")
    public void setPlanoAcao(final String planoAcao) {
        this.planoAcao = planoAcao;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
        return "Conformidade(regulacao=" + this.getRegulacao() + ", entidadeId=" + this.getEntidadeId() + ", tipoEntidade=" + this.getTipoEntidade() + ", status=" + this.getStatus() + ", dataVerificacao=" + this.getDataVerificacao() + ", dataProximaVerificacao=" + this.getDataProximaVerificacao() + ", observacoes=" + this.getObservacoes() + ", evidencias=" + this.getEvidencias() + ", naoConformidades=" + this.getNaoConformidades() + ", planoAcao=" + this.getPlanoAcao() + ")";
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public boolean equals(final java.lang.Object o) {
        if (o == this) return true;
        if (!(o instanceof Conformidade)) return false;
        final Conformidade other = (Conformidade) o;
        if (!other.canEqual((java.lang.Object) this)) return false;
        if (!super.equals(o)) return false;
        final java.lang.Object this$entidadeId = this.getEntidadeId();
        final java.lang.Object other$entidadeId = other.getEntidadeId();
        if (this$entidadeId == null ? other$entidadeId != null : !this$entidadeId.equals(other$entidadeId)) return false;
        final java.lang.Object this$regulacao = this.getRegulacao();
        final java.lang.Object other$regulacao = other.getRegulacao();
        if (this$regulacao == null ? other$regulacao != null : !this$regulacao.equals(other$regulacao)) return false;
        final java.lang.Object this$tipoEntidade = this.getTipoEntidade();
        final java.lang.Object other$tipoEntidade = other.getTipoEntidade();
        if (this$tipoEntidade == null ? other$tipoEntidade != null : !this$tipoEntidade.equals(other$tipoEntidade)) return false;
        final java.lang.Object this$status = this.getStatus();
        final java.lang.Object other$status = other.getStatus();
        if (this$status == null ? other$status != null : !this$status.equals(other$status)) return false;
        final java.lang.Object this$dataVerificacao = this.getDataVerificacao();
        final java.lang.Object other$dataVerificacao = other.getDataVerificacao();
        if (this$dataVerificacao == null ? other$dataVerificacao != null : !this$dataVerificacao.equals(other$dataVerificacao)) return false;
        final java.lang.Object this$dataProximaVerificacao = this.getDataProximaVerificacao();
        final java.lang.Object other$dataProximaVerificacao = other.getDataProximaVerificacao();
        if (this$dataProximaVerificacao == null ? other$dataProximaVerificacao != null : !this$dataProximaVerificacao.equals(other$dataProximaVerificacao)) return false;
        final java.lang.Object this$observacoes = this.getObservacoes();
        final java.lang.Object other$observacoes = other.getObservacoes();
        if (this$observacoes == null ? other$observacoes != null : !this$observacoes.equals(other$observacoes)) return false;
        final java.lang.Object this$evidencias = this.getEvidencias();
        final java.lang.Object other$evidencias = other.getEvidencias();
        if (this$evidencias == null ? other$evidencias != null : !this$evidencias.equals(other$evidencias)) return false;
        final java.lang.Object this$naoConformidades = this.getNaoConformidades();
        final java.lang.Object other$naoConformidades = other.getNaoConformidades();
        if (this$naoConformidades == null ? other$naoConformidades != null : !this$naoConformidades.equals(other$naoConformidades)) return false;
        final java.lang.Object this$planoAcao = this.getPlanoAcao();
        final java.lang.Object other$planoAcao = other.getPlanoAcao();
        if (this$planoAcao == null ? other$planoAcao != null : !this$planoAcao.equals(other$planoAcao)) return false;
        return true;
    }

    @java.lang.SuppressWarnings("all")
    protected boolean canEqual(final java.lang.Object other) {
        return other instanceof Conformidade;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public int hashCode() {
        final int PRIME = 59;
        int result = super.hashCode();
        final java.lang.Object $entidadeId = this.getEntidadeId();
        result = result * PRIME + ($entidadeId == null ? 43 : $entidadeId.hashCode());
        final java.lang.Object $regulacao = this.getRegulacao();
        result = result * PRIME + ($regulacao == null ? 43 : $regulacao.hashCode());
        final java.lang.Object $tipoEntidade = this.getTipoEntidade();
        result = result * PRIME + ($tipoEntidade == null ? 43 : $tipoEntidade.hashCode());
        final java.lang.Object $status = this.getStatus();
        result = result * PRIME + ($status == null ? 43 : $status.hashCode());
        final java.lang.Object $dataVerificacao = this.getDataVerificacao();
        result = result * PRIME + ($dataVerificacao == null ? 43 : $dataVerificacao.hashCode());
        final java.lang.Object $dataProximaVerificacao = this.getDataProximaVerificacao();
        result = result * PRIME + ($dataProximaVerificacao == null ? 43 : $dataProximaVerificacao.hashCode());
        final java.lang.Object $observacoes = this.getObservacoes();
        result = result * PRIME + ($observacoes == null ? 43 : $observacoes.hashCode());
        final java.lang.Object $evidencias = this.getEvidencias();
        result = result * PRIME + ($evidencias == null ? 43 : $evidencias.hashCode());
        final java.lang.Object $naoConformidades = this.getNaoConformidades();
        result = result * PRIME + ($naoConformidades == null ? 43 : $naoConformidades.hashCode());
        final java.lang.Object $planoAcao = this.getPlanoAcao();
        result = result * PRIME + ($planoAcao == null ? 43 : $planoAcao.hashCode());
        return result;
    }

    @java.lang.SuppressWarnings("all")
    public Conformidade() {
    }

    /**
     * Creates a new {@code Conformidade} instance.
     *
     * @param regulacao Regulamentação associada à conformidade.
     * @param entidadeId ID da entidade técnica sendo avaliada.
     * @param tipoEntidade Tipo da entidade avaliada (ex: CLIENTE, CONTA).
     * @param status Status atual da conformidade.
     * @param dataVerificacao Data e hora da última verificação realizada.
     * @param dataProximaVerificacao Data e hora previstas para a próxima verificação.
     * @param observacoes Observações técnicas do analista de conformidade.
     * @param evidencias Evidências coletadas para suportar o status (JSON).
     * @param naoConformidades Detalhamento das não conformidades (JSON).
     * @param planoAcao Plano de ação para correção (JSON).
     */
    @java.lang.SuppressWarnings("all")
    public Conformidade(final Regulacao regulacao, final Long entidadeId, final String tipoEntidade, final StatusConformidade status, final LocalDateTime dataVerificacao, final LocalDateTime dataProximaVerificacao, final String observacoes, final String evidencias, final String naoConformidades, final String planoAcao) {
        this.regulacao = regulacao;
        this.entidadeId = entidadeId;
        this.tipoEntidade = tipoEntidade;
        this.status = status;
        this.dataVerificacao = dataVerificacao;
        this.dataProximaVerificacao = dataProximaVerificacao;
        this.observacoes = observacoes;
        this.evidencias = evidencias;
        this.naoConformidades = naoConformidades;
        this.planoAcao = planoAcao;
    }
}
