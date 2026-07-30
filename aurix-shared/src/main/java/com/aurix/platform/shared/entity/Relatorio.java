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
 * Entidade Relatorio do Aurix.
 * Representa um relatório de analytics.
 */
@Entity
@Table(name = "relatorios", schema = "aurix")
public class Relatorio extends BaseEntity {
    /**
     * Comprimento padrão para campos longos.
     */
    private static final int LENGTH_LONG = 1000;
    /**
     * Nome descritivo do relatório.
     */
    @NotBlank(message = "Nome do relatório é obrigatório")
    @Column(nullable = false)
    private String nome;
    /**
     * Detalhamento do conteúdo abordado.
     */
    @Column(length = LENGTH_LONG)
    private String descricao;
    /**
     * Tipo técnico da estrutura do relatório.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_relatorio", nullable = false)
    private TipoRelatorio tipoRelatorio;
    /**
     * Categoria funcional do relatório (FINANCEIRO, etc.).
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "categoria", nullable = false)
    private CategoriaRelatorio categoria;
    /**
     * Data e hora precisa em que os dados foram consolidados.
     */
    @Column(name = "data_geracao", nullable = false)
    private LocalDateTime dataGeracao = LocalDateTime.now();
    /**
     * Início do intervalo temporal coberto pelos dados.
     */
    @Column(name = "data_periodo_inicio")
    private LocalDateTime dataPeriodoInicio;
    /**
     * Término do intervalo temporal coberto pelos dados.
     */
    @Column(name = "data_periodo_fim")
    private LocalDateTime dataPeriodoFim;
    /**
     * Status atual do processo de geração do arquivo.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private StatusRelatorio status = StatusRelatorio.GERANDO;
    /**
     * Localização física ou URI do arquivo gerado.
     */
    @Column(name = "caminho_arquivo")
    private String caminhoArquivo;
    /**
     * Extensão técnica do arquivo (PDF, XLSX, etc.).
     */
    @Column(name = "formato_arquivo")
    private String formatoArquivo;
    /**
     * Tamanho do arquivo em bytes.
     */
    @Column(name = "tamanho_arquivo")
    private Long tamanhoArquivo;
    /**
     * Filtros e parâmetros utilizados na geração (JSON).
     */
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "parametros", columnDefinition = "jsonb")
    private String parametros;
    /**
     * Conteúdo consolidado em formato JSON para processamento.
     */
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "dados_relatorio", columnDefinition = "jsonb")
    private String dadosRelatorio;
    /**
     * Nome do usuário ou identificador do processo que solicitou.
     */
    @Column(name = "usuario_geracao")
    private String usuarioGeracao;
    /**
     * Configurações de periodicidade de geração (JSON).
     */
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "agendamento", columnDefinition = "jsonb")
    private String agendamento;

    /**
     * Verifica se o relatório está pronto.
     * 
     * @return true se pronto, false caso contrário.
     */
    public boolean isPronto() {
        return status == StatusRelatorio.PRONTO;
    }

    /**
     * Verifica se o relatório falhou.
     * 
     * @return true se falhou, false caso contrário.
     */
    public boolean isFalhou() {
        return status == StatusRelatorio.FALHOU;
    }

    /**
     * Verifica se o relatório está sendo gerado.
     * 
     * @return true se gerando, false caso contrário.
     */
    public boolean isGerando() {
        return status == StatusRelatorio.GERANDO;
    }


    /**
     * Enum para tipo de relatório.
     */
    public enum TipoRelatorio {
        /**
         * Dashboard.
         */
        DASHBOARD("Dashboard"), /**
         * Tabular.
         */
        TABULAR("Tabular"), /**
         * Gráfico.
         */
        GRAFICO("Gráfico"), /**
         * Resumo.
         */
        RESUMO("Resumo"), /**
         * Detalhado.
         */
        DETALHADO("Detalhado"), /**
         * Comparativo.
         */
        COMPARATIVO("Comparativo"), /**
         * Tendência.
         */
        TENDENCIA("Tendência");
        /**
         * Descrição do tipo.
         */
        private final String descricao;

        TipoRelatorio(final String desc) {
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
     * Enum para categoria do relatório.
     */
    public enum CategoriaRelatorio {
        /**
         * Financeiro.
         */
        FINANCEIRO("Financeiro"), /**
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
        QUALIDADE("Qualidade"), /**
         * Auditoria.
         */
        AUDITORIA("Auditoria");
        /**
         * Descrição da categoria.
         */
        private final String descricao;

        CategoriaRelatorio(final String desc) {
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
     * Enum para status do relatório.
     */
    public enum StatusRelatorio {
        /**
         * Gerando.
         */
        GERANDO("Gerando"), /**
         * Pronto.
         */
        PRONTO("Pronto"), /**
         * Falhou.
         */
        FALHOU("Falhou"), /**
         * Expirado.
         */
        EXPIRADO("Expirado");
        /**
         * Descrição do status.
         */
        private final String descricao;

        StatusRelatorio(final String desc) {
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
     * Nome descritivo do relatório.
     */
    @java.lang.SuppressWarnings("all")
    public String getNome() {
        return this.nome;
    }

    /**
     * Detalhamento do conteúdo abordado.
     */
    @java.lang.SuppressWarnings("all")
    public String getDescricao() {
        return this.descricao;
    }

    /**
     * Tipo técnico da estrutura do relatório.
     */
    @java.lang.SuppressWarnings("all")
    public TipoRelatorio getTipoRelatorio() {
        return this.tipoRelatorio;
    }

    /**
     * Categoria funcional do relatório (FINANCEIRO, etc.).
     */
    @java.lang.SuppressWarnings("all")
    public CategoriaRelatorio getCategoria() {
        return this.categoria;
    }

    /**
     * Data e hora precisa em que os dados foram consolidados.
     */
    @java.lang.SuppressWarnings("all")
    public LocalDateTime getDataGeracao() {
        return this.dataGeracao;
    }

    /**
     * Início do intervalo temporal coberto pelos dados.
     */
    @java.lang.SuppressWarnings("all")
    public LocalDateTime getDataPeriodoInicio() {
        return this.dataPeriodoInicio;
    }

    /**
     * Término do intervalo temporal coberto pelos dados.
     */
    @java.lang.SuppressWarnings("all")
    public LocalDateTime getDataPeriodoFim() {
        return this.dataPeriodoFim;
    }

    /**
     * Status atual do processo de geração do arquivo.
     */
    @java.lang.SuppressWarnings("all")
    public StatusRelatorio getStatus() {
        return this.status;
    }

    /**
     * Localização física ou URI do arquivo gerado.
     */
    @java.lang.SuppressWarnings("all")
    public String getCaminhoArquivo() {
        return this.caminhoArquivo;
    }

    /**
     * Extensão técnica do arquivo (PDF, XLSX, etc.).
     */
    @java.lang.SuppressWarnings("all")
    public String getFormatoArquivo() {
        return this.formatoArquivo;
    }

    /**
     * Tamanho do arquivo em bytes.
     */
    @java.lang.SuppressWarnings("all")
    public Long getTamanhoArquivo() {
        return this.tamanhoArquivo;
    }

    /**
     * Filtros e parâmetros utilizados na geração (JSON).
     */
    @java.lang.SuppressWarnings("all")
    public String getParametros() {
        return this.parametros;
    }

    /**
     * Conteúdo consolidado em formato JSON para processamento.
     */
    @java.lang.SuppressWarnings("all")
    public String getDadosRelatorio() {
        return this.dadosRelatorio;
    }

    /**
     * Nome do usuário ou identificador do processo que solicitou.
     */
    @java.lang.SuppressWarnings("all")
    public String getUsuarioGeracao() {
        return this.usuarioGeracao;
    }

    /**
     * Configurações de periodicidade de geração (JSON).
     */
    @java.lang.SuppressWarnings("all")
    public String getAgendamento() {
        return this.agendamento;
    }

    /**
     * Nome descritivo do relatório.
     */
    @java.lang.SuppressWarnings("all")
    public void setNome(final String nome) {
        this.nome = nome;
    }

    /**
     * Detalhamento do conteúdo abordado.
     */
    @java.lang.SuppressWarnings("all")
    public void setDescricao(final String descricao) {
        this.descricao = descricao;
    }

    /**
     * Tipo técnico da estrutura do relatório.
     */
    @java.lang.SuppressWarnings("all")
    public void setTipoRelatorio(final TipoRelatorio tipoRelatorio) {
        this.tipoRelatorio = tipoRelatorio;
    }

    /**
     * Categoria funcional do relatório (FINANCEIRO, etc.).
     */
    @java.lang.SuppressWarnings("all")
    public void setCategoria(final CategoriaRelatorio categoria) {
        this.categoria = categoria;
    }

    /**
     * Data e hora precisa em que os dados foram consolidados.
     */
    @java.lang.SuppressWarnings("all")
    public void setDataGeracao(final LocalDateTime dataGeracao) {
        this.dataGeracao = dataGeracao;
    }

    /**
     * Início do intervalo temporal coberto pelos dados.
     */
    @java.lang.SuppressWarnings("all")
    public void setDataPeriodoInicio(final LocalDateTime dataPeriodoInicio) {
        this.dataPeriodoInicio = dataPeriodoInicio;
    }

    /**
     * Término do intervalo temporal coberto pelos dados.
     */
    @java.lang.SuppressWarnings("all")
    public void setDataPeriodoFim(final LocalDateTime dataPeriodoFim) {
        this.dataPeriodoFim = dataPeriodoFim;
    }

    /**
     * Status atual do processo de geração do arquivo.
     */
    @java.lang.SuppressWarnings("all")
    public void setStatus(final StatusRelatorio status) {
        this.status = status;
    }

    /**
     * Localização física ou URI do arquivo gerado.
     */
    @java.lang.SuppressWarnings("all")
    public void setCaminhoArquivo(final String caminhoArquivo) {
        this.caminhoArquivo = caminhoArquivo;
    }

    /**
     * Extensão técnica do arquivo (PDF, XLSX, etc.).
     */
    @java.lang.SuppressWarnings("all")
    public void setFormatoArquivo(final String formatoArquivo) {
        this.formatoArquivo = formatoArquivo;
    }

    /**
     * Tamanho do arquivo em bytes.
     */
    @java.lang.SuppressWarnings("all")
    public void setTamanhoArquivo(final Long tamanhoArquivo) {
        this.tamanhoArquivo = tamanhoArquivo;
    }

    /**
     * Filtros e parâmetros utilizados na geração (JSON).
     */
    @java.lang.SuppressWarnings("all")
    public void setParametros(final String parametros) {
        this.parametros = parametros;
    }

    /**
     * Conteúdo consolidado em formato JSON para processamento.
     */
    @java.lang.SuppressWarnings("all")
    public void setDadosRelatorio(final String dadosRelatorio) {
        this.dadosRelatorio = dadosRelatorio;
    }

    /**
     * Nome do usuário ou identificador do processo que solicitou.
     */
    @java.lang.SuppressWarnings("all")
    public void setUsuarioGeracao(final String usuarioGeracao) {
        this.usuarioGeracao = usuarioGeracao;
    }

    /**
     * Configurações de periodicidade de geração (JSON).
     */
    @java.lang.SuppressWarnings("all")
    public void setAgendamento(final String agendamento) {
        this.agendamento = agendamento;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
        return "Relatorio(nome=" + this.getNome() + ", descricao=" + this.getDescricao() + ", tipoRelatorio=" + this.getTipoRelatorio() + ", categoria=" + this.getCategoria() + ", dataGeracao=" + this.getDataGeracao() + ", dataPeriodoInicio=" + this.getDataPeriodoInicio() + ", dataPeriodoFim=" + this.getDataPeriodoFim() + ", status=" + this.getStatus() + ", caminhoArquivo=" + this.getCaminhoArquivo() + ", formatoArquivo=" + this.getFormatoArquivo() + ", tamanhoArquivo=" + this.getTamanhoArquivo() + ", parametros=" + this.getParametros() + ", dadosRelatorio=" + this.getDadosRelatorio() + ", usuarioGeracao=" + this.getUsuarioGeracao() + ", agendamento=" + this.getAgendamento() + ")";
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public boolean equals(final java.lang.Object o) {
        if (o == this) return true;
        if (!(o instanceof Relatorio)) return false;
        final Relatorio other = (Relatorio) o;
        if (!other.canEqual((java.lang.Object) this)) return false;
        if (!super.equals(o)) return false;
        final java.lang.Object this$tamanhoArquivo = this.getTamanhoArquivo();
        final java.lang.Object other$tamanhoArquivo = other.getTamanhoArquivo();
        if (this$tamanhoArquivo == null ? other$tamanhoArquivo != null : !this$tamanhoArquivo.equals(other$tamanhoArquivo)) return false;
        final java.lang.Object this$nome = this.getNome();
        final java.lang.Object other$nome = other.getNome();
        if (this$nome == null ? other$nome != null : !this$nome.equals(other$nome)) return false;
        final java.lang.Object this$descricao = this.getDescricao();
        final java.lang.Object other$descricao = other.getDescricao();
        if (this$descricao == null ? other$descricao != null : !this$descricao.equals(other$descricao)) return false;
        final java.lang.Object this$tipoRelatorio = this.getTipoRelatorio();
        final java.lang.Object other$tipoRelatorio = other.getTipoRelatorio();
        if (this$tipoRelatorio == null ? other$tipoRelatorio != null : !this$tipoRelatorio.equals(other$tipoRelatorio)) return false;
        final java.lang.Object this$categoria = this.getCategoria();
        final java.lang.Object other$categoria = other.getCategoria();
        if (this$categoria == null ? other$categoria != null : !this$categoria.equals(other$categoria)) return false;
        final java.lang.Object this$dataGeracao = this.getDataGeracao();
        final java.lang.Object other$dataGeracao = other.getDataGeracao();
        if (this$dataGeracao == null ? other$dataGeracao != null : !this$dataGeracao.equals(other$dataGeracao)) return false;
        final java.lang.Object this$dataPeriodoInicio = this.getDataPeriodoInicio();
        final java.lang.Object other$dataPeriodoInicio = other.getDataPeriodoInicio();
        if (this$dataPeriodoInicio == null ? other$dataPeriodoInicio != null : !this$dataPeriodoInicio.equals(other$dataPeriodoInicio)) return false;
        final java.lang.Object this$dataPeriodoFim = this.getDataPeriodoFim();
        final java.lang.Object other$dataPeriodoFim = other.getDataPeriodoFim();
        if (this$dataPeriodoFim == null ? other$dataPeriodoFim != null : !this$dataPeriodoFim.equals(other$dataPeriodoFim)) return false;
        final java.lang.Object this$status = this.getStatus();
        final java.lang.Object other$status = other.getStatus();
        if (this$status == null ? other$status != null : !this$status.equals(other$status)) return false;
        final java.lang.Object this$caminhoArquivo = this.getCaminhoArquivo();
        final java.lang.Object other$caminhoArquivo = other.getCaminhoArquivo();
        if (this$caminhoArquivo == null ? other$caminhoArquivo != null : !this$caminhoArquivo.equals(other$caminhoArquivo)) return false;
        final java.lang.Object this$formatoArquivo = this.getFormatoArquivo();
        final java.lang.Object other$formatoArquivo = other.getFormatoArquivo();
        if (this$formatoArquivo == null ? other$formatoArquivo != null : !this$formatoArquivo.equals(other$formatoArquivo)) return false;
        final java.lang.Object this$parametros = this.getParametros();
        final java.lang.Object other$parametros = other.getParametros();
        if (this$parametros == null ? other$parametros != null : !this$parametros.equals(other$parametros)) return false;
        final java.lang.Object this$dadosRelatorio = this.getDadosRelatorio();
        final java.lang.Object other$dadosRelatorio = other.getDadosRelatorio();
        if (this$dadosRelatorio == null ? other$dadosRelatorio != null : !this$dadosRelatorio.equals(other$dadosRelatorio)) return false;
        final java.lang.Object this$usuarioGeracao = this.getUsuarioGeracao();
        final java.lang.Object other$usuarioGeracao = other.getUsuarioGeracao();
        if (this$usuarioGeracao == null ? other$usuarioGeracao != null : !this$usuarioGeracao.equals(other$usuarioGeracao)) return false;
        final java.lang.Object this$agendamento = this.getAgendamento();
        final java.lang.Object other$agendamento = other.getAgendamento();
        if (this$agendamento == null ? other$agendamento != null : !this$agendamento.equals(other$agendamento)) return false;
        return true;
    }

    @java.lang.SuppressWarnings("all")
    protected boolean canEqual(final java.lang.Object other) {
        return other instanceof Relatorio;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public int hashCode() {
        final int PRIME = 59;
        int result = super.hashCode();
        final java.lang.Object $tamanhoArquivo = this.getTamanhoArquivo();
        result = result * PRIME + ($tamanhoArquivo == null ? 43 : $tamanhoArquivo.hashCode());
        final java.lang.Object $nome = this.getNome();
        result = result * PRIME + ($nome == null ? 43 : $nome.hashCode());
        final java.lang.Object $descricao = this.getDescricao();
        result = result * PRIME + ($descricao == null ? 43 : $descricao.hashCode());
        final java.lang.Object $tipoRelatorio = this.getTipoRelatorio();
        result = result * PRIME + ($tipoRelatorio == null ? 43 : $tipoRelatorio.hashCode());
        final java.lang.Object $categoria = this.getCategoria();
        result = result * PRIME + ($categoria == null ? 43 : $categoria.hashCode());
        final java.lang.Object $dataGeracao = this.getDataGeracao();
        result = result * PRIME + ($dataGeracao == null ? 43 : $dataGeracao.hashCode());
        final java.lang.Object $dataPeriodoInicio = this.getDataPeriodoInicio();
        result = result * PRIME + ($dataPeriodoInicio == null ? 43 : $dataPeriodoInicio.hashCode());
        final java.lang.Object $dataPeriodoFim = this.getDataPeriodoFim();
        result = result * PRIME + ($dataPeriodoFim == null ? 43 : $dataPeriodoFim.hashCode());
        final java.lang.Object $status = this.getStatus();
        result = result * PRIME + ($status == null ? 43 : $status.hashCode());
        final java.lang.Object $caminhoArquivo = this.getCaminhoArquivo();
        result = result * PRIME + ($caminhoArquivo == null ? 43 : $caminhoArquivo.hashCode());
        final java.lang.Object $formatoArquivo = this.getFormatoArquivo();
        result = result * PRIME + ($formatoArquivo == null ? 43 : $formatoArquivo.hashCode());
        final java.lang.Object $parametros = this.getParametros();
        result = result * PRIME + ($parametros == null ? 43 : $parametros.hashCode());
        final java.lang.Object $dadosRelatorio = this.getDadosRelatorio();
        result = result * PRIME + ($dadosRelatorio == null ? 43 : $dadosRelatorio.hashCode());
        final java.lang.Object $usuarioGeracao = this.getUsuarioGeracao();
        result = result * PRIME + ($usuarioGeracao == null ? 43 : $usuarioGeracao.hashCode());
        final java.lang.Object $agendamento = this.getAgendamento();
        result = result * PRIME + ($agendamento == null ? 43 : $agendamento.hashCode());
        return result;
    }

    @java.lang.SuppressWarnings("all")
    public Relatorio() {
    }

    /**
     * Creates a new {@code Relatorio} instance.
     *
     * @param nome Nome descritivo do relatório.
     * @param descricao Detalhamento do conteúdo abordado.
     * @param tipoRelatorio Tipo técnico da estrutura do relatório.
     * @param categoria Categoria funcional do relatório (FINANCEIRO, etc.).
     * @param dataGeracao Data e hora precisa em que os dados foram consolidados.
     * @param dataPeriodoInicio Início do intervalo temporal coberto pelos dados.
     * @param dataPeriodoFim Término do intervalo temporal coberto pelos dados.
     * @param status Status atual do processo de geração do arquivo.
     * @param caminhoArquivo Localização física ou URI do arquivo gerado.
     * @param formatoArquivo Extensão técnica do arquivo (PDF, XLSX, etc.).
     * @param tamanhoArquivo Tamanho do arquivo em bytes.
     * @param parametros Filtros e parâmetros utilizados na geração (JSON).
     * @param dadosRelatorio Conteúdo consolidado em formato JSON para processamento.
     * @param usuarioGeracao Nome do usuário ou identificador do processo que solicitou.
     * @param agendamento Configurações de periodicidade de geração (JSON).
     */
    @java.lang.SuppressWarnings("all")
    public Relatorio(final String nome, final String descricao, final TipoRelatorio tipoRelatorio, final CategoriaRelatorio categoria, final LocalDateTime dataGeracao, final LocalDateTime dataPeriodoInicio, final LocalDateTime dataPeriodoFim, final StatusRelatorio status, final String caminhoArquivo, final String formatoArquivo, final Long tamanhoArquivo, final String parametros, final String dadosRelatorio, final String usuarioGeracao, final String agendamento) {
        this.nome = nome;
        this.descricao = descricao;
        this.tipoRelatorio = tipoRelatorio;
        this.categoria = categoria;
        this.dataGeracao = dataGeracao;
        this.dataPeriodoInicio = dataPeriodoInicio;
        this.dataPeriodoFim = dataPeriodoFim;
        this.status = status;
        this.caminhoArquivo = caminhoArquivo;
        this.formatoArquivo = formatoArquivo;
        this.tamanhoArquivo = tamanhoArquivo;
        this.parametros = parametros;
        this.dadosRelatorio = dadosRelatorio;
        this.usuarioGeracao = usuarioGeracao;
        this.agendamento = agendamento;
    }
}
