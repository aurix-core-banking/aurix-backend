package com.aurix.platform.shared.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

/**
 * Entidade Metrica do Aurix.
 * Representa uma métrica de negócio.
 */
@Entity
@Table(name = "metricas", schema = "aurix")
public class Metrica extends BaseEntity {
    /**
     * Comprimento padrão para descrições.
     */
    private static final int DESCRIPTION_MAX_LENGTH = 500;
    /**
     * Precisão decimal padrão para valores de métricas.
     */
    private static final int DECIMAL_PRECISION = 15;
    /**
     * Escala decimal padrão para valores de métricas.
     */
    private static final int DECIMAL_SCALE = 2;
    /**
     * Valor fixo para cem (percentual).
     */
    private static final int ONE_HUNDRED = 100;
    /**
     * Escala para divisão de variação percentual.
     */
    private static final int VARIATION_SCALE = 4;
    /**
     * Nome descritivo da métrica.
     */
    @NotBlank(message = "Nome da métrica é obrigatório")
    @Column(nullable = false)
    private String nome;
    /**
     * Detalhamento do que a métrica mede.
     */
    @Column(length = DESCRIPTION_MAX_LENGTH)
    private String descricao;
    /**
     * Valor numérico capturado na medição atual.
     */
    @NotNull(message = "Valor da métrica é obrigatório")
    @Column(nullable = false, precision = DECIMAL_PRECISION, scale = DECIMAL_SCALE)
    private BigDecimal valor;
    /**
     * Valor numérico capturado na medição anterior.
     */
    @Column(name = "valor_anterior", precision = DECIMAL_PRECISION, scale = DECIMAL_SCALE)
    private BigDecimal valorAnterior;
    /**
     * Data e hora em que a medição foi realizada.
     */
    @Column(name = "data_medicao", nullable = false)
    private LocalDateTime dataMedicao = LocalDateTime.now();
    /**
     * Tipo técnico da unidade de medida.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_metrica", nullable = false)
    private TipoMetrica tipoMetrica;
    /**
     * Categoria funcional da métrica (FINANCEIRA, etc.).
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "categoria", nullable = false)
    private CategoriaMetrica categoria;
    /**
     * Nome legível da unidade de medida (ex: BRL, %).
     */
    @Column(name = "unidade_medida")
    private String unidadeMedida;
    /**
     * Indicador qualitativo de tendência (CRESCENTE, etc.).
     */
    @Column(name = "tendencia")
    private String tendencia;
    /**
     * Valor alvo definido como meta.
     */
    @Column(name = "meta", precision = DECIMAL_PRECISION, scale = DECIMAL_SCALE)
    private BigDecimal meta;
    /**
     * Piso mínimo aceitável para o valor.
     */
    @Column(name = "limite_inferior", precision = DECIMAL_PRECISION, scale = DECIMAL_SCALE)
    private BigDecimal limiteInferior;
    /**
     * Teto máximo aceitável para o valor.
     */
    @Column(name = "limite_superior", precision = DECIMAL_PRECISION, scale = DECIMAL_SCALE)
    private BigDecimal limiteSuperior;
    /**
     * Metadados suplementares em formato JSON.
     */
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "dados_extras", columnDefinition = "jsonb")
    private String dadosExtras;

    /**
     * Calcula a variação percentual.
     *
     * @return BigDecimal variação.
     */
    public BigDecimal getVariacaoPercentual() {
        if (valorAnterior == null || valorAnterior.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO;
        }
        return valor.subtract(valorAnterior).divide(valorAnterior, VARIATION_SCALE, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(ONE_HUNDRED));
    }

    /**
     * Verifica se a métrica está dentro dos limites.
     *
     * @return true se dentro dos limites, false caso contrário.
     */
    public boolean isDentroLimites() {
        if (limiteInferior != null && valor.compareTo(limiteInferior) < 0) {
            return false;
        }
        if (limiteSuperior != null && valor.compareTo(limiteSuperior) > 0) {
            return false;
        }
        return true;
    }

    /**
     * Verifica se atingiu a meta.
     *
     * @return true se meta atingida, false caso contrário.
     */
    public boolean isMetaAtingida() {
        return meta != null && valor.compareTo(meta) >= 0;
    }


    /**
     * Enum para tipo de métrica.
     */
    public enum TipoMetrica {
        /**
         * Contador.
         */
        CONTADOR("Contador"), /**
         * Valor Monetário.
         */
        VALOR_MONETARIO("Valor Monetário"), /**
         * Percentual.
         */
        PERCENTUAL("Percentual"), /**
         * Tempo.
         */
        TEMPO("Tempo"), /**
         * Quantidade.
         */
        QUANTIDADE("Quantidade");
        /**
         * Descrição do tipo.
         */
        private final String descricao;

        TipoMetrica(final String desc) {
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
     * Enum para categoria da métrica.
     */
    public enum CategoriaMetrica {
        /**
         * Financeira.
         */
        FINANCEIRA("Financeira"), /**
         * Operacional.
         */
        OPERACIONAL("Operacional"), /**
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

        CategoriaMetrica(final String desc) {
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
     * Nome descritivo da métrica.
     */
    @java.lang.SuppressWarnings("all")
    public String getNome() {
        return this.nome;
    }

    /**
     * Detalhamento do que a métrica mede.
     */
    @java.lang.SuppressWarnings("all")
    public String getDescricao() {
        return this.descricao;
    }

    /**
     * Valor numérico capturado na medição atual.
     */
    @java.lang.SuppressWarnings("all")
    public BigDecimal getValor() {
        return this.valor;
    }

    /**
     * Valor numérico capturado na medição anterior.
     */
    @java.lang.SuppressWarnings("all")
    public BigDecimal getValorAnterior() {
        return this.valorAnterior;
    }

    /**
     * Data e hora em que a medição foi realizada.
     */
    @java.lang.SuppressWarnings("all")
    public LocalDateTime getDataMedicao() {
        return this.dataMedicao;
    }

    /**
     * Tipo técnico da unidade de medida.
     */
    @java.lang.SuppressWarnings("all")
    public TipoMetrica getTipoMetrica() {
        return this.tipoMetrica;
    }

    /**
     * Categoria funcional da métrica (FINANCEIRA, etc.).
     */
    @java.lang.SuppressWarnings("all")
    public CategoriaMetrica getCategoria() {
        return this.categoria;
    }

    /**
     * Nome legível da unidade de medida (ex: BRL, %).
     */
    @java.lang.SuppressWarnings("all")
    public String getUnidadeMedida() {
        return this.unidadeMedida;
    }

    /**
     * Indicador qualitativo de tendência (CRESCENTE, etc.).
     */
    @java.lang.SuppressWarnings("all")
    public String getTendencia() {
        return this.tendencia;
    }

    /**
     * Valor alvo definido como meta.
     */
    @java.lang.SuppressWarnings("all")
    public BigDecimal getMeta() {
        return this.meta;
    }

    /**
     * Piso mínimo aceitável para o valor.
     */
    @java.lang.SuppressWarnings("all")
    public BigDecimal getLimiteInferior() {
        return this.limiteInferior;
    }

    /**
     * Teto máximo aceitável para o valor.
     */
    @java.lang.SuppressWarnings("all")
    public BigDecimal getLimiteSuperior() {
        return this.limiteSuperior;
    }

    /**
     * Metadados suplementares em formato JSON.
     */
    @java.lang.SuppressWarnings("all")
    public String getDadosExtras() {
        return this.dadosExtras;
    }

    /**
     * Nome descritivo da métrica.
     */
    @java.lang.SuppressWarnings("all")
    public void setNome(final String nome) {
        this.nome = nome;
    }

    /**
     * Detalhamento do que a métrica mede.
     */
    @java.lang.SuppressWarnings("all")
    public void setDescricao(final String descricao) {
        this.descricao = descricao;
    }

    /**
     * Valor numérico capturado na medição atual.
     */
    @java.lang.SuppressWarnings("all")
    public void setValor(final BigDecimal valor) {
        this.valor = valor;
    }

    /**
     * Valor numérico capturado na medição anterior.
     */
    @java.lang.SuppressWarnings("all")
    public void setValorAnterior(final BigDecimal valorAnterior) {
        this.valorAnterior = valorAnterior;
    }

    /**
     * Data e hora em que a medição foi realizada.
     */
    @java.lang.SuppressWarnings("all")
    public void setDataMedicao(final LocalDateTime dataMedicao) {
        this.dataMedicao = dataMedicao;
    }

    /**
     * Tipo técnico da unidade de medida.
     */
    @java.lang.SuppressWarnings("all")
    public void setTipoMetrica(final TipoMetrica tipoMetrica) {
        this.tipoMetrica = tipoMetrica;
    }

    /**
     * Categoria funcional da métrica (FINANCEIRA, etc.).
     */
    @java.lang.SuppressWarnings("all")
    public void setCategoria(final CategoriaMetrica categoria) {
        this.categoria = categoria;
    }

    /**
     * Nome legível da unidade de medida (ex: BRL, %).
     */
    @java.lang.SuppressWarnings("all")
    public void setUnidadeMedida(final String unidadeMedida) {
        this.unidadeMedida = unidadeMedida;
    }

    /**
     * Indicador qualitativo de tendência (CRESCENTE, etc.).
     */
    @java.lang.SuppressWarnings("all")
    public void setTendencia(final String tendencia) {
        this.tendencia = tendencia;
    }

    /**
     * Valor alvo definido como meta.
     */
    @java.lang.SuppressWarnings("all")
    public void setMeta(final BigDecimal meta) {
        this.meta = meta;
    }

    /**
     * Piso mínimo aceitável para o valor.
     */
    @java.lang.SuppressWarnings("all")
    public void setLimiteInferior(final BigDecimal limiteInferior) {
        this.limiteInferior = limiteInferior;
    }

    /**
     * Teto máximo aceitável para o valor.
     */
    @java.lang.SuppressWarnings("all")
    public void setLimiteSuperior(final BigDecimal limiteSuperior) {
        this.limiteSuperior = limiteSuperior;
    }

    /**
     * Metadados suplementares em formato JSON.
     */
    @java.lang.SuppressWarnings("all")
    public void setDadosExtras(final String dadosExtras) {
        this.dadosExtras = dadosExtras;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
        return "Metrica(nome=" + this.getNome() + ", descricao=" + this.getDescricao() + ", valor=" + this.getValor() + ", valorAnterior=" + this.getValorAnterior() + ", dataMedicao=" + this.getDataMedicao() + ", tipoMetrica=" + this.getTipoMetrica() + ", categoria=" + this.getCategoria() + ", unidadeMedida=" + this.getUnidadeMedida() + ", tendencia=" + this.getTendencia() + ", meta=" + this.getMeta() + ", limiteInferior=" + this.getLimiteInferior() + ", limiteSuperior=" + this.getLimiteSuperior() + ", dadosExtras=" + this.getDadosExtras() + ")";
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public boolean equals(final java.lang.Object o) {
        if (o == this) return true;
        if (!(o instanceof Metrica)) return false;
        final Metrica other = (Metrica) o;
        if (!other.canEqual((java.lang.Object) this)) return false;
        if (!super.equals(o)) return false;
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
        return true;
    }

    @java.lang.SuppressWarnings("all")
    protected boolean canEqual(final java.lang.Object other) {
        return other instanceof Metrica;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public int hashCode() {
        final int PRIME = 59;
        int result = super.hashCode();
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
        return result;
    }

    @java.lang.SuppressWarnings("all")
    public Metrica() {
    }

    /**
     * Creates a new {@code Metrica} instance.
     *
     * @param nome Nome descritivo da métrica.
     * @param descricao Detalhamento do que a métrica mede.
     * @param valor Valor numérico capturado na medição atual.
     * @param valorAnterior Valor numérico capturado na medição anterior.
     * @param dataMedicao Data e hora em que a medição foi realizada.
     * @param tipoMetrica Tipo técnico da unidade de medida.
     * @param categoria Categoria funcional da métrica (FINANCEIRA, etc.).
     * @param unidadeMedida Nome legível da unidade de medida (ex: BRL, %).
     * @param tendencia Indicador qualitativo de tendência (CRESCENTE, etc.).
     * @param meta Valor alvo definido como meta.
     * @param limiteInferior Piso mínimo aceitável para o valor.
     * @param limiteSuperior Teto máximo aceitável para o valor.
     * @param dadosExtras Metadados suplementares em formato JSON.
     */
    @java.lang.SuppressWarnings("all")
    public Metrica(final String nome, final String descricao, final BigDecimal valor, final BigDecimal valorAnterior, final LocalDateTime dataMedicao, final TipoMetrica tipoMetrica, final CategoriaMetrica categoria, final String unidadeMedida, final String tendencia, final BigDecimal meta, final BigDecimal limiteInferior, final BigDecimal limiteSuperior, final String dadosExtras) {
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
    }
}
