package com.aurix.platform.shared.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

/**
 * Entidade LogAuditoria do Aurix.
 * Representa um log de auditoria.
 */
@Entity
@Table(name = "logs_auditoria", schema = "aurix")
public class LogAuditoria extends BaseEntity {
    /**
     * Comprimento padrão para campos de descrição longa.
     */
    private static final int DESCRIPTION_MAX_LENGTH = 1000;
    /**
     * Ação realizada (ex: LOGIN, CREATE, UPDATE).
     */
    @NotBlank(message = "Ação é obrigatória")
    @Column(nullable = false)
    private String acao;
    /**
     * Descrição detalhada do evento registrado.
     */
    @Column(length = DESCRIPTION_MAX_LENGTH)
    private String descricao;
    /**
     * Nome da entidade afetada pelo log.
     */
    @NotNull(message = "Entidade é obrigatória")
    @Column(name = "entidade", nullable = false)
    private String entidade;
    /**
     * Identificador único da instância da entidade.
     */
    @Column(name = "entidade_id")
    private Long entidadeId;
    /**
     * Identificador do usuário que realizou a ação.
     */
    @Column(name = "usuario_id")
    private Long usuarioId;
    /**
     * Nome do usuário que realizou a ação.
     */
    @Column(name = "usuario_nome")
    private String usuarioNome;
    /**
     * Endereço IP de onde partiu a solicitação.
     */
    @Column(name = "ip_origem")
    private String ipOrigem;
    /**
     * Identificação do navegador ou cliente (User-Agent).
     */
    @Column(name = "user_agent")
    private String userAgent;
    /**
     * Data e hora precisa em que a ação ocorreu.
     */
    @Column(name = "data_acao", nullable = false)
    private LocalDateTime dataAcao = LocalDateTime.now();
    /**
     * Tipo técnico da ação executada.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_acao", nullable = false)
    private TipoAcao tipoAcao;
    /**
     * Categoria sistêmica da auditoria.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "categoria", nullable = false)
    private CategoriaAuditoria categoria;
    /**
     * Nível de severidade do registro (INFO, ERROR, etc.).
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "nivel", nullable = false)
    private NivelAuditoria nivel = NivelAuditoria.INFO;
    /**
     * Estado dos dados antes da modificação (JSON).
     */
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "dados_anteriores", columnDefinition = "jsonb")
    private String dadosAnteriores;
    /**
     * Estado dos dados após a modificação (JSON).
     */
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "dados_novos", columnDefinition = "jsonb")
    private String dadosNovos;
    /**
     * Resultado qualitativo da operação (SUCESSO, FALHA).
     */
    @Column(name = "resultado")
    private String resultado;
    /**
     * Código de erro retornado, se aplicável.
     */
    @Column(name = "codigo_erro")
    private String codigoErro;
    /**
     * Mensagem técnica detalhada do erro, se aplicável.
     */
    @Column(name = "mensagem_erro", length = DESCRIPTION_MAX_LENGTH)
    private String mensagemErro;
    /**
     * Metadados suplementares do log (JSON).
     */
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "dados_extras", columnDefinition = "jsonb")
    private String dadosExtras;

    /**
     * Verifica se a ação foi bem-sucedida.
     *
     * @return true se sucesso, false caso contrário.
     */
    public boolean isSucesso() {
        return "SUCESSO".equals(resultado);
    }

    /**
     * Verifica se a ação falhou.
     *
     * @return true se falha, false caso contrário.
     */
    public boolean isFalha() {
        return "FALHA".equals(resultado) || "ERRO".equals(resultado);
    }

    /**
     * Verifica se é uma ação crítica.
     *
     * @return true se crítica, false caso contrário.
     */
    public boolean isCritica() {
        return nivel == NivelAuditoria.CRITICO;
    }


    /**
     * Enum para tipo de ação.
     */
    public enum TipoAcao {
        /**
         * Criar.
         */
        CREATE("Criar"), /**
         * Ler.
         */
        READ("Ler"), /**
         * Atualizar.
         */
        UPDATE("Atualizar"), /**
         * Deletar.
         */
        DELETE("Deletar"), /**
         * Login.
         */
        LOGIN("Login"), /**
         * Logout.
         */
        LOGOUT("Logout"), /**
         * Exportar.
         */
        EXPORT("Exportar"), /**
         * Importar.
         */
        IMPORT("Importar"), /**
         * Aprovar.
         */
        APPROVE("Aprovar"), /**
         * Rejeitar.
         */
        REJECT("Rejeitar"), /**
         * Cancelar.
         */
        CANCEL("Cancelar");
        /**
         * Descrição do tipo de ação.
         */
        private final String descricao;

        TipoAcao(final String desc) {
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
     * Enum para categoria de auditoria.
     */
    public enum CategoriaAuditoria {
        /**
         * Segurança.
         */
        SEGURANCA("Segurança"), /**
         * Financeiro.
         */
        FINANCEIRO("Financeiro"), /**
         * Operacional.
         */
        OPERACIONAL("Operacional"), /**
         * Cliente.
         */
        CLIENTE("Cliente"), /**
         * Compliance.
         */
        COMPLIANCE("Compliance"), /**
         * Sistema.
         */
        SISTEMA("Sistema"), /**
         * Usuário.
         */
        USUARIO("Usuário");
        /**
         * Descrição da categoria.
         */
        private final String descricao;

        CategoriaAuditoria(final String desc) {
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
     * Enum para nível de auditoria.
     */
    public enum NivelAuditoria {
        /**
         * Debug.
         */
        DEBUG("Debug"), /**
         * Informação.
         */
        INFO("Informação"), /**
         * Aviso.
         */
        WARN("Aviso"), /**
         * Erro.
         */
        ERROR("Erro"), /**
         * Crítico.
         */
        CRITICO("Crítico");
        /**
         * Descrição do nível.
         */
        private final String descricao;

        NivelAuditoria(final String desc) {
            this.descricao = desc;
        }

        /**
         * Retorna a descrição do nível.
         * 
         * @return a descrição.
         */
        public String getDescricao() {
            return descricao;
        }
    }

    /**
     * Ação realizada (ex: LOGIN, CREATE, UPDATE).
     */
    @java.lang.SuppressWarnings("all")
    public String getAcao() {
        return this.acao;
    }

    /**
     * Descrição detalhada do evento registrado.
     */
    @java.lang.SuppressWarnings("all")
    public String getDescricao() {
        return this.descricao;
    }

    /**
     * Nome da entidade afetada pelo log.
     */
    @java.lang.SuppressWarnings("all")
    public String getEntidade() {
        return this.entidade;
    }

    /**
     * Identificador único da instância da entidade.
     */
    @java.lang.SuppressWarnings("all")
    public Long getEntidadeId() {
        return this.entidadeId;
    }

    /**
     * Identificador do usuário que realizou a ação.
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
     * Endereço IP de onde partiu a solicitação.
     */
    @java.lang.SuppressWarnings("all")
    public String getIpOrigem() {
        return this.ipOrigem;
    }

    /**
     * Identificação do navegador ou cliente (User-Agent).
     */
    @java.lang.SuppressWarnings("all")
    public String getUserAgent() {
        return this.userAgent;
    }

    /**
     * Data e hora precisa em que a ação ocorreu.
     */
    @java.lang.SuppressWarnings("all")
    public LocalDateTime getDataAcao() {
        return this.dataAcao;
    }

    /**
     * Tipo técnico da ação executada.
     */
    @java.lang.SuppressWarnings("all")
    public TipoAcao getTipoAcao() {
        return this.tipoAcao;
    }

    /**
     * Categoria sistêmica da auditoria.
     */
    @java.lang.SuppressWarnings("all")
    public CategoriaAuditoria getCategoria() {
        return this.categoria;
    }

    /**
     * Nível de severidade do registro (INFO, ERROR, etc.).
     */
    @java.lang.SuppressWarnings("all")
    public NivelAuditoria getNivel() {
        return this.nivel;
    }

    /**
     * Estado dos dados antes da modificação (JSON).
     */
    @java.lang.SuppressWarnings("all")
    public String getDadosAnteriores() {
        return this.dadosAnteriores;
    }

    /**
     * Estado dos dados após a modificação (JSON).
     */
    @java.lang.SuppressWarnings("all")
    public String getDadosNovos() {
        return this.dadosNovos;
    }

    /**
     * Resultado qualitativo da operação (SUCESSO, FALHA).
     */
    @java.lang.SuppressWarnings("all")
    public String getResultado() {
        return this.resultado;
    }

    /**
     * Código de erro retornado, se aplicável.
     */
    @java.lang.SuppressWarnings("all")
    public String getCodigoErro() {
        return this.codigoErro;
    }

    /**
     * Mensagem técnica detalhada do erro, se aplicável.
     */
    @java.lang.SuppressWarnings("all")
    public String getMensagemErro() {
        return this.mensagemErro;
    }

    /**
     * Metadados suplementares do log (JSON).
     */
    @java.lang.SuppressWarnings("all")
    public String getDadosExtras() {
        return this.dadosExtras;
    }

    /**
     * Ação realizada (ex: LOGIN, CREATE, UPDATE).
     */
    @java.lang.SuppressWarnings("all")
    public void setAcao(final String acao) {
        this.acao = acao;
    }

    /**
     * Descrição detalhada do evento registrado.
     */
    @java.lang.SuppressWarnings("all")
    public void setDescricao(final String descricao) {
        this.descricao = descricao;
    }

    /**
     * Nome da entidade afetada pelo log.
     */
    @java.lang.SuppressWarnings("all")
    public void setEntidade(final String entidade) {
        this.entidade = entidade;
    }

    /**
     * Identificador único da instância da entidade.
     */
    @java.lang.SuppressWarnings("all")
    public void setEntidadeId(final Long entidadeId) {
        this.entidadeId = entidadeId;
    }

    /**
     * Identificador do usuário que realizou a ação.
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
     * Endereço IP de onde partiu a solicitação.
     */
    @java.lang.SuppressWarnings("all")
    public void setIpOrigem(final String ipOrigem) {
        this.ipOrigem = ipOrigem;
    }

    /**
     * Identificação do navegador ou cliente (User-Agent).
     */
    @java.lang.SuppressWarnings("all")
    public void setUserAgent(final String userAgent) {
        this.userAgent = userAgent;
    }

    /**
     * Data e hora precisa em que a ação ocorreu.
     */
    @java.lang.SuppressWarnings("all")
    public void setDataAcao(final LocalDateTime dataAcao) {
        this.dataAcao = dataAcao;
    }

    /**
     * Tipo técnico da ação executada.
     */
    @java.lang.SuppressWarnings("all")
    public void setTipoAcao(final TipoAcao tipoAcao) {
        this.tipoAcao = tipoAcao;
    }

    /**
     * Categoria sistêmica da auditoria.
     */
    @java.lang.SuppressWarnings("all")
    public void setCategoria(final CategoriaAuditoria categoria) {
        this.categoria = categoria;
    }

    /**
     * Nível de severidade do registro (INFO, ERROR, etc.).
     */
    @java.lang.SuppressWarnings("all")
    public void setNivel(final NivelAuditoria nivel) {
        this.nivel = nivel;
    }

    /**
     * Estado dos dados antes da modificação (JSON).
     */
    @java.lang.SuppressWarnings("all")
    public void setDadosAnteriores(final String dadosAnteriores) {
        this.dadosAnteriores = dadosAnteriores;
    }

    /**
     * Estado dos dados após a modificação (JSON).
     */
    @java.lang.SuppressWarnings("all")
    public void setDadosNovos(final String dadosNovos) {
        this.dadosNovos = dadosNovos;
    }

    /**
     * Resultado qualitativo da operação (SUCESSO, FALHA).
     */
    @java.lang.SuppressWarnings("all")
    public void setResultado(final String resultado) {
        this.resultado = resultado;
    }

    /**
     * Código de erro retornado, se aplicável.
     */
    @java.lang.SuppressWarnings("all")
    public void setCodigoErro(final String codigoErro) {
        this.codigoErro = codigoErro;
    }

    /**
     * Mensagem técnica detalhada do erro, se aplicável.
     */
    @java.lang.SuppressWarnings("all")
    public void setMensagemErro(final String mensagemErro) {
        this.mensagemErro = mensagemErro;
    }

    /**
     * Metadados suplementares do log (JSON).
     */
    @java.lang.SuppressWarnings("all")
    public void setDadosExtras(final String dadosExtras) {
        this.dadosExtras = dadosExtras;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
        return "LogAuditoria(acao=" + this.getAcao() + ", descricao=" + this.getDescricao() + ", entidade=" + this.getEntidade() + ", entidadeId=" + this.getEntidadeId() + ", usuarioId=" + this.getUsuarioId() + ", usuarioNome=" + this.getUsuarioNome() + ", ipOrigem=" + this.getIpOrigem() + ", userAgent=" + this.getUserAgent() + ", dataAcao=" + this.getDataAcao() + ", tipoAcao=" + this.getTipoAcao() + ", categoria=" + this.getCategoria() + ", nivel=" + this.getNivel() + ", dadosAnteriores=" + this.getDadosAnteriores() + ", dadosNovos=" + this.getDadosNovos() + ", resultado=" + this.getResultado() + ", codigoErro=" + this.getCodigoErro() + ", mensagemErro=" + this.getMensagemErro() + ", dadosExtras=" + this.getDadosExtras() + ")";
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public boolean equals(final java.lang.Object o) {
        if (o == this) return true;
        if (!(o instanceof LogAuditoria)) return false;
        final LogAuditoria other = (LogAuditoria) o;
        if (!other.canEqual((java.lang.Object) this)) return false;
        if (!super.equals(o)) return false;
        final java.lang.Object this$entidadeId = this.getEntidadeId();
        final java.lang.Object other$entidadeId = other.getEntidadeId();
        if (this$entidadeId == null ? other$entidadeId != null : !this$entidadeId.equals(other$entidadeId)) return false;
        final java.lang.Object this$usuarioId = this.getUsuarioId();
        final java.lang.Object other$usuarioId = other.getUsuarioId();
        if (this$usuarioId == null ? other$usuarioId != null : !this$usuarioId.equals(other$usuarioId)) return false;
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
        return true;
    }

    @java.lang.SuppressWarnings("all")
    protected boolean canEqual(final java.lang.Object other) {
        return other instanceof LogAuditoria;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public int hashCode() {
        final int PRIME = 59;
        int result = super.hashCode();
        final java.lang.Object $entidadeId = this.getEntidadeId();
        result = result * PRIME + ($entidadeId == null ? 43 : $entidadeId.hashCode());
        final java.lang.Object $usuarioId = this.getUsuarioId();
        result = result * PRIME + ($usuarioId == null ? 43 : $usuarioId.hashCode());
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
        return result;
    }

    @java.lang.SuppressWarnings("all")
    public LogAuditoria() {
    }

    /**
     * Creates a new {@code LogAuditoria} instance.
     *
     * @param acao Ação realizada (ex: LOGIN, CREATE, UPDATE).
     * @param descricao Descrição detalhada do evento registrado.
     * @param entidade Nome da entidade afetada pelo log.
     * @param entidadeId Identificador único da instância da entidade.
     * @param usuarioId Identificador do usuário que realizou a ação.
     * @param usuarioNome Nome do usuário que realizou a ação.
     * @param ipOrigem Endereço IP de onde partiu a solicitação.
     * @param userAgent Identificação do navegador ou cliente (User-Agent).
     * @param dataAcao Data e hora precisa em que a ação ocorreu.
     * @param tipoAcao Tipo técnico da ação executada.
     * @param categoria Categoria sistêmica da auditoria.
     * @param nivel Nível de severidade do registro (INFO, ERROR, etc.).
     * @param dadosAnteriores Estado dos dados antes da modificação (JSON).
     * @param dadosNovos Estado dos dados após a modificação (JSON).
     * @param resultado Resultado qualitativo da operação (SUCESSO, FALHA).
     * @param codigoErro Código de erro retornado, se aplicável.
     * @param mensagemErro Mensagem técnica detalhada do erro, se aplicável.
     * @param dadosExtras Metadados suplementares do log (JSON).
     */
    @java.lang.SuppressWarnings("all")
    public LogAuditoria(final String acao, final String descricao, final String entidade, final Long entidadeId, final Long usuarioId, final String usuarioNome, final String ipOrigem, final String userAgent, final LocalDateTime dataAcao, final TipoAcao tipoAcao, final CategoriaAuditoria categoria, final NivelAuditoria nivel, final String dadosAnteriores, final String dadosNovos, final String resultado, final String codigoErro, final String mensagemErro, final String dadosExtras) {
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
    }
}
