package com.aurix.platform.shared.dto;

import com.aurix.platform.shared.entity.LogAuditoria;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * DTO para LogAuditoria.
 */
public class LogAuditoriaDTO {
    /**
     * ID do log.
     */
    private Long id;
    /**
     * Ação realizada (ex: LOGIN, CREATE, DELETE).
     */
    @NotBlank(message = "Ação é obrigatória")
    private String acao;
    /**
     * Descrição detalhada da ação.
     */
    private String descricao;
    /**
     * Nome da entidade afetada.
     */
    @NotNull(message = "Entidade é obrigatória")
    private String entidade;
    /**
     * ID da entidade afetada.
     */
    private Long entidadeId;
    /**
     * ID do usuário que realizou a ação.
     */
    private Long usuarioId;
    /**
     * Nome do usuário que realizou a ação.
     */
    private String usuarioNome;
    /**
     * IP de origem da requisição.
     */
    private String ipOrigem;
    /**
     * User Agent do navegador/cliente.
     */
    private String userAgent;
    /**
     * Data e hora em que a ação ocorreu.
     */
    private LocalDateTime dataAcao;
    /**
     * Tipo da ação (SISTEMA, USUARIO, etc).
     */
    private LogAuditoria.TipoAcao tipoAcao;
    /**
     * Categoria da auditoria.
     */
    private LogAuditoria.CategoriaAuditoria categoria;
    /**
     * Nível de severidade/criticidade.
     */
    private LogAuditoria.NivelAuditoria nivel;
    /**
     * Dados anteriores à modificação (JSON).
     */
    private String dadosAnteriores;
    /**
     * Dados após a modificação (JSON).
     */
    private String dadosNovos;
    /**
     * Resultado da ação (ex: SUCESSO, ERRO).
     */
    private String resultado;
    /**
     * Código de erro, se houver.
     */
    private String codigoErro;
    /**
     * Mensagem de erro detalhada, se houver.
     */
    private String mensagemErro;
    /**
     * Dados extras em formato JSON.
     */
    private String dadosExtras;
    /**
     * Indica se a ação foi bem sucedida.
     */
    private Boolean sucesso;
    /**
     * Indica se houve falha na ação.
     */
    private Boolean falha;
    /**
     * Indica se a ação é crítica.
     */
    private Boolean critica;
    /**
     * Data de criação do registro.
     */
    private String dataCriacao;
    /**
     * Data da última atualização.
     */
    private String dataAtualizacao;

    /**
     * ID do log.
     */
    @java.lang.SuppressWarnings("all")
    public Long getId() {
        return this.id;
    }

    /**
     * Ação realizada (ex: LOGIN, CREATE, DELETE).
     */
    @java.lang.SuppressWarnings("all")
    public String getAcao() {
        return this.acao;
    }

    /**
     * Descrição detalhada da ação.
     */
    @java.lang.SuppressWarnings("all")
    public String getDescricao() {
        return this.descricao;
    }

    /**
     * Nome da entidade afetada.
     */
    @java.lang.SuppressWarnings("all")
    public String getEntidade() {
        return this.entidade;
    }

    /**
     * ID da entidade afetada.
     */
    @java.lang.SuppressWarnings("all")
    public Long getEntidadeId() {
        return this.entidadeId;
    }

    /**
     * ID do usuário que realizou a ação.
     */
    @java.lang.SuppressWarnings("all")
    public Long getUsuarioId() {
        return this.usuarioId;
    }

    /**
     * Nome do usuário que realizou a ação.
     */
    @java.lang.SuppressWarnings("all")
    public String getUsuarioNome() {
        return this.usuarioNome;
    }

    /**
     * IP de origem da requisição.
     */
    @java.lang.SuppressWarnings("all")
    public String getIpOrigem() {
        return this.ipOrigem;
    }

    /**
     * User Agent do navegador/cliente.
     */
    @java.lang.SuppressWarnings("all")
    public String getUserAgent() {
        return this.userAgent;
    }

    /**
     * Data e hora em que a ação ocorreu.
     */
    @java.lang.SuppressWarnings("all")
    public LocalDateTime getDataAcao() {
        return this.dataAcao;
    }

    /**
     * Tipo da ação (SISTEMA, USUARIO, etc).
     */
    @java.lang.SuppressWarnings("all")
    public LogAuditoria.TipoAcao getTipoAcao() {
        return this.tipoAcao;
    }

    /**
     * Categoria da auditoria.
     */
    @java.lang.SuppressWarnings("all")
    public LogAuditoria.CategoriaAuditoria getCategoria() {
        return this.categoria;
    }

    /**
     * Nível de severidade/criticidade.
     */
    @java.lang.SuppressWarnings("all")
    public LogAuditoria.NivelAuditoria getNivel() {
        return this.nivel;
    }

    /**
     * Dados anteriores à modificação (JSON).
     */
    @java.lang.SuppressWarnings("all")
    public String getDadosAnteriores() {
        return this.dadosAnteriores;
    }

    /**
     * Dados após a modificação (JSON).
     */
    @java.lang.SuppressWarnings("all")
    public String getDadosNovos() {
        return this.dadosNovos;
    }

    /**
     * Resultado da ação (ex: SUCESSO, ERRO).
     */
    @java.lang.SuppressWarnings("all")
    public String getResultado() {
        return this.resultado;
    }

    /**
     * Código de erro, se houver.
     */
    @java.lang.SuppressWarnings("all")
    public String getCodigoErro() {
        return this.codigoErro;
    }

    /**
     * Mensagem de erro detalhada, se houver.
     */
    @java.lang.SuppressWarnings("all")
    public String getMensagemErro() {
        return this.mensagemErro;
    }

    /**
     * Dados extras em formato JSON.
     */
    @java.lang.SuppressWarnings("all")
    public String getDadosExtras() {
        return this.dadosExtras;
    }

    /**
     * Indica se a ação foi bem sucedida.
     */
    @java.lang.SuppressWarnings("all")
    public Boolean getSucesso() {
        return this.sucesso;
    }

    /**
     * Indica se houve falha na ação.
     */
    @java.lang.SuppressWarnings("all")
    public Boolean getFalha() {
        return this.falha;
    }

    /**
     * Indica se a ação é crítica.
     */
    @java.lang.SuppressWarnings("all")
    public Boolean getCritica() {
        return this.critica;
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
     * ID do log.
     */
    @java.lang.SuppressWarnings("all")
    public void setId(final Long id) {
        this.id = id;
    }

    /**
     * Ação realizada (ex: LOGIN, CREATE, DELETE).
     */
    @java.lang.SuppressWarnings("all")
    public void setAcao(final String acao) {
        this.acao = acao;
    }

    /**
     * Descrição detalhada da ação.
     */
    @java.lang.SuppressWarnings("all")
    public void setDescricao(final String descricao) {
        this.descricao = descricao;
    }

    /**
     * Nome da entidade afetada.
     */
    @java.lang.SuppressWarnings("all")
    public void setEntidade(final String entidade) {
        this.entidade = entidade;
    }

    /**
     * ID da entidade afetada.
     */
    @java.lang.SuppressWarnings("all")
    public void setEntidadeId(final Long entidadeId) {
        this.entidadeId = entidadeId;
    }

    /**
     * ID do usuário que realizou a ação.
     */
    @java.lang.SuppressWarnings("all")
    public void setUsuarioId(final Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    /**
     * Nome do usuário que realizou a ação.
     */
    @java.lang.SuppressWarnings("all")
    public void setUsuarioNome(final String usuarioNome) {
        this.usuarioNome = usuarioNome;
    }

    /**
     * IP de origem da requisição.
     */
    @java.lang.SuppressWarnings("all")
    public void setIpOrigem(final String ipOrigem) {
        this.ipOrigem = ipOrigem;
    }

    /**
     * User Agent do navegador/cliente.
     */
    @java.lang.SuppressWarnings("all")
    public void setUserAgent(final String userAgent) {
        this.userAgent = userAgent;
    }

    /**
     * Data e hora em que a ação ocorreu.
     */
    @java.lang.SuppressWarnings("all")
    public void setDataAcao(final LocalDateTime dataAcao) {
        this.dataAcao = dataAcao;
    }

    /**
     * Tipo da ação (SISTEMA, USUARIO, etc).
     */
    @java.lang.SuppressWarnings("all")
    public void setTipoAcao(final LogAuditoria.TipoAcao tipoAcao) {
        this.tipoAcao = tipoAcao;
    }

    /**
     * Categoria da auditoria.
     */
    @java.lang.SuppressWarnings("all")
    public void setCategoria(final LogAuditoria.CategoriaAuditoria categoria) {
        this.categoria = categoria;
    }

    /**
     * Nível de severidade/criticidade.
     */
    @java.lang.SuppressWarnings("all")
    public void setNivel(final LogAuditoria.NivelAuditoria nivel) {
        this.nivel = nivel;
    }

    /**
     * Dados anteriores à modificação (JSON).
     */
    @java.lang.SuppressWarnings("all")
    public void setDadosAnteriores(final String dadosAnteriores) {
        this.dadosAnteriores = dadosAnteriores;
    }

    /**
     * Dados após a modificação (JSON).
     */
    @java.lang.SuppressWarnings("all")
    public void setDadosNovos(final String dadosNovos) {
        this.dadosNovos = dadosNovos;
    }

    /**
     * Resultado da ação (ex: SUCESSO, ERRO).
     */
    @java.lang.SuppressWarnings("all")
    public void setResultado(final String resultado) {
        this.resultado = resultado;
    }

    /**
     * Código de erro, se houver.
     */
    @java.lang.SuppressWarnings("all")
    public void setCodigoErro(final String codigoErro) {
        this.codigoErro = codigoErro;
    }

    /**
     * Mensagem de erro detalhada, se houver.
     */
    @java.lang.SuppressWarnings("all")
    public void setMensagemErro(final String mensagemErro) {
        this.mensagemErro = mensagemErro;
    }

    /**
     * Dados extras em formato JSON.
     */
    @java.lang.SuppressWarnings("all")
    public void setDadosExtras(final String dadosExtras) {
        this.dadosExtras = dadosExtras;
    }

    /**
     * Indica se a ação foi bem sucedida.
     */
    @java.lang.SuppressWarnings("all")
    public void setSucesso(final Boolean sucesso) {
        this.sucesso = sucesso;
    }

    /**
     * Indica se houve falha na ação.
     */
    @java.lang.SuppressWarnings("all")
    public void setFalha(final Boolean falha) {
        this.falha = falha;
    }

    /**
     * Indica se a ação é crítica.
     */
    @java.lang.SuppressWarnings("all")
    public void setCritica(final Boolean critica) {
        this.critica = critica;
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
        if (!(o instanceof LogAuditoriaDTO)) return false;
        final LogAuditoriaDTO other = (LogAuditoriaDTO) o;
        if (!other.canEqual((java.lang.Object) this)) return false;
        final java.lang.Object this$id = this.getId();
        final java.lang.Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final java.lang.Object this$entidadeId = this.getEntidadeId();
        final java.lang.Object other$entidadeId = other.getEntidadeId();
        if (this$entidadeId == null ? other$entidadeId != null : !this$entidadeId.equals(other$entidadeId)) return false;
        final java.lang.Object this$usuarioId = this.getUsuarioId();
        final java.lang.Object other$usuarioId = other.getUsuarioId();
        if (this$usuarioId == null ? other$usuarioId != null : !this$usuarioId.equals(other$usuarioId)) return false;
        final java.lang.Object this$sucesso = this.getSucesso();
        final java.lang.Object other$sucesso = other.getSucesso();
        if (this$sucesso == null ? other$sucesso != null : !this$sucesso.equals(other$sucesso)) return false;
        final java.lang.Object this$falha = this.getFalha();
        final java.lang.Object other$falha = other.getFalha();
        if (this$falha == null ? other$falha != null : !this$falha.equals(other$falha)) return false;
        final java.lang.Object this$critica = this.getCritica();
        final java.lang.Object other$critica = other.getCritica();
        if (this$critica == null ? other$critica != null : !this$critica.equals(other$critica)) return false;
        final java.lang.Object this$acao = this.getAcao();
        final java.lang.Object other$acao = other.getAcao();
        if (this$acao == null ? other$acao != null : !this$acao.equals(other$acao)) return false;
        final java.lang.Object this$descricao = this.getDescricao();
        final java.lang.Object other$descricao = other.getDescricao();
        if (this$descricao == null ? other$descricao != null : !this$descricao.equals(other$descricao)) return false;
        final java.lang.Object this$entidade = this.getEntidade();
        final java.lang.Object other$entidade = other.getEntidade();
        if (this$entidade == null ? other$entidade != null : !this$entidade.equals(other$entidade)) return false;
        final java.lang.Object this$usuarioNome = this.getUsuarioNome();
        final java.lang.Object other$usuarioNome = other.getUsuarioNome();
        if (this$usuarioNome == null ? other$usuarioNome != null : !this$usuarioNome.equals(other$usuarioNome)) return false;
        final java.lang.Object this$ipOrigem = this.getIpOrigem();
        final java.lang.Object other$ipOrigem = other.getIpOrigem();
        if (this$ipOrigem == null ? other$ipOrigem != null : !this$ipOrigem.equals(other$ipOrigem)) return false;
        final java.lang.Object this$userAgent = this.getUserAgent();
        final java.lang.Object other$userAgent = other.getUserAgent();
        if (this$userAgent == null ? other$userAgent != null : !this$userAgent.equals(other$userAgent)) return false;
        final java.lang.Object this$dataAcao = this.getDataAcao();
        final java.lang.Object other$dataAcao = other.getDataAcao();
        if (this$dataAcao == null ? other$dataAcao != null : !this$dataAcao.equals(other$dataAcao)) return false;
        final java.lang.Object this$tipoAcao = this.getTipoAcao();
        final java.lang.Object other$tipoAcao = other.getTipoAcao();
        if (this$tipoAcao == null ? other$tipoAcao != null : !this$tipoAcao.equals(other$tipoAcao)) return false;
        final java.lang.Object this$categoria = this.getCategoria();
        final java.lang.Object other$categoria = other.getCategoria();
        if (this$categoria == null ? other$categoria != null : !this$categoria.equals(other$categoria)) return false;
        final java.lang.Object this$nivel = this.getNivel();
        final java.lang.Object other$nivel = other.getNivel();
        if (this$nivel == null ? other$nivel != null : !this$nivel.equals(other$nivel)) return false;
        final java.lang.Object this$dadosAnteriores = this.getDadosAnteriores();
        final java.lang.Object other$dadosAnteriores = other.getDadosAnteriores();
        if (this$dadosAnteriores == null ? other$dadosAnteriores != null : !this$dadosAnteriores.equals(other$dadosAnteriores)) return false;
        final java.lang.Object this$dadosNovos = this.getDadosNovos();
        final java.lang.Object other$dadosNovos = other.getDadosNovos();
        if (this$dadosNovos == null ? other$dadosNovos != null : !this$dadosNovos.equals(other$dadosNovos)) return false;
        final java.lang.Object this$resultado = this.getResultado();
        final java.lang.Object other$resultado = other.getResultado();
        if (this$resultado == null ? other$resultado != null : !this$resultado.equals(other$resultado)) return false;
        final java.lang.Object this$codigoErro = this.getCodigoErro();
        final java.lang.Object other$codigoErro = other.getCodigoErro();
        if (this$codigoErro == null ? other$codigoErro != null : !this$codigoErro.equals(other$codigoErro)) return false;
        final java.lang.Object this$mensagemErro = this.getMensagemErro();
        final java.lang.Object other$mensagemErro = other.getMensagemErro();
        if (this$mensagemErro == null ? other$mensagemErro != null : !this$mensagemErro.equals(other$mensagemErro)) return false;
        final java.lang.Object this$dadosExtras = this.getDadosExtras();
        final java.lang.Object other$dadosExtras = other.getDadosExtras();
        if (this$dadosExtras == null ? other$dadosExtras != null : !this$dadosExtras.equals(other$dadosExtras)) return false;
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
        return other instanceof LogAuditoriaDTO;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final java.lang.Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final java.lang.Object $entidadeId = this.getEntidadeId();
        result = result * PRIME + ($entidadeId == null ? 43 : $entidadeId.hashCode());
        final java.lang.Object $usuarioId = this.getUsuarioId();
        result = result * PRIME + ($usuarioId == null ? 43 : $usuarioId.hashCode());
        final java.lang.Object $sucesso = this.getSucesso();
        result = result * PRIME + ($sucesso == null ? 43 : $sucesso.hashCode());
        final java.lang.Object $falha = this.getFalha();
        result = result * PRIME + ($falha == null ? 43 : $falha.hashCode());
        final java.lang.Object $critica = this.getCritica();
        result = result * PRIME + ($critica == null ? 43 : $critica.hashCode());
        final java.lang.Object $acao = this.getAcao();
        result = result * PRIME + ($acao == null ? 43 : $acao.hashCode());
        final java.lang.Object $descricao = this.getDescricao();
        result = result * PRIME + ($descricao == null ? 43 : $descricao.hashCode());
        final java.lang.Object $entidade = this.getEntidade();
        result = result * PRIME + ($entidade == null ? 43 : $entidade.hashCode());
        final java.lang.Object $usuarioNome = this.getUsuarioNome();
        result = result * PRIME + ($usuarioNome == null ? 43 : $usuarioNome.hashCode());
        final java.lang.Object $ipOrigem = this.getIpOrigem();
        result = result * PRIME + ($ipOrigem == null ? 43 : $ipOrigem.hashCode());
        final java.lang.Object $userAgent = this.getUserAgent();
        result = result * PRIME + ($userAgent == null ? 43 : $userAgent.hashCode());
        final java.lang.Object $dataAcao = this.getDataAcao();
        result = result * PRIME + ($dataAcao == null ? 43 : $dataAcao.hashCode());
        final java.lang.Object $tipoAcao = this.getTipoAcao();
        result = result * PRIME + ($tipoAcao == null ? 43 : $tipoAcao.hashCode());
        final java.lang.Object $categoria = this.getCategoria();
        result = result * PRIME + ($categoria == null ? 43 : $categoria.hashCode());
        final java.lang.Object $nivel = this.getNivel();
        result = result * PRIME + ($nivel == null ? 43 : $nivel.hashCode());
        final java.lang.Object $dadosAnteriores = this.getDadosAnteriores();
        result = result * PRIME + ($dadosAnteriores == null ? 43 : $dadosAnteriores.hashCode());
        final java.lang.Object $dadosNovos = this.getDadosNovos();
        result = result * PRIME + ($dadosNovos == null ? 43 : $dadosNovos.hashCode());
        final java.lang.Object $resultado = this.getResultado();
        result = result * PRIME + ($resultado == null ? 43 : $resultado.hashCode());
        final java.lang.Object $codigoErro = this.getCodigoErro();
        result = result * PRIME + ($codigoErro == null ? 43 : $codigoErro.hashCode());
        final java.lang.Object $mensagemErro = this.getMensagemErro();
        result = result * PRIME + ($mensagemErro == null ? 43 : $mensagemErro.hashCode());
        final java.lang.Object $dadosExtras = this.getDadosExtras();
        result = result * PRIME + ($dadosExtras == null ? 43 : $dadosExtras.hashCode());
        final java.lang.Object $dataCriacao = this.getDataCriacao();
        result = result * PRIME + ($dataCriacao == null ? 43 : $dataCriacao.hashCode());
        final java.lang.Object $dataAtualizacao = this.getDataAtualizacao();
        result = result * PRIME + ($dataAtualizacao == null ? 43 : $dataAtualizacao.hashCode());
        return result;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
        return "LogAuditoriaDTO(id=" + this.getId() + ", acao=" + this.getAcao() + ", descricao=" + this.getDescricao() + ", entidade=" + this.getEntidade() + ", entidadeId=" + this.getEntidadeId() + ", usuarioId=" + this.getUsuarioId() + ", usuarioNome=" + this.getUsuarioNome() + ", ipOrigem=" + this.getIpOrigem() + ", userAgent=" + this.getUserAgent() + ", dataAcao=" + this.getDataAcao() + ", tipoAcao=" + this.getTipoAcao() + ", categoria=" + this.getCategoria() + ", nivel=" + this.getNivel() + ", dadosAnteriores=" + this.getDadosAnteriores() + ", dadosNovos=" + this.getDadosNovos() + ", resultado=" + this.getResultado() + ", codigoErro=" + this.getCodigoErro() + ", mensagemErro=" + this.getMensagemErro() + ", dadosExtras=" + this.getDadosExtras() + ", sucesso=" + this.getSucesso() + ", falha=" + this.getFalha() + ", critica=" + this.getCritica() + ", dataCriacao=" + this.getDataCriacao() + ", dataAtualizacao=" + this.getDataAtualizacao() + ")";
    }

    @java.lang.SuppressWarnings("all")
    public LogAuditoriaDTO() {
    }

    /**
     * Creates a new {@code LogAuditoriaDTO} instance.
     *
     * @param id ID do log.
     * @param acao Ação realizada (ex: LOGIN, CREATE, DELETE).
     * @param descricao Descrição detalhada da ação.
     * @param entidade Nome da entidade afetada.
     * @param entidadeId ID da entidade afetada.
     * @param usuarioId ID do usuário que realizou a ação.
     * @param usuarioNome Nome do usuário que realizou a ação.
     * @param ipOrigem IP de origem da requisição.
     * @param userAgent User Agent do navegador/cliente.
     * @param dataAcao Data e hora em que a ação ocorreu.
     * @param tipoAcao Tipo da ação (SISTEMA, USUARIO, etc).
     * @param categoria Categoria da auditoria.
     * @param nivel Nível de severidade/criticidade.
     * @param dadosAnteriores Dados anteriores à modificação (JSON).
     * @param dadosNovos Dados após a modificação (JSON).
     * @param resultado Resultado da ação (ex: SUCESSO, ERRO).
     * @param codigoErro Código de erro, se houver.
     * @param mensagemErro Mensagem de erro detalhada, se houver.
     * @param dadosExtras Dados extras em formato JSON.
     * @param sucesso Indica se a ação foi bem sucedida.
     * @param falha Indica se houve falha na ação.
     * @param critica Indica se a ação é crítica.
     * @param dataCriacao Data de criação do registro.
     * @param dataAtualizacao Data da última atualização.
     */
    @java.lang.SuppressWarnings("all")
    public LogAuditoriaDTO(final Long id, final String acao, final String descricao, final String entidade, final Long entidadeId, final Long usuarioId, final String usuarioNome, final String ipOrigem, final String userAgent, final LocalDateTime dataAcao, final LogAuditoria.TipoAcao tipoAcao, final LogAuditoria.CategoriaAuditoria categoria, final LogAuditoria.NivelAuditoria nivel, final String dadosAnteriores, final String dadosNovos, final String resultado, final String codigoErro, final String mensagemErro, final String dadosExtras, final Boolean sucesso, final Boolean falha, final Boolean critica, final String dataCriacao, final String dataAtualizacao) {
        this.id = id;
        this.acao = acao;
        this.descricao = descricao;
        this.entidade = entidade;
        this.entidadeId = entidadeId;
        this.usuarioId = usuarioId;
        this.usuarioNome = usuarioNome;
        this.ipOrigem = ipOrigem;
        this.userAgent = userAgent;
        this.dataAcao = dataAcao;
        this.tipoAcao = tipoAcao;
        this.categoria = categoria;
        this.nivel = nivel;
        this.dadosAnteriores = dadosAnteriores;
        this.dadosNovos = dadosNovos;
        this.resultado = resultado;
        this.codigoErro = codigoErro;
        this.mensagemErro = mensagemErro;
        this.dadosExtras = dadosExtras;
        this.sucesso = sucesso;
        this.falha = falha;
        this.critica = critica;
        this.dataCriacao = dataCriacao;
        this.dataAtualizacao = dataAtualizacao;
    }
}
