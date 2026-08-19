-- Renegociações de crédito
CREATE TABLE renegociacoes (
    id BIGSERIAL PRIMARY KEY,
    tenant_id VARCHAR(50) NOT NULL,
    contrato_original_id BIGINT NOT NULL,
    cliente_id BIGINT NOT NULL,
    saldo_devedor_anterior NUMERIC(18,2) NOT NULL,
    saldo_devedor_renegociado NUMERIC(18,2) NOT NULL,
    taxa_juros_anterior NUMERIC(7,5) NOT NULL,
    taxa_juros_renegociada NUMERIC(7,5) NOT NULL,
    prazo_anterior INT NOT NULL,
    prazo_renegociado INT NOT NULL,
    valor_parcela_anterior NUMERIC(18,2) NOT NULL,
    valor_parcela_renegociada NUMERIC(18,2) NOT NULL,
    sistema_amortizacao VARCHAR(10) NOT NULL,
    status VARCHAR(20) NOT NULL DEFAULT 'PENDENTE',
    data_solicitacao TIMESTAMP NOT NULL DEFAULT NOW(),
    data_aprovacao TIMESTAMP,
    data_contratacao TIMESTAMP,
    observacoes TEXT,
    data_criacao TIMESTAMP NOT NULL DEFAULT NOW(),
    data_atualizacao TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_renegociacoes_cliente ON renegociacoes(cliente_id);
CREATE INDEX idx_renegociacoes_contrato ON renegociacoes(contrato_original_id);
CREATE INDEX idx_renegociacoes_status ON renegociacoes(status);

-- Parcelas da renegociação
CREATE TABLE renegociacao_parcelas (
    id BIGSERIAL PRIMARY KEY,
    renegociacao_id BIGINT NOT NULL REFERENCES renegociacoes(id),
    numero INT NOT NULL,
    data_vencimento DATE NOT NULL,
    valor_parcela NUMERIC(18,2) NOT NULL,
    valor_amortizacao NUMERIC(18,2) NOT NULL,
    valor_juros NUMERIC(18,2) NOT NULL,
    valor_saldo_devedor NUMERIC(18,2) NOT NULL,
    data_pagamento DATE,
    status VARCHAR(20) NOT NULL DEFAULT 'PENDENTE',
    data_criacao TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_reneg_parcelas_reneg ON renegociacao_parcelas(renegociacao_id);

-- Seguros prestamista
CREATE TABLE seguros_prestamista (
    id BIGSERIAL PRIMARY KEY,
    tenant_id VARCHAR(50) NOT NULL,
    contrato_id BIGINT NOT NULL,
    cliente_id BIGINT NOT NULL,
    saldo_devedor_inicial NUMERIC(18,2) NOT NULL,
    taxa_mensal NUMERIC(7,4) NOT NULL DEFAULT 0.005,
    valor_premio_mensal NUMERIC(18,2) NOT NULL,
    coberturas JSONB NOT NULL DEFAULT '["MANTE","INVALIDEZ_PERMANENTE","DESEMPREGO"]',
    carencia_dias INT NOT NULL DEFAULT 30,
    data_inicio DATE NOT NULL,
    data_fim DATE,
    data_cancelamento DATE,
    motivo_cancelamento TEXT,
    status VARCHAR(20) NOT NULL DEFAULT 'ATIVO',
    data_criacao TIMESTAMP NOT NULL DEFAULT NOW(),
    data_atualizacao TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_seguros_contrato ON seguros_prestamista(contrato_id);
CREATE INDEX idx_seguros_cliente ON seguros_prestamista(cliente_id);
CREATE INDEX idx_seguros_status ON seguros_prestamista(status);

-- Sinistros do seguro
CREATE TABLE sinistros (
    id BIGSERIAL PRIMARY KEY,
    seguro_id BIGINT NOT NULL REFERENCES seguros_prestamista(id),
    tipo_sinistro VARCHAR(30) NOT NULL,
    descricao TEXT NOT NULL,
    data_ocorrencia DATE NOT NULL,
    data_abertura TIMESTAMP NOT NULL DEFAULT NOW(),
    data_analise TIMESTAMP,
    data_resolucao TIMESTAMP,
    valor_indenizacao NUMERIC(18,2),
    status VARCHAR(20) NOT NULL DEFAULT 'ABERTO',
    documentos JSONB,
    data_criacao TIMESTAMP NOT NULL DEFAULT NOW(),
    data_atualizacao TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_sinistros_seguro ON sinistros(seguro_id);
CREATE INDEX idx_sinistros_status ON sinistros(status);
