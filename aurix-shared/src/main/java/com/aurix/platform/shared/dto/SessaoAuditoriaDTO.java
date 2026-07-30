package com.aurix.platform.shared.dto;

import com.aurix.platform.shared.entity.SessaoAuditoria;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * DTO para SessaoAuditoria.
 */
public class SessaoAuditoriaDTO {
    /**
     * ID da sessão de auditoria.
     */
    private Long id;
    /**
     * ID do usuário da sessão.
     */
    @NotNull(message = "Usuário é obrigatório")
    private Long usuarioId;
    /**
     * Nome do usuário da sessão.
     */
    private String usuarioNome;
    /**
     * IP de origem da conexão.
     */
    private String ipOrigem;
    /**
     * User Agent do cliente.
     */
    private String userAgent;
    /**
     * Data e hora de início da sessão.
     */
    private LocalDateTime dataInicio;
    /**
     * Data e hora de término da sessão.
     */
    private LocalDateTime dataFim;
    /**
     * Status da sessão (ATIVA, ENCERRADA, EXPIRADA).
     */
    private SessaoAuditoria.StatusSessao status;
    /**
     * Token identificador da sessão.
     */
    private String tokenSessao;
    /**
     * Dados persistidos da sessão (JSON).
     */
    private String dadosSessao;
    /**
     * Registro de atividades durante a sessão.
     */
    private String atividadesRealizadas;
    /**
     * Dados extras em formato JSON.
     */
    private String dadosExtras;
    /**
     * Indica se a sessão está ativa.
     */
    private Boolean ativa;
    /**
     * Indica se a sessão foi encerrada formalmente.
     */
    private Boolean encerrada;
    /**
     * Indica se a sessão expirou por inatividade.
     */
    private Boolean expirada;
    /**
     * Duração total em minutos.
     */
    private Long duracaoMinutos;
    /**
     * Data de criação do registro.
     */
    private String dataCriacao;
    /**
     * Data da última atualização.
     */
    private String dataAtualizacao;

    /**
     * ID da sessão de auditoria.
     */
    @java.lang.SuppressWarnings("all")
    public Long getId() {
        return this.id;
    }

    /**
     * ID do usuário da sessão.
     */
    @java.lang.SuppressWarnings("all")
    public Long getUsuarioId() {
        return this.usuarioId;
    }

    /**
     * Nome do usuário da sessão.
     */
    @java.lang.SuppressWarnings("all")
    public String getUsuarioNome() {
        return this.usuarioNome;
    }

    /**
     * IP de origem da conexão.
     */
    @java.lang.SuppressWarnings("all")
    public String getIpOrigem() {
        return this.ipOrigem;
    }

    /**
     * User Agent do cliente.
     */
    @java.lang.SuppressWarnings("all")
    public String getUserAgent() {
        return this.userAgent;
    }

    /**
     * Data e hora de início da sessão.
     */
    @java.lang.SuppressWarnings("all")
    public LocalDateTime getDataInicio() {
        return this.dataInicio;
    }

    /**
     * Data e hora de término da sessão.
     */
    @java.lang.SuppressWarnings("all")
    public LocalDateTime getDataFim() {
        return this.dataFim;
    }

    /**
     * Status da sessão (ATIVA, ENCERRADA, EXPIRADA).
     */
    @java.lang.SuppressWarnings("all")
    public SessaoAuditoria.StatusSessao getStatus() {
        return this.status;
    }

    /**
     * Token identificador da sessão.
     */
    @java.lang.SuppressWarnings("all")
    public String getTokenSessao() {
        return this.tokenSessao;
    }

    /**
     * Dados persistidos da sessão (JSON).
     */
    @java.lang.SuppressWarnings("all")
    public String getDadosSessao() {
        return this.dadosSessao;
    }

    /**
     * Registro de atividades durante a sessão.
     */
    @java.lang.SuppressWarnings("all")
    public String getAtividadesRealizadas() {
        return this.atividadesRealizadas;
    }

    /**
     * Dados extras em formato JSON.
     */
    @java.lang.SuppressWarnings("all")
    public String getDadosExtras() {
        return this.dadosExtras;
    }

    /**
     * Indica se a sessão está ativa.
     */
    @java.lang.SuppressWarnings("all")
    public Boolean getAtiva() {
        return this.ativa;
    }

    /**
     * Indica se a sessão foi encerrada formalmente.
     */
    @java.lang.SuppressWarnings("all")
    public Boolean getEncerrada() {
        return this.encerrada;
    }

    /**
     * Indica se a sessão expirou por inatividade.
     */
    @java.lang.SuppressWarnings("all")
    public Boolean getExpirada() {
        return this.expirada;
    }

    /**
     * Duração total em minutos.
     */
    @java.lang.SuppressWarnings("all")
    public Long getDuracaoMinutos() {
        return this.duracaoMinutos;
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
     * ID da sessão de auditoria.
     */
    @java.lang.SuppressWarnings("all")
    public void setId(final Long id) {
        this.id = id;
    }

    /**
     * ID do usuário da sessão.
     */
    @java.lang.SuppressWarnings("all")
    public void setUsuarioId(final Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    /**
     * Nome do usuário da sessão.
     */
    @java.lang.SuppressWarnings("all")
    public void setUsuarioNome(final String usuarioNome) {
        this.usuarioNome = usuarioNome;
    }

    /**
     * IP de origem da conexão.
     */
    @java.lang.SuppressWarnings("all")
    public void setIpOrigem(final String ipOrigem) {
        this.ipOrigem = ipOrigem;
    }

    /**
     * User Agent do cliente.
     */
    @java.lang.SuppressWarnings("all")
    public void setUserAgent(final String userAgent) {
        this.userAgent = userAgent;
    }

    /**
     * Data e hora de início da sessão.
     */
    @java.lang.SuppressWarnings("all")
    public void setDataInicio(final LocalDateTime dataInicio) {
        this.dataInicio = dataInicio;
    }

    /**
     * Data e hora de término da sessão.
     */
    @java.lang.SuppressWarnings("all")
    public void setDataFim(final LocalDateTime dataFim) {
        this.dataFim = dataFim;
    }

    /**
     * Status da sessão (ATIVA, ENCERRADA, EXPIRADA).
     */
    @java.lang.SuppressWarnings("all")
    public void setStatus(final SessaoAuditoria.StatusSessao status) {
        this.status = status;
    }

    /**
     * Token identificador da sessão.
     */
    @java.lang.SuppressWarnings("all")
    public void setTokenSessao(final String tokenSessao) {
        this.tokenSessao = tokenSessao;
    }

    /**
     * Dados persistidos da sessão (JSON).
     */
    @java.lang.SuppressWarnings("all")
    public void setDadosSessao(final String dadosSessao) {
        this.dadosSessao = dadosSessao;
    }

    /**
     * Registro de atividades durante a sessão.
     */
    @java.lang.SuppressWarnings("all")
    public void setAtividadesRealizadas(final String atividadesRealizadas) {
        this.atividadesRealizadas = atividadesRealizadas;
    }

    /**
     * Dados extras em formato JSON.
     */
    @java.lang.SuppressWarnings("all")
    public void setDadosExtras(final String dadosExtras) {
        this.dadosExtras = dadosExtras;
    }

    /**
     * Indica se a sessão está ativa.
     */
    @java.lang.SuppressWarnings("all")
    public void setAtiva(final Boolean ativa) {
        this.ativa = ativa;
    }

    /**
     * Indica se a sessão foi encerrada formalmente.
     */
    @java.lang.SuppressWarnings("all")
    public void setEncerrada(final Boolean encerrada) {
        this.encerrada = encerrada;
    }

    /**
     * Indica se a sessão expirou por inatividade.
     */
    @java.lang.SuppressWarnings("all")
    public void setExpirada(final Boolean expirada) {
        this.expirada = expirada;
    }

    /**
     * Duração total em minutos.
     */
    @java.lang.SuppressWarnings("all")
    public void setDuracaoMinutos(final Long duracaoMinutos) {
        this.duracaoMinutos = duracaoMinutos;
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
        if (!(o instanceof SessaoAuditoriaDTO)) return false;
        final SessaoAuditoriaDTO other = (SessaoAuditoriaDTO) o;
        if (!other.canEqual((java.lang.Object) this)) return false;
        final java.lang.Object this$id = this.getId();
        final java.lang.Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final java.lang.Object this$usuarioId = this.getUsuarioId();
        final java.lang.Object other$usuarioId = other.getUsuarioId();
        if (this$usuarioId == null ? other$usuarioId != null : !this$usuarioId.equals(other$usuarioId)) return false;
        final java.lang.Object this$ativa = this.getAtiva();
        final java.lang.Object other$ativa = other.getAtiva();
        if (this$ativa == null ? other$ativa != null : !this$ativa.equals(other$ativa)) return false;
        final java.lang.Object this$encerrada = this.getEncerrada();
        final java.lang.Object other$encerrada = other.getEncerrada();
        if (this$encerrada == null ? other$encerrada != null : !this$encerrada.equals(other$encerrada)) return false;
        final java.lang.Object this$expirada = this.getExpirada();
        final java.lang.Object other$expirada = other.getExpirada();
        if (this$expirada == null ? other$expirada != null : !this$expirada.equals(other$expirada)) return false;
        final java.lang.Object this$duracaoMinutos = this.getDuracaoMinutos();
        final java.lang.Object other$duracaoMinutos = other.getDuracaoMinutos();
        if (this$duracaoMinutos == null ? other$duracaoMinutos != null : !this$duracaoMinutos.equals(other$duracaoMinutos)) return false;
        final java.lang.Object this$usuarioNome = this.getUsuarioNome();
        final java.lang.Object other$usuarioNome = other.getUsuarioNome();
        if (this$usuarioNome == null ? other$usuarioNome != null : !this$usuarioNome.equals(other$usuarioNome)) return false;
        final java.lang.Object this$ipOrigem = this.getIpOrigem();
        final java.lang.Object other$ipOrigem = other.getIpOrigem();
        if (this$ipOrigem == null ? other$ipOrigem != null : !this$ipOrigem.equals(other$ipOrigem)) return false;
        final java.lang.Object this$userAgent = this.getUserAgent();
        final java.lang.Object other$userAgent = other.getUserAgent();
        if (this$userAgent == null ? other$userAgent != null : !this$userAgent.equals(other$userAgent)) return false;
        final java.lang.Object this$dataInicio = this.getDataInicio();
        final java.lang.Object other$dataInicio = other.getDataInicio();
        if (this$dataInicio == null ? other$dataInicio != null : !this$dataInicio.equals(other$dataInicio)) return false;
        final java.lang.Object this$dataFim = this.getDataFim();
        final java.lang.Object other$dataFim = other.getDataFim();
        if (this$dataFim == null ? other$dataFim != null : !this$dataFim.equals(other$dataFim)) return false;
        final java.lang.Object this$status = this.getStatus();
        final java.lang.Object other$status = other.getStatus();
        if (this$status == null ? other$status != null : !this$status.equals(other$status)) return false;
        final java.lang.Object this$tokenSessao = this.getTokenSessao();
        final java.lang.Object other$tokenSessao = other.getTokenSessao();
        if (this$tokenSessao == null ? other$tokenSessao != null : !this$tokenSessao.equals(other$tokenSessao)) return false;
        final java.lang.Object this$dadosSessao = this.getDadosSessao();
        final java.lang.Object other$dadosSessao = other.getDadosSessao();
        if (this$dadosSessao == null ? other$dadosSessao != null : !this$dadosSessao.equals(other$dadosSessao)) return false;
        final java.lang.Object this$atividadesRealizadas = this.getAtividadesRealizadas();
        final java.lang.Object other$atividadesRealizadas = other.getAtividadesRealizadas();
        if (this$atividadesRealizadas == null ? other$atividadesRealizadas != null : !this$atividadesRealizadas.equals(other$atividadesRealizadas)) return false;
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
        return other instanceof SessaoAuditoriaDTO;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final java.lang.Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final java.lang.Object $usuarioId = this.getUsuarioId();
        result = result * PRIME + ($usuarioId == null ? 43 : $usuarioId.hashCode());
        final java.lang.Object $ativa = this.getAtiva();
        result = result * PRIME + ($ativa == null ? 43 : $ativa.hashCode());
        final java.lang.Object $encerrada = this.getEncerrada();
        result = result * PRIME + ($encerrada == null ? 43 : $encerrada.hashCode());
        final java.lang.Object $expirada = this.getExpirada();
        result = result * PRIME + ($expirada == null ? 43 : $expirada.hashCode());
        final java.lang.Object $duracaoMinutos = this.getDuracaoMinutos();
        result = result * PRIME + ($duracaoMinutos == null ? 43 : $duracaoMinutos.hashCode());
        final java.lang.Object $usuarioNome = this.getUsuarioNome();
        result = result * PRIME + ($usuarioNome == null ? 43 : $usuarioNome.hashCode());
        final java.lang.Object $ipOrigem = this.getIpOrigem();
        result = result * PRIME + ($ipOrigem == null ? 43 : $ipOrigem.hashCode());
        final java.lang.Object $userAgent = this.getUserAgent();
        result = result * PRIME + ($userAgent == null ? 43 : $userAgent.hashCode());
        final java.lang.Object $dataInicio = this.getDataInicio();
        result = result * PRIME + ($dataInicio == null ? 43 : $dataInicio.hashCode());
        final java.lang.Object $dataFim = this.getDataFim();
        result = result * PRIME + ($dataFim == null ? 43 : $dataFim.hashCode());
        final java.lang.Object $status = this.getStatus();
        result = result * PRIME + ($status == null ? 43 : $status.hashCode());
        final java.lang.Object $tokenSessao = this.getTokenSessao();
        result = result * PRIME + ($tokenSessao == null ? 43 : $tokenSessao.hashCode());
        final java.lang.Object $dadosSessao = this.getDadosSessao();
        result = result * PRIME + ($dadosSessao == null ? 43 : $dadosSessao.hashCode());
        final java.lang.Object $atividadesRealizadas = this.getAtividadesRealizadas();
        result = result * PRIME + ($atividadesRealizadas == null ? 43 : $atividadesRealizadas.hashCode());
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
        return "SessaoAuditoriaDTO(id=" + this.getId() + ", usuarioId=" + this.getUsuarioId() + ", usuarioNome=" + this.getUsuarioNome() + ", ipOrigem=" + this.getIpOrigem() + ", userAgent=" + this.getUserAgent() + ", dataInicio=" + this.getDataInicio() + ", dataFim=" + this.getDataFim() + ", status=" + this.getStatus() + ", tokenSessao=" + this.getTokenSessao() + ", dadosSessao=" + this.getDadosSessao() + ", atividadesRealizadas=" + this.getAtividadesRealizadas() + ", dadosExtras=" + this.getDadosExtras() + ", ativa=" + this.getAtiva() + ", encerrada=" + this.getEncerrada() + ", expirada=" + this.getExpirada() + ", duracaoMinutos=" + this.getDuracaoMinutos() + ", dataCriacao=" + this.getDataCriacao() + ", dataAtualizacao=" + this.getDataAtualizacao() + ")";
    }

    @java.lang.SuppressWarnings("all")
    public SessaoAuditoriaDTO() {
    }

    /**
     * Creates a new {@code SessaoAuditoriaDTO} instance.
     *
     * @param id ID da sessão de auditoria.
     * @param usuarioId ID do usuário da sessão.
     * @param usuarioNome Nome do usuário da sessão.
     * @param ipOrigem IP de origem da conexão.
     * @param userAgent User Agent do cliente.
     * @param dataInicio Data e hora de início da sessão.
     * @param dataFim Data e hora de término da sessão.
     * @param status Status da sessão (ATIVA, ENCERRADA, EXPIRADA).
     * @param tokenSessao Token identificador da sessão.
     * @param dadosSessao Dados persistidos da sessão (JSON).
     * @param atividadesRealizadas Registro de atividades durante a sessão.
     * @param dadosExtras Dados extras em formato JSON.
     * @param ativa Indica se a sessão está ativa.
     * @param encerrada Indica se a sessão foi encerrada formalmente.
     * @param expirada Indica se a sessão expirou por inatividade.
     * @param duracaoMinutos Duração total em minutos.
     * @param dataCriacao Data de criação do registro.
     * @param dataAtualizacao Data da última atualização.
     */
    @java.lang.SuppressWarnings("all")
    public SessaoAuditoriaDTO(final Long id, final Long usuarioId, final String usuarioNome, final String ipOrigem, final String userAgent, final LocalDateTime dataInicio, final LocalDateTime dataFim, final SessaoAuditoria.StatusSessao status, final String tokenSessao, final String dadosSessao, final String atividadesRealizadas, final String dadosExtras, final Boolean ativa, final Boolean encerrada, final Boolean expirada, final Long duracaoMinutos, final String dataCriacao, final String dataAtualizacao) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.usuarioNome = usuarioNome;
        this.ipOrigem = ipOrigem;
        this.userAgent = userAgent;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.status = status;
        this.tokenSessao = tokenSessao;
        this.dadosSessao = dadosSessao;
        this.atividadesRealizadas = atividadesRealizadas;
        this.dadosExtras = dadosExtras;
        this.ativa = ativa;
        this.encerrada = encerrada;
        this.expirada = expirada;
        this.duracaoMinutos = duracaoMinutos;
        this.dataCriacao = dataCriacao;
        this.dataAtualizacao = dataAtualizacao;
    }
}
