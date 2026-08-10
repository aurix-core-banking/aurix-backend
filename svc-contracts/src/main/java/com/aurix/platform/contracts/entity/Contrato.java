package com.aurix.platform.contracts.entity;

import com.aurix.platform.shared.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "contratos", schema = "aurix")
public class Contrato extends BaseEntity {

    public enum TipoContrato {
        EMPRESTIMO, FINANCIAMENTO, SEGURO, CONSIGNADO, CARTAO, CAMBIO, OUTROS
    }

    public enum StatusContrato {
        RASCUNHO, AGUARDANDO_ASSINATURA, ASSINADO, ATIVO, LIQUIDADO, CANCELADO, REJEITADO
    }

    @Column(name = "numero_contrato", nullable = false, unique = true, length = 50)
    private String numeroContrato;

    @Column(name = "produto_id")
    private Long produtoId;

    @Column(name = "produto_codigo", length = 50)
    private String produtoCodigo;

    @Column(name = "cliente_id", nullable = false)
    private Long clienteId;

    @Column(name = "cliente_documento", length = 14)
    private String clienteDocumento;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_contrato", nullable = false, length = 30)
    private TipoContrato tipoContrato;

    @Column(precision = 19, scale = 4)
    private BigDecimal valor;

    @Column(name = "prazo_meses")
    private Integer prazoMeses;

    @Column(name = "valor_parcela", precision = 19, scale = 4)
    private BigDecimal valorParcela;

    @Column(name = "taxa_juros", precision = 9, scale = 4)
    private BigDecimal taxaJuros;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private StatusContrato status = StatusContrato.RASCUNHO;

    @Column(name = "data_assinatura")
    private LocalDateTime dataAssinatura;

    @Column(name = "data_vigencia_inicio")
    private LocalDate dataVigenciaInicio;

    @Column(name = "data_vigencia_fim")
    private LocalDate dataVigenciaFim;

    @Column(name = "termos_texto", columnDefinition = "TEXT")
    private String termosTexto;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "assinatura_digital", columnDefinition = "JSONB")
    private String assinaturaDigital;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "dados_json", columnDefinition = "JSONB")
    private String dadosJson;

    public String getNumeroContrato() {
        return numeroContrato;
    }

    public void setNumeroContrato(String numeroContrato) {
        this.numeroContrato = numeroContrato;
    }

    public Long getProdutoId() {
        return produtoId;
    }

    public void setProdutoId(Long produtoId) {
        this.produtoId = produtoId;
    }

    public String getProdutoCodigo() {
        return produtoCodigo;
    }

    public void setProdutoCodigo(String produtoCodigo) {
        this.produtoCodigo = produtoCodigo;
    }

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }

    public String getClienteDocumento() {
        return clienteDocumento;
    }

    public void setClienteDocumento(String clienteDocumento) {
        this.clienteDocumento = clienteDocumento;
    }

    public TipoContrato getTipoContrato() {
        return tipoContrato;
    }

    public void setTipoContrato(TipoContrato tipoContrato) {
        this.tipoContrato = tipoContrato;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public Integer getPrazoMeses() {
        return prazoMeses;
    }

    public void setPrazoMeses(Integer prazoMeses) {
        this.prazoMeses = prazoMeses;
    }

    public BigDecimal getValorParcela() {
        return valorParcela;
    }

    public void setValorParcela(BigDecimal valorParcela) {
        this.valorParcela = valorParcela;
    }

    public BigDecimal getTaxaJuros() {
        return taxaJuros;
    }

    public void setTaxaJuros(BigDecimal taxaJuros) {
        this.taxaJuros = taxaJuros;
    }

    public StatusContrato getStatus() {
        return status;
    }

    public void setStatus(StatusContrato status) {
        this.status = status;
    }

    public LocalDateTime getDataAssinatura() {
        return dataAssinatura;
    }

    public void setDataAssinatura(LocalDateTime dataAssinatura) {
        this.dataAssinatura = dataAssinatura;
    }

    public LocalDate getDataVigenciaInicio() {
        return dataVigenciaInicio;
    }

    public void setDataVigenciaInicio(LocalDate dataVigenciaInicio) {
        this.dataVigenciaInicio = dataVigenciaInicio;
    }

    public LocalDate getDataVigenciaFim() {
        return dataVigenciaFim;
    }

    public void setDataVigenciaFim(LocalDate dataVigenciaFim) {
        this.dataVigenciaFim = dataVigenciaFim;
    }

    public String getTermosTexto() {
        return termosTexto;
    }

    public void setTermosTexto(String termosTexto) {
        this.termosTexto = termosTexto;
    }

    public String getAssinaturaDigital() {
        return assinaturaDigital;
    }

    public void setAssinaturaDigital(String assinaturaDigital) {
        this.assinaturaDigital = assinaturaDigital;
    }

    public String getDadosJson() {
        return dadosJson;
    }

    public void setDadosJson(String dadosJson) {
        this.dadosJson = dadosJson;
    }
}
