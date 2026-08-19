-- ============================================================
-- Flyway: V1 — svc-savings
-- Tabelas de contas poupanca e movimentacoes.
-- ============================================================

CREATE TABLE IF NOT EXISTS aurix.contas_poupanca (
    id                      BIGSERIAL PRIMARY KEY,
    tenant_id               VARCHAR(64) NOT NULL,
    cliente_id              BIGINT NOT NULL,
    conta_corrente_id       BIGINT,
    numero_conta            VARCHAR(30) NOT NULL,
    saldo                   NUMERIC(15,2) NOT NULL DEFAULT 0.00,
    aniversario_dia         INTEGER NOT NULL DEFAULT 1,
    taxa_rendimento         NUMERIC(8,6) DEFAULT 0.061700,
    data_abertura           DATE NOT NULL,
    ultimo_aniversario      DATE,
    status                  VARCHAR(20) NOT NULL DEFAULT 'ATIVA',
    data_criacao            TIMESTAMP NOT NULL DEFAULT NOW(),
    data_atualizacao        TIMESTAMP,
    versao                  INTEGER DEFAULT 1
);

CREATE INDEX IF NOT EXISTS idx_poupanca_tenant ON aurix.contas_poupanca (tenant_id);
CREATE INDEX IF NOT EXISTS idx_poupanca_cliente ON aurix.contas_poupanca (tenant_id, cliente_id);
CREATE INDEX IF NOT EXISTS idx_poupanca_numero ON aurix.contas_poupanca (tenant_id, numero_conta);
CREATE INDEX IF NOT EXISTS idx_poupanca_status ON aurix.contas_poupanca (tenant_id, status);

CREATE TABLE IF NOT EXISTS aurix.movimentacoes_poupanca (
    id                      BIGSERIAL PRIMARY KEY,
    tenant_id               VARCHAR(64) NOT NULL,
    poupanca_id             BIGINT NOT NULL REFERENCES aurix.contas_poupanca(id),
    tipo_movimentacao       VARCHAR(20) NOT NULL,
    valor                   NUMERIC(15,2) NOT NULL,
    saldo_anterior          NUMERIC(15,2),
    saldo_atual             NUMERIC(15,2),
    descricao               VARCHAR(500),
    data_movimentacao       TIMESTAMP NOT NULL DEFAULT NOW(),
    data_criacao            TIMESTAMP NOT NULL DEFAULT NOW(),
    data_atualizacao        TIMESTAMP,
    versao                  INTEGER DEFAULT 1
);

CREATE INDEX IF NOT EXISTS idx_mov_poupanca ON aurix.movimentacoes_poupanca (poupanca_id);
CREATE INDEX IF NOT EXISTS idx_mov_poupanca_tenant ON aurix.movimentacoes_poupanca (tenant_id);
CREATE INDEX IF NOT EXISTS idx_mov_poupanca_data ON aurix.movimentacoes_poupanca (data_movimentacao);
