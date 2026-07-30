package com.aurix.platform.shared.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

/**
 * Entidade SessaoAuditoria do Aurix.
 * Representa uma sessão de auditoria.
 */
@Entity
@Table(name = "sessoes_auditoria", schema = "aurix")
public class SessaoAuditoria extends BaseEntity {
    /**
     * Identificador único do usuário em sessão.
     */
    @NotNull(message = "Usuário é obrigatório")
    @Column(name = "usuario_id", nullable = false)
    private Long usuarioId;
    /**
     * Nome amigável do usuário logado.
     */
    @Column(name = "usuario_nome")
    private String usuarioNome;
    /**
     * Endereço IP de origem da conexão.
     */
    @Column(name = "ip_origem")
    private String ipOrigem;
    /**
     * Navegador ou cliente utilizado (User-Agent).
     */
    @Column(name = "user_agent")
    private String userAgent;
    /**
     * Data e hora em que a sessão foi estabelecida.
     */
    @Column(name = "data_inicio", nullable = false)
    private LocalDateTime dataInicio = LocalDateTime.now();
    /**
     * Data e hora de encerramento ou expiração.
     */
    @Column(name = "data_fim")
    private LocalDateTime dataFim;
    /**
     * Estado atual da conectividade da sessão.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private StatusSessao status = StatusSessao.ATIVA;
    /**
     * Identificador alfanumérico único da sessão (JWT ou similar).
     */
    @Column(name = "token_sessao")
    private String tokenSessao;
    /**
     * Metadados contextuais da sessão (JSON).
     */
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "dados_sessao", columnDefinition = "jsonb")
    private String dadosSessao;
    /**
     * Registro cronológico resumido das ações (JSON).
     */
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "atividades_realizadas", columnDefinition = "jsonb")
    private String atividadesRealizadas;
    /**
     * Informações suplementares e técnicas (JSON).
     */
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "dados_extras", columnDefinition = "jsonb")
    private String dadosExtras;

    /**
     * Verifica se a sessão está ativa.
     *
     * @return true se ativa, false caso contrário.
     */
    public boolean isAtiva() {
        return status == StatusSessao.ATIVA;
    }

    /**
     * Verifica se a sessão foi encerrada.
     *
     * @return true se encerrada, false caso contrário.
     */
    public boolean isEncerrada() {
        return status == StatusSessao.ENCERRADA;
    }

    /**
     * Verifica se a sessão expirou.
     *
     * @return true se expirada, false caso contrário.
     */
    public boolean isExpirada() {
        return status == StatusSessao.EXPIRADA;
    }

    /**
     * Calcula a duração da sessão.
     *
     * @return Long duração em minutos.
     */
    public Long getDuracaoMinutos() {
        if (dataFim == null) {
            return null;
        }
        return java.time.Duration.between(dataInicio, dataFim).toMinutes();
    }


    /**
     * Enum para status da sessão.
     */
    public enum StatusSessao {
        /**
         * Ativa.
         */
        ATIVA("Ativa"), /**
         * Encerrada.
         */
        ENCERRADA("Encerrada"), /**
         * Expirada.
         */
        EXPIRADA("Expirada"), /**
         * Bloqueada.
         */
        BLOQUEADA("Bloqueada");
        /**
         * Descrição do status.
         */
        private final String descricao;

        StatusSessao(final String desc) {
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
     * Identificador único do usuário em sessão.
     */
    @java.lang.SuppressWarnings("all")
    public Long getUsuarioId() {
        return this.usuarioId;
    }

    /**
     * Nome amigável do usuário logado.
     */
    @java.lang.SuppressWarnings("all")
    public String getUsuarioNome() {
        return this.usuarioNome;
    }

    /**
     * Endereço IP de origem da conexão.
     */
    @java.lang.SuppressWarnings("all")
    public String getIpOrigem() {
        return this.ipOrigem;
    }

    /**
     * Navegador ou cliente utilizado (User-Agent).
     */
    @java.lang.SuppressWarnings("all")
    public String getUserAgent() {
        return this.userAgent;
    }

    /**
     * Data e hora em que a sessão foi estabelecida.
     */
    @java.lang.SuppressWarnings("all")
    public LocalDateTime getDataInicio() {
        return this.dataInicio;
    }

    /**
     * Data e hora de encerramento ou expiração.
     */
    @java.lang.SuppressWarnings("all")
    public LocalDateTime getDataFim() {
        return this.dataFim;
    }

    /**
     * Estado atual da conectividade da sessão.
     */
    @java.lang.SuppressWarnings("all")
    public StatusSessao getStatus() {
        return this.status;
    }

    /**
     * Identificador alfanumérico único da sessão (JWT ou similar).
     */
    @java.lang.SuppressWarnings("all")
    public String getTokenSessao() {
        return this.tokenSessao;
    }

    /**
     * Metadados contextuais da sessão (JSON).
     */
    @java.lang.SuppressWarnings("all")
    public String getDadosSessao() {
        return this.dadosSessao;
    }

    /**
     * Registro cronológico resumido das ações (JSON).
     */
    @java.lang.SuppressWarnings("all")
    public String getAtividadesRealizadas() {
        return this.atividadesRealizadas;
    }

    /**
     * Informações suplementares e técnicas (JSON).
     */
    @java.lang.SuppressWarnings("all")
    public String getDadosExtras() {
        return this.dadosExtras;
    }

    /**
     * Identificador único do usuário em sessão.
     */
    @java.lang.SuppressWarnings("all")
    public void setUsuarioId(final Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    /**
     * Nome amigável do usuário logado.
     */
    @java.lang.SuppressWarnings("all")
    public void setUsuarioNome(final String usuarioNome) {
        this.usuarioNome = usuarioNome;
    }

    /**
     * Endereço IP de origem da conexão.
     */
    @java.lang.SuppressWarnings("all")
    public void setIpOrigem(final String ipOrigem) {
        this.ipOrigem = ipOrigem;
    }

    /**
     * Navegador ou cliente utilizado (User-Agent).
     */
    @java.lang.SuppressWarnings("all")
    public void setUserAgent(final String userAgent) {
        this.userAgent = userAgent;
    }

    /**
     * Data e hora em que a sessão foi estabelecida.
     */
    @java.lang.SuppressWarnings("all")
    public void setDataInicio(final LocalDateTime dataInicio) {
        this.dataInicio = dataInicio;
    }

    /**
     * Data e hora de encerramento ou expiração.
     */
    @java.lang.SuppressWarnings("all")
    public void setDataFim(final LocalDateTime dataFim) {
        this.dataFim = dataFim;
    }

    /**
     * Estado atual da conectividade da sessão.
     */
    @java.lang.SuppressWarnings("all")
    public void setStatus(final StatusSessao status) {
        this.status = status;
    }

    /**
     * Identificador alfanumérico único da sessão (JWT ou similar).
     */
    @java.lang.SuppressWarnings("all")
    public void setTokenSessao(final String tokenSessao) {
        this.tokenSessao = tokenSessao;
    }

    /**
     * Metadados contextuais da sessão (JSON).
     */
    @java.lang.SuppressWarnings("all")
    public void setDadosSessao(final String dadosSessao) {
        this.dadosSessao = dadosSessao;
    }

    /**
     * Registro cronológico resumido das ações (JSON).
     */
    @java.lang.SuppressWarnings("all")
    public void setAtividadesRealizadas(final String atividadesRealizadas) {
        this.atividadesRealizadas = atividadesRealizadas;
    }

    /**
     * Informações suplementares e técnicas (JSON).
     */
    @java.lang.SuppressWarnings("all")
    public void setDadosExtras(final String dadosExtras) {
        this.dadosExtras = dadosExtras;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
        return "SessaoAuditoria(usuarioId=" + this.getUsuarioId() + ", usuarioNome=" + this.getUsuarioNome() + ", ipOrigem=" + this.getIpOrigem() + ", userAgent=" + this.getUserAgent() + ", dataInicio=" + this.getDataInicio() + ", dataFim=" + this.getDataFim() + ", status=" + this.getStatus() + ", tokenSessao=" + this.getTokenSessao() + ", dadosSessao=" + this.getDadosSessao() + ", atividadesRealizadas=" + this.getAtividadesRealizadas() + ", dadosExtras=" + this.getDadosExtras() + ")";
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public boolean equals(final java.lang.Object o) {
        if (o == this) return true;
        if (!(o instanceof SessaoAuditoria)) return false;
        final SessaoAuditoria other = (SessaoAuditoria) o;
        if (!other.canEqual((java.lang.Object) this)) return false;
        if (!super.equals(o)) return false;
        final java.lang.Object this$usuarioId = this.getUsuarioId();
        final java.lang.Object other$usuarioId = other.getUsuarioId();
        if (this$usuarioId == null ? other$usuarioId != null : !this$usuarioId.equals(other$usuarioId)) return false;
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
        return true;
    }

    @java.lang.SuppressWarnings("all")
    protected boolean canEqual(final java.lang.Object other) {
        return other instanceof SessaoAuditoria;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public int hashCode() {
        final int PRIME = 59;
        int result = super.hashCode();
        final java.lang.Object $usuarioId = this.getUsuarioId();
        result = result * PRIME + ($usuarioId == null ? 43 : $usuarioId.hashCode());
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
        return result;
    }

    @java.lang.SuppressWarnings("all")
    public SessaoAuditoria() {
    }

    /**
     * Creates a new {@code SessaoAuditoria} instance.
     *
     * @param usuarioId Identificador único do usuário em sessão.
     * @param usuarioNome Nome amigável do usuário logado.
     * @param ipOrigem Endereço IP de origem da conexão.
     * @param userAgent Navegador ou cliente utilizado (User-Agent).
     * @param dataInicio Data e hora em que a sessão foi estabelecida.
     * @param dataFim Data e hora de encerramento ou expiração.
     * @param status Estado atual da conectividade da sessão.
     * @param tokenSessao Identificador alfanumérico único da sessão (JWT ou similar).
     * @param dadosSessao Metadados contextuais da sessão (JSON).
     * @param atividadesRealizadas Registro cronológico resumido das ações (JSON).
     * @param dadosExtras Informações suplementares e técnicas (JSON).
     */
    @java.lang.SuppressWarnings("all")
    public SessaoAuditoria(final Long usuarioId, final String usuarioNome, final String ipOrigem, final String userAgent, final LocalDateTime dataInicio, final LocalDateTime dataFim, final StatusSessao status, final String tokenSessao, final String dadosSessao, final String atividadesRealizadas, final String dadosExtras) {
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
    }
}
