package com.aurix.platform.shared.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

/**
 * Entidade Dashboard do Aurix.
 * Representa um dashboard de analytics.
 */
@Entity
@Table(name = "dashboards", schema = "aurix")
public class Dashboard extends BaseEntity {
    /**
     * Comprimento padrão para campos de descrição.
     */
    private static final int DESCRIPTION_MAX_LENGTH = 1000;
    /**
     * Nome descritivo do dashboard.
     */
    @NotBlank(message = "Nome do dashboard é obrigatório")
    @Column(nullable = false)
    private String nome;
    /**
     * Descrição detalhada do propósito do dashboard.
     */
    @Column(length = DESCRIPTION_MAX_LENGTH)
    private String descricao;
    /**
     * Categoria funcional (FINANCEIRO, RISCO, etc.).
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "categoria", nullable = false)
    private CategoriaDashboard categoria;
    /**
     * Definição visual do layout (JSON).
     */
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "layout", columnDefinition = "jsonb")
    private String layout;
    /**
     * Lista e configuração dos widgets inclusos (JSON).
     */
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "widgets", columnDefinition = "jsonb")
    private String widgets;
    /**
     * Filtros padrão aplicados ao dashboard (JSON).
     */
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "filtros", columnDefinition = "jsonb")
    private String filtros;
    /**
     * Configurações técnicas de exibição (JSON).
     */
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "configuracao", columnDefinition = "jsonb")
    private String configuracao;
    /**
     * Identificador do usuário que criou o dashboard.
     */
    @Column(name = "usuario_criacao")
    private String usuarioCriacao;
    /**
     * Indica se o dashboard está compartilhado com outros.
     */
    @Column(name = "compartilhado")
    private Boolean compartilhado = false;
    /**
     * Indica se o dashboard é visível publicamente.
     */
    @Column(name = "publico")
    private Boolean publico = false;
    /**
     * Data e hora da última sincronização ou atualização.
     */
    @Column(name = "ultima_atualizacao")
    private LocalDateTime ultimaAtualizacao = LocalDateTime.now();

    /**
     * Verifica se o dashboard é público.
     *
     * @return true se público, false caso contrário.
     */
    public boolean isPublico() {
        return publico != null && publico;
    }

    /**
     * Verifica se o dashboard é compartilhado.
     *
     * @return true se compartilhado, false caso contrário.
     */
    public boolean isCompartilhado() {
        return compartilhado != null && compartilhado;
    }


    /**
     * Enum para categoria do dashboard.
     */
    public enum CategoriaDashboard {
        /**
         * Executivo.
         */
        EXECUTIVO("Executivo"), /**
         * Operacional.
         */
        OPERACIONAL("Operacional"), /**
         * Financeiro.
         */
        FINANCEIRO("Financeiro"), /**
         * Cliente.
         */
        CLIENTE("Cliente"), /**
         * Risco.
         */
        RISCO("Risco"), /**
         * Compliance.
         */
        COMPLIANCE("Compliance"), /**
         * Performance.
         */
        PERFORMANCE("Performance"), /**
         * Qualidade.
         */
        QUALIDADE("Qualidade");
        /**
         * Descrição da categoria.
         */
        private final String descricao;

        CategoriaDashboard(final String desc) {
            this.descricao = desc;
        }

        /**
         * Retorna a descrição da categoria.
         *
         * @return a descrição.
         */
        public String getDescricao() {
            return descricao;
        }
    }

    /**
     * Nome descritivo do dashboard.
     */
    @java.lang.SuppressWarnings("all")
    public String getNome() {
        return this.nome;
    }

    /**
     * Descrição detalhada do propósito do dashboard.
     */
    @java.lang.SuppressWarnings("all")
    public String getDescricao() {
        return this.descricao;
    }

    /**
     * Categoria funcional (FINANCEIRO, RISCO, etc.).
     */
    @java.lang.SuppressWarnings("all")
    public CategoriaDashboard getCategoria() {
        return this.categoria;
    }

    /**
     * Definição visual do layout (JSON).
     */
    @java.lang.SuppressWarnings("all")
    public String getLayout() {
        return this.layout;
    }

    /**
     * Lista e configuração dos widgets inclusos (JSON).
     */
    @java.lang.SuppressWarnings("all")
    public String getWidgets() {
        return this.widgets;
    }

    /**
     * Filtros padrão aplicados ao dashboard (JSON).
     */
    @java.lang.SuppressWarnings("all")
    public String getFiltros() {
        return this.filtros;
    }

    /**
     * Configurações técnicas de exibição (JSON).
     */
    @java.lang.SuppressWarnings("all")
    public String getConfiguracao() {
        return this.configuracao;
    }

    /**
     * Identificador do usuário que criou o dashboard.
     */
    @java.lang.SuppressWarnings("all")
    public String getUsuarioCriacao() {
        return this.usuarioCriacao;
    }

    /**
     * Indica se o dashboard está compartilhado com outros.
     */
    @java.lang.SuppressWarnings("all")
    public Boolean getCompartilhado() {
        return this.compartilhado;
    }

    /**
     * Indica se o dashboard é visível publicamente.
     */
    @java.lang.SuppressWarnings("all")
    public Boolean getPublico() {
        return this.publico;
    }

    /**
     * Data e hora da última sincronização ou atualização.
     */
    @java.lang.SuppressWarnings("all")
    public LocalDateTime getUltimaAtualizacao() {
        return this.ultimaAtualizacao;
    }

    /**
     * Nome descritivo do dashboard.
     */
    @java.lang.SuppressWarnings("all")
    public void setNome(final String nome) {
        this.nome = nome;
    }

    /**
     * Descrição detalhada do propósito do dashboard.
     */
    @java.lang.SuppressWarnings("all")
    public void setDescricao(final String descricao) {
        this.descricao = descricao;
    }

    /**
     * Categoria funcional (FINANCEIRO, RISCO, etc.).
     */
    @java.lang.SuppressWarnings("all")
    public void setCategoria(final CategoriaDashboard categoria) {
        this.categoria = categoria;
    }

    /**
     * Definição visual do layout (JSON).
     */
    @java.lang.SuppressWarnings("all")
    public void setLayout(final String layout) {
        this.layout = layout;
    }

    /**
     * Lista e configuração dos widgets inclusos (JSON).
     */
    @java.lang.SuppressWarnings("all")
    public void setWidgets(final String widgets) {
        this.widgets = widgets;
    }

    /**
     * Filtros padrão aplicados ao dashboard (JSON).
     */
    @java.lang.SuppressWarnings("all")
    public void setFiltros(final String filtros) {
        this.filtros = filtros;
    }

    /**
     * Configurações técnicas de exibição (JSON).
     */
    @java.lang.SuppressWarnings("all")
    public void setConfiguracao(final String configuracao) {
        this.configuracao = configuracao;
    }

    /**
     * Identificador do usuário que criou o dashboard.
     */
    @java.lang.SuppressWarnings("all")
    public void setUsuarioCriacao(final String usuarioCriacao) {
        this.usuarioCriacao = usuarioCriacao;
    }

    /**
     * Indica se o dashboard está compartilhado com outros.
     */
    @java.lang.SuppressWarnings("all")
    public void setCompartilhado(final Boolean compartilhado) {
        this.compartilhado = compartilhado;
    }

    /**
     * Indica se o dashboard é visível publicamente.
     */
    @java.lang.SuppressWarnings("all")
    public void setPublico(final Boolean publico) {
        this.publico = publico;
    }

    /**
     * Data e hora da última sincronização ou atualização.
     */
    @java.lang.SuppressWarnings("all")
    public void setUltimaAtualizacao(final LocalDateTime ultimaAtualizacao) {
        this.ultimaAtualizacao = ultimaAtualizacao;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
        return "Dashboard(nome=" + this.getNome() + ", descricao=" + this.getDescricao() + ", categoria=" + this.getCategoria() + ", layout=" + this.getLayout() + ", widgets=" + this.getWidgets() + ", filtros=" + this.getFiltros() + ", configuracao=" + this.getConfiguracao() + ", usuarioCriacao=" + this.getUsuarioCriacao() + ", compartilhado=" + this.getCompartilhado() + ", publico=" + this.getPublico() + ", ultimaAtualizacao=" + this.getUltimaAtualizacao() + ")";
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public boolean equals(final java.lang.Object o) {
        if (o == this) return true;
        if (!(o instanceof Dashboard)) return false;
        final Dashboard other = (Dashboard) o;
        if (!other.canEqual((java.lang.Object) this)) return false;
        if (!super.equals(o)) return false;
        final java.lang.Object this$compartilhado = this.getCompartilhado();
        final java.lang.Object other$compartilhado = other.getCompartilhado();
        if (this$compartilhado == null ? other$compartilhado != null : !this$compartilhado.equals(other$compartilhado)) return false;
        final java.lang.Object this$publico = this.getPublico();
        final java.lang.Object other$publico = other.getPublico();
        if (this$publico == null ? other$publico != null : !this$publico.equals(other$publico)) return false;
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
        return true;
    }

    @java.lang.SuppressWarnings("all")
    protected boolean canEqual(final java.lang.Object other) {
        return other instanceof Dashboard;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public int hashCode() {
        final int PRIME = 59;
        int result = super.hashCode();
        final java.lang.Object $compartilhado = this.getCompartilhado();
        result = result * PRIME + ($compartilhado == null ? 43 : $compartilhado.hashCode());
        final java.lang.Object $publico = this.getPublico();
        result = result * PRIME + ($publico == null ? 43 : $publico.hashCode());
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
        return result;
    }

    @java.lang.SuppressWarnings("all")
    public Dashboard() {
    }

    /**
     * Creates a new {@code Dashboard} instance.
     *
     * @param nome Nome descritivo do dashboard.
     * @param descricao Descrição detalhada do propósito do dashboard.
     * @param categoria Categoria funcional (FINANCEIRO, RISCO, etc.).
     * @param layout Definição visual do layout (JSON).
     * @param widgets Lista e configuração dos widgets inclusos (JSON).
     * @param filtros Filtros padrão aplicados ao dashboard (JSON).
     * @param configuracao Configurações técnicas de exibição (JSON).
     * @param usuarioCriacao Identificador do usuário que criou o dashboard.
     * @param compartilhado Indica se o dashboard está compartilhado com outros.
     * @param publico Indica se o dashboard é visível publicamente.
     * @param ultimaAtualizacao Data e hora da última sincronização ou atualização.
     */
    @java.lang.SuppressWarnings("all")
    public Dashboard(final String nome, final String descricao, final CategoriaDashboard categoria, final String layout, final String widgets, final String filtros, final String configuracao, final String usuarioCriacao, final Boolean compartilhado, final Boolean publico, final LocalDateTime ultimaAtualizacao) {
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
    }
}
