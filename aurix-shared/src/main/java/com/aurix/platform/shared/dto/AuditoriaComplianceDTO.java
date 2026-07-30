package com.aurix.platform.shared.dto;

import com.aurix.platform.shared.entity.AuditoriaCompliance;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * DTO para AuditoriaCompliance.
 */
public class AuditoriaComplianceDTO {
    /**
     * ID da auditoria.
     */
    private Long id;
    /**
     * Título da auditoria.
     */
    @NotBlank(message = "Título da auditoria é obrigatório")
    private String titulo;
    /**
     * Descrição detalhada.
     */
    private String descricao;
    /**
     * ID da regulamentação associada.
     */
    @NotNull(message = "Regulamentação é obrigatória")
    private Long regulacaoId;
    /**
     * Nome da regulamentação associada.
     */
    private String regulacaoNome;
    /**
     * Nome do auditor responsável.
     */
    private String auditorResponsavel;
    /**
     * Data de início da auditoria.
     */
    private LocalDateTime dataInicio;
    /**
     * Data de término da auditoria.
     */
    private LocalDateTime dataFim;
    /**
     * Status atual da auditoria.
     */
    private AuditoriaCompliance.StatusAuditoria status;
    /**
     * Tipo de auditoria realizada.
     */
    private AuditoriaCompliance.TipoAuditoria tipoAuditoria;
    /**
     * Escopo da auditoria.
     */
    private String escopo;
    /**
     * Metodologia utilizada.
     */
    private String metodologia;
    /**
     * Critérios de avaliação.
     */
    private String criterios;
    /**
     * Evidências coletadas durante o processo.
     */
    private String evidenciasColetadas;
    /**
     * Não conformidades identificadas.
     */
    private String naoConformidadesIdentificadas;
    /**
     * Recomendações do auditor.
     */
    private String recomendacoes;
    /**
     * Plano de correção proposto.
     */
    private String planoCorrecao;
    /**
     * Link ou conteúdo do relatório final.
     */
    private String relatorioFinal;
    /**
     * Indica se a auditoria está em andamento.
     */
    private Boolean emAndamento;
    /**
     * Indica se a auditoria foi concluída.
     */
    private Boolean concluida;
    /**
     * Indica se a auditoria está atrasada.
     */
    private Boolean atrasada;
    /**
     * Data de criação do registro.
     */
    private String dataCriacao;
    /**
     * Data da última atualização.
     */
    private String dataAtualizacao;

    /**
     * ID da auditoria.
     */
    @java.lang.SuppressWarnings("all")
    public Long getId() {
        return this.id;
    }

    /**
     * Título da auditoria.
     */
    @java.lang.SuppressWarnings("all")
    public String getTitulo() {
        return this.titulo;
    }

    /**
     * Descrição detalhada.
     */
    @java.lang.SuppressWarnings("all")
    public String getDescricao() {
        return this.descricao;
    }

    /**
     * ID da regulamentação associada.
     */
    @java.lang.SuppressWarnings("all")
    public Long getRegulacaoId() {
        return this.regulacaoId;
    }

    /**
     * Nome da regulamentação associada.
     */
    @java.lang.SuppressWarnings("all")
    public String getRegulacaoNome() {
        return this.regulacaoNome;
    }

    /**
     * Nome do auditor responsável.
     */
    @java.lang.SuppressWarnings("all")
    public String getAuditorResponsavel() {
        return this.auditorResponsavel;
    }

    /**
     * Data de início da auditoria.
     */
    @java.lang.SuppressWarnings("all")
    public LocalDateTime getDataInicio() {
        return this.dataInicio;
    }

    /**
     * Data de término da auditoria.
     */
    @java.lang.SuppressWarnings("all")
    public LocalDateTime getDataFim() {
        return this.dataFim;
    }

    /**
     * Status atual da auditoria.
     */
    @java.lang.SuppressWarnings("all")
    public AuditoriaCompliance.StatusAuditoria getStatus() {
        return this.status;
    }

    /**
     * Tipo de auditoria realizada.
     */
    @java.lang.SuppressWarnings("all")
    public AuditoriaCompliance.TipoAuditoria getTipoAuditoria() {
        return this.tipoAuditoria;
    }

    /**
     * Escopo da auditoria.
     */
    @java.lang.SuppressWarnings("all")
    public String getEscopo() {
        return this.escopo;
    }

    /**
     * Metodologia utilizada.
     */
    @java.lang.SuppressWarnings("all")
    public String getMetodologia() {
        return this.metodologia;
    }

    /**
     * Critérios de avaliação.
     */
    @java.lang.SuppressWarnings("all")
    public String getCriterios() {
        return this.criterios;
    }

    /**
     * Evidências coletadas durante o processo.
     */
    @java.lang.SuppressWarnings("all")
    public String getEvidenciasColetadas() {
        return this.evidenciasColetadas;
    }

    /**
     * Não conformidades identificadas.
     */
    @java.lang.SuppressWarnings("all")
    public String getNaoConformidadesIdentificadas() {
        return this.naoConformidadesIdentificadas;
    }

    /**
     * Recomendações do auditor.
     */
    @java.lang.SuppressWarnings("all")
    public String getRecomendacoes() {
        return this.recomendacoes;
    }

    /**
     * Plano de correção proposto.
     */
    @java.lang.SuppressWarnings("all")
    public String getPlanoCorrecao() {
        return this.planoCorrecao;
    }

    /**
     * Link ou conteúdo do relatório final.
     */
    @java.lang.SuppressWarnings("all")
    public String getRelatorioFinal() {
        return this.relatorioFinal;
    }

    /**
     * Indica se a auditoria está em andamento.
     */
    @java.lang.SuppressWarnings("all")
    public Boolean getEmAndamento() {
        return this.emAndamento;
    }

    /**
     * Indica se a auditoria foi concluída.
     */
    @java.lang.SuppressWarnings("all")
    public Boolean getConcluida() {
        return this.concluida;
    }

    /**
     * Indica se a auditoria está atrasada.
     */
    @java.lang.SuppressWarnings("all")
    public Boolean getAtrasada() {
        return this.atrasada;
    }

    /**
     * Data de criação do registro.
     */
    @java.lang.SuppressWarnings("all")
    public String getDataCriacao() {
        return this.dataCriacao;
    }

    /**
     * Data da última atualização.
     */
    @java.lang.SuppressWarnings("all")
    public String getDataAtualizacao() {
        return this.dataAtualizacao;
    }

    /**
     * ID da auditoria.
     */
    @java.lang.SuppressWarnings("all")
    public void setId(final Long id) {
        this.id = id;
    }

    /**
     * Título da auditoria.
     */
    @java.lang.SuppressWarnings("all")
    public void setTitulo(final String titulo) {
        this.titulo = titulo;
    }

    /**
     * Descrição detalhada.
     */
    @java.lang.SuppressWarnings("all")
    public void setDescricao(final String descricao) {
        this.descricao = descricao;
    }

    /**
     * ID da regulamentação associada.
     */
    @java.lang.SuppressWarnings("all")
    public void setRegulacaoId(final Long regulacaoId) {
        this.regulacaoId = regulacaoId;
    }

    /**
     * Nome da regulamentação associada.
     */
    @java.lang.SuppressWarnings("all")
    public void setRegulacaoNome(final String regulacaoNome) {
        this.regulacaoNome = regulacaoNome;
    }

    /**
     * Nome do auditor responsável.
     */
    @java.lang.SuppressWarnings("all")
    public void setAuditorResponsavel(final String auditorResponsavel) {
        this.auditorResponsavel = auditorResponsavel;
    }

    /**
     * Data de início da auditoria.
     */
    @java.lang.SuppressWarnings("all")
    public void setDataInicio(final LocalDateTime dataInicio) {
        this.dataInicio = dataInicio;
    }

    /**
     * Data de término da auditoria.
     */
    @java.lang.SuppressWarnings("all")
    public void setDataFim(final LocalDateTime dataFim) {
        this.dataFim = dataFim;
    }

    /**
     * Status atual da auditoria.
     */
    @java.lang.SuppressWarnings("all")
    public void setStatus(final AuditoriaCompliance.StatusAuditoria status) {
        this.status = status;
    }

    /**
     * Tipo de auditoria realizada.
     */
    @java.lang.SuppressWarnings("all")
    public void setTipoAuditoria(final AuditoriaCompliance.TipoAuditoria tipoAuditoria) {
        this.tipoAuditoria = tipoAuditoria;
    }

    /**
     * Escopo da auditoria.
     */
    @java.lang.SuppressWarnings("all")
    public void setEscopo(final String escopo) {
        this.escopo = escopo;
    }

    /**
     * Metodologia utilizada.
     */
    @java.lang.SuppressWarnings("all")
    public void setMetodologia(final String metodologia) {
        this.metodologia = metodologia;
    }

    /**
     * Critérios de avaliação.
     */
    @java.lang.SuppressWarnings("all")
    public void setCriterios(final String criterios) {
        this.criterios = criterios;
    }

    /**
     * Evidências coletadas durante o processo.
     */
    @java.lang.SuppressWarnings("all")
    public void setEvidenciasColetadas(final String evidenciasColetadas) {
        this.evidenciasColetadas = evidenciasColetadas;
    }

    /**
     * Não conformidades identificadas.
     */
    @java.lang.SuppressWarnings("all")
    public void setNaoConformidadesIdentificadas(final String naoConformidadesIdentificadas) {
        this.naoConformidadesIdentificadas = naoConformidadesIdentificadas;
    }

    /**
     * Recomendações do auditor.
     */
    @java.lang.SuppressWarnings("all")
    public void setRecomendacoes(final String recomendacoes) {
        this.recomendacoes = recomendacoes;
    }

    /**
     * Plano de correção proposto.
     */
    @java.lang.SuppressWarnings("all")
    public void setPlanoCorrecao(final String planoCorrecao) {
        this.planoCorrecao = planoCorrecao;
    }

    /**
     * Link ou conteúdo do relatório final.
     */
    @java.lang.SuppressWarnings("all")
    public void setRelatorioFinal(final String relatorioFinal) {
        this.relatorioFinal = relatorioFinal;
    }

    /**
     * Indica se a auditoria está em andamento.
     */
    @java.lang.SuppressWarnings("all")
    public void setEmAndamento(final Boolean emAndamento) {
        this.emAndamento = emAndamento;
    }

    /**
     * Indica se a auditoria foi concluída.
     */
    @java.lang.SuppressWarnings("all")
    public void setConcluida(final Boolean concluida) {
        this.concluida = concluida;
    }

    /**
     * Indica se a auditoria está atrasada.
     */
    @java.lang.SuppressWarnings("all")
    public void setAtrasada(final Boolean atrasada) {
        this.atrasada = atrasada;
    }

    /**
     * Data de criação do registro.
     */
    @java.lang.SuppressWarnings("all")
    public void setDataCriacao(final String dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    /**
     * Data da última atualização.
     */
    @java.lang.SuppressWarnings("all")
    public void setDataAtualizacao(final String dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public boolean equals(final java.lang.Object o) {
        if (o == this) return true;
        if (!(o instanceof AuditoriaComplianceDTO)) return false;
        final AuditoriaComplianceDTO other = (AuditoriaComplianceDTO) o;
        if (!other.canEqual((java.lang.Object) this)) return false;
        final java.lang.Object this$id = this.getId();
        final java.lang.Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final java.lang.Object this$regulacaoId = this.getRegulacaoId();
        final java.lang.Object other$regulacaoId = other.getRegulacaoId();
        if (this$regulacaoId == null ? other$regulacaoId != null : !this$regulacaoId.equals(other$regulacaoId)) return false;
        final java.lang.Object this$emAndamento = this.getEmAndamento();
        final java.lang.Object other$emAndamento = other.getEmAndamento();
        if (this$emAndamento == null ? other$emAndamento != null : !this$emAndamento.equals(other$emAndamento)) return false;
        final java.lang.Object this$concluida = this.getConcluida();
        final java.lang.Object other$concluida = other.getConcluida();
        if (this$concluida == null ? other$concluida != null : !this$concluida.equals(other$concluida)) return false;
        final java.lang.Object this$atrasada = this.getAtrasada();
        final java.lang.Object other$atrasada = other.getAtrasada();
        if (this$atrasada == null ? other$atrasada != null : !this$atrasada.equals(other$atrasada)) return false;
        final java.lang.Object this$titulo = this.getTitulo();
        final java.lang.Object other$titulo = other.getTitulo();
        if (this$titulo == null ? other$titulo != null : !this$titulo.equals(other$titulo)) return false;
        final java.lang.Object this$descricao = this.getDescricao();
        final java.lang.Object other$descricao = other.getDescricao();
        if (this$descricao == null ? other$descricao != null : !this$descricao.equals(other$descricao)) return false;
        final java.lang.Object this$regulacaoNome = this.getRegulacaoNome();
        final java.lang.Object other$regulacaoNome = other.getRegulacaoNome();
        if (this$regulacaoNome == null ? other$regulacaoNome != null : !this$regulacaoNome.equals(other$regulacaoNome)) return false;
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
        final java.lang.Object this$dataCriacao = this.getDataCriacao();
        final java.lang.Object other$dataCriacao = other.getDataCriacao();
        if (this$dataCriacao == null ? other$dataCriacao != null : !this$dataCriacao.equals(other$dataCriacao)) return false;
        final java.lang.Object this$dataAtualizacao = this.getDataAtualizacao();
        final java.lang.Object other$dataAtualizacao = other.getDataAtualizacao();
        if (this$dataAtualizacao == null ? other$dataAtualizacao != null : !this$dataAtualizacao.equals(other$dataAtualizacao)) return false;
        return true;
    }

    @java.lang.SuppressWarnings("all")
    protected boolean canEqual(final java.lang.Object other) {
        return other instanceof AuditoriaComplianceDTO;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final java.lang.Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final java.lang.Object $regulacaoId = this.getRegulacaoId();
        result = result * PRIME + ($regulacaoId == null ? 43 : $regulacaoId.hashCode());
        final java.lang.Object $emAndamento = this.getEmAndamento();
        result = result * PRIME + ($emAndamento == null ? 43 : $emAndamento.hashCode());
        final java.lang.Object $concluida = this.getConcluida();
        result = result * PRIME + ($concluida == null ? 43 : $concluida.hashCode());
        final java.lang.Object $atrasada = this.getAtrasada();
        result = result * PRIME + ($atrasada == null ? 43 : $atrasada.hashCode());
        final java.lang.Object $titulo = this.getTitulo();
        result = result * PRIME + ($titulo == null ? 43 : $titulo.hashCode());
        final java.lang.Object $descricao = this.getDescricao();
        result = result * PRIME + ($descricao == null ? 43 : $descricao.hashCode());
        final java.lang.Object $regulacaoNome = this.getRegulacaoNome();
        result = result * PRIME + ($regulacaoNome == null ? 43 : $regulacaoNome.hashCode());
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
        final java.lang.Object $dataCriacao = this.getDataCriacao();
        result = result * PRIME + ($dataCriacao == null ? 43 : $dataCriacao.hashCode());
        final java.lang.Object $dataAtualizacao = this.getDataAtualizacao();
        result = result * PRIME + ($dataAtualizacao == null ? 43 : $dataAtualizacao.hashCode());
        return result;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
        return "AuditoriaComplianceDTO(id=" + this.getId() + ", titulo=" + this.getTitulo() + ", descricao=" + this.getDescricao() + ", regulacaoId=" + this.getRegulacaoId() + ", regulacaoNome=" + this.getRegulacaoNome() + ", auditorResponsavel=" + this.getAuditorResponsavel() + ", dataInicio=" + this.getDataInicio() + ", dataFim=" + this.getDataFim() + ", status=" + this.getStatus() + ", tipoAuditoria=" + this.getTipoAuditoria() + ", escopo=" + this.getEscopo() + ", metodologia=" + this.getMetodologia() + ", criterios=" + this.getCriterios() + ", evidenciasColetadas=" + this.getEvidenciasColetadas() + ", naoConformidadesIdentificadas=" + this.getNaoConformidadesIdentificadas() + ", recomendacoes=" + this.getRecomendacoes() + ", planoCorrecao=" + this.getPlanoCorrecao() + ", relatorioFinal=" + this.getRelatorioFinal() + ", emAndamento=" + this.getEmAndamento() + ", concluida=" + this.getConcluida() + ", atrasada=" + this.getAtrasada() + ", dataCriacao=" + this.getDataCriacao() + ", dataAtualizacao=" + this.getDataAtualizacao() + ")";
    }

    @java.lang.SuppressWarnings("all")
    public AuditoriaComplianceDTO() {
    }

    /**
     * Creates a new {@code AuditoriaComplianceDTO} instance.
     *
     * @param id ID da auditoria.
     * @param titulo Título da auditoria.
     * @param descricao Descrição detalhada.
     * @param regulacaoId ID da regulamentação associada.
     * @param regulacaoNome Nome da regulamentação associada.
     * @param auditorResponsavel Nome do auditor responsável.
     * @param dataInicio Data de início da auditoria.
     * @param dataFim Data de término da auditoria.
     * @param status Status atual da auditoria.
     * @param tipoAuditoria Tipo de auditoria realizada.
     * @param escopo Escopo da auditoria.
     * @param metodologia Metodologia utilizada.
     * @param criterios Critérios de avaliação.
     * @param evidenciasColetadas Evidências coletadas durante o processo.
     * @param naoConformidadesIdentificadas Não conformidades identificadas.
     * @param recomendacoes Recomendações do auditor.
     * @param planoCorrecao Plano de correção proposto.
     * @param relatorioFinal Link ou conteúdo do relatório final.
     * @param emAndamento Indica se a auditoria está em andamento.
     * @param concluida Indica se a auditoria foi concluída.
     * @param atrasada Indica se a auditoria está atrasada.
     * @param dataCriacao Data de criação do registro.
     * @param dataAtualizacao Data da última atualização.
     */
    @java.lang.SuppressWarnings("all")
    public AuditoriaComplianceDTO(final Long id, final String titulo, final String descricao, final Long regulacaoId, final String regulacaoNome, final String auditorResponsavel, final LocalDateTime dataInicio, final LocalDateTime dataFim, final AuditoriaCompliance.StatusAuditoria status, final AuditoriaCompliance.TipoAuditoria tipoAuditoria, final String escopo, final String metodologia, final String criterios, final String evidenciasColetadas, final String naoConformidadesIdentificadas, final String recomendacoes, final String planoCorrecao, final String relatorioFinal, final Boolean emAndamento, final Boolean concluida, final Boolean atrasada, final String dataCriacao, final String dataAtualizacao) {
        this.id = id;
        this.titulo = titulo;
        this.descricao = descricao;
        this.regulacaoId = regulacaoId;
        this.regulacaoNome = regulacaoNome;
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
        this.emAndamento = emAndamento;
        this.concluida = concluida;
        this.atrasada = atrasada;
        this.dataCriacao = dataCriacao;
        this.dataAtualizacao = dataAtualizacao;
    }
}
