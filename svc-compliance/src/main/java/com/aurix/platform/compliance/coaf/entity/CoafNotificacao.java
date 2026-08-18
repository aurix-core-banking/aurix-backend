package com.aurix.platform.compliance.coaf.entity;

import com.aurix.platform.shared.entity.BaseEntity;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "coaf_notificacoes", schema = "aurix")
public class CoafNotificacao extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String codigoNotificacao;

    @Column(name = "cliente_id")
    private Long clienteId;

    @Column(name = "cpf_cnpj", nullable = false, length = 14)
    private String cpfCnpj;

    @Column(name = "nome_cliente", nullable = false, length = 255)
    private String nomeCliente;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoNotificacaoCoaf tipoNotificacao;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusNotificacaoCoaf status = StatusNotificacaoCoaf.PENDENTE;

    @Column(nullable = false)
    private LocalDateTime dataOcorrencia;

    @Column
    private LocalDateTime dataNotificacao;

    @Column(nullable = false)
    private LocalDateTime prazoNotificacao;

    @Column(length = 255)
    private String motivoSuspeita;

    @Column(columnDefinition = "TEXT")
    private String descricaoOperacao;

    @Column(precision = 18, scale = 2)
    private BigDecimal valorOperacao;

    @Column(length = 10)
    private String moeda = "BRL";

    @Column(length = 100)
    private String tipoOperacao;

    @Column(length = 20)
    private String canalOperacao;

    @Column(length = 200)
    private String instituicaoOrigem;

    @Column(length = 200)
    private String instituicaoDestino;

    @Column(columnDefinition = "TEXT")
    private String dadosTransacao;

    @Column(columnDefinition = "TEXT")
    private String xmlNotificacao;

    @Column(columnDefinition = "TEXT")
    private String observacoes;

    @Column
    private Boolean dentroPrazo = true;

    @Column
    private Integer diasRestantes;

    public enum TipoNotificacaoCoaf {
        TRANSACAO_SUSPEITA, OPERACAO_INCOMUM, VALOR_ABAIXO_LIMITE,
        REPETICAO_OPERACOES, TITULAR_PEQUENA, JURISDICAO_RISCO,
        OUTROS
    }

    public enum StatusNotificacaoCoaf {
        PENDENTE, EM_PROCESSAMENTO, ENVIADA, CONFIRMADA,
        REJEITADA, CANCELADA, FORA_PRAZO
    }

    @SuppressWarnings("all")
    public CoafNotificacao() {
    }

    public String getCodigoNotificacao() {
        return this.codigoNotificacao;
    }

    public Long getClienteId() {
        return this.clienteId;
    }

    public String getCpfCnpj() {
        return this.cpfCnpj;
    }

    public String getNomeCliente() {
        return this.nomeCliente;
    }

    public TipoNotificacaoCoaf getTipoNotificacao() {
        return this.tipoNotificacao;
    }

    public StatusNotificacaoCoaf getStatus() {
        return this.status;
    }

    public LocalDateTime getDataOcorrencia() {
        return this.dataOcorrencia;
    }

    public LocalDateTime getDataNotificacao() {
        return this.dataNotificacao;
    }

    public LocalDateTime getPrazoNotificacao() {
        return this.prazoNotificacao;
    }

    public String getMotivoSuspeita() {
        return this.motivoSuspeita;
    }

    public String getDescricaoOperacao() {
        return this.descricaoOperacao;
    }

    public BigDecimal getValorOperacao() {
        return this.valorOperacao;
    }

    public String getMoeda() {
        return this.moeda;
    }

    public String getTipoOperacao() {
        return this.tipoOperacao;
    }

    public String getCanalOperacao() {
        return this.canalOperacao;
    }

    public String getInstituicaoOrigem() {
        return this.instituicaoOrigem;
    }

    public String getInstituicaoDestino() {
        return this.instituicaoDestino;
    }

    public String getDadosTransacao() {
        return this.dadosTransacao;
    }

    public String getXmlNotificacao() {
        return this.xmlNotificacao;
    }

    public String getObservacoes() {
        return this.observacoes;
    }

    public Boolean getDentroPrazo() {
        return this.dentroPrazo;
    }

    public Integer getDiasRestantes() {
        return this.diasRestantes;
    }

    public void setCodigoNotificacao(final String codigoNotificacao) {
        this.codigoNotificacao = codigoNotificacao;
    }

    public void setClienteId(final Long clienteId) {
        this.clienteId = clienteId;
    }

    public void setCpfCnpj(final String cpfCnpj) {
        this.cpfCnpj = cpfCnpj;
    }

    public void setNomeCliente(final String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }

    public void setTipoNotificacao(final TipoNotificacaoCoaf tipoNotificacao) {
        this.tipoNotificacao = tipoNotificacao;
    }

    public void setStatus(final StatusNotificacaoCoaf status) {
        this.status = status;
    }

    public void setDataOcorrencia(final LocalDateTime dataOcorrencia) {
        this.dataOcorrencia = dataOcorrencia;
    }

    public void setDataNotificacao(final LocalDateTime dataNotificacao) {
        this.dataNotificacao = dataNotificacao;
    }

    public void setPrazoNotificacao(final LocalDateTime prazoNotificacao) {
        this.prazoNotificacao = prazoNotificacao;
    }

    public void setMotivoSuspeita(final String motivoSuspeita) {
        this.motivoSuspeita = motivoSuspeita;
    }

    public void setDescricaoOperacao(final String descricaoOperacao) {
        this.descricaoOperacao = descricaoOperacao;
    }

    public void setValorOperacao(final BigDecimal valorOperacao) {
        this.valorOperacao = valorOperacao;
    }

    public void setMoeda(final String moeda) {
        this.moeda = moeda;
    }

    public void setTipoOperacao(final String tipoOperacao) {
        this.tipoOperacao = tipoOperacao;
    }

    public void setCanalOperacao(final String canalOperacao) {
        this.canalOperacao = canalOperacao;
    }

    public void setInstituicaoOrigem(final String instituicaoOrigem) {
        this.instituicaoOrigem = instituicaoOrigem;
    }

    public void setInstituicaoDestino(final String instituicaoDestino) {
        this.instituicaoDestino = instituicaoDestino;
    }

    public void setDadosTransacao(final String dadosTransacao) {
        this.dadosTransacao = dadosTransacao;
    }

    public void setXmlNotificacao(final String xmlNotificacao) {
        this.xmlNotificacao = xmlNotificacao;
    }

    public void setObservacoes(final String observacoes) {
        this.observacoes = observacoes;
    }

    public void setDentroPrazo(final Boolean dentroPrazo) {
        this.dentroPrazo = dentroPrazo;
    }

    public void setDiasRestantes(final Integer diasRestantes) {
        this.diasRestantes = diasRestantes;
    }
}
