-- V2__cartoes_virtuais_tokens_fidelidade.sql
-- Tabelas para cartões virtuais, tokenização, chargeback e programa de fidelidade

-- Tabela de cartões virtuais
CREATE TABLE IF NOT EXISTS aurix.cartoes_virtuais (
    id BIGSERIAL PRIMARY KEY,
    cartao_fisico_id BIGINT NOT NULL REFERENCES aurix.cartoes(id),
    numero_cartao VARCHAR(16) NOT NULL UNIQUE,
    numero_cartao_mascarado VARCHAR(30) NOT NULL,
    cvv VARCHAR(3) NOT NULL,
    data_validade DATE NOT NULL,
    nome_portador VARCHAR(100) NOT NULL,
    conta_id BIGINT NOT NULL,
    bandeira VARCHAR(20) NOT NULL,
    status VARCHAR(20) NOT NULL DEFAULT 'ATIVO',
    limite_credito NUMERIC(19,4) NOT NULL DEFAULT 0,
    limite_utilizado NUMERIC(19,4) NOT NULL DEFAULT 0,
    limite_disponivel NUMERIC(19,4) NOT NULL DEFAULT 0,
    data_criacao TIMESTAMP NOT NULL DEFAULT NOW(),
    data_ativacao TIMESTAMP,
    data_bloqueio TIMESTAMP,
    data_cancelamento TIMESTAMP,
    motivo_bloqueio VARCHAR(500),
    permite_compras_nacionais BOOLEAN NOT NULL DEFAULT TRUE,
    permite_compras_internacionais BOOLEAN NOT NULL DEFAULT FALSE,
    permite_saque BOOLEAN NOT NULL DEFAULT FALSE,
    permite_parcelamento BOOLEAN NOT NULL DEFAULT FALSE,
    tenant_id VARCHAR(64)
);

CREATE INDEX idx_cartoes_virtuais_fisico ON aurix.cartoes_virtuais(cartao_fisico_id);
CREATE INDEX idx_cartoes_virtuais_conta ON aurix.cartoes_virtuais(conta_id);
CREATE INDEX idx_cartoes_virtuais_status ON aurix.cartoes_virtuais(status);
CREATE INDEX idx_cartoes_virtuais_tenant ON aurix.cartoes_virtuais(tenant_id);

-- Tabela de tokens de cartão (vault)
CREATE TABLE IF NOT EXISTS aurix.card_tokens (
    id BIGSERIAL PRIMARY KEY,
    token_formatado VARCHAR(40) NOT NULL UNIQUE,
    cartao_id BIGINT NOT NULL REFERENCES aurix.cartoes(id),
    merchant_id VARCHAR(100) NOT NULL,
    pan_encriptado TEXT NOT NULL,
    ultimos_4_digitos VARCHAR(4) NOT NULL,
    bandeira VARCHAR(20) NOT NULL,
    descricao VARCHAR(200),
    status VARCHAR(20) NOT NULL DEFAULT 'ATIVO',
    data_criacao TIMESTAMP NOT NULL DEFAULT NOW(),
    data_expiracao TIMESTAMP NOT NULL,
    data_revogacao TIMESTAMP,
    tenant_id VARCHAR(64)
);

CREATE INDEX idx_card_tokens_cartao ON aurix.card_tokens(cartao_id);
CREATE INDEX idx_card_tokens_merchant ON aurix.card_tokens(merchant_id);
CREATE INDEX idx_card_tokens_status ON aurix.card_tokens(status);
CREATE INDEX idx_card_tokens_tenant ON aurix.card_tokens(tenant_id);

-- Tabela de chargebacks
CREATE TABLE IF NOT EXISTS aurix.card_chargebacks (
    id BIGSERIAL PRIMARY KEY,
    transacao_id BIGINT NOT NULL REFERENCES aurix.transacoes_cartao(id),
    cartao_id BIGINT NOT NULL REFERENCES aurix.cartoes(id),
    motivo VARCHAR(50) NOT NULL,
    descricao TEXT,
    valor_transacao NUMERIC(19,4) NOT NULL,
    status VARCHAR(20) NOT NULL DEFAULT 'ANALISE',
    data_solicitacao TIMESTAMP NOT NULL DEFAULT NOW(),
    data_prazo_resolucao TIMESTAMP NOT NULL,
    data_estorno_temporario TIMESTAMP NOT NULL,
    data_resolucao TIMESTAMP,
    resultado VARCHAR(100),
    tenant_id VARCHAR(64)
);

CREATE INDEX idx_card_chargebacks_transacao ON aurix.card_chargebacks(transacao_id);
CREATE INDEX idx_card_chargebacks_cartao ON aurix.card_chargebacks(cartao_id);
CREATE INDEX idx_card_chargebacks_status ON aurix.card_chargebacks(status);
CREATE INDEX idx_card_chargebacks_tenant ON aurix.card_chargebacks(tenant_id);

-- Tabela de evidências de chargeback
CREATE TABLE IF NOT EXISTS aurix.card_chargeback_evidencias (
    id BIGSERIAL PRIMARY KEY,
    chargeback_id BIGINT NOT NULL REFERENCES aurix.card_chargebacks(id),
    descricao TEXT NOT NULL,
    nome_arquivo VARCHAR(200),
    tamanho_arquivo BIGINT,
    data_envio TIMESTAMP NOT NULL DEFAULT NOW(),
    tenant_id VARCHAR(64)
);

CREATE INDEX idx_card_chargeback_ev_chargeback ON aurix.card_chargeback_evidencias(chargeback_id);

-- Tabela de pontos de fidelidade
CREATE TABLE IF NOT EXISTS aurix.fidelidade_pontos (
    id BIGSERIAL PRIMARY KEY,
    conta_id BIGINT NOT NULL,
    pontos_acumulados INTEGER NOT NULL DEFAULT 0,
    pontos_resgatados INTEGER NOT NULL DEFAULT 0,
    pontos_expirados INTEGER NOT NULL DEFAULT 0,
    pontos_disponiveis INTEGER NOT NULL DEFAULT 0,
    data_criacao TIMESTAMP NOT NULL DEFAULT NOW(),
    data_atualizacao TIMESTAMP NOT NULL DEFAULT NOW(),
    tenant_id VARCHAR(64)
);

CREATE UNIQUE INDEX idx_fidelidade_pontos_conta ON aurix.fidelidade_pontos(conta_id);
CREATE INDEX idx_fidelidade_pontos_tenant ON aurix.fidelidade_pontos(tenant_id);

-- Tabela de resgates de fidelidade
CREATE TABLE IF NOT EXISTS aurix.fidelidade_resgates (
    id BIGSERIAL PRIMARY KEY,
    conta_id BIGINT NOT NULL,
    pontos INTEGER NOT NULL,
    tipo_resgate VARCHAR(20) NOT NULL,
    parceiro VARCHAR(100),
    valor_estimado NUMERIC(19,4),
    status VARCHAR(20) NOT NULL DEFAULT 'PROCESSADO',
    data_resgate TIMESTAMP NOT NULL DEFAULT NOW(),
    data_processamento TIMESTAMP,
    tenant_id VARCHAR(64)
);

CREATE INDEX idx_fidelidade_resgates_conta ON aurix.fidelidade_resgates(conta_id);
CREATE INDEX idx_fidelidade_resgates_tipo ON aurix.fidelidade_resgates(tipo_resgate);
CREATE INDEX idx_fidelidade_resgates_tenant ON aurix.fidelidade_resgates(tenant_id);
