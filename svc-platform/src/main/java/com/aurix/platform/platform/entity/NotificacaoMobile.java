package com.aurix.platform.platform.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

/**
 * Entidade que representa uma notificação mobile
 * 
 * Gerencia notificações push para dispositivos móveis
 */
@Entity
@Table(name = "notificacoes_mobile", schema = "aurix")
public class NotificacaoMobile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "notificacao_id", unique = true, nullable = false, length = 100)
    private String notificacaoId;
    @Column(name = "dispositivo_id", nullable = false, length = 100)
    private String dispositivoId;
    @Column(name = "cliente_id", nullable = false, length = 50)
    private String clienteId;
    @Column(name = "device_token", nullable = false, length = 500)
    private String deviceToken;
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_notificacao", nullable = false)
    private TipoNotificacao tipoNotificacao;
    @Enumerated(EnumType.STRING)
    @Column(name = "categoria", nullable = false)
    private CategoriaNotificacao categoria;
    @Column(name = "titulo", nullable = false, length = 200)
    private String titulo;
    @Column(name = "mensagem", nullable = false, length = 1000)
    private String mensagem;
    @Column(name = "corpo", length = 2000)
    private String corpo;
    @Column(name = "icone", length = 100)
    private String icone;
    @Column(name = "imagem", length = 200)
    private String imagem;
    @Column(name = "som", length = 100)
    private String som;
    @Column(name = "vibracao", nullable = false)
    private Boolean vibracao;
    @Column(name = "led_color", length = 10)
    private String ledColor;
    @Enumerated(EnumType.STRING)
    @Column(name = "prioridade", nullable = false)
    private PrioridadeNotificacao prioridade;
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private StatusNotificacao status;
    @Column(name = "data_agendamento")
    private LocalDateTime dataAgendamento;
    @Column(name = "data_envio")
    private LocalDateTime dataEnvio;
    @Column(name = "data_entrega")
    private LocalDateTime dataEntrega;
    @Column(name = "data_leitura")
    private LocalDateTime dataLeitura;
    @Column(name = "data_expiracao")
    private LocalDateTime dataExpiracao;
    @Column(name = "tentativas_envio")
    private Integer tentativasEnvio;
    @Column(name = "max_tentativas")
    private Integer maxTentativas;
    @Column(name = "codigo_erro", length = 50)
    private String codigoErro;
    @Column(name = "mensagem_erro", length = 500)
    private String mensagemErro;
    @Column(name = "transacao_id", length = 100)
    private String transacaoId;
    @Column(name = "valor", precision = 15, scale = 2)
    private java.math.BigDecimal valor;
    @Column(name = "conta_envolvida", length = 50)
    private String contaEnvolvida;
    @Column(name = "acao_url", length = 500)
    private String acaoUrl;
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "dados_extras", columnDefinition = "jsonb")
    private String dadosExtras;
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "metadados", columnDefinition = "jsonb")
    private String metadados;
    @CreationTimestamp
    @Column(name = "data_criacao", nullable = false, updatable = false)
    private LocalDateTime dataCriacao;


    /**
     * Tipo de notificação
     */
    public enum TipoNotificacao {
        TRANSACAO,  // Notificação de transação
        SEGURANCA,  // Notificação de segurança
        PROMOCIONAL,  // Notificação promocional
        INFORMATIVA,  // Notificação informativa
        URGENTE,  // Notificação urgente
        PIX,  // Notificação PIX
        CARTAO,  // Notificação de cartão
        INVESTIMENTO,  // Notificação de investimento
        EMPRESTIMO,  // Notificação de empréstimo
        FATURA,  // Notificação de fatura
        LIMITE,  // Notificação de limite
        BLOQUEIO,  // Notificação de bloqueio
        DESBLOQUEIO,  // Notificação de desbloqueio
        ATUALIZACAO,  // Notificação de atualização
        MANUTENCAO,  // Notificação de manutenção
        OUTROS // Outros tipos
        ;
    }


    /**
     * Categoria da notificação
     */
    public enum CategoriaNotificacao {
        FINANCEIRA,  // Notificação financeira
        SEGURANCA,  // Notificação de segurança
        PROMOCIONAL,  // Notificação promocional
        OPERACIONAL,  // Notificação operacional
        SISTEMA,  // Notificação do sistema
        COMPLIANCE,  // Notificação de compliance
        SUPORTE,  // Notificação de suporte
        MARKETING,  // Notificação de marketing
        OUTROS // Outras categorias
        ;
    }


    /**
     * Prioridade da notificação
     */
    public enum PrioridadeNotificacao {
        BAIXA,  // Prioridade baixa
        NORMAL,  // Prioridade normal
        ALTA,  // Prioridade alta
        CRITICA // Prioridade crítica
        ;
    }


    /**
     * Status da notificação
     */
    public enum StatusNotificacao {
        PENDENTE,  // Pendente de envio
        AGENDADA,  // Agendada para envio
        ENVIANDO,  // Enviando
        ENVIADA,  // Enviada com sucesso
        ENTREGUE,  // Entregue ao dispositivo
        LIDA,  // Lida pelo usuário
        FALHOU,  // Falhou no envio
        EXPIRADA,  // Expirada
        CANCELADA // Cancelada
        ;
    }


    @java.lang.SuppressWarnings("all")
    public static class NotificacaoMobileBuilder {
        @java.lang.SuppressWarnings("all")
        private Long id;
        @java.lang.SuppressWarnings("all")
        private String notificacaoId;
        @java.lang.SuppressWarnings("all")
        private String dispositivoId;
        @java.lang.SuppressWarnings("all")
        private String clienteId;
        @java.lang.SuppressWarnings("all")
        private String deviceToken;
        @java.lang.SuppressWarnings("all")
        private TipoNotificacao tipoNotificacao;
        @java.lang.SuppressWarnings("all")
        private CategoriaNotificacao categoria;
        @java.lang.SuppressWarnings("all")
        private String titulo;
        @java.lang.SuppressWarnings("all")
        private String mensagem;
        @java.lang.SuppressWarnings("all")
        private String corpo;
        @java.lang.SuppressWarnings("all")
        private String icone;
        @java.lang.SuppressWarnings("all")
        private String imagem;
        @java.lang.SuppressWarnings("all")
        private String som;
        @java.lang.SuppressWarnings("all")
        private Boolean vibracao;
        @java.lang.SuppressWarnings("all")
        private String ledColor;
        @java.lang.SuppressWarnings("all")
        private PrioridadeNotificacao prioridade;
        @java.lang.SuppressWarnings("all")
        private StatusNotificacao status;
        @java.lang.SuppressWarnings("all")
        private LocalDateTime dataAgendamento;
        @java.lang.SuppressWarnings("all")
        private LocalDateTime dataEnvio;
        @java.lang.SuppressWarnings("all")
        private LocalDateTime dataEntrega;
        @java.lang.SuppressWarnings("all")
        private LocalDateTime dataLeitura;
        @java.lang.SuppressWarnings("all")
        private LocalDateTime dataExpiracao;
        @java.lang.SuppressWarnings("all")
        private Integer tentativasEnvio;
        @java.lang.SuppressWarnings("all")
        private Integer maxTentativas;
        @java.lang.SuppressWarnings("all")
        private String codigoErro;
        @java.lang.SuppressWarnings("all")
        private String mensagemErro;
        @java.lang.SuppressWarnings("all")
        private String transacaoId;
        @java.lang.SuppressWarnings("all")
        private java.math.BigDecimal valor;
        @java.lang.SuppressWarnings("all")
        private String contaEnvolvida;
        @java.lang.SuppressWarnings("all")
        private String acaoUrl;
        @java.lang.SuppressWarnings("all")
        private String dadosExtras;
        @java.lang.SuppressWarnings("all")
        private String metadados;
        @java.lang.SuppressWarnings("all")
        private LocalDateTime dataCriacao;

        @java.lang.SuppressWarnings("all")
        NotificacaoMobileBuilder() {
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public NotificacaoMobile.NotificacaoMobileBuilder id(final Long id) {
            this.id = id;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public NotificacaoMobile.NotificacaoMobileBuilder notificacaoId(final String notificacaoId) {
            this.notificacaoId = notificacaoId;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public NotificacaoMobile.NotificacaoMobileBuilder dispositivoId(final String dispositivoId) {
            this.dispositivoId = dispositivoId;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public NotificacaoMobile.NotificacaoMobileBuilder clienteId(final String clienteId) {
            this.clienteId = clienteId;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public NotificacaoMobile.NotificacaoMobileBuilder deviceToken(final String deviceToken) {
            this.deviceToken = deviceToken;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public NotificacaoMobile.NotificacaoMobileBuilder tipoNotificacao(final TipoNotificacao tipoNotificacao) {
            this.tipoNotificacao = tipoNotificacao;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public NotificacaoMobile.NotificacaoMobileBuilder categoria(final CategoriaNotificacao categoria) {
            this.categoria = categoria;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public NotificacaoMobile.NotificacaoMobileBuilder titulo(final String titulo) {
            this.titulo = titulo;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public NotificacaoMobile.NotificacaoMobileBuilder mensagem(final String mensagem) {
            this.mensagem = mensagem;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public NotificacaoMobile.NotificacaoMobileBuilder corpo(final String corpo) {
            this.corpo = corpo;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public NotificacaoMobile.NotificacaoMobileBuilder icone(final String icone) {
            this.icone = icone;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public NotificacaoMobile.NotificacaoMobileBuilder imagem(final String imagem) {
            this.imagem = imagem;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public NotificacaoMobile.NotificacaoMobileBuilder som(final String som) {
            this.som = som;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public NotificacaoMobile.NotificacaoMobileBuilder vibracao(final Boolean vibracao) {
            this.vibracao = vibracao;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public NotificacaoMobile.NotificacaoMobileBuilder ledColor(final String ledColor) {
            this.ledColor = ledColor;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public NotificacaoMobile.NotificacaoMobileBuilder prioridade(final PrioridadeNotificacao prioridade) {
            this.prioridade = prioridade;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public NotificacaoMobile.NotificacaoMobileBuilder status(final StatusNotificacao status) {
            this.status = status;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public NotificacaoMobile.NotificacaoMobileBuilder dataAgendamento(final LocalDateTime dataAgendamento) {
            this.dataAgendamento = dataAgendamento;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public NotificacaoMobile.NotificacaoMobileBuilder dataEnvio(final LocalDateTime dataEnvio) {
            this.dataEnvio = dataEnvio;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public NotificacaoMobile.NotificacaoMobileBuilder dataEntrega(final LocalDateTime dataEntrega) {
            this.dataEntrega = dataEntrega;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public NotificacaoMobile.NotificacaoMobileBuilder dataLeitura(final LocalDateTime dataLeitura) {
            this.dataLeitura = dataLeitura;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public NotificacaoMobile.NotificacaoMobileBuilder dataExpiracao(final LocalDateTime dataExpiracao) {
            this.dataExpiracao = dataExpiracao;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public NotificacaoMobile.NotificacaoMobileBuilder tentativasEnvio(final Integer tentativasEnvio) {
            this.tentativasEnvio = tentativasEnvio;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public NotificacaoMobile.NotificacaoMobileBuilder maxTentativas(final Integer maxTentativas) {
            this.maxTentativas = maxTentativas;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public NotificacaoMobile.NotificacaoMobileBuilder codigoErro(final String codigoErro) {
            this.codigoErro = codigoErro;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public NotificacaoMobile.NotificacaoMobileBuilder mensagemErro(final String mensagemErro) {
            this.mensagemErro = mensagemErro;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public NotificacaoMobile.NotificacaoMobileBuilder transacaoId(final String transacaoId) {
            this.transacaoId = transacaoId;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public NotificacaoMobile.NotificacaoMobileBuilder valor(final java.math.BigDecimal valor) {
            this.valor = valor;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public NotificacaoMobile.NotificacaoMobileBuilder contaEnvolvida(final String contaEnvolvida) {
            this.contaEnvolvida = contaEnvolvida;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public NotificacaoMobile.NotificacaoMobileBuilder acaoUrl(final String acaoUrl) {
            this.acaoUrl = acaoUrl;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public NotificacaoMobile.NotificacaoMobileBuilder dadosExtras(final String dadosExtras) {
            this.dadosExtras = dadosExtras;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public NotificacaoMobile.NotificacaoMobileBuilder metadados(final String metadados) {
            this.metadados = metadados;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public NotificacaoMobile.NotificacaoMobileBuilder dataCriacao(final LocalDateTime dataCriacao) {
            this.dataCriacao = dataCriacao;
            return this;
        }

        @java.lang.SuppressWarnings("all")
        public NotificacaoMobile build() {
            return new NotificacaoMobile(this.id, this.notificacaoId, this.dispositivoId, this.clienteId, this.deviceToken, this.tipoNotificacao, this.categoria, this.titulo, this.mensagem, this.corpo, this.icone, this.imagem, this.som, this.vibracao, this.ledColor, this.prioridade, this.status, this.dataAgendamento, this.dataEnvio, this.dataEntrega, this.dataLeitura, this.dataExpiracao, this.tentativasEnvio, this.maxTentativas, this.codigoErro, this.mensagemErro, this.transacaoId, this.valor, this.contaEnvolvida, this.acaoUrl, this.dadosExtras, this.metadados, this.dataCriacao);
        }

        @java.lang.Override
        @java.lang.SuppressWarnings("all")
        public java.lang.String toString() {
            return "NotificacaoMobile.NotificacaoMobileBuilder(id=" + this.id + ", notificacaoId=" + this.notificacaoId + ", dispositivoId=" + this.dispositivoId + ", clienteId=" + this.clienteId + ", deviceToken=" + this.deviceToken + ", tipoNotificacao=" + this.tipoNotificacao + ", categoria=" + this.categoria + ", titulo=" + this.titulo + ", mensagem=" + this.mensagem + ", corpo=" + this.corpo + ", icone=" + this.icone + ", imagem=" + this.imagem + ", som=" + this.som + ", vibracao=" + this.vibracao + ", ledColor=" + this.ledColor + ", prioridade=" + this.prioridade + ", status=" + this.status + ", dataAgendamento=" + this.dataAgendamento + ", dataEnvio=" + this.dataEnvio + ", dataEntrega=" + this.dataEntrega + ", dataLeitura=" + this.dataLeitura + ", dataExpiracao=" + this.dataExpiracao + ", tentativasEnvio=" + this.tentativasEnvio + ", maxTentativas=" + this.maxTentativas + ", codigoErro=" + this.codigoErro + ", mensagemErro=" + this.mensagemErro + ", transacaoId=" + this.transacaoId + ", valor=" + this.valor + ", contaEnvolvida=" + this.contaEnvolvida + ", acaoUrl=" + this.acaoUrl + ", dadosExtras=" + this.dadosExtras + ", metadados=" + this.metadados + ", dataCriacao=" + this.dataCriacao + ")";
        }
    }

    @java.lang.SuppressWarnings("all")
    public static NotificacaoMobile.NotificacaoMobileBuilder builder() {
        return new NotificacaoMobile.NotificacaoMobileBuilder();
    }

    @java.lang.SuppressWarnings("all")
    public Long getId() {
        return this.id;
    }

    @java.lang.SuppressWarnings("all")
    public String getNotificacaoId() {
        return this.notificacaoId;
    }

    @java.lang.SuppressWarnings("all")
    public String getDispositivoId() {
        return this.dispositivoId;
    }

    @java.lang.SuppressWarnings("all")
    public String getClienteId() {
        return this.clienteId;
    }

    @java.lang.SuppressWarnings("all")
    public String getDeviceToken() {
        return this.deviceToken;
    }

    @java.lang.SuppressWarnings("all")
    public TipoNotificacao getTipoNotificacao() {
        return this.tipoNotificacao;
    }

    @java.lang.SuppressWarnings("all")
    public CategoriaNotificacao getCategoria() {
        return this.categoria;
    }

    @java.lang.SuppressWarnings("all")
    public String getTitulo() {
        return this.titulo;
    }

    @java.lang.SuppressWarnings("all")
    public String getMensagem() {
        return this.mensagem;
    }

    @java.lang.SuppressWarnings("all")
    public String getCorpo() {
        return this.corpo;
    }

    @java.lang.SuppressWarnings("all")
    public String getIcone() {
        return this.icone;
    }

    @java.lang.SuppressWarnings("all")
    public String getImagem() {
        return this.imagem;
    }

    @java.lang.SuppressWarnings("all")
    public String getSom() {
        return this.som;
    }

    @java.lang.SuppressWarnings("all")
    public Boolean getVibracao() {
        return this.vibracao;
    }

    @java.lang.SuppressWarnings("all")
    public String getLedColor() {
        return this.ledColor;
    }

    @java.lang.SuppressWarnings("all")
    public PrioridadeNotificacao getPrioridade() {
        return this.prioridade;
    }

    @java.lang.SuppressWarnings("all")
    public StatusNotificacao getStatus() {
        return this.status;
    }

    @java.lang.SuppressWarnings("all")
    public LocalDateTime getDataAgendamento() {
        return this.dataAgendamento;
    }

    @java.lang.SuppressWarnings("all")
    public LocalDateTime getDataEnvio() {
        return this.dataEnvio;
    }

    @java.lang.SuppressWarnings("all")
    public LocalDateTime getDataEntrega() {
        return this.dataEntrega;
    }

    @java.lang.SuppressWarnings("all")
    public LocalDateTime getDataLeitura() {
        return this.dataLeitura;
    }

    @java.lang.SuppressWarnings("all")
    public LocalDateTime getDataExpiracao() {
        return this.dataExpiracao;
    }

    @java.lang.SuppressWarnings("all")
    public Integer getTentativasEnvio() {
        return this.tentativasEnvio;
    }

    @java.lang.SuppressWarnings("all")
    public Integer getMaxTentativas() {
        return this.maxTentativas;
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
    public String getTransacaoId() {
        return this.transacaoId;
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
    public String getAcaoUrl() {
        return this.acaoUrl;
    }

    @java.lang.SuppressWarnings("all")
    public String getDadosExtras() {
        return this.dadosExtras;
    }

    @java.lang.SuppressWarnings("all")
    public String getMetadados() {
        return this.metadados;
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
    public void setNotificacaoId(final String notificacaoId) {
        this.notificacaoId = notificacaoId;
    }

    @java.lang.SuppressWarnings("all")
    public void setDispositivoId(final String dispositivoId) {
        this.dispositivoId = dispositivoId;
    }

    @java.lang.SuppressWarnings("all")
    public void setClienteId(final String clienteId) {
        this.clienteId = clienteId;
    }

    @java.lang.SuppressWarnings("all")
    public void setDeviceToken(final String deviceToken) {
        this.deviceToken = deviceToken;
    }

    @java.lang.SuppressWarnings("all")
    public void setTipoNotificacao(final TipoNotificacao tipoNotificacao) {
        this.tipoNotificacao = tipoNotificacao;
    }

    @java.lang.SuppressWarnings("all")
    public void setCategoria(final CategoriaNotificacao categoria) {
        this.categoria = categoria;
    }

    @java.lang.SuppressWarnings("all")
    public void setTitulo(final String titulo) {
        this.titulo = titulo;
    }

    @java.lang.SuppressWarnings("all")
    public void setMensagem(final String mensagem) {
        this.mensagem = mensagem;
    }

    @java.lang.SuppressWarnings("all")
    public void setCorpo(final String corpo) {
        this.corpo = corpo;
    }

    @java.lang.SuppressWarnings("all")
    public void setIcone(final String icone) {
        this.icone = icone;
    }

    @java.lang.SuppressWarnings("all")
    public void setImagem(final String imagem) {
        this.imagem = imagem;
    }

    @java.lang.SuppressWarnings("all")
    public void setSom(final String som) {
        this.som = som;
    }

    @java.lang.SuppressWarnings("all")
    public void setVibracao(final Boolean vibracao) {
        this.vibracao = vibracao;
    }

    @java.lang.SuppressWarnings("all")
    public void setLedColor(final String ledColor) {
        this.ledColor = ledColor;
    }

    @java.lang.SuppressWarnings("all")
    public void setPrioridade(final PrioridadeNotificacao prioridade) {
        this.prioridade = prioridade;
    }

    @java.lang.SuppressWarnings("all")
    public void setStatus(final StatusNotificacao status) {
        this.status = status;
    }

    @java.lang.SuppressWarnings("all")
    public void setDataAgendamento(final LocalDateTime dataAgendamento) {
        this.dataAgendamento = dataAgendamento;
    }

    @java.lang.SuppressWarnings("all")
    public void setDataEnvio(final LocalDateTime dataEnvio) {
        this.dataEnvio = dataEnvio;
    }

    @java.lang.SuppressWarnings("all")
    public void setDataEntrega(final LocalDateTime dataEntrega) {
        this.dataEntrega = dataEntrega;
    }

    @java.lang.SuppressWarnings("all")
    public void setDataLeitura(final LocalDateTime dataLeitura) {
        this.dataLeitura = dataLeitura;
    }

    @java.lang.SuppressWarnings("all")
    public void setDataExpiracao(final LocalDateTime dataExpiracao) {
        this.dataExpiracao = dataExpiracao;
    }

    @java.lang.SuppressWarnings("all")
    public void setTentativasEnvio(final Integer tentativasEnvio) {
        this.tentativasEnvio = tentativasEnvio;
    }

    @java.lang.SuppressWarnings("all")
    public void setMaxTentativas(final Integer maxTentativas) {
        this.maxTentativas = maxTentativas;
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
    public void setTransacaoId(final String transacaoId) {
        this.transacaoId = transacaoId;
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
    public void setAcaoUrl(final String acaoUrl) {
        this.acaoUrl = acaoUrl;
    }

    @java.lang.SuppressWarnings("all")
    public void setDadosExtras(final String dadosExtras) {
        this.dadosExtras = dadosExtras;
    }

    @java.lang.SuppressWarnings("all")
    public void setMetadados(final String metadados) {
        this.metadados = metadados;
    }

    @java.lang.SuppressWarnings("all")
    public void setDataCriacao(final LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public boolean equals(final java.lang.Object o) {
        if (o == this) return true;
        if (!(o instanceof NotificacaoMobile)) return false;
        final NotificacaoMobile other = (NotificacaoMobile) o;
        if (!other.canEqual((java.lang.Object) this)) return false;
        final java.lang.Object this$id = this.getId();
        final java.lang.Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final java.lang.Object this$vibracao = this.getVibracao();
        final java.lang.Object other$vibracao = other.getVibracao();
        if (this$vibracao == null ? other$vibracao != null : !this$vibracao.equals(other$vibracao)) return false;
        final java.lang.Object this$tentativasEnvio = this.getTentativasEnvio();
        final java.lang.Object other$tentativasEnvio = other.getTentativasEnvio();
        if (this$tentativasEnvio == null ? other$tentativasEnvio != null : !this$tentativasEnvio.equals(other$tentativasEnvio)) return false;
        final java.lang.Object this$maxTentativas = this.getMaxTentativas();
        final java.lang.Object other$maxTentativas = other.getMaxTentativas();
        if (this$maxTentativas == null ? other$maxTentativas != null : !this$maxTentativas.equals(other$maxTentativas)) return false;
        final java.lang.Object this$notificacaoId = this.getNotificacaoId();
        final java.lang.Object other$notificacaoId = other.getNotificacaoId();
        if (this$notificacaoId == null ? other$notificacaoId != null : !this$notificacaoId.equals(other$notificacaoId)) return false;
        final java.lang.Object this$dispositivoId = this.getDispositivoId();
        final java.lang.Object other$dispositivoId = other.getDispositivoId();
        if (this$dispositivoId == null ? other$dispositivoId != null : !this$dispositivoId.equals(other$dispositivoId)) return false;
        final java.lang.Object this$clienteId = this.getClienteId();
        final java.lang.Object other$clienteId = other.getClienteId();
        if (this$clienteId == null ? other$clienteId != null : !this$clienteId.equals(other$clienteId)) return false;
        final java.lang.Object this$deviceToken = this.getDeviceToken();
        final java.lang.Object other$deviceToken = other.getDeviceToken();
        if (this$deviceToken == null ? other$deviceToken != null : !this$deviceToken.equals(other$deviceToken)) return false;
        final java.lang.Object this$tipoNotificacao = this.getTipoNotificacao();
        final java.lang.Object other$tipoNotificacao = other.getTipoNotificacao();
        if (this$tipoNotificacao == null ? other$tipoNotificacao != null : !this$tipoNotificacao.equals(other$tipoNotificacao)) return false;
        final java.lang.Object this$categoria = this.getCategoria();
        final java.lang.Object other$categoria = other.getCategoria();
        if (this$categoria == null ? other$categoria != null : !this$categoria.equals(other$categoria)) return false;
        final java.lang.Object this$titulo = this.getTitulo();
        final java.lang.Object other$titulo = other.getTitulo();
        if (this$titulo == null ? other$titulo != null : !this$titulo.equals(other$titulo)) return false;
        final java.lang.Object this$mensagem = this.getMensagem();
        final java.lang.Object other$mensagem = other.getMensagem();
        if (this$mensagem == null ? other$mensagem != null : !this$mensagem.equals(other$mensagem)) return false;
        final java.lang.Object this$corpo = this.getCorpo();
        final java.lang.Object other$corpo = other.getCorpo();
        if (this$corpo == null ? other$corpo != null : !this$corpo.equals(other$corpo)) return false;
        final java.lang.Object this$icone = this.getIcone();
        final java.lang.Object other$icone = other.getIcone();
        if (this$icone == null ? other$icone != null : !this$icone.equals(other$icone)) return false;
        final java.lang.Object this$imagem = this.getImagem();
        final java.lang.Object other$imagem = other.getImagem();
        if (this$imagem == null ? other$imagem != null : !this$imagem.equals(other$imagem)) return false;
        final java.lang.Object this$som = this.getSom();
        final java.lang.Object other$som = other.getSom();
        if (this$som == null ? other$som != null : !this$som.equals(other$som)) return false;
        final java.lang.Object this$ledColor = this.getLedColor();
        final java.lang.Object other$ledColor = other.getLedColor();
        if (this$ledColor == null ? other$ledColor != null : !this$ledColor.equals(other$ledColor)) return false;
        final java.lang.Object this$prioridade = this.getPrioridade();
        final java.lang.Object other$prioridade = other.getPrioridade();
        if (this$prioridade == null ? other$prioridade != null : !this$prioridade.equals(other$prioridade)) return false;
        final java.lang.Object this$status = this.getStatus();
        final java.lang.Object other$status = other.getStatus();
        if (this$status == null ? other$status != null : !this$status.equals(other$status)) return false;
        final java.lang.Object this$dataAgendamento = this.getDataAgendamento();
        final java.lang.Object other$dataAgendamento = other.getDataAgendamento();
        if (this$dataAgendamento == null ? other$dataAgendamento != null : !this$dataAgendamento.equals(other$dataAgendamento)) return false;
        final java.lang.Object this$dataEnvio = this.getDataEnvio();
        final java.lang.Object other$dataEnvio = other.getDataEnvio();
        if (this$dataEnvio == null ? other$dataEnvio != null : !this$dataEnvio.equals(other$dataEnvio)) return false;
        final java.lang.Object this$dataEntrega = this.getDataEntrega();
        final java.lang.Object other$dataEntrega = other.getDataEntrega();
        if (this$dataEntrega == null ? other$dataEntrega != null : !this$dataEntrega.equals(other$dataEntrega)) return false;
        final java.lang.Object this$dataLeitura = this.getDataLeitura();
        final java.lang.Object other$dataLeitura = other.getDataLeitura();
        if (this$dataLeitura == null ? other$dataLeitura != null : !this$dataLeitura.equals(other$dataLeitura)) return false;
        final java.lang.Object this$dataExpiracao = this.getDataExpiracao();
        final java.lang.Object other$dataExpiracao = other.getDataExpiracao();
        if (this$dataExpiracao == null ? other$dataExpiracao != null : !this$dataExpiracao.equals(other$dataExpiracao)) return false;
        final java.lang.Object this$codigoErro = this.getCodigoErro();
        final java.lang.Object other$codigoErro = other.getCodigoErro();
        if (this$codigoErro == null ? other$codigoErro != null : !this$codigoErro.equals(other$codigoErro)) return false;
        final java.lang.Object this$mensagemErro = this.getMensagemErro();
        final java.lang.Object other$mensagemErro = other.getMensagemErro();
        if (this$mensagemErro == null ? other$mensagemErro != null : !this$mensagemErro.equals(other$mensagemErro)) return false;
        final java.lang.Object this$transacaoId = this.getTransacaoId();
        final java.lang.Object other$transacaoId = other.getTransacaoId();
        if (this$transacaoId == null ? other$transacaoId != null : !this$transacaoId.equals(other$transacaoId)) return false;
        final java.lang.Object this$valor = this.getValor();
        final java.lang.Object other$valor = other.getValor();
        if (this$valor == null ? other$valor != null : !this$valor.equals(other$valor)) return false;
        final java.lang.Object this$contaEnvolvida = this.getContaEnvolvida();
        final java.lang.Object other$contaEnvolvida = other.getContaEnvolvida();
        if (this$contaEnvolvida == null ? other$contaEnvolvida != null : !this$contaEnvolvida.equals(other$contaEnvolvida)) return false;
        final java.lang.Object this$acaoUrl = this.getAcaoUrl();
        final java.lang.Object other$acaoUrl = other.getAcaoUrl();
        if (this$acaoUrl == null ? other$acaoUrl != null : !this$acaoUrl.equals(other$acaoUrl)) return false;
        final java.lang.Object this$dadosExtras = this.getDadosExtras();
        final java.lang.Object other$dadosExtras = other.getDadosExtras();
        if (this$dadosExtras == null ? other$dadosExtras != null : !this$dadosExtras.equals(other$dadosExtras)) return false;
        final java.lang.Object this$metadados = this.getMetadados();
        final java.lang.Object other$metadados = other.getMetadados();
        if (this$metadados == null ? other$metadados != null : !this$metadados.equals(other$metadados)) return false;
        final java.lang.Object this$dataCriacao = this.getDataCriacao();
        final java.lang.Object other$dataCriacao = other.getDataCriacao();
        if (this$dataCriacao == null ? other$dataCriacao != null : !this$dataCriacao.equals(other$dataCriacao)) return false;
        return true;
    }

    @java.lang.SuppressWarnings("all")
    protected boolean canEqual(final java.lang.Object other) {
        return other instanceof NotificacaoMobile;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final java.lang.Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final java.lang.Object $vibracao = this.getVibracao();
        result = result * PRIME + ($vibracao == null ? 43 : $vibracao.hashCode());
        final java.lang.Object $tentativasEnvio = this.getTentativasEnvio();
        result = result * PRIME + ($tentativasEnvio == null ? 43 : $tentativasEnvio.hashCode());
        final java.lang.Object $maxTentativas = this.getMaxTentativas();
        result = result * PRIME + ($maxTentativas == null ? 43 : $maxTentativas.hashCode());
        final java.lang.Object $notificacaoId = this.getNotificacaoId();
        result = result * PRIME + ($notificacaoId == null ? 43 : $notificacaoId.hashCode());
        final java.lang.Object $dispositivoId = this.getDispositivoId();
        result = result * PRIME + ($dispositivoId == null ? 43 : $dispositivoId.hashCode());
        final java.lang.Object $clienteId = this.getClienteId();
        result = result * PRIME + ($clienteId == null ? 43 : $clienteId.hashCode());
        final java.lang.Object $deviceToken = this.getDeviceToken();
        result = result * PRIME + ($deviceToken == null ? 43 : $deviceToken.hashCode());
        final java.lang.Object $tipoNotificacao = this.getTipoNotificacao();
        result = result * PRIME + ($tipoNotificacao == null ? 43 : $tipoNotificacao.hashCode());
        final java.lang.Object $categoria = this.getCategoria();
        result = result * PRIME + ($categoria == null ? 43 : $categoria.hashCode());
        final java.lang.Object $titulo = this.getTitulo();
        result = result * PRIME + ($titulo == null ? 43 : $titulo.hashCode());
        final java.lang.Object $mensagem = this.getMensagem();
        result = result * PRIME + ($mensagem == null ? 43 : $mensagem.hashCode());
        final java.lang.Object $corpo = this.getCorpo();
        result = result * PRIME + ($corpo == null ? 43 : $corpo.hashCode());
        final java.lang.Object $icone = this.getIcone();
        result = result * PRIME + ($icone == null ? 43 : $icone.hashCode());
        final java.lang.Object $imagem = this.getImagem();
        result = result * PRIME + ($imagem == null ? 43 : $imagem.hashCode());
        final java.lang.Object $som = this.getSom();
        result = result * PRIME + ($som == null ? 43 : $som.hashCode());
        final java.lang.Object $ledColor = this.getLedColor();
        result = result * PRIME + ($ledColor == null ? 43 : $ledColor.hashCode());
        final java.lang.Object $prioridade = this.getPrioridade();
        result = result * PRIME + ($prioridade == null ? 43 : $prioridade.hashCode());
        final java.lang.Object $status = this.getStatus();
        result = result * PRIME + ($status == null ? 43 : $status.hashCode());
        final java.lang.Object $dataAgendamento = this.getDataAgendamento();
        result = result * PRIME + ($dataAgendamento == null ? 43 : $dataAgendamento.hashCode());
        final java.lang.Object $dataEnvio = this.getDataEnvio();
        result = result * PRIME + ($dataEnvio == null ? 43 : $dataEnvio.hashCode());
        final java.lang.Object $dataEntrega = this.getDataEntrega();
        result = result * PRIME + ($dataEntrega == null ? 43 : $dataEntrega.hashCode());
        final java.lang.Object $dataLeitura = this.getDataLeitura();
        result = result * PRIME + ($dataLeitura == null ? 43 : $dataLeitura.hashCode());
        final java.lang.Object $dataExpiracao = this.getDataExpiracao();
        result = result * PRIME + ($dataExpiracao == null ? 43 : $dataExpiracao.hashCode());
        final java.lang.Object $codigoErro = this.getCodigoErro();
        result = result * PRIME + ($codigoErro == null ? 43 : $codigoErro.hashCode());
        final java.lang.Object $mensagemErro = this.getMensagemErro();
        result = result * PRIME + ($mensagemErro == null ? 43 : $mensagemErro.hashCode());
        final java.lang.Object $transacaoId = this.getTransacaoId();
        result = result * PRIME + ($transacaoId == null ? 43 : $transacaoId.hashCode());
        final java.lang.Object $valor = this.getValor();
        result = result * PRIME + ($valor == null ? 43 : $valor.hashCode());
        final java.lang.Object $contaEnvolvida = this.getContaEnvolvida();
        result = result * PRIME + ($contaEnvolvida == null ? 43 : $contaEnvolvida.hashCode());
        final java.lang.Object $acaoUrl = this.getAcaoUrl();
        result = result * PRIME + ($acaoUrl == null ? 43 : $acaoUrl.hashCode());
        final java.lang.Object $dadosExtras = this.getDadosExtras();
        result = result * PRIME + ($dadosExtras == null ? 43 : $dadosExtras.hashCode());
        final java.lang.Object $metadados = this.getMetadados();
        result = result * PRIME + ($metadados == null ? 43 : $metadados.hashCode());
        final java.lang.Object $dataCriacao = this.getDataCriacao();
        result = result * PRIME + ($dataCriacao == null ? 43 : $dataCriacao.hashCode());
        return result;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
        return "NotificacaoMobile(id=" + this.getId() + ", notificacaoId=" + this.getNotificacaoId() + ", dispositivoId=" + this.getDispositivoId() + ", clienteId=" + this.getClienteId() + ", deviceToken=" + this.getDeviceToken() + ", tipoNotificacao=" + this.getTipoNotificacao() + ", categoria=" + this.getCategoria() + ", titulo=" + this.getTitulo() + ", mensagem=" + this.getMensagem() + ", corpo=" + this.getCorpo() + ", icone=" + this.getIcone() + ", imagem=" + this.getImagem() + ", som=" + this.getSom() + ", vibracao=" + this.getVibracao() + ", ledColor=" + this.getLedColor() + ", prioridade=" + this.getPrioridade() + ", status=" + this.getStatus() + ", dataAgendamento=" + this.getDataAgendamento() + ", dataEnvio=" + this.getDataEnvio() + ", dataEntrega=" + this.getDataEntrega() + ", dataLeitura=" + this.getDataLeitura() + ", dataExpiracao=" + this.getDataExpiracao() + ", tentativasEnvio=" + this.getTentativasEnvio() + ", maxTentativas=" + this.getMaxTentativas() + ", codigoErro=" + this.getCodigoErro() + ", mensagemErro=" + this.getMensagemErro() + ", transacaoId=" + this.getTransacaoId() + ", valor=" + this.getValor() + ", contaEnvolvida=" + this.getContaEnvolvida() + ", acaoUrl=" + this.getAcaoUrl() + ", dadosExtras=" + this.getDadosExtras() + ", metadados=" + this.getMetadados() + ", dataCriacao=" + this.getDataCriacao() + ")";
    }

    @java.lang.SuppressWarnings("all")
    public NotificacaoMobile() {
    }

    @java.lang.SuppressWarnings("all")
    public NotificacaoMobile(final Long id, final String notificacaoId, final String dispositivoId, final String clienteId, final String deviceToken, final TipoNotificacao tipoNotificacao, final CategoriaNotificacao categoria, final String titulo, final String mensagem, final String corpo, final String icone, final String imagem, final String som, final Boolean vibracao, final String ledColor, final PrioridadeNotificacao prioridade, final StatusNotificacao status, final LocalDateTime dataAgendamento, final LocalDateTime dataEnvio, final LocalDateTime dataEntrega, final LocalDateTime dataLeitura, final LocalDateTime dataExpiracao, final Integer tentativasEnvio, final Integer maxTentativas, final String codigoErro, final String mensagemErro, final String transacaoId, final java.math.BigDecimal valor, final String contaEnvolvida, final String acaoUrl, final String dadosExtras, final String metadados, final LocalDateTime dataCriacao) {
        this.id = id;
        this.notificacaoId = notificacaoId;
        this.dispositivoId = dispositivoId;
        this.clienteId = clienteId;
        this.deviceToken = deviceToken;
        this.tipoNotificacao = tipoNotificacao;
        this.categoria = categoria;
        this.titulo = titulo;
        this.mensagem = mensagem;
        this.corpo = corpo;
        this.icone = icone;
        this.imagem = imagem;
        this.som = som;
        this.vibracao = vibracao;
        this.ledColor = ledColor;
        this.prioridade = prioridade;
        this.status = status;
        this.dataAgendamento = dataAgendamento;
        this.dataEnvio = dataEnvio;
        this.dataEntrega = dataEntrega;
        this.dataLeitura = dataLeitura;
        this.dataExpiracao = dataExpiracao;
        this.tentativasEnvio = tentativasEnvio;
        this.maxTentativas = maxTentativas;
        this.codigoErro = codigoErro;
        this.mensagemErro = mensagemErro;
        this.transacaoId = transacaoId;
        this.valor = valor;
        this.contaEnvolvida = contaEnvolvida;
        this.acaoUrl = acaoUrl;
        this.dadosExtras = dadosExtras;
        this.metadados = metadados;
        this.dataCriacao = dataCriacao;
    }
}
