package com.aurix.platform.shared.dto;

import com.aurix.platform.shared.entity.Dashboard;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * DTO para Dashboard.
 */
public class DashboardDTO {
    /**
     * ID do dashboard.
     */
    private Long id;
    /**
     * Nome do dashboard.
     */
    @NotBlank(message = "Nome do dashboard é obrigatório")
    private String nome;
    /**
     * Descrição da finalidade do dashboard.
     */
    private String descricao;
    /**
     * Categoria do dashboard.
     */
    @NotNull(message = "Categoria é obrigatória")
    private Dashboard.CategoriaDashboard categoria;
    /**
     * Configuração do layout.
     */
    private String layout;
    /**
     * Configuração dos widgets.
     */
    private String widgets;
    /**
     * Filtros aplicados por padrão.
     */
    private String filtros;
    /**
     * Outras configurações.
     */
    private String configuracao;
    /**
     * Usuário que criou o dashboard.
     */
    private String usuarioCriacao;
    /**
     * Indica se é compartilhado com outros.
     */
    private Boolean compartilhado;
    /**
     * Indica se é público.
     */
    private Boolean publico;
    /**
     * Data e hora da última atualização de dados.
     */
    private LocalDateTime ultimaAtualizacao;
    /**
     * Indica se o dashboard é público (alias).
     */
    private Boolean isPublico;
    /**
     * Indica se o dashboard é compartilhado (alias).
     */
    private Boolean isCompartilhado;
    /**
     * Data de criação do registro.
     */
    private String dataCriacao;
    /**
     * Data da última atualização.
     */
    private String dataAtualizacao;

    /**
     * ID do dashboard.
     */
    @java.lang.SuppressWarnings("all")
    public Long getId() {
        return this.id;
    }

    /**
     * Nome do dashboard.
     */
    @java.lang.SuppressWarnings("all")
    public String getNome() {
        return this.nome;
    }

    /**
     * Descrição da finalidade do dashboard.
     */
    @java.lang.SuppressWarnings("all")
    public String getDescricao() {
        return this.descricao;
    }

    /**
     * Categoria do dashboard.
     */
    @java.lang.SuppressWarnings("all")
    public Dashboard.CategoriaDashboard getCategoria() {
        return this.categoria;
    }

    /**
     * Configuração do layout.
     */
    @java.lang.SuppressWarnings("all")
    public String getLayout() {
        return this.layout;
    }

    /**
     * Configuração dos widgets.
     */
    @java.lang.SuppressWarnings("all")
    public String getWidgets() {
        return this.widgets;
    }

    /**
     * Filtros aplicados por padrão.
     */
    @java.lang.SuppressWarnings("all")
    public String getFiltros() {
        return this.filtros;
    }

    /**
     * Outras configurações.
     */
    @java.lang.SuppressWarnings("all")
    public String getConfiguracao() {
        return this.configuracao;
    }

    /**
     * Usuário que criou o dashboard.
     */
    @java.lang.SuppressWarnings("all")
    public String getUsuarioCriacao() {
        return this.usuarioCriacao;
    }

    /**
     * Indica se é compartilhado com outros.
     */
    @java.lang.SuppressWarnings("all")
    public Boolean getCompartilhado() {
        return this.compartilhado;
    }

    /**
     * Indica se é público.
     */
    @java.lang.SuppressWarnings("all")
    public Boolean getPublico() {
        return this.publico;
    }

    /**
     * Data e hora da última atualização de dados.
     */
    @java.lang.SuppressWarnings("all")
    public LocalDateTime getUltimaAtualizacao() {
        return this.ultimaAtualizacao;
    }

    /**
     * Indica se o dashboard é público (alias).
     */
    @java.lang.SuppressWarnings("all")
    public Boolean getIsPublico() {
        return this.isPublico;
    }

    /**
     * Indica se o dashboard é compartilhado (alias).
     */
    @java.lang.SuppressWarnings("all")
    public Boolean getIsCompartilhado() {
        return this.isCompartilhado;
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
     * ID do dashboard.
     */
    @java.lang.SuppressWarnings("all")
    public void setId(final Long id) {
        this.id = id;
    }

    /**
     * Nome do dashboard.
     */
    @java.lang.SuppressWarnings("all")
    public void setNome(final String nome) {
        this.nome = nome;
    }

    /**
     * Descrição da finalidade do dashboard.
     */
    @java.lang.SuppressWarnings("all")
    public void setDescricao(final String descricao) {
        this.descricao = descricao;
    }

    /**
     * Categoria do dashboard.
     */
    @java.lang.SuppressWarnings("all")
    public void setCategoria(final Dashboard.CategoriaDashboard categoria) {
        this.categoria = categoria;
    }

    /**
     * Configuração do layout.
     */
    @java.lang.SuppressWarnings("all")
    public void setLayout(final String layout) {
        this.layout = layout;
    }

    /**
     * Configuração dos widgets.
     */
    @java.lang.SuppressWarnings("all")
    public void setWidgets(final String widgets) {
        this.widgets = widgets;
    }

    /**
     * Filtros aplicados por padrão.
     */
    @java.lang.SuppressWarnings("all")
    public void setFiltros(final String filtros) {
        this.filtros = filtros;
    }

    /**
     * Outras configurações.
     */
    @java.lang.SuppressWarnings("all")
    public void setConfiguracao(final String configuracao) {
        this.configuracao = configuracao;
    }

    /**
     * Usuário que criou o dashboard.
     */
    @java.lang.SuppressWarnings("all")
    public void setUsuarioCriacao(final String usuarioCriacao) {
        this.usuarioCriacao = usuarioCriacao;
    }

    /**
     * Indica se é compartilhado com outros.
     */
    @java.lang.SuppressWarnings("all")
    public void setCompartilhado(final Boolean compartilhado) {
        this.compartilhado = compartilhado;
    }

    /**
     * Indica se é público.
     */
    @java.lang.SuppressWarnings("all")
    public void setPublico(final Boolean publico) {
        this.publico = publico;
    }

    /**
     * Data e hora da última atualização de dados.
     */
    @java.lang.SuppressWarnings("all")
    public void setUltimaAtualizacao(final LocalDateTime ultimaAtualizacao) {
        this.ultimaAtualizacao = ultimaAtualizacao;
    }

    /**
     * Indica se o dashboard é público (alias).
     */
    @java.lang.SuppressWarnings("all")
    public void setIsPublico(final Boolean isPublico) {
        this.isPublico = isPublico;
    }

    /**
     * Indica se o dashboard é compartilhado (alias).
     */
    @java.lang.SuppressWarnings("all")
    public void setIsCompartilhado(final Boolean isCompartilhado) {
        this.isCompartilhado = isCompartilhado;
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
        if (!(o instanceof DashboardDTO)) return false;
        final DashboardDTO other = (DashboardDTO) o;
        if (!other.canEqual((java.lang.Object) this)) return false;
        final java.lang.Object this$id = this.getId();
        final java.lang.Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final java.lang.Object this$compartilhado = this.getCompartilhado();
        final java.lang.Object other$compartilhado = other.getCompartilhado();
        if (this$compartilhado == null ? other$compartilhado != null : !this$compartilhado.equals(other$compartilhado)) return false;
        final java.lang.Object this$publico = this.getPublico();
        final java.lang.Object other$publico = other.getPublico();
        if (this$publico == null ? other$publico != null : !this$publico.equals(other$publico)) return false;
        final java.lang.Object this$isPublico = this.getIsPublico();
        final java.lang.Object other$isPublico = other.getIsPublico();
        if (this$isPublico == null ? other$isPublico != null : !this$isPublico.equals(other$isPublico)) return false;
        final java.lang.Object this$isCompartilhado = this.getIsCompartilhado();
        final java.lang.Object other$isCompartilhado = other.getIsCompartilhado();
        if (this$isCompartilhado == null ? other$isCompartilhado != null : !this$isCompartilhado.equals(other$isCompartilhado)) return false;
        final java.lang.Object this$nome = this.getNome();
        final java.lang.Object other$nome = other.getNome();
        if (this$nome == null ? other$nome != null : !this$nome.equals(other$nome)) return false;
        final java.lang.Object this$descricao = this.getDescricao();
        final java.lang.Object other$descricao = other.getDescricao();
        if (this$descricao == null ? other$descricao != null : !this$descricao.equals(other$descricao)) return false;
        final java.lang.Object this$categoria = this.getCategoria();
        final java.lang.Object other$categoria = other.getCategoria();
        if (this$categoria == null ? other$categoria != null : !this$categoria.equals(other$categoria)) return false;
        final java.lang.Object this$layout = this.getLayout();
        final java.lang.Object other$layout = other.getLayout();
        if (this$layout == null ? other$layout != null : !this$layout.equals(other$layout)) return false;
        final java.lang.Object this$widgets = this.getWidgets();
        final java.lang.Object other$widgets = other.getWidgets();
        if (this$widgets == null ? other$widgets != null : !this$widgets.equals(other$widgets)) return false;
        final java.lang.Object this$filtros = this.getFiltros();
        final java.lang.Object other$filtros = other.getFiltros();
        if (this$filtros == null ? other$filtros != null : !this$filtros.equals(other$filtros)) return false;
        final java.lang.Object this$configuracao = this.getConfiguracao();
        final java.lang.Object other$configuracao = other.getConfiguracao();
        if (this$configuracao == null ? other$configuracao != null : !this$configuracao.equals(other$configuracao)) return false;
        final java.lang.Object this$usuarioCriacao = this.getUsuarioCriacao();
        final java.lang.Object other$usuarioCriacao = other.getUsuarioCriacao();
        if (this$usuarioCriacao == null ? other$usuarioCriacao != null : !this$usuarioCriacao.equals(other$usuarioCriacao)) return false;
        final java.lang.Object this$ultimaAtualizacao = this.getUltimaAtualizacao();
        final java.lang.Object other$ultimaAtualizacao = other.getUltimaAtualizacao();
        if (this$ultimaAtualizacao == null ? other$ultimaAtualizacao != null : !this$ultimaAtualizacao.equals(other$ultimaAtualizacao)) return false;
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
        return other instanceof DashboardDTO;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final java.lang.Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final java.lang.Object $compartilhado = this.getCompartilhado();
        result = result * PRIME + ($compartilhado == null ? 43 : $compartilhado.hashCode());
        final java.lang.Object $publico = this.getPublico();
        result = result * PRIME + ($publico == null ? 43 : $publico.hashCode());
        final java.lang.Object $isPublico = this.getIsPublico();
        result = result * PRIME + ($isPublico == null ? 43 : $isPublico.hashCode());
        final java.lang.Object $isCompartilhado = this.getIsCompartilhado();
        result = result * PRIME + ($isCompartilhado == null ? 43 : $isCompartilhado.hashCode());
        final java.lang.Object $nome = this.getNome();
        result = result * PRIME + ($nome == null ? 43 : $nome.hashCode());
        final java.lang.Object $descricao = this.getDescricao();
        result = result * PRIME + ($descricao == null ? 43 : $descricao.hashCode());
        final java.lang.Object $categoria = this.getCategoria();
        result = result * PRIME + ($categoria == null ? 43 : $categoria.hashCode());
        final java.lang.Object $layout = this.getLayout();
        result = result * PRIME + ($layout == null ? 43 : $layout.hashCode());
        final java.lang.Object $widgets = this.getWidgets();
        result = result * PRIME + ($widgets == null ? 43 : $widgets.hashCode());
        final java.lang.Object $filtros = this.getFiltros();
        result = result * PRIME + ($filtros == null ? 43 : $filtros.hashCode());
        final java.lang.Object $configuracao = this.getConfiguracao();
        result = result * PRIME + ($configuracao == null ? 43 : $configuracao.hashCode());
        final java.lang.Object $usuarioCriacao = this.getUsuarioCriacao();
        result = result * PRIME + ($usuarioCriacao == null ? 43 : $usuarioCriacao.hashCode());
        final java.lang.Object $ultimaAtualizacao = this.getUltimaAtualizacao();
        result = result * PRIME + ($ultimaAtualizacao == null ? 43 : $ultimaAtualizacao.hashCode());
        final java.lang.Object $dataCriacao = this.getDataCriacao();
        result = result * PRIME + ($dataCriacao == null ? 43 : $dataCriacao.hashCode());
        final java.lang.Object $dataAtualizacao = this.getDataAtualizacao();
        result = result * PRIME + ($dataAtualizacao == null ? 43 : $dataAtualizacao.hashCode());
        return result;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
        return "DashboardDTO(id=" + this.getId() + ", nome=" + this.getNome() + ", descricao=" + this.getDescricao() + ", categoria=" + this.getCategoria() + ", layout=" + this.getLayout() + ", widgets=" + this.getWidgets() + ", filtros=" + this.getFiltros() + ", configuracao=" + this.getConfiguracao() + ", usuarioCriacao=" + this.getUsuarioCriacao() + ", compartilhado=" + this.getCompartilhado() + ", publico=" + this.getPublico() + ", ultimaAtualizacao=" + this.getUltimaAtualizacao() + ", isPublico=" + this.getIsPublico() + ", isCompartilhado=" + this.getIsCompartilhado() + ", dataCriacao=" + this.getDataCriacao() + ", dataAtualizacao=" + this.getDataAtualizacao() + ")";
    }

    @java.lang.SuppressWarnings("all")
    public DashboardDTO() {
    }

    /**
     * Creates a new {@code DashboardDTO} instance.
     *
     * @param id ID do dashboard.
     * @param nome Nome do dashboard.
     * @param descricao Descrição da finalidade do dashboard.
     * @param categoria Categoria do dashboard.
     * @param layout Configuração do layout.
     * @param widgets Configuração dos widgets.
     * @param filtros Filtros aplicados por padrão.
     * @param configuracao Outras configurações.
     * @param usuarioCriacao Usuário que criou o dashboard.
     * @param compartilhado Indica se é compartilhado com outros.
     * @param publico Indica se é público.
     * @param ultimaAtualizacao Data e hora da última atualização de dados.
     * @param isPublico Indica se o dashboard é público (alias).
     * @param isCompartilhado Indica se o dashboard é compartilhado (alias).
     * @param dataCriacao Data de criação do registro.
     * @param dataAtualizacao Data da última atualização.
     */
    @java.lang.SuppressWarnings("all")
    public DashboardDTO(final Long id, final String nome, final String descricao, final Dashboard.CategoriaDashboard categoria, final String layout, final String widgets, final String filtros, final String configuracao, final String usuarioCriacao, final Boolean compartilhado, final Boolean publico, final LocalDateTime ultimaAtualizacao, final Boolean isPublico, final Boolean isCompartilhado, final String dataCriacao, final String dataAtualizacao) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.categoria = categoria;
        this.layout = layout;
        this.widgets = widgets;
        this.filtros = filtros;
        this.configuracao = configuracao;
        this.usuarioCriacao = usuarioCriacao;
        this.compartilhado = compartilhado;
        this.publico = publico;
        this.ultimaAtualizacao = ultimaAtualizacao;
        this.isPublico = isPublico;
        this.isCompartilhado = isCompartilhado;
        this.dataCriacao = dataCriacao;
        this.dataAtualizacao = dataAtualizacao;
    }
}
