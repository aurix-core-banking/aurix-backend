package com.aurix.platform.shared.dto;

import com.aurix.platform.shared.entity.Relatorio;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * DTO para Relatorio.
 */
public class RelatorioDTO {
    /**
     * ID do relatório.
     */
    private Long id;
    /**
     * Nome descritivo do relatório gerado.
     */
    @NotBlank(message = "Nome do relatório é obrigatório")
    private String nome;
    /**
     * Descrição do conteúdo ou objetivo.
     */
    private String descricao;
    /**
     * Tipo técnico do relatório.
     */
    @NotNull(message = "Tipo do relatório é obrigatório")
    private Relatorio.TipoRelatorio tipoRelatorio;
    /**
     * Categoria funcional do relatório.
     */
    @NotNull(message = "Categoria é obrigatória")
    private Relatorio.CategoriaRelatorio categoria;
    /**
     * Data e hora da geração do arquivo.
     */
    private LocalDateTime dataGeracao;
    /**
     * Início do período abrangido pelos dados.
     */
    private LocalDateTime dataPeriodoInicio;
    /**
     * Fim do período abrangido pelos dados.
     */
    private LocalDateTime dataPeriodoFim;
    /**
     * Status do processamento do relatório.
     */
    private Relatorio.StatusRelatorio status;
    /**
     * Caminho físico ou URL do arquivo gerado.
     */
    private String caminhoArquivo;
    /**
     * Formato do arquivo (PDF, XLSX, CSV).
     */
    private String formatoArquivo;
    /**
     * Tamanho do arquivo em bytes.
     */
    private Long tamanhoArquivo;
    /**
     * Parâmetros utilizados na consulta (JSON).
     */
    private String parametros;
    /**
     * Sumário dos dados extraídos (JSON).
     */
    private String dadosRelatorio;
    /**
     * Usuário que solicitou a geração.
     */
    private String usuarioGeracao;
    /**
     * Configuração de agendamento, se houver.
     */
    private String agendamento;
    /**
     * Indica se o arquivo está pronto para download.
     */
    private Boolean pronto;
    /**
     * Indica se houve falha na geração.
     */
    private Boolean falhou;
    /**
     * Indica se o relatório ainda está sendo gerado.
     */
    private Boolean gerando;
    /**
     * Data de criação do registro.
     */
    private String dataCriacao;
    /**
     * Data da última atualização.
     */
    private String dataAtualizacao;

    /**
     * ID do relatório.
     */
    @java.lang.SuppressWarnings("all")
    public Long getId() {
        return this.id;
    }

    /**
     * Nome descritivo do relatório gerado.
     */
    @java.lang.SuppressWarnings("all")
    public String getNome() {
        return this.nome;
    }

    /**
     * Descrição do conteúdo ou objetivo.
     */
    @java.lang.SuppressWarnings("all")
    public String getDescricao() {
        return this.descricao;
    }

    /**
     * Tipo técnico do relatório.
     */
    @java.lang.SuppressWarnings("all")
    public Relatorio.TipoRelatorio getTipoRelatorio() {
        return this.tipoRelatorio;
    }

    /**
     * Categoria funcional do relatório.
     */
    @java.lang.SuppressWarnings("all")
    public Relatorio.CategoriaRelatorio getCategoria() {
        return this.categoria;
    }

    /**
     * Data e hora da geração do arquivo.
     */
    @java.lang.SuppressWarnings("all")
    public LocalDateTime getDataGeracao() {
        return this.dataGeracao;
    }

    /**
     * Início do período abrangido pelos dados.
     */
    @java.lang.SuppressWarnings("all")
    public LocalDateTime getDataPeriodoInicio() {
        return this.dataPeriodoInicio;
    }

    /**
     * Fim do período abrangido pelos dados.
     */
    @java.lang.SuppressWarnings("all")
    public LocalDateTime getDataPeriodoFim() {
        return this.dataPeriodoFim;
    }

    /**
     * Status do processamento do relatório.
     */
    @java.lang.SuppressWarnings("all")
    public Relatorio.StatusRelatorio getStatus() {
        return this.status;
    }

    /**
     * Caminho físico ou URL do arquivo gerado.
     */
    @java.lang.SuppressWarnings("all")
    public String getCaminhoArquivo() {
        return this.caminhoArquivo;
    }

    /**
     * Formato do arquivo (PDF, XLSX, CSV).
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
     * Parâmetros utilizados na consulta (JSON).
     */
    @java.lang.SuppressWarnings("all")
    public String getParametros() {
        return this.parametros;
    }

    /**
     * Sumário dos dados extraídos (JSON).
     */
    @java.lang.SuppressWarnings("all")
    public String getDadosRelatorio() {
        return this.dadosRelatorio;
    }

    /**
     * Usuário que solicitou a geração.
     */
    @java.lang.SuppressWarnings("all")
    public String getUsuarioGeracao() {
        return this.usuarioGeracao;
    }

    /**
     * Configuração de agendamento, se houver.
     */
    @java.lang.SuppressWarnings("all")
    public String getAgendamento() {
        return this.agendamento;
    }

    /**
     * Indica se o arquivo está pronto para download.
     */
    @java.lang.SuppressWarnings("all")
    public Boolean getPronto() {
        return this.pronto;
    }

    /**
     * Indica se houve falha na geração.
     */
    @java.lang.SuppressWarnings("all")
    public Boolean getFalhou() {
        return this.falhou;
    }

    /**
     * Indica se o relatório ainda está sendo gerado.
     */
    @java.lang.SuppressWarnings("all")
    public Boolean getGerando() {
        return this.gerando;
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
     * ID do relatório.
     */
    @java.lang.SuppressWarnings("all")
    public void setId(final Long id) {
        this.id = id;
    }

    /**
     * Nome descritivo do relatório gerado.
     */
    @java.lang.SuppressWarnings("all")
    public void setNome(final String nome) {
        this.nome = nome;
    }

    /**
     * Descrição do conteúdo ou objetivo.
     */
    @java.lang.SuppressWarnings("all")
    public void setDescricao(final String descricao) {
        this.descricao = descricao;
    }

    /**
     * Tipo técnico do relatório.
     */
    @java.lang.SuppressWarnings("all")
    public void setTipoRelatorio(final Relatorio.TipoRelatorio tipoRelatorio) {
        this.tipoRelatorio = tipoRelatorio;
    }

    /**
     * Categoria funcional do relatório.
     */
    @java.lang.SuppressWarnings("all")
    public void setCategoria(final Relatorio.CategoriaRelatorio categoria) {
        this.categoria = categoria;
    }

    /**
     * Data e hora da geração do arquivo.
     */
    @java.lang.SuppressWarnings("all")
    public void setDataGeracao(final LocalDateTime dataGeracao) {
        this.dataGeracao = dataGeracao;
    }

    /**
     * Início do período abrangido pelos dados.
     */
    @java.lang.SuppressWarnings("all")
    public void setDataPeriodoInicio(final LocalDateTime dataPeriodoInicio) {
        this.dataPeriodoInicio = dataPeriodoInicio;
    }

    /**
     * Fim do período abrangido pelos dados.
     */
    @java.lang.SuppressWarnings("all")
    public void setDataPeriodoFim(final LocalDateTime dataPeriodoFim) {
        this.dataPeriodoFim = dataPeriodoFim;
    }

    /**
     * Status do processamento do relatório.
     */
    @java.lang.SuppressWarnings("all")
    public void setStatus(final Relatorio.StatusRelatorio status) {
        this.status = status;
    }

    /**
     * Caminho físico ou URL do arquivo gerado.
     */
    @java.lang.SuppressWarnings("all")
    public void setCaminhoArquivo(final String caminhoArquivo) {
        this.caminhoArquivo = caminhoArquivo;
    }

    /**
     * Formato do arquivo (PDF, XLSX, CSV).
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
     * Parâmetros utilizados na consulta (JSON).
     */
    @java.lang.SuppressWarnings("all")
    public void setParametros(final String parametros) {
        this.parametros = parametros;
    }

    /**
     * Sumário dos dados extraídos (JSON).
     */
    @java.lang.SuppressWarnings("all")
    public void setDadosRelatorio(final String dadosRelatorio) {
        this.dadosRelatorio = dadosRelatorio;
    }

    /**
     * Usuário que solicitou a geração.
     */
    @java.lang.SuppressWarnings("all")
    public void setUsuarioGeracao(final String usuarioGeracao) {
        this.usuarioGeracao = usuarioGeracao;
    }

    /**
     * Configuração de agendamento, se houver.
     */
    @java.lang.SuppressWarnings("all")
    public void setAgendamento(final String agendamento) {
        this.agendamento = agendamento;
    }

    /**
     * Indica se o arquivo está pronto para download.
     */
    @java.lang.SuppressWarnings("all")
    public void setPronto(final Boolean pronto) {
        this.pronto = pronto;
    }

    /**
     * Indica se houve falha na geração.
     */
    @java.lang.SuppressWarnings("all")
    public void setFalhou(final Boolean falhou) {
        this.falhou = falhou;
    }

    /**
     * Indica se o relatório ainda está sendo gerado.
     */
    @java.lang.SuppressWarnings("all")
    public void setGerando(final Boolean gerando) {
        this.gerando = gerando;
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
        if (!(o instanceof RelatorioDTO)) return false;
        final RelatorioDTO other = (RelatorioDTO) o;
        if (!other.canEqual((java.lang.Object) this)) return false;
        final java.lang.Object this$id = this.getId();
        final java.lang.Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final java.lang.Object this$tamanhoArquivo = this.getTamanhoArquivo();
        final java.lang.Object other$tamanhoArquivo = other.getTamanhoArquivo();
        if (this$tamanhoArquivo == null ? other$tamanhoArquivo != null : !this$tamanhoArquivo.equals(other$tamanhoArquivo)) return false;
        final java.lang.Object this$pronto = this.getPronto();
        final java.lang.Object other$pronto = other.getPronto();
        if (this$pronto == null ? other$pronto != null : !this$pronto.equals(other$pronto)) return false;
        final java.lang.Object this$falhou = this.getFalhou();
        final java.lang.Object other$falhou = other.getFalhou();
        if (this$falhou == null ? other$falhou != null : !this$falhou.equals(other$falhou)) return false;
        final java.lang.Object this$gerando = this.getGerando();
        final java.lang.Object other$gerando = other.getGerando();
        if (this$gerando == null ? other$gerando != null : !this$gerando.equals(other$gerando)) return false;
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
        return other instanceof RelatorioDTO;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final java.lang.Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final java.lang.Object $tamanhoArquivo = this.getTamanhoArquivo();
        result = result * PRIME + ($tamanhoArquivo == null ? 43 : $tamanhoArquivo.hashCode());
        final java.lang.Object $pronto = this.getPronto();
        result = result * PRIME + ($pronto == null ? 43 : $pronto.hashCode());
        final java.lang.Object $falhou = this.getFalhou();
        result = result * PRIME + ($falhou == null ? 43 : $falhou.hashCode());
        final java.lang.Object $gerando = this.getGerando();
        result = result * PRIME + ($gerando == null ? 43 : $gerando.hashCode());
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
        final java.lang.Object $dataCriacao = this.getDataCriacao();
        result = result * PRIME + ($dataCriacao == null ? 43 : $dataCriacao.hashCode());
        final java.lang.Object $dataAtualizacao = this.getDataAtualizacao();
        result = result * PRIME + ($dataAtualizacao == null ? 43 : $dataAtualizacao.hashCode());
        return result;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
        return "RelatorioDTO(id=" + this.getId() + ", nome=" + this.getNome() + ", descricao=" + this.getDescricao() + ", tipoRelatorio=" + this.getTipoRelatorio() + ", categoria=" + this.getCategoria() + ", dataGeracao=" + this.getDataGeracao() + ", dataPeriodoInicio=" + this.getDataPeriodoInicio() + ", dataPeriodoFim=" + this.getDataPeriodoFim() + ", status=" + this.getStatus() + ", caminhoArquivo=" + this.getCaminhoArquivo() + ", formatoArquivo=" + this.getFormatoArquivo() + ", tamanhoArquivo=" + this.getTamanhoArquivo() + ", parametros=" + this.getParametros() + ", dadosRelatorio=" + this.getDadosRelatorio() + ", usuarioGeracao=" + this.getUsuarioGeracao() + ", agendamento=" + this.getAgendamento() + ", pronto=" + this.getPronto() + ", falhou=" + this.getFalhou() + ", gerando=" + this.getGerando() + ", dataCriacao=" + this.getDataCriacao() + ", dataAtualizacao=" + this.getDataAtualizacao() + ")";
    }

    @java.lang.SuppressWarnings("all")
    public RelatorioDTO() {
    }

    /**
     * Creates a new {@code RelatorioDTO} instance.
     *
     * @param id ID do relatório.
     * @param nome Nome descritivo do relatório gerado.
     * @param descricao Descrição do conteúdo ou objetivo.
     * @param tipoRelatorio Tipo técnico do relatório.
     * @param categoria Categoria funcional do relatório.
     * @param dataGeracao Data e hora da geração do arquivo.
     * @param dataPeriodoInicio Início do período abrangido pelos dados.
     * @param dataPeriodoFim Fim do período abrangido pelos dados.
     * @param status Status do processamento do relatório.
     * @param caminhoArquivo Caminho físico ou URL do arquivo gerado.
     * @param formatoArquivo Formato do arquivo (PDF, XLSX, CSV).
     * @param tamanhoArquivo Tamanho do arquivo em bytes.
     * @param parametros Parâmetros utilizados na consulta (JSON).
     * @param dadosRelatorio Sumário dos dados extraídos (JSON).
     * @param usuarioGeracao Usuário que solicitou a geração.
     * @param agendamento Configuração de agendamento, se houver.
     * @param pronto Indica se o arquivo está pronto para download.
     * @param falhou Indica se houve falha na geração.
     * @param gerando Indica se o relatório ainda está sendo gerado.
     * @param dataCriacao Data de criação do registro.
     * @param dataAtualizacao Data da última atualização.
     */
    @java.lang.SuppressWarnings("all")
    public RelatorioDTO(final Long id, final String nome, final String descricao, final Relatorio.TipoRelatorio tipoRelatorio, final Relatorio.CategoriaRelatorio categoria, final LocalDateTime dataGeracao, final LocalDateTime dataPeriodoInicio, final LocalDateTime dataPeriodoFim, final Relatorio.StatusRelatorio status, final String caminhoArquivo, final String formatoArquivo, final Long tamanhoArquivo, final String parametros, final String dadosRelatorio, final String usuarioGeracao, final String agendamento, final Boolean pronto, final Boolean falhou, final Boolean gerando, final String dataCriacao, final String dataAtualizacao) {
        this.id = id;
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
        this.pronto = pronto;
        this.falhou = falhou;
        this.gerando = gerando;
        this.dataCriacao = dataCriacao;
        this.dataAtualizacao = dataAtualizacao;
    }
}
