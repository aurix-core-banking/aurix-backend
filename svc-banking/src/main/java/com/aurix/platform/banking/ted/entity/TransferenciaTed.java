package com.aurix.platform.banking.ted.entity;

import com.aurix.platform.shared.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "ted_transferencias", schema = "aurix")
public class TransferenciaTed extends BaseEntity {

    @NotNull
    @Column(name = "conta_origem_id", nullable = false)
    private Long contaOrigemId;

    @NotBlank
    @Column(name = "conta_origem_numero", nullable = false, length = 20)
    private String contaOrigemNumero;

    @NotBlank
    @Column(name = "ispb_destino", nullable = false, length = 8)
    private String ispbDestino;

    @NotBlank
    @Column(name = "conta_destino_agencia", nullable = false, length = 4)
    private String contaDestinoAgencia;

    @NotBlank
    @Column(name = "conta_destino_conta", nullable = false, length = 20)
    private String contaDestinoConta;

    @Column(name = "conta_destino_nome", length = 200)
    private String contaDestinoNome;

    @Column(name = "conta_destino_documento", length = 20)
    private String contaDestinoDocumento;

    @NotNull
    @DecimalMin("0.01")
    @Column(name = "valor", nullable = false, precision = 15, scale = 2)
    private BigDecimal valor;

    @Column(name = "descricao", length = 500)
    private String descricao;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private StatusTed status = StatusTed.PENDENTE;

    @Column(name = "data_processamento")
    private LocalDateTime dataProcessamento;

    @Column(name = "data_confirmacao")
    private LocalDateTime dataConfirmacao;

    @Column(name = "codigo_banco_destino", length = 3)
    private String codigoBancoDestino;

    @Column(name = "agencia_destino", length = 4)
    private String agenciaDestino;

    @Column(name = "motivo_falha", length = 500)
    private String motivoFalha;

    @Column(name = "spi_protocolo", length = 50)
    private String spiProtocolo;

    public enum StatusTed {
        PENDENTE, PROCESSADA, CONFIRMADA, FALHOU, CANCELADA
    }

    public TransferenciaTed() {}

    public TransferenciaTed(Long contaOrigemId, String ispbDestino, String contaDestinoAgencia,
                            String contaDestinoConta, BigDecimal valor) {
        this.contaOrigemId = contaOrigemId;
        this.ispbDestino = ispbDestino;
        this.contaDestinoAgencia = contaDestinoAgencia;
        this.contaDestinoConta = contaDestinoConta;
        this.valor = valor;
    }

    public Long getContaOrigemId() { return contaOrigemId; }
    public void setContaOrigemId(Long contaOrigemId) { this.contaOrigemId = contaOrigemId; }
    public String getContaOrigemNumero() { return contaOrigemNumero; }
    public void setContaOrigemNumero(String contaOrigemNumero) { this.contaOrigemNumero = contaOrigemNumero; }
    public String getIspbDestino() { return ispbDestino; }
    public void setIspbDestino(String ispbDestino) { this.ispbDestino = ispbDestino; }
    public String getContaDestinoAgencia() { return contaDestinoAgencia; }
    public void setContaDestinoAgencia(String contaDestinoAgencia) { this.contaDestinoAgencia = contaDestinoAgencia; }
    public String getContaDestinoConta() { return contaDestinoConta; }
    public void setContaDestinoConta(String contaDestinoConta) { this.contaDestinoConta = contaDestinoConta; }
    public String getContaDestinoNome() { return contaDestinoNome; }
    public void setContaDestinoNome(String contaDestinoNome) { this.contaDestinoNome = contaDestinoNome; }
    public String getContaDestinoDocumento() { return contaDestinoDocumento; }
    public void setContaDestinoDocumento(String contaDestinoDocumento) { this.contaDestinoDocumento = contaDestinoDocumento; }
    public BigDecimal getValor() { return valor; }
    public void setValor(BigDecimal valor) { this.valor = valor; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public StatusTed getStatus() { return status; }
    public void setStatus(StatusTed status) { this.status = status; }
    public LocalDateTime getDataProcessamento() { return dataProcessamento; }
    public void setDataProcessamento(LocalDateTime dataProcessamento) { this.dataProcessamento = dataProcessamento; }
    public LocalDateTime getDataConfirmacao() { return dataConfirmacao; }
    public void setDataConfirmacao(LocalDateTime dataConfirmacao) { this.dataConfirmacao = dataConfirmacao; }
    public String getCodigoBancoDestino() { return codigoBancoDestino; }
    public void setCodigoBancoDestino(String codigoBancoDestino) { this.codigoBancoDestino = codigoBancoDestino; }
    public String getAgenciaDestino() { return agenciaDestino; }
    public void setAgenciaDestino(String agenciaDestino) { this.agenciaDestino = agenciaDestino; }
    public String getMotivoFalha() { return motivoFalha; }
    public void setMotivoFalha(String motivoFalha) { this.motivoFalha = motivoFalha; }
    public String getSpiProtocolo() { return spiProtocolo; }
    public void setSpiProtocolo(String spiProtocolo) { this.spiProtocolo = spiProtocolo; }
}
