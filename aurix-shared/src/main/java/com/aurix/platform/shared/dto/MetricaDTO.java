package com.aurix.platform.shared.dto;

import com.aurix.platform.shared.entity.Metrica;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO para Metrica.
 */
public class MetricaDTO {
    /**
     * ID da métrica.
     */
    private Long id;
    /**
     * Nome descritivo da métrica.
     */
    @NotBlank(message = "Nome da métrica é obrigatório")
    private String nome;
    /**
     * Descrição detalhada da métrica.
     */
    private String descricao;
    /**
     * Valor atual medido.
     */
    @NotNull(message = "Valor da métrica é obrigatório")
    private BigDecimal valor;
    /**
     * Valor medido anteriormente.
     */
    private BigDecimal valorAnterior;
    /**
     * Data e hora da medição.
     */
    private LocalDateTime dataMedicao;
    /**
     * Tipo da métrica (ex: FINANCEIRA, OPERACIONAL).
     */
    private Metrica.TipoMetrica tipoMetrica;
    /**
     * Categoria da métrica.
     */
    private Metrica.CategoriaMetrica categoria;
    /**
     * Unidade de medida (ex: R$, %, ms).
     */
    private String unidadeMedida;
    /**
     * Tendência da métrica (ex: ALTA, BAIXA, ESTAVEL).
     */
    private String tendencia;
    /**
     * Meta definida para a métrica.
     */
    private BigDecimal meta;
    /**
     * Limite inferior aceitável.
     */
    private BigDecimal limiteInferior;
    /**
     * Limite superior aceitável.
     */
    private BigDecimal limiteSuperior;
    /**
     * Dados extras em formato JSON.
     */
    private String dadosExtras;
    /**
     * Variação percentual entre a última e a penúltima medida.
     */
    private BigDecimal variacaoPercentual;
    /**
     * Indica se a medida está dentro dos limites aceitáveis.
     */
    private Boolean dentroLimites;
    /**
     * Indica se a meta definida foi atingida.
     */
    private Boolean metaAtingida;
    /**
     * Data de criação do registro.
     */
    private String dataCriacao;
    /**
     * Data da última atualização.
     */
    private String dataAtualizacao;

    /**
     * ID da métrica.
     */
    @java.lang.SuppressWarnings("all")
    public Long getId() {
        return this.id;
    }

    /**
     * Nome descritivo da métrica.
     */
    @java.lang.SuppressWarnings("all")
    public String getNome() {
        return this.nome;
    }

    /**
     * Descrição detalhada da métrica.
     */
    @java.lang.SuppressWarnings("all")
    public String getDescricao() {
        return this.descricao;
    }

    /**
     * Valor atual medido.
     */
    @java.lang.SuppressWarnings("all")
    public BigDecimal getValor() {
        return this.valor;
    }

    /**
     * Valor medido anteriormente.
     */
    @java.lang.SuppressWarnings("all")
    public BigDecimal getValorAnterior() {
        return this.valorAnterior;
    }

    /**
     * Data e hora da medição.
     */
    @java.lang.SuppressWarnings("all")
    public LocalDateTime getDataMedicao() {
        return this.dataMedicao;
    }

    /**
     * Tipo da métrica (ex: FINANCEIRA, OPERACIONAL).
     */
    @java.lang.SuppressWarnings("all")
    public Metrica.TipoMetrica getTipoMetrica() {
        return this.tipoMetrica;
    }

    /**
     * Categoria da métrica.
     */
    @java.lang.SuppressWarnings("all")
    public Metrica.CategoriaMetrica getCategoria() {
        return this.categoria;
    }

    /**
     * Unidade de medida (ex: R$, %, ms).
     */
    @java.lang.SuppressWarnings("all")
    public String getUnidadeMedida() {
        return this.unidadeMedida;
    }

    /**
     * Tendência da métrica (ex: ALTA, BAIXA, ESTAVEL).
     */
    @java.lang.SuppressWarnings("all")
    public String getTendencia() {
        return this.tendencia;
    }

    /**
     * Meta definida para a métrica.
     */
    @java.lang.SuppressWarnings("all")
    public BigDecimal getMeta() {
        return this.meta;
    }

    /**
     * Limite inferior aceitável.
     */
    @java.lang.SuppressWarnings("all")
    public BigDecimal getLimiteInferior() {
        return this.limiteInferior;
    }

    /**
     * Limite superior aceitável.
     */
    @java.lang.SuppressWarnings("all")
    public BigDecimal getLimiteSuperior() {
        return this.limiteSuperior;
    }

    /**
     * Dados extras em formato JSON.
     */
    @java.lang.SuppressWarnings("all")
    public String getDadosExtras() {
        return this.dadosExtras;
    }

    /**
     * Variação percentual entre a última e a penúltima medida.
     */
    @java.lang.SuppressWarnings("all")
    public BigDecimal getVariacaoPercentual() {
        return this.variacaoPercentual;
    }

    /**
     * Indica se a medida está dentro dos limites aceitáveis.
     */
    @java.lang.SuppressWarnings("all")
    public Boolean getDentroLimites() {
        return this.dentroLimites;
    }

    /**
     * Indica se a meta definida foi atingida.
     */
    @java.lang.SuppressWarnings("all")
    public Boolean getMetaAtingida() {
        return this.metaAtingida;
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
     * ID da métrica.
     */
    @java.lang.SuppressWarnings("all")
    public void setId(final Long id) {
        this.id = id;
    }

    /**
     * Nome descritivo da métrica.
     */
    @java.lang.SuppressWarnings("all")
    public void setNome(final String nome) {
        this.nome = nome;
    }

    /**
     * Descrição detalhada da métrica.
     */
    @java.lang.SuppressWarnings("all")
    public void setDescricao(final String descricao) {
        this.descricao = descricao;
    }

    /**
     * Valor atual medido.
     */
    @java.lang.SuppressWarnings("all")
    public void setValor(final BigDecimal valor) {
        this.valor = valor;
    }

    /**
     * Valor medido anteriormente.
     */
    @java.lang.SuppressWarnings("all")
    public void setValorAnterior(final BigDecimal valorAnterior) {
        this.valorAnterior = valorAnterior;
    }

    /**
     * Data e hora da medição.
     */
    @java.lang.SuppressWarnings("all")
    public void setDataMedicao(final LocalDateTime dataMedicao) {
        this.dataMedicao = dataMedicao;
    }

    /**
     * Tipo da métrica (ex: FINANCEIRA, OPERACIONAL).
     */
    @java.lang.SuppressWarnings("all")
    public void setTipoMetrica(final Metrica.TipoMetrica tipoMetrica) {
        this.tipoMetrica = tipoMetrica;
    }

    /**
     * Categoria da métrica.
     */
    @java.lang.SuppressWarnings("all")
    public void setCategoria(final Metrica.CategoriaMetrica categoria) {
        this.categoria = categoria;
    }

    /**
     * Unidade de medida (ex: R$, %, ms).
     */
    @java.lang.SuppressWarnings("all")
    public void setUnidadeMedida(final String unidadeMedida) {
        this.unidadeMedida = unidadeMedida;
    }

    /**
     * Tendência da métrica (ex: ALTA, BAIXA, ESTAVEL).
     */
    @java.lang.SuppressWarnings("all")
    public void setTendencia(final String tendencia) {
        this.tendencia = tendencia;
    }

    /**
     * Meta definida para a métrica.
     */
    @java.lang.SuppressWarnings("all")
    public void setMeta(final BigDecimal meta) {
        this.meta = meta;
    }

    /**
     * Limite inferior aceitável.
     */
    @java.lang.SuppressWarnings("all")
    public void setLimiteInferior(final BigDecimal limiteInferior) {
        this.limiteInferior = limiteInferior;
    }

    /**
     * Limite superior aceitável.
     */
    @java.lang.SuppressWarnings("all")
    public void setLimiteSuperior(final BigDecimal limiteSuperior) {
        this.limiteSuperior = limiteSuperior;
    }

    /**
     * Dados extras em formato JSON.
     */
    @java.lang.SuppressWarnings("all")
    public void setDadosExtras(final String dadosExtras) {
        this.dadosExtras = dadosExtras;
    }

    /**
     * Variação percentual entre a última e a penúltima medida.
     */
    @java.lang.SuppressWarnings("all")
    public void setVariacaoPercentual(final BigDecimal variacaoPercentual) {
        this.variacaoPercentual = variacaoPercentual;
    }

    /**
     * Indica se a medida está dentro dos limites aceitáveis.
     */
    @java.lang.SuppressWarnings("all")
    public void setDentroLimites(final Boolean dentroLimites) {
        this.dentroLimites = dentroLimites;
    }

    /**
     * Indica se a meta definida foi atingida.
     */
    @java.lang.SuppressWarnings("all")
    public void setMetaAtingida(final Boolean metaAtingida) {
        this.metaAtingida = metaAtingida;
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
        if (!(o instanceof MetricaDTO)) return false;
        final MetricaDTO other = (MetricaDTO) o;
        if (!other.canEqual((java.lang.Object) this)) return false;
        final java.lang.Object this$id = this.getId();
        final java.lang.Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final java.lang.Object this$dentroLimites = this.getDentroLimites();
        final java.lang.Object other$dentroLimites = other.getDentroLimites();
        if (this$dentroLimites == null ? other$dentroLimites != null : !this$dentroLimites.equals(other$dentroLimites)) return false;
        final java.lang.Object this$metaAtingida = this.getMetaAtingida();
        final java.lang.Object other$metaAtingida = other.getMetaAtingida();
        if (this$metaAtingida == null ? other$metaAtingida != null : !this$metaAtingida.equals(other$metaAtingida)) return false;
        final java.lang.Object this$nome = this.getNome();
        final java.lang.Object other$nome = other.getNome();
        if (this$nome == null ? other$nome != null : !this$nome.equals(other$nome)) return false;
        final java.lang.Object this$descricao = this.getDescricao();
        final java.lang.Object other$descricao = other.getDescricao();
        if (this$descricao == null ? other$descricao != null : !this$descricao.equals(other$descricao)) return false;
        final java.lang.Object this$valor = this.getValor();
        final java.lang.Object other$valor = other.getValor();
        if (this$valor == null ? other$valor != null : !this$valor.equals(other$valor)) return false;
        final java.lang.Object this$valorAnterior = this.getValorAnterior();
        final java.lang.Object other$valorAnterior = other.getValorAnterior();
        if (this$valorAnterior == null ? other$valorAnterior != null : !this$valorAnterior.equals(other$valorAnterior)) return false;
        final java.lang.Object this$dataMedicao = this.getDataMedicao();
        final java.lang.Object other$dataMedicao = other.getDataMedicao();
        if (this$dataMedicao == null ? other$dataMedicao != null : !this$dataMedicao.equals(other$dataMedicao)) return false;
        final java.lang.Object this$tipoMetrica = this.getTipoMetrica();
        final java.lang.Object other$tipoMetrica = other.getTipoMetrica();
        if (this$tipoMetrica == null ? other$tipoMetrica != null : !this$tipoMetrica.equals(other$tipoMetrica)) return false;
        final java.lang.Object this$categoria = this.getCategoria();
        final java.lang.Object other$categoria = other.getCategoria();
        if (this$categoria == null ? other$categoria != null : !this$categoria.equals(other$categoria)) return false;
        final java.lang.Object this$unidadeMedida = this.getUnidadeMedida();
        final java.lang.Object other$unidadeMedida = other.getUnidadeMedida();
        if (this$unidadeMedida == null ? other$unidadeMedida != null : !this$unidadeMedida.equals(other$unidadeMedida)) return false;
        final java.lang.Object this$tendencia = this.getTendencia();
        final java.lang.Object other$tendencia = other.getTendencia();
        if (this$tendencia == null ? other$tendencia != null : !this$tendencia.equals(other$tendencia)) return false;
        final java.lang.Object this$meta = this.getMeta();
        final java.lang.Object other$meta = other.getMeta();
        if (this$meta == null ? other$meta != null : !this$meta.equals(other$meta)) return false;
        final java.lang.Object this$limiteInferior = this.getLimiteInferior();
        final java.lang.Object other$limiteInferior = other.getLimiteInferior();
        if (this$limiteInferior == null ? other$limiteInferior != null : !this$limiteInferior.equals(other$limiteInferior)) return false;
        final java.lang.Object this$limiteSuperior = this.getLimiteSuperior();
        final java.lang.Object other$limiteSuperior = other.getLimiteSuperior();
        if (this$limiteSuperior == null ? other$limiteSuperior != null : !this$limiteSuperior.equals(other$limiteSuperior)) return false;
        final java.lang.Object this$dadosExtras = this.getDadosExtras();
        final java.lang.Object other$dadosExtras = other.getDadosExtras();
        if (this$dadosExtras == null ? other$dadosExtras != null : !this$dadosExtras.equals(other$dadosExtras)) return false;
        final java.lang.Object this$variacaoPercentual = this.getVariacaoPercentual();
        final java.lang.Object other$variacaoPercentual = other.getVariacaoPercentual();
        if (this$variacaoPercentual == null ? other$variacaoPercentual != null : !this$variacaoPercentual.equals(other$variacaoPercentual)) return false;
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
        return other instanceof MetricaDTO;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final java.lang.Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final java.lang.Object $dentroLimites = this.getDentroLimites();
        result = result * PRIME + ($dentroLimites == null ? 43 : $dentroLimites.hashCode());
        final java.lang.Object $metaAtingida = this.getMetaAtingida();
        result = result * PRIME + ($metaAtingida == null ? 43 : $metaAtingida.hashCode());
        final java.lang.Object $nome = this.getNome();
        result = result * PRIME + ($nome == null ? 43 : $nome.hashCode());
        final java.lang.Object $descricao = this.getDescricao();
        result = result * PRIME + ($descricao == null ? 43 : $descricao.hashCode());
        final java.lang.Object $valor = this.getValor();
        result = result * PRIME + ($valor == null ? 43 : $valor.hashCode());
        final java.lang.Object $valorAnterior = this.getValorAnterior();
        result = result * PRIME + ($valorAnterior == null ? 43 : $valorAnterior.hashCode());
        final java.lang.Object $dataMedicao = this.getDataMedicao();
        result = result * PRIME + ($dataMedicao == null ? 43 : $dataMedicao.hashCode());
        final java.lang.Object $tipoMetrica = this.getTipoMetrica();
        result = result * PRIME + ($tipoMetrica == null ? 43 : $tipoMetrica.hashCode());
        final java.lang.Object $categoria = this.getCategoria();
        result = result * PRIME + ($categoria == null ? 43 : $categoria.hashCode());
        final java.lang.Object $unidadeMedida = this.getUnidadeMedida();
        result = result * PRIME + ($unidadeMedida == null ? 43 : $unidadeMedida.hashCode());
        final java.lang.Object $tendencia = this.getTendencia();
        result = result * PRIME + ($tendencia == null ? 43 : $tendencia.hashCode());
        final java.lang.Object $meta = this.getMeta();
        result = result * PRIME + ($meta == null ? 43 : $meta.hashCode());
        final java.lang.Object $limiteInferior = this.getLimiteInferior();
        result = result * PRIME + ($limiteInferior == null ? 43 : $limiteInferior.hashCode());
        final java.lang.Object $limiteSuperior = this.getLimiteSuperior();
        result = result * PRIME + ($limiteSuperior == null ? 43 : $limiteSuperior.hashCode());
        final java.lang.Object $dadosExtras = this.getDadosExtras();
        result = result * PRIME + ($dadosExtras == null ? 43 : $dadosExtras.hashCode());
        final java.lang.Object $variacaoPercentual = this.getVariacaoPercentual();
        result = result * PRIME + ($variacaoPercentual == null ? 43 : $variacaoPercentual.hashCode());
        final java.lang.Object $dataCriacao = this.getDataCriacao();
        result = result * PRIME + ($dataCriacao == null ? 43 : $dataCriacao.hashCode());
        final java.lang.Object $dataAtualizacao = this.getDataAtualizacao();
        result = result * PRIME + ($dataAtualizacao == null ? 43 : $dataAtualizacao.hashCode());
        return result;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
        return "MetricaDTO(id=" + this.getId() + ", nome=" + this.getNome() + ", descricao=" + this.getDescricao() + ", valor=" + this.getValor() + ", valorAnterior=" + this.getValorAnterior() + ", dataMedicao=" + this.getDataMedicao() + ", tipoMetrica=" + this.getTipoMetrica() + ", categoria=" + this.getCategoria() + ", unidadeMedida=" + this.getUnidadeMedida() + ", tendencia=" + this.getTendencia() + ", meta=" + this.getMeta() + ", limiteInferior=" + this.getLimiteInferior() + ", limiteSuperior=" + this.getLimiteSuperior() + ", dadosExtras=" + this.getDadosExtras() + ", variacaoPercentual=" + this.getVariacaoPercentual() + ", dentroLimites=" + this.getDentroLimites() + ", metaAtingida=" + this.getMetaAtingida() + ", dataCriacao=" + this.getDataCriacao() + ", dataAtualizacao=" + this.getDataAtualizacao() + ")";
    }

    @java.lang.SuppressWarnings("all")
    public MetricaDTO() {
    }

    /**
     * Creates a new {@code MetricaDTO} instance.
     *
     * @param id ID da métrica.
     * @param nome Nome descritivo da métrica.
     * @param descricao Descrição detalhada da métrica.
     * @param valor Valor atual medido.
     * @param valorAnterior Valor medido anteriormente.
     * @param dataMedicao Data e hora da medição.
     * @param tipoMetrica Tipo da métrica (ex: FINANCEIRA, OPERACIONAL).
     * @param categoria Categoria da métrica.
     * @param unidadeMedida Unidade de medida (ex: R$, %, ms).
     * @param tendencia Tendência da métrica (ex: ALTA, BAIXA, ESTAVEL).
     * @param meta Meta definida para a métrica.
     * @param limiteInferior Limite inferior aceitável.
     * @param limiteSuperior Limite superior aceitável.
     * @param dadosExtras Dados extras em formato JSON.
     * @param variacaoPercentual Variação percentual entre a última e a penúltima medida.
     * @param dentroLimites Indica se a medida está dentro dos limites aceitáveis.
     * @param metaAtingida Indica se a meta definida foi atingida.
     * @param dataCriacao Data de criação do registro.
     * @param dataAtualizacao Data da última atualização.
     */
    @java.lang.SuppressWarnings("all")
    public MetricaDTO(final Long id, final String nome, final String descricao, final BigDecimal valor, final BigDecimal valorAnterior, final LocalDateTime dataMedicao, final Metrica.TipoMetrica tipoMetrica, final Metrica.CategoriaMetrica categoria, final String unidadeMedida, final String tendencia, final BigDecimal meta, final BigDecimal limiteInferior, final BigDecimal limiteSuperior, final String dadosExtras, final BigDecimal variacaoPercentual, final Boolean dentroLimites, final Boolean metaAtingida, final String dataCriacao, final String dataAtualizacao) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.valor = valor;
        this.valorAnterior = valorAnterior;
        this.dataMedicao = dataMedicao;
        this.tipoMetrica = tipoMetrica;
        this.categoria = categoria;
        this.unidadeMedida = unidadeMedida;
        this.tendencia = tendencia;
        this.meta = meta;
        this.limiteInferior = limiteInferior;
        this.limiteSuperior = limiteSuperior;
        this.dadosExtras = dadosExtras;
        this.variacaoPercentual = variacaoPercentual;
        this.dentroLimites = dentroLimites;
        this.metaAtingida = metaAtingida;
        this.dataCriacao = dataCriacao;
        this.dataAtualizacao = dataAtualizacao;
    }
}
