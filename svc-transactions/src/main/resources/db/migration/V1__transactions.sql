-- ============================================================
-- Flyway: V1 — svc-transactions
-- Tabela de transacoes financeiras e controle de saldo.
-- ============================================================

CREATE TABLE IF NOT EXISTS aurix.transacoes (
    id                      BIGSERIAL PRIMARY KEY,
    tenant_id               VARCHAR(64) NOT NULL,
    codigo_transacao        VARCHAR(30) NOT NULL,
    conta_origem_id         BIGINT,
    conta_destino_id        BIGINT,
    tipo_transacao          VARCHAR(30) NOT NULL DEFAULT 'TRANSFERENCIA_INTERNA',
    valor                   NUMERIC(15,2) NOT NULL,
    descricao               VARCHAR(500),
    status                  VARCHAR(20) NOT NULL DEFAULT 'PENDENTE',
    dados_pix               TEXT,
    dados_ted               TEXT,
    data_transacao          TIMESTAMP NOT NULL DEFAULT NOW(),
    data_processamento      TIMESTAMP,
    data_criacao            TIMESTAMP NOT NULL DEFAULT NOW(),
    data_atualizacao        TIMESTAMP,
    versao                  INTEGER DEFAULT 1
);

CREATE INDEX IF NOT EXISTS idx_transacao_tenant ON aurix.transacoes (tenant_id);
CREATE INDEX IF NOT EXISTS idx_transacao_codigo ON aurix.transacoes (tenant_id, codigo_transacao);
CREATE INDEX IF NOT EXISTS idx_transacao_conta_origem ON aurix.transacoes (conta_origem_id);
CREATE INDEX IF NOT EXISTS idx_transacao_conta_destino ON aurix.transacoes (conta_destino_id);
CREATE INDEX IF NOT EXISTS idx_transacao_status ON aurix.transacoes (tenant_id, status);
CREATE INDEX IF NOT EXISTS idx_transacao_data ON aurix.transacoes (tenant_id, data_transacao);

-- Controle de saldo para conciliacao
CREATE TABLE IF NOT EXISTS aurix.controle_saldo (
    id                      BIGSERIAL PRIMARY KEY,
    tenant_id               VARCHAR(64) NOT NULL,
    conta_id                BIGINT NOT NULL,
    saldo_disponivel        NUMERIC(15,2) NOT NULL DEFAULT 0.00,
    saldo_bloqueado         NUMERIC(15,2) NOT NULL DEFAULT 0.00,
    saldo_pendente          NUMERIC(15,2) NOT NULL DEFAULT 0.00,
    saldo_total             NUMERIC(15,2) NOT NULL DEFAULT 0.00,
    bloqueio_operacoes      BOOLEAN DEFAULT FALSE,
    versao_saldo            INTEGER NOT NULL DEFAULT 1,
    data_ultima_atualizacao TIMESTAMP,
    data_criacao            TIMESTAMP NOT NULL DEFAULT NOW(),
    data_atualizacao        TIMESTAMP
);

CREATE INDEX IF NOT EXISTS idx_controle_saldo_conta ON aurix.controle_saldo (conta_id);
CREATE INDEX IF NOT EXISTS idx_controle_saldo_tenant ON aurix.controle_saldo (tenant_id);
