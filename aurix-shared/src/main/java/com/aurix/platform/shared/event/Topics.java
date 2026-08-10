package com.aurix.platform.shared.event;

/**
 * Nomes de tópico Kafka usados pela plataforma, seguindo a convenção
 * {@code <dominio>.<entidade>.<evento>.<versao>} definida no ADR-0001
 * ({@code docs/02-technical/arquitetura/adr/0001-comunicacao-entre-servicos.md}).
 *
 * <p>Centralizado aqui (em vez de strings literais espalhadas em publishers e
 * consumers) para eliminar o risco de o nome do tópico divergir entre quem
 * publica e quem consome.</p>
 */
public final class Topics {

    private Topics() {
    }

    // ===== aurix-core: conta =====
    public static final String CONTA_CRIADA = "core.conta.criada.v1";
    public static final String CONTA_ATUALIZADA = "core.conta.atualizada.v1";
    public static final String CONTA_BLOQUEADA = "core.conta.bloqueada.v1";

    // ===== aurix-core / aurix-pix: transacao =====
    public static final String TRANSACAO_REALIZADA = "core.transacao.realizada.v1";
    public static final String TRANSACAO_LIQUIDADA = "core.transacao.liquidada.v1";
    public static final String TRANSACAO_CONCILIADA = "core.transacao.conciliada.v1";

    // ===== aurix-tax: imposto =====
    public static final String IMPOSTO_CALCULADO = "tax.imposto.calculado.v1";
    public static final String IMPOSTO_REGISTRADO = "tax.imposto.registrado.v1";

    // ===== aurix-settlement: liquidez =====
    public static final String LIQUIDEZ_PROCESSADA = "settlement.liquidez.processada.v1";
    public static final String LIQUIDEZ_REJEITADA = "settlement.liquidez.rejeitada.v1";

    // ===== aurix-billing: fatura =====
    public static final String FATURA_EMITIDA = "billing.fatura.emitida.v1";
    public static final String FATURA_PAGA = "billing.fatura.paga.v1";

    // ===== aurix-bacen: relatorio =====
    public static final String RELATORIO_GERADO = "bacen.relatorio.gerado.v1";
    public static final String RELATORIO_ENVIADO = "bacen.relatorio.enviado.v1";

    // ===== customer =====
    public static final String CUSTOMER_CLIENTE_CRIADO = "customer.cliente.criado.v1";
    public static final String CUSTOMER_CLIENTE_ATUALIZADO = "customer.cliente.atualizado.v1";
    public static final String CUSTOMER_CLIENTE_STATUS_ALTERADO = "customer.cliente.status.alterado.v1";

    // ===== kyc =====
    public static final String KYC_SOLICITACAO_APROVADA = "kyc.solicitacao.aprovada.v1";
    public static final String KYC_SOLICITACAO_REJEITADA = "kyc.solicitacao.rejeitada.v1";

    // ===== fraud =====
    public static final String FRAUD_TRANSACAO_BLOQUEADA = "fraud.transacao.bloqueada.v1";
    public static final String FRAUD_OCORRENCIA_CRIADA = "fraud.ocorrencia.criada.v1";
    public static final String FRAUD_SCORE_ALTERADO = "fraud.score.alterado.v1";

    // ===== notification =====
    public static final String NOTIFICATION_NOTIFICACAO_ENVIADA = "notification.notificacao.enviada.v1";
    public static final String NOTIFICATION_NOTIFICACAO_FALHOU = "notification.notificacao.falhou.v1";

    // ===== credit =====
    public static final String CREDIT_SOLICITACAO_CRIADA = "credit.solicitacao.criada.v1";

    // ===== cartoes =====
    public static final String CARTOES_CARTAO_EMITIDO = "cartoes.cartao.emitido.v1";
    public static final String CARTOES_TRANSACAO_AUTORIZADA = "cartoes.transacao.autorizada.v1";
    public static final String CARTOES_TRANSACAO_ESTORNADA = "cartoes.transacao.estornada.v1";
    public static final String CARTOES_FATURA_FECHADA = "cartoes.fatura.fechada.v1";
    public static final String CARTOES_FATURA_PAGA = "cartoes.fatura.paga.v1";

    // ===== consignado =====
    public static final String CONSIGNADO_CONTRATO_ASSINADO = "consignado.contrato.assinado.v1";

    // ===== financiamento =====
    public static final String FINANCIAMENTO_CONTRATO_ASSINADO = "financiamento.contrato.assinado.v1";

    // ===== investimento =====
    public static final String INVESTIMENTO_ORDEM_EXECUTADA = "investimento.ordem.executada.v1";

    // ===== seguros =====
    public static final String SEGUROS_APOLICE_EMITIDA = "seguros.apolice.emitida.v1";

    // ===== cambio =====
    public static final String CAMBIO_COTACAO_ATUALIZADA = "cambio.cotacao.atualizada.v1";
    public static final String CAMBIO_CONTRATO_FECHADO = "cambio.contrato.fechado.v1";
    public static final String CAMBIO_CONTRATO_LIQUIDADO = "cambio.contrato.liquidado.v1";
    public static final String CAMBIO_REMESSA_PROCESSADA = "cambio.remessa.processada.v1";

    // ===== consignado (full) =====
    public static final String CONSIGNADO_PARCELA_DEBITADA = "consignado.parcela.debitada.v1";
    public static final String CONSIGNADO_MARGEM_ATUALIZADA = "consignado.margem.atualizada.v1";
    public static final String CONSIGNADO_CONTRATO_LIQUIDADO = "consignado.contrato.liquidado.v1";

    // ===== financiamento (full) =====
    public static final String FINANCIAMENTO_SIMULACAO_REALIZADA = "financiamento.simulacao.realizada.v1";
    public static final String FINANCIAMENTO_PARCELA_PAGA = "financiamento.parcela.paga.v1";
    public static final String FINANCIAMENTO_CONTRATO_LIQUIDADO = "financiamento.contrato.liquidado.v1";
    public static final String FINANCIAMENTO_GARANTIA_REGISTRADA = "financiamento.garantia.registrada.v1";

    // ===== investimento (full) =====
    public static final String INVESTIMENTO_CONTA_CRIADA = "investimento.conta.criada.v1";
    public static final String INVESTIMENTO_RESGATE_PROCESSADO = "investimento.resgate.processado.v1";

    // ===== poupanca =====
    public static final String POUPANCA_CONTA_CRIADA = "poupanca.conta.criada.v1";
    public static final String POUPANCA_DEPOSITO_REALIZADO = "poupanca.deposito.realizado.v1";
    public static final String POUPANCA_SAQUE_REALIZADO = "poupanca.saque.realizado.v1";
    public static final String POUPANCA_RENDIMENTO_CREDITADO = "poupanca.rendimento.creditado.v1";

    // ===== salario =====
    public static final String SALARIO_CONTA_CRIADA = "salario.conta.criada.v1";
    public static final String SALARIO_CREDITADO = "salario.creditado.v1";
    public static final String SALARIO_PORTABILIDADE_SOLICITADA = "salario.portabilidade.solicitada.v1";

    // ===== seguros (full) =====
    public static final String SEGUROS_PREMIO_PAGO = "seguros.premio.pago.v1";
    public static final String SEGUROS_SINISTRO_ABERTO = "seguros.sinistro.aberto.v1";
    public static final String SEGUROS_SINISTRO_LIQUIDADO = "seguros.sinistro.liquidado.v1";

    // ===== acquirer =====
    public static final String ACQUIRER_TRANSACAO_AUTORIZADA = "acquirer.transacao.autorizada.v1";
    public static final String ACQUIRER_TRANSACAO_CAPTURADA = "acquirer.transacao.capturada.v1";
    public static final String ACQUIRER_TRANSACAO_LIQUIDADA = "acquirer.transacao.liquidada.v1";
    public static final String ACQUIRER_TRANSACAO_ESTORNADA = "acquirer.transacao.estornada.v1";

    // ===== collections =====
    public static final String COLLECTIONS_BOLETO_EMITIDO = "collections.boleto.emitido.v1";
    public static final String COLLECTIONS_COBRANCA_PAGA = "collections.cobranca.paga.v1";
    public static final String COLLECTIONS_COBRANCA_NEGATIVADA = "collections.cobranca.negativada.v1";
    public static final String COLLECTIONS_COBRANCA_CANCELADA = "collections.cobranca.cancelada.v1";

    // ===== guarantee =====
    public static final String GUARANTEE_GARANTIA_REGISTRADA = "guarantee.garantia.registrada.v1";
    public static final String GUARANTEE_GARANTIA_LIBERADA = "guarantee.garantia.liberada.v1";

    // ===== infraestrutura: dead letter queue =====
    public static final String DLQ = "aurix-dlq";
    public static final String REPROCESS = "aurix-reprocess";
    public static final String DLQ_PERMANENT = "aurix-dlq-permanent";

    // ===== compliance: lgpd =====
    public static final String LGPD_DADOS_EXCLUIDOS = "compliance.lgpd.dados.excluidos.v1";

    // ===== products: catalogo =====
    public static final String PRODUTO_CRIADO = "products.produto.criado.v1";
    public static final String PRODUTO_ATUALIZADO = "products.produto.atualizado.v1";
    public static final String PRODUTO_DESCONTINUADO = "products.produto.descontinuado.v1";

    // ===== contracts: gestao de contratos =====
    public static final String CONTRATO_CRIADO = "contracts.contrato.criado.v1";
    public static final String CONTRATO_ASSINADO = "contracts.contrato.assinado.v1";
    public static final String CONTRATO_LIQUIDADO = "contracts.contrato.liquidado.v1";
    public static final String CONTRATO_CANCELADO = "contracts.contrato.cancelado.v1";
}
