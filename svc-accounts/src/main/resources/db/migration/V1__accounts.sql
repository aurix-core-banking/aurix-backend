-- ============================================================
-- Flyway: V1 — svc-accounts
-- Tabela de controle de contas correntes e seu saldo.
-- ============================================================

CREATE TABLE IF NOT EXISTS aurix.contas (
    id                      BIGSERIAL PRIMARY KEY,
    tenant_id               VARCHAR(64) NOT NULL,
    numero_conta            VARCHAR(20) NOT NULL,
    cliente_id              BIGINT NOT NULL,
    tipo_conta              VARCHAR(20) NOT NULL DEFAULT 'CORRENTE',
    saldo                   NUMERIC(15,2) NOT NULL DEFAULT 0.00,
    limite_credito          NUMERIC(15,2) NOT NULL DEFAULT 0.00,
    limite_utilizado        NUMERIC(15,2) NOT NULL DEFAULT 0.00,
    status                  VARCHAR(20) NOT NULL DEFAULT 'ATIVA',
    dados_extras            TEXT,
    data_abertura           TIMESTAMP NOT NULL DEFAULT NOW(),
    data_fechamento         TIMESTAMP,
    data_criacao            TIMESTAMP NOT NULL DEFAULT NOW(),
    data_atualizacao        TIMESTAMP,
    versao                  INTEGER DEFAULT 1
);

CREATE INDEX IF NOT EXISTS idx_conta_tenant ON aurix.contas (tenant_id);
CREATE INDEX IF NOT EXISTS idx_conta_numero ON aurix.contas (tenant_id, numero_conta);
CREATE INDEX IF NOT EXISTS idx_conta_cliente ON aurix.contas (tenant_id, cliente_id);
CREATE INDEX IF NOT EXISTS idx_conta_status ON aurix.contas (tenant_id, status);
