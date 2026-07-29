package com.aurix.platform.platform.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "logs_atividade_internet_banking", schema = "aurix")
public class LogAtividadeInternetBanking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "log_id", unique = true, nullable = false, length = 100)
    private String logId;
    @Column(name = "sessao_id", nullable = false, length = 100)
    private String sessaoId;
    @Column(name = "cliente_id", nullable = false, length = 50)
    private String clienteId;
    @Column(name = "usuario_id", nullable = false, length = 50)
    private String usuarioId;
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_atividade", nullable = false)
    private TipoAtividade tipoAtividade;
    @Enumerated(EnumType.STRING)
    @Column(name = "categoria", nullable = false)
    private CategoriaAtividade categoria;
    @Column(name = "acao", nullable = false, length = 200)
    private String acao;
    @Column(name = "descricao", length = 500)
    private String descricao;
    @Column(name = "resultado", length = 100)
    private String resultado;
    @Column(name = "status", nullable = false, length = 50)
    private String status;
    @Column(name = "ip_address", length = 45)
    private String ipAddress;
    @Column(name = "user_agent", length = 500)
    private String userAgent;
    @Column(name = "device_id", length = 100)
    private String deviceId;
    @Column(name = "geolocalizacao", length = 200)
    private String geolocalizacao;
    @Column(name = "valor", precision = 15, scale = 2)
    private java.math.BigDecimal valor;
    @Column(name = "conta_envolvida", length = 50)
    private String contaEnvolvida;
    @Column(name = "transacao_id", length = 100)
    private String transacaoId;
    @Column(name = "tempo_resposta_ms")
    private Long tempoRespostaMs;
    @Column(name = "codigo_erro", length = 50)
    private String codigoErro;
    @Column(name = "mensagem_erro", length = 500)
    private String mensagemErro;
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "dados_adicionais", columnDefinition = "jsonb")
    private String dadosAdicionais;
    @Column(name = "data_atividade", nullable = false)
    private LocalDateTime dataAtividade;
    @CreationTimestamp
    @Column(name = "data_criacao", nullable = false, updatable = false)
    private LocalDateTime dataCriacao;

    public enum TipoAtividade {
        LOGIN, LOGOUT, NAVEGACAO, CONSULTA, TRANSACAO, CONFIGURACAO, SEGURANCA, ERRO, SUSPEITA, BLOQUEIO, DESBLOQUEIO, ALTERACAO_SENHA, MFA, BIOMETRICO, OUTROS;
    }

    public enum CategoriaAtividade {
        AUTENTICACAO, NAVEGACAO, CONSULTAS, TRANSACOES, CONFIGURACOES, SEGURANCA, SISTEMA, COMPLIANCE, SUPORTE, OUTROS;
    }

    @java.lang.SuppressWarnings("all")
    public static class LogAtividadeInternetBankingBuilder {
        @java.lang.SuppressWarnings("all")
        private Long id;
        @java.lang.SuppressWarnings("all")
        private String logId;
        @java.lang.SuppressWarnings("all")
        private String sessaoId;
        @java.lang.SuppressWarnings("all")
        private String clienteId;
        @java.lang.SuppressWarnings("all")
        private String usuarioId;
        @java.lang.SuppressWarnings("all")
        private TipoAtividade tipoAtividade;
        @java.lang.SuppressWarnings("all")
        private CategoriaAtividade categoria;
        @java.lang.SuppressWarnings("all")
        private String acao;
        @java.lang.SuppressWarnings("all")
        private String descricao;
        @java.lang.SuppressWarnings("all")
        private String resultado;
        @java.lang.SuppressWarnings("all")
        private String status;
        @java.lang.SuppressWarnings("all")
        private String ipAddress;
        @java.lang.SuppressWarnings("all")
        private String userAgent;
        @java.lang.SuppressWarnings("all")
        private String deviceId;
        @java.lang.SuppressWarnings("all")
        private String geolocalizacao;
        @java.lang.SuppressWarnings("all")
        private java.math.BigDecimal valor;
        @java.lang.SuppressWarnings("all")
        private String contaEnvolvida;
        @java.lang.SuppressWarnings("all")
        private String transacaoId;
        @java.lang.SuppressWarnings("all")
        private Long tempoRespostaMs;
        @java.lang.SuppressWarnings("all")
        private String codigoErro;
        @java.lang.SuppressWarnings("all")
        private String mensagemErro;
        @java.lang.SuppressWarnings("all")
        private String dadosAdicionais;
        @java.lang.SuppressWarnings("all")
        private LocalDateTime dataAtividade;
        @java.lang.SuppressWarnings("all")
        private LocalDateTime dataCriacao;

        @java.lang.SuppressWarnings("all")
        LogAtividadeInternetBankingBuilder() {
        }

        @java.lang.SuppressWarnings("all")
        public LogAtividadeInternetBanking.LogAtividadeInternetBankingBuilder id(final Long id) {
            this.id = id;
            return this;
        }

        @java.lang.SuppressWarnings("all")
        public LogAtividadeInternetBanking.LogAtividadeInternetBankingBuilder logId(final String logId) {
            this.logId = logId;
            return this;
        }

        @java.lang.SuppressWarnings("all")
        public LogAtividadeInternetBanking.LogAtividadeInternetBankingBuilder sessaoId(final String sessaoId) {
            this.sessaoId = sessaoId;
            return this;
        }

        @java.lang.SuppressWarnings("all")
        public LogAtividadeInternetBanking.LogAtividadeInternetBankingBuilder clienteId(final String clienteId) {
            this.clienteId = clienteId;
            return this;
        }

        @java.lang.SuppressWarnings("all")
        public LogAtividadeInternetBanking.LogAtividadeInternetBankingBuilder usuarioId(final String usuarioId) {
            this.usuarioId = usuarioId;
            return this;
        }

        @java.lang.SuppressWarnings("all")
        public LogAtividadeInternetBanking.LogAtividadeInternetBankingBuilder tipoAtividade(final TipoAtividade tipoAtividade) {
            this.tipoAtividade = tipoAtividade;
            return this;
        }

        @java.lang.SuppressWarnings("all")
        public LogAtividadeInternetBanking.LogAtividadeInternetBankingBuilder categoria(final CategoriaAtividade categoria) {
            this.categoria = categoria;
            return this;
        }

        @java.lang.SuppressWarnings("all")
        public LogAtividadeInternetBanking.LogAtividadeInternetBankingBuilder acao(final String acao) {
            this.acao = acao;
            return this;
        }

        @java.lang.SuppressWarnings("all")
        public LogAtividadeInternetBanking.LogAtividadeInternetBankingBuilder descricao(final String descricao) {
            this.descricao = descricao;
            return this;
        }

        @java.lang.SuppressWarnings("all")
        public LogAtividadeInternetBanking.LogAtividadeInternetBankingBuilder resultado(final String resultado) {
            this.resultado = resultado;
            return this;
        }

        @java.lang.SuppressWarnings("all")
        public LogAtividadeInternetBanking.LogAtividadeInternetBankingBuilder status(final String status) {
            this.status = status;
            return this;
        }

        @java.lang.SuppressWarnings("all")
        public LogAtividadeInternetBanking.LogAtividadeInternetBankingBuilder ipAddress(final String ipAddress) {
            this.ipAddress = ipAddress;
            return this;
        }

        @java.lang.SuppressWarnings("all")
        public LogAtividadeInternetBanking.LogAtividadeInternetBankingBuilder userAgent(final String userAgent) {
            this.userAgent = userAgent;
            return this;
        }

        @java.lang.SuppressWarnings("all")
        public LogAtividadeInternetBanking.LogAtividadeInternetBankingBuilder deviceId(final String deviceId) {
            this.deviceId = deviceId;
            return this;
        }

        @java.lang.SuppressWarnings("all")
        public LogAtividadeInternetBanking.LogAtividadeInternetBankingBuilder geolocalizacao(final String geolocalizacao) {
            this.geolocalizacao = geolocalizacao;
            return this;
        }

        @java.lang.SuppressWarnings("all")
        public LogAtividadeInternetBanking.LogAtividadeInternetBankingBuilder valor(final java.math.BigDecimal valor) {
            this.valor = valor;
            return this;
        }

        @java.lang.SuppressWarnings("all")
        public LogAtividadeInternetBanking.LogAtividadeInternetBankingBuilder contaEnvolvida(final String contaEnvolvida) {
            this.contaEnvolvida = contaEnvolvida;
            return this;
        }

        @java.lang.SuppressWarnings("all")
        public LogAtividadeInternetBanking.LogAtividadeInternetBankingBuilder transacaoId(final String transacaoId) {
            this.transacaoId = transacaoId;
            return this;
        }

        @java.lang.SuppressWarnings("all")
        public LogAtividadeInternetBanking.LogAtividadeInternetBankingBuilder tempoRespostaMs(final Long tempoRespostaMs) {
            this.tempoRespostaMs = tempoRespostaMs;
            return this;
        }

        @java.lang.SuppressWarnings("all")
        public LogAtividadeInternetBanking.LogAtividadeInternetBankingBuilder codigoErro(final String codigoErro) {
            this.codigoErro = codigoErro;
            return this;
        }

        @java.lang.SuppressWarnings("all")
        public LogAtividadeInternetBanking.LogAtividadeInternetBankingBuilder mensagemErro(final String mensagemErro) {
            this.mensagemErro = mensagemErro;
            return this;
        }

        @java.lang.SuppressWarnings("all")
        public LogAtividadeInternetBanking.LogAtividadeInternetBankingBuilder dadosAdicionais(final String dadosAdicionais) {
            this.dadosAdicionais = dadosAdicionais;
            return this;
        }

        @java.lang.SuppressWarnings("all")
        public LogAtividadeInternetBanking.LogAtividadeInternetBankingBuilder dataAtividade(final LocalDateTime dataAtividade) {
            this.dataAtividade = dataAtividade;
            return this;
        }

        @java.lang.SuppressWarnings("all")
        public LogAtividadeInternetBanking.LogAtividadeInternetBankingBuilder dataCriacao(final LocalDateTime dataCriacao) {
            this.dataCriacao = dataCriacao;
            return this;
        }

        @java.lang.SuppressWarnings("all")
        public LogAtividadeInternetBanking build() {
            return new LogAtividadeInternetBanking(this.id, this.logId, this.sessaoId, this.clienteId, this.usuarioId, this.tipoAtividade, this.categoria, this.acao, this.descricao, this.resultado, this.status, this.ipAddress, this.userAgent, this.deviceId, this.geolocalizacao, this.valor, this.contaEnvolvida, this.transacaoId, this.tempoRespostaMs, this.codigoErro, this.mensagemErro, this.dadosAdicionais, this.dataAtividade, this.dataCriacao);
        }

        @java.lang.Override
        @java.lang.SuppressWarnings("all")
        public java.lang.String toString() {
            return "LogAtividadeInternetBanking.LogAtividadeInternetBankingBuilder(id=" + this.id + ", logId=" + this.logId + ", sessaoId=" + this.sessaoId + ", clienteId=" + this.clienteId + ", usuarioId=" + this.usuarioId + ", tipoAtividade=" + this.tipoAtividade + ", categoria=" + this.categoria + ", acao=" + this.acao + ", descricao=" + this.descricao + ", resultado=" + this.resultado + ", status=" + this.status + ", ipAddress=" + this.ipAddress + ", userAgent=" + this.userAgent + ", deviceId=" + this.deviceId + ", geolocalizacao=" + this.geolocalizacao + ", valor=" + this.valor + ", contaEnvolvida=" + this.contaEnvolvida + ", transacaoId=" + this.transacaoId + ", tempoRespostaMs=" + this.tempoRespostaMs + ", codigoErro=" + this.codigoErro + ", mensagemErro=" + this.mensagemErro + ", dadosAdicionais=" + this.dadosAdicionais + ", dataAtividade=" + this.dataAtividade + ", dataCriacao=" + this.dataCriacao + ")";
        }
    }

    @java.lang.SuppressWarnings("all")
    public static LogAtividadeInternetBanking.LogAtividadeInternetBankingBuilder builder() {
        return new LogAtividadeInternetBanking.LogAtividadeInternetBankingBuilder();
    }

    @java.lang.SuppressWarnings("all")
    public Long getId() {
        return this.id;
    }

    @java.lang.SuppressWarnings("all")
    public String getLogId() {
        return this.logId;
    }

    @java.lang.SuppressWarnings("all")
    public String getSessaoId() {
        return this.sessaoId;
    }

    @java.lang.SuppressWarnings("all")
    public String getClienteId() {
        return this.clienteId;
    }

    @java.lang.SuppressWarnings("all")
    public String getUsuarioId() {
        return this.usuarioId;
    }

    @java.lang.SuppressWarnings("all")
    public TipoAtividade getTipoAtividade() {
        return this.tipoAtividade;
    }

    @java.lang.SuppressWarnings("all")
    public CategoriaAtividade getCategoria() {
        return this.categoria;
    }

    @java.lang.SuppressWarnings("all")
    public String getAcao() {
        return this.acao;
    }

    @java.lang.SuppressWarnings("all")
    public String getDescricao() {
        return this.descricao;
    }

    @java.lang.SuppressWarnings("all")
    public String getResultado() {
        return this.resultado;
    }

    @java.lang.SuppressWarnings("all")
    public String getStatus() {
        return this.status;
    }

    @java.lang.SuppressWarnings("all")
    public String getIpAddress() {
        return this.ipAddress;
    }

    @java.lang.SuppressWarnings("all")
    public String getUserAgent() {
        return this.userAgent;
    }

    @java.lang.SuppressWarnings("all")
    public String getDeviceId() {
        return this.deviceId;
    }

    @java.lang.SuppressWarnings("all")
    public String getGeolocalizacao() {
        return this.geolocalizacao;
    }

    @java.lang.SuppressWarnings("all")
    public java.math.BigDecimal getValor() {
        return this.valor;
    }

    @java.lang.SuppressWarnings("all")
    public String getContaEnvolvida() {
        return this.contaEnvolvida;
    }

    @java.lang.SuppressWarnings("all")
    public String getTransacaoId() {
        return this.transacaoId;
    }

    @java.lang.SuppressWarnings("all")
    public Long getTempoRespostaMs() {
        return this.tempoRespostaMs;
    }

    @java.lang.SuppressWarnings("all")
    public String getCodigoErro() {
        return this.codigoErro;
    }

    @java.lang.SuppressWarnings("all")
    public String getMensagemErro() {
        return this.mensagemErro;
    }

    @java.lang.SuppressWarnings("all")
    public String getDadosAdicionais() {
        return this.dadosAdicionais;
    }

    @java.lang.SuppressWarnings("all")
    public LocalDateTime getDataAtividade() {
        return this.dataAtividade;
    }

    @java.lang.SuppressWarnings("all")
    public LocalDateTime getDataCriacao() {
        return this.dataCriacao;
    }

    @java.lang.SuppressWarnings("all")
    public void setId(final Long id) {
        this.id = id;
    }

    @java.lang.SuppressWarnings("all")
    public void setLogId(final String logId) {
        this.logId = logId;
    }

    @java.lang.SuppressWarnings("all")
    public void setSessaoId(final String sessaoId) {
        this.sessaoId = sessaoId;
    }

    @java.lang.SuppressWarnings("all")
    public void setClienteId(final String clienteId) {
        this.clienteId = clienteId;
    }

    @java.lang.SuppressWarnings("all")
    public void setUsuarioId(final String usuarioId) {
        this.usuarioId = usuarioId;
    }

    @java.lang.SuppressWarnings("all")
    public void setTipoAtividade(final TipoAtividade tipoAtividade) {
        this.tipoAtividade = tipoAtividade;
    }

    @java.lang.SuppressWarnings("all")
    public void setCategoria(final CategoriaAtividade categoria) {
        this.categoria = categoria;
    }

    @java.lang.SuppressWarnings("all")
    public void setAcao(final String acao) {
        this.acao = acao;
    }

    @java.lang.SuppressWarnings("all")
    public void setDescricao(final String descricao) {
        this.descricao = descricao;
    }

    @java.lang.SuppressWarnings("all")
    public void setResultado(final String resultado) {
        this.resultado = resultado;
    }

    @java.lang.SuppressWarnings("all")
    public void setStatus(final String status) {
        this.status = status;
    }

    @java.lang.SuppressWarnings("all")
    public void setIpAddress(final String ipAddress) {
        this.ipAddress = ipAddress;
    }

    @java.lang.SuppressWarnings("all")
    public void setUserAgent(final String userAgent) {
        this.userAgent = userAgent;
    }

    @java.lang.SuppressWarnings("all")
    public void setDeviceId(final String deviceId) {
        this.deviceId = deviceId;
    }

    @java.lang.SuppressWarnings("all")
    public void setGeolocalizacao(final String geolocalizacao) {
        this.geolocalizacao = geolocalizacao;
    }

    @java.lang.SuppressWarnings("all")
    public void setValor(final java.math.BigDecimal valor) {
        this.valor = valor;
    }

    @java.lang.SuppressWarnings("all")
    public void setContaEnvolvida(final String contaEnvolvida) {
        this.contaEnvolvida = contaEnvolvida;
    }

    @java.lang.SuppressWarnings("all")
    public void setTransacaoId(final String transacaoId) {
        this.transacaoId = transacaoId;
    }

    @java.lang.SuppressWarnings("all")
    public void setTempoRespostaMs(final Long tempoRespostaMs) {
        this.tempoRespostaMs = tempoRespostaMs;
    }

    @java.lang.SuppressWarnings("all")
    public void setCodigoErro(final String codigoErro) {
        this.codigoErro = codigoErro;
    }

    @java.lang.SuppressWarnings("all")
    public void setMensagemErro(final String mensagemErro) {
        this.mensagemErro = mensagemErro;
    }

    @java.lang.SuppressWarnings("all")
    public void setDadosAdicionais(final String dadosAdicionais) {
        this.dadosAdicionais = dadosAdicionais;
    }

    @java.lang.SuppressWarnings("all")
    public void setDataAtividade(final LocalDateTime dataAtividade) {
        this.dataAtividade = dataAtividade;
    }

    @java.lang.SuppressWarnings("all")
    public void setDataCriacao(final LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public boolean equals(final java.lang.Object o) {
        if (o == this) return true;
        if (!(o instanceof LogAtividadeInternetBanking)) return false;
        final LogAtividadeInternetBanking other = (LogAtividadeInternetBanking) o;
        if (!other.canEqual((java.lang.Object) this)) return false;
        final java.lang.Object this$id = this.getId();
        final java.lang.Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final java.lang.Object this$tempoRespostaMs = this.getTempoRespostaMs();
        final java.lang.Object other$tempoRespostaMs = other.getTempoRespostaMs();
        if (this$tempoRespostaMs == null ? other$tempoRespostaMs != null : !this$tempoRespostaMs.equals(other$tempoRespostaMs)) return false;
        final java.lang.Object this$logId = this.getLogId();
        final java.lang.Object other$logId = other.getLogId();
        if (this$logId == null ? other$logId != null : !this$logId.equals(other$logId)) return false;
        final java.lang.Object this$sessaoId = this.getSessaoId();
        final java.lang.Object other$sessaoId = other.getSessaoId();
        if (this$sessaoId == null ? other$sessaoId != null : !this$sessaoId.equals(other$sessaoId)) return false;
        final java.lang.Object this$clienteId = this.getClienteId();
        final java.lang.Object other$clienteId = other.getClienteId();
        if (this$clienteId == null ? other$clienteId != null : !this$clienteId.equals(other$clienteId)) return false;
        final java.lang.Object this$usuarioId = this.getUsuarioId();
        final java.lang.Object other$usuarioId = other.getUsuarioId();
        if (this$usuarioId == null ? other$usuarioId != null : !this$usuarioId.equals(other$usuarioId)) return false;
        final java.lang.Object this$tipoAtividade = this.getTipoAtividade();
        final java.lang.Object other$tipoAtividade = other.getTipoAtividade();
        if (this$tipoAtividade == null ? other$tipoAtividade != null : !this$tipoAtividade.equals(other$tipoAtividade)) return false;
        final java.lang.Object this$categoria = this.getCategoria();
        final java.lang.Object other$categoria = other.getCategoria();
        if (this$categoria == null ? other$categoria != null : !this$categoria.equals(other$categoria)) return false;
        final java.lang.Object this$acao = this.getAcao();
        final java.lang.Object other$acao = other.getAcao();
        if (this$acao == null ? other$acao != null : !this$acao.equals(other$acao)) return false;
        final java.lang.Object this$descricao = this.getDescricao();
        final java.lang.Object other$descricao = other.getDescricao();
        if (this$descricao == null ? other$descricao != null : !this$descricao.equals(other$descricao)) return false;
        final java.lang.Object this$resultado = this.getResultado();
        final java.lang.Object other$resultado = other.getResultado();
        if (this$resultado == null ? other$resultado != null : !this$resultado.equals(other$resultado)) return false;
        final java.lang.Object this$status = this.getStatus();
        final java.lang.Object other$status = other.getStatus();
        if (this$status == null ? other$status != null : !this$status.equals(other$status)) return false;
        final java.lang.Object this$ipAddress = this.getIpAddress();
        final java.lang.Object other$ipAddress = other.getIpAddress();
        if (this$ipAddress == null ? other$ipAddress != null : !this$ipAddress.equals(other$ipAddress)) return false;
        final java.lang.Object this$userAgent = this.getUserAgent();
        final java.lang.Object other$userAgent = other.getUserAgent();
        if (this$userAgent == null ? other$userAgent != null : !this$userAgent.equals(other$userAgent)) return false;
        final java.lang.Object this$deviceId = this.getDeviceId();
        final java.lang.Object other$deviceId = other.getDeviceId();
        if (this$deviceId == null ? other$deviceId != null : !this$deviceId.equals(other$deviceId)) return false;
        final java.lang.Object this$geolocalizacao = this.getGeolocalizacao();
        final java.lang.Object other$geolocalizacao = other.getGeolocalizacao();
        if (this$geolocalizacao == null ? other$geolocalizacao != null : !this$geolocalizacao.equals(other$geolocalizacao)) return false;
        final java.lang.Object this$valor = this.getValor();
        final java.lang.Object other$valor = other.getValor();
        if (this$valor == null ? other$valor != null : !this$valor.equals(other$valor)) return false;
        final java.lang.Object this$contaEnvolvida = this.getContaEnvolvida();
        final java.lang.Object other$contaEnvolvida = other.getContaEnvolvida();
        if (this$contaEnvolvida == null ? other$contaEnvolvida != null : !this$contaEnvolvida.equals(other$contaEnvolvida)) return false;
        final java.lang.Object this$transacaoId = this.getTransacaoId();
        final java.lang.Object other$transacaoId = other.getTransacaoId();
        if (this$transacaoId == null ? other$transacaoId != null : !this$transacaoId.equals(other$transacaoId)) return false;
        final java.lang.Object this$codigoErro = this.getCodigoErro();
        final java.lang.Object other$codigoErro = other.getCodigoErro();
        if (this$codigoErro == null ? other$codigoErro != null : !this$codigoErro.equals(other$codigoErro)) return false;
        final java.lang.Object this$mensagemErro = this.getMensagemErro();
        final java.lang.Object other$mensagemErro = other.getMensagemErro();
        if (this$mensagemErro == null ? other$mensagemErro != null : !this$mensagemErro.equals(other$mensagemErro)) return false;
        final java.lang.Object this$dadosAdicionais = this.getDadosAdicionais();
        final java.lang.Object other$dadosAdicionais = other.getDadosAdicionais();
        if (this$dadosAdicionais == null ? other$dadosAdicionais != null : !this$dadosAdicionais.equals(other$dadosAdicionais)) return false;
        final java.lang.Object this$dataAtividade = this.getDataAtividade();
        final java.lang.Object other$dataAtividade = other.getDataAtividade();
        if (this$dataAtividade == null ? other$dataAtividade != null : !this$dataAtividade.equals(other$dataAtividade)) return false;
        final java.lang.Object this$dataCriacao = this.getDataCriacao();
        final java.lang.Object other$dataCriacao = other.getDataCriacao();
        if (this$dataCriacao == null ? other$dataCriacao != null : !this$dataCriacao.equals(other$dataCriacao)) return false;
        return true;
    }

    @java.lang.SuppressWarnings("all")
    protected boolean canEqual(final java.lang.Object other) {
        return other instanceof LogAtividadeInternetBanking;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final java.lang.Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final java.lang.Object $tempoRespostaMs = this.getTempoRespostaMs();
        result = result * PRIME + ($tempoRespostaMs == null ? 43 : $tempoRespostaMs.hashCode());
        final java.lang.Object $logId = this.getLogId();
        result = result * PRIME + ($logId == null ? 43 : $logId.hashCode());
        final java.lang.Object $sessaoId = this.getSessaoId();
        result = result * PRIME + ($sessaoId == null ? 43 : $sessaoId.hashCode());
        final java.lang.Object $clienteId = this.getClienteId();
        result = result * PRIME + ($clienteId == null ? 43 : $clienteId.hashCode());
        final java.lang.Object $usuarioId = this.getUsuarioId();
        result = result * PRIME + ($usuarioId == null ? 43 : $usuarioId.hashCode());
        final java.lang.Object $tipoAtividade = this.getTipoAtividade();
        result = result * PRIME + ($tipoAtividade == null ? 43 : $tipoAtividade.hashCode());
        final java.lang.Object $categoria = this.getCategoria();
        result = result * PRIME + ($categoria == null ? 43 : $categoria.hashCode());
        final java.lang.Object $acao = this.getAcao();
        result = result * PRIME + ($acao == null ? 43 : $acao.hashCode());
        final java.lang.Object $descricao = this.getDescricao();
        result = result * PRIME + ($descricao == null ? 43 : $descricao.hashCode());
        final java.lang.Object $resultado = this.getResultado();
        result = result * PRIME + ($resultado == null ? 43 : $resultado.hashCode());
        final java.lang.Object $status = this.getStatus();
        result = result * PRIME + ($status == null ? 43 : $status.hashCode());
        final java.lang.Object $ipAddress = this.getIpAddress();
        result = result * PRIME + ($ipAddress == null ? 43 : $ipAddress.hashCode());
        final java.lang.Object $userAgent = this.getUserAgent();
        result = result * PRIME + ($userAgent == null ? 43 : $userAgent.hashCode());
        final java.lang.Object $deviceId = this.getDeviceId();
        result = result * PRIME + ($deviceId == null ? 43 : $deviceId.hashCode());
        final java.lang.Object $geolocalizacao = this.getGeolocalizacao();
        result = result * PRIME + ($geolocalizacao == null ? 43 : $geolocalizacao.hashCode());
        final java.lang.Object $valor = this.getValor();
        result = result * PRIME + ($valor == null ? 43 : $valor.hashCode());
        final java.lang.Object $contaEnvolvida = this.getContaEnvolvida();
        result = result * PRIME + ($contaEnvolvida == null ? 43 : $contaEnvolvida.hashCode());
        final java.lang.Object $transacaoId = this.getTransacaoId();
        result = result * PRIME + ($transacaoId == null ? 43 : $transacaoId.hashCode());
        final java.lang.Object $codigoErro = this.getCodigoErro();
        result = result * PRIME + ($codigoErro == null ? 43 : $codigoErro.hashCode());
        final java.lang.Object $mensagemErro = this.getMensagemErro();
        result = result * PRIME + ($mensagemErro == null ? 43 : $mensagemErro.hashCode());
        final java.lang.Object $dadosAdicionais = this.getDadosAdicionais();
        result = result * PRIME + ($dadosAdicionais == null ? 43 : $dadosAdicionais.hashCode());
        final java.lang.Object $dataAtividade = this.getDataAtividade();
        result = result * PRIME + ($dataAtividade == null ? 43 : $dataAtividade.hashCode());
        final java.lang.Object $dataCriacao = this.getDataCriacao();
        result = result * PRIME + ($dataCriacao == null ? 43 : $dataCriacao.hashCode());
        return result;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
        return "LogAtividadeInternetBanking(id=" + this.getId() + ", logId=" + this.getLogId() + ", sessaoId=" + this.getSessaoId() + ", clienteId=" + this.getClienteId() + ", usuarioId=" + this.getUsuarioId() + ", tipoAtividade=" + this.getTipoAtividade() + ", categoria=" + this.getCategoria() + ", acao=" + this.getAcao() + ", descricao=" + this.getDescricao() + ", resultado=" + this.getResultado() + ", status=" + this.getStatus() + ", ipAddress=" + this.getIpAddress() + ", userAgent=" + this.getUserAgent() + ", deviceId=" + this.getDeviceId() + ", geolocalizacao=" + this.getGeolocalizacao() + ", valor=" + this.getValor() + ", contaEnvolvida=" + this.getContaEnvolvida() + ", transacaoId=" + this.getTransacaoId() + ", tempoRespostaMs=" + this.getTempoRespostaMs() + ", codigoErro=" + this.getCodigoErro() + ", mensagemErro=" + this.getMensagemErro() + ", dadosAdicionais=" + this.getDadosAdicionais() + ", dataAtividade=" + this.getDataAtividade() + ", dataCriacao=" + this.getDataCriacao() + ")";
    }

    @java.lang.SuppressWarnings("all")
    public LogAtividadeInternetBanking() {
    }

    @java.lang.SuppressWarnings("all")
    public LogAtividadeInternetBanking(final Long id, final String logId, final String sessaoId, final String clienteId, final String usuarioId, final TipoAtividade tipoAtividade, final CategoriaAtividade categoria, final String acao, final String descricao, final String resultado, final String status, final String ipAddress, final String userAgent, final String deviceId, final String geolocalizacao, final java.math.BigDecimal valor, final String contaEnvolvida, final String transacaoId, final Long tempoRespostaMs, final String codigoErro, final String mensagemErro, final String dadosAdicionais, final LocalDateTime dataAtividade, final LocalDateTime dataCriacao) {
        this.id = id;
        this.logId = logId;
        this.sessaoId = sessaoId;
        this.clienteId = clienteId;
        this.usuarioId = usuarioId;
        this.tipoAtividade = tipoAtividade;
        this.categoria = categoria;
        this.acao = acao;
        this.descricao = descricao;
        this.resultado = resultado;
        this.status = status;
        this.ipAddress = ipAddress;
        this.userAgent = userAgent;
        this.deviceId = deviceId;
        this.geolocalizacao = geolocalizacao;
        this.valor = valor;
        this.contaEnvolvida = contaEnvolvida;
        this.transacaoId = transacaoId;
        this.tempoRespostaMs = tempoRespostaMs;
        this.codigoErro = codigoErro;
        this.mensagemErro = mensagemErro;
        this.dadosAdicionais = dadosAdicionais;
        this.dataAtividade = dataAtividade;
        this.dataCriacao = dataCriacao;
    }
}
