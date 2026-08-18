package com.aurix.platform.shared.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Entidade Chargeback do Aurix.
 * Representa uma solicitação de chargeback (estorno) de uma transação.
 */
@Entity
@Table(name = "chargebacks", schema = "aurix")
public class Chargeback extends BaseEntity {
    private static final int CODIGO_LENGTH = 100;
    private static final int DOCUMENT_LENGTH = 100;
    private static final int MOTIVO_DESC_LENGTH = 500;
    private static final int RESULTADO_LENGTH = 30;
    private static final int JUSTIFICATIVA_LENGTH = 500;
    private static final int DECIMAL_PRECISION = 15;
    private static final int DECIMAL_SCALE = 2;

    @NotBlank(message = "Código chargeback é obrigatório")
    @Column(name = "codigo_chargeback", unique = true, nullable = false, length = CODIGO_LENGTH)
    private String codigoChargeback;

    @NotNull(message = "Conta é obrigatória")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "conta_id", nullable = false)
    private Conta conta;

    @Column(name = "transacao_origem_id")
    private Long transacaoOrigemId;

    @NotBlank(message = "Tipo de origem é obrigatório")
    @Size(max = 30, message = "Tipo de origem deve ter no máximo 30 caracteres")
    @Column(name = "tipo_origem", nullable = false, length = 30)
    private String tipoOrigem;

    @Size(max = DOCUMENT_LENGTH, message = "Documento origem deve ter no máximo 100 caracteres")
    @Column(name = "documento_origem", length = DOCUMENT_LENGTH)
    private String documentoOrigem;

    @NotNull(message = "Valor original é obrigatório")
    @DecimalMin(value = "0.01", message = "Valor original deve ser maior que zero")
    @Column(name = "valor_original", precision = DECIMAL_PRECISION, scale = DECIMAL_SCALE, nullable = false)
    private BigDecimal valorOriginal;

    @NotNull(message = "Valor do chargeback é obrigatório")
    @DecimalMin(value = "0.01", message = "Valor do chargeback deve ser maior que zero")
    @Column(name = "valor_chargeback", precision = DECIMAL_PRECISION, scale = DECIMAL_SCALE, nullable = false)
    private BigDecimal valorChargeback;

    @NotNull(message = "Motivo é obrigatório")
    @Enumerated(EnumType.STRING)
    @Column(name = "motivo", nullable = false)
    private MotivoChargeback motivo;

    @Size(max = MOTIVO_DESC_LENGTH, message = "Descrição do motivo deve ter no máximo 500 caracteres")
    @Column(name = "descricao_motivo", length = MOTIVO_DESC_LENGTH)
    private String descricaoMotivo;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private StatusChargeback status = StatusChargeback.ABERTO;

    @Column(name = "data_transacao_origem")
    private LocalDateTime dataTransacaoOrigem;

    @Column(name = "data_solicitacao", nullable = false)
    private LocalDateTime dataSolicitacao = LocalDateTime.now();

    @Column(name = "data_analise")
    private LocalDateTime dataAnalise;

    @Column(name = "data_contestacao")
    private LocalDateTime dataContestacao;

    @Column(name = "data_resolucao")
    private LocalDateTime dataResolucao;

    @Enumerated(EnumType.STRING)
    @Column(name = "resultado")
    private ResultadoChargeback resultado;

    @Size(max = JUSTIFICATIVA_LENGTH, message = "Justificativa deve ter no máximo 500 caracteres")
    @Column(name = "justificativa_resolucao", length = JUSTIFICATIVA_LENGTH)
    private String justificativaResolucao;

    @NotNull(message = "Prazo limite é obrigatório")
    @Column(name = "prazo_limite", nullable = false)
    private LocalDateTime prazoLimite;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "dados_adicionais", columnDefinition = "jsonb")
    private String dadosAdicionais;

    /**
     * Motivos de chargeback conforme regulação BACEN/brand.
     */
    public enum MotivoChargeback {
        FRAUDE("Fraude"),
        NAO_RECONHECIDO("Transação não reconhecida pelo titular"),
        PRODUTO_NAO_RECEBIDO("Produto ou serviço não recebido"),
        ERRO_VALOR("Valor divergente do autorizado"),
        DUPLICIDADE("Cobrança duplicada"),
        CANCELAMENTO_NAO_PROCESSADO("Cancelamento não processado pelo estabelecimento"),
        OUTROS("Outros motivos");

        private final String descricao;

        MotivoChargeback(final String desc) {
            this.descricao = desc;
        }

        public String getDescricao() {
            return descricao;
        }
    }

    /**
     * Status do processo de chargeback.
     */
    public enum StatusChargeback {
        ABERTO("Aberto"),
        EM_ANALISE("Em Análise"),
        EM_CONTESTACAO("Em Contestação"),
        DEFERIDO("Deferido"),
        INDEFERIDO("Indeferido"),
        PARCIAL("Parcialmente Deferido"),
        CANCELADO("Cancelado");

        private final String descricao;

        StatusChargeback(final String desc) {
            this.descricao = desc;
        }

        public String getDescricao() {
            return descricao;
        }
    }

    /**
     * Resultado final do chargeback.
     */
    public enum ResultadoChargeback {
        DEFERIDO("Deferido"),
        INDEFERIDO("Indeferido"),
        PARCIAL("Parcial");

        private final String descricao;

        ResultadoChargeback(final String desc) {
            this.descricao = desc;
        }

        public String getDescricao() {
            return descricao;
        }
    }

    @java.lang.SuppressWarnings("all")
    public String getCodigoChargeback() {
        return this.codigoChargeback;
    }

    @java.lang.SuppressWarnings("all")
    public Conta getConta() {
        return this.conta;
    }

    @java.lang.SuppressWarnings("all")
    public Long getTransacaoOrigemId() {
        return this.transacaoOrigemId;
    }

    @java.lang.SuppressWarnings("all")
    public String getTipoOrigem() {
        return this.tipoOrigem;
    }

    @java.lang.SuppressWarnings("all")
    public String getDocumentoOrigem() {
        return this.documentoOrigem;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getValorOriginal() {
        return this.valorOriginal;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getValorChargeback() {
        return this.valorChargeback;
    }

    @java.lang.SuppressWarnings("all")
    public MotivoChargeback getMotivo() {
        return this.motivo;
    }

    @java.lang.SuppressWarnings("all")
    public String getDescricaoMotivo() {
        return this.descricaoMotivo;
    }

    @java.lang.SuppressWarnings("all")
    public StatusChargeback getStatus() {
        return this.status;
    }

    @java.lang.SuppressWarnings("all")
    public LocalDateTime getDataTransacaoOrigem() {
        return this.dataTransacaoOrigem;
    }

    @java.lang.SuppressWarnings("all")
    public LocalDateTime getDataSolicitacao() {
        return this.dataSolicitacao;
    }

    @java.lang.SuppressWarnings("all")
    public LocalDateTime getDataAnalise() {
        return this.dataAnalise;
    }

    @java.lang.SuppressWarnings("all")
    public LocalDateTime getDataContestacao() {
        return this.dataContestacao;
    }

    @java.lang.SuppressWarnings("all")
    public LocalDateTime getDataResolucao() {
        return this.dataResolucao;
    }

    @java.lang.SuppressWarnings("all")
    public ResultadoChargeback getResultado() {
        return this.resultado;
    }

    @java.lang.SuppressWarnings("all")
    public String getJustificativaResolucao() {
        return this.justificativaResolucao;
    }

    @java.lang.SuppressWarnings("all")
    public LocalDateTime getPrazoLimite() {
        return this.prazoLimite;
    }

    @java.lang.SuppressWarnings("all")
    public String getDadosAdicionais() {
        return this.dadosAdicionais;
    }

    @java.lang.SuppressWarnings("all")
    public void setCodigoChargeback(final String codigoChargeback) {
        this.codigoChargeback = codigoChargeback;
    }

    @java.lang.SuppressWarnings("all")
    public void setConta(final Conta conta) {
        this.conta = conta;
    }

    @java.lang.SuppressWarnings("all")
    public void setTransacaoOrigemId(final Long transacaoOrigemId) {
        this.transacaoOrigemId = transacaoOrigemId;
    }

    @java.lang.SuppressWarnings("all")
    public void setTipoOrigem(final String tipoOrigem) {
        this.tipoOrigem = tipoOrigem;
    }

    @java.lang.SuppressWarnings("all")
    public void setDocumentoOrigem(final String documentoOrigem) {
        this.documentoOrigem = documentoOrigem;
    }

    @java.lang.SuppressWarnings("all")
    public void setValorOriginal(final BigDecimal valorOriginal) {
        this.valorOriginal = valorOriginal;
    }

    @java.lang.SuppressWarnings("all")
    public void setValorChargeback(final BigDecimal valorChargeback) {
        this.valorChargeback = valorChargeback;
    }

    @java.lang.SuppressWarnings("all")
    public void setMotivo(final MotivoChargeback motivo) {
        this.motivo = motivo;
    }

    @java.lang.SuppressWarnings("all")
    public void setDescricaoMotivo(final String descricaoMotivo) {
        this.descricaoMotivo = descricaoMotivo;
    }

    @java.lang.SuppressWarnings("all")
    public void setStatus(final StatusChargeback status) {
        this.status = status;
    }

    @java.lang.SuppressWarnings("all")
    public void setDataTransacaoOrigem(final LocalDateTime dataTransacaoOrigem) {
        this.dataTransacaoOrigem = dataTransacaoOrigem;
    }

    @java.lang.SuppressWarnings("all")
    public void setDataSolicitacao(final LocalDateTime dataSolicitacao) {
        this.dataSolicitacao = dataSolicitacao;
    }

    @java.lang.SuppressWarnings("all")
    public void setDataAnalise(final LocalDateTime dataAnalise) {
        this.dataAnalise = dataAnalise;
    }

    @java.lang.SuppressWarnings("all")
    public void setDataContestacao(final LocalDateTime dataContestacao) {
        this.dataContestacao = dataContestacao;
    }

    @java.lang.SuppressWarnings("all")
    public void setDataResolucao(final LocalDateTime dataResolucao) {
        this.dataResolucao = dataResolucao;
    }

    @java.lang.SuppressWarnings("all")
    public void setResultado(final ResultadoChargeback resultado) {
        this.resultado = resultado;
    }

    @java.lang.SuppressWarnings("all")
    public void setJustificativaResolucao(final String justificativaResolucao) {
        this.justificativaResolucao = justificativaResolucao;
    }

    @java.lang.SuppressWarnings("all")
    public void setPrazoLimite(final LocalDateTime prazoLimite) {
        this.prazoLimite = prazoLimite;
    }

    @java.lang.SuppressWarnings("all")
    public void setDadosAdicionais(final String dadosAdicionais) {
        this.dadosAdicionais = dadosAdicionais;
    }
}
