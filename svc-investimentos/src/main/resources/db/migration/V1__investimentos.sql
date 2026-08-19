-- ============================================================
-- Flyway: V1 — Investimentos
-- Tabelas para produtos de investimento, aplicações, resgates,
-- portfólio e rendimentos.
-- ============================================================

-- ──────────────────────────────────────────────────────────────
-- 1. TABELA produtos_investimento
-- ──────────────────────────────────────────────────────────────
CREATE TABLE IF NOT EXISTS aurix.produtos_investimento (
    id                      BIGSERIAL PRIMARY KEY,
    tenant_id               VARCHAR(64) NOT NULL,
    nome                    VARCHAR(100) NOT NULL,
    descricao               VARCHAR(500),
    tipo                    VARCHAR(20) NOT NULL,
    tipo_renda              VARCHAR(10) NOT NULL,
    taxa_rendimento         NUMERIC(7,5) NOT NULL,
    taxa_adm                NUMERIC(7,5),
    valor_minimo            NUMERIC(18,2) NOT NULL,
    prazo_minimo_dias       INTEGER NOT NULL,
    data_vencimento         DATE,
    carencia_dias           INTEGER,
    ativo                   BOOLEAN NOT NULL DEFAULT TRUE,
    data_criacao            TIMESTAMP NOT NULL DEFAULT NOW(),
    data_atualizacao        TIMESTAMP,
    versao                  INTEGER DEFAULT 1
);

CREATE INDEX IF NOT EXISTS idx_produto_inv_tenant ON aurix.produtos_investimento (tenant_id);
CREATE INDEX IF NOT EXISTS idx_produto_inv_tipo ON aurix.produtos_investimento (tipo);
CREATE INDEX IF NOT EXISTS idx_produto_inv_renda ON aurix.produtos_investimento (tipo_renda);
CREATE INDEX IF NOT EXISTS idx_produto_inv_ativo ON aurix.produtos_investimento (ativo);

-- ──────────────────────────────────────────────────────────────
-- 2. TABELA aplicacoes
-- ──────────────────────────────────────────────────────────────
CREATE TABLE IF NOT EXISTS aurix.aplicacoes (
    id                      BIGSERIAL PRIMARY KEY,
    tenant_id               VARCHAR(64) NOT NULL,
    cliente_id              BIGINT NOT NULL,
    produto_id              BIGINT NOT NULL REFERENCES aurix.produtos_investimento(id),
    produto_tipo            VARCHAR(20) NOT NULL,
    valor_aplicado          NUMERIC(18,2) NOT NULL,
    valor_bruto             NUMERIC(18,2) NOT NULL,
    valor_liquido           NUMERIC(18,2) NOT NULL,
    iof                     NUMERIC(18,2) DEFAULT 0,
    ir                      NUMERIC(18,2) DEFAULT 0,
    taxa_rendimento         NUMERIC(7,5) NOT NULL,
    data_aplicacao          DATE NOT NULL,
    data_vencimento         DATE NOT NULL,
    data_resgate            DATE,
    status                  VARCHAR(20) NOT NULL DEFAULT 'APLICADA',
    conta_corrente_id       BIGINT NOT NULL,
    data_criacao            TIMESTAMP NOT NULL DEFAULT NOW(),
    data_atualizacao        TIMESTAMP,
    versao                  INTEGER DEFAULT 1
);

CREATE INDEX IF NOT EXISTS idx_aplicacao_cliente ON aurix.aplicacoes (cliente_id);
CREATE INDEX IF NOT EXISTS idx_aplicacao_produto ON aurix.aplicacoes (produto_id);
CREATE INDEX IF NOT EXISTS idx_aplicacao_status ON aurix.aplicacoes (status);
CREATE INDEX IF NOT EXISTS idx_aplicacao_tenant ON aurix.aplicacoes (tenant_id);
CREATE INDEX IF NOT EXISTS idx_aplicacao_vencimento ON aurix.aplicacoes (data_vencimento);

-- ──────────────────────────────────────────────────────────────
-- 3. TABELA resgates
-- ──────────────────────────────────────────────────────────────
CREATE TABLE IF NOT EXISTS aurix.resgates (
    id                      BIGSERIAL PRIMARY KEY,
    tenant_id               VARCHAR(64) NOT NULL,
    aplicacao_id            BIGINT NOT NULL REFERENCES aurix.aplicacoes(id),
    cliente_id              BIGINT NOT NULL,
    valor_resgatado         NUMERIC(18,2) NOT NULL,
    iof                     NUMERIC(18,2) DEFAULT 0,
    ir                      NUMERIC(18,2) DEFAULT 0,
    valor_liquido           NUMERIC(18,2) NOT NULL,
    data_solicitacao        TIMESTAMP NOT NULL DEFAULT NOW(),
    data_processamento      TIMESTAMP,
    status                  VARCHAR(20) NOT NULL DEFAULT 'PENDENTE',
    motivo                  VARCHAR(500),
    data_criacao            TIMESTAMP NOT NULL DEFAULT NOW(),
    data_atualizacao        TIMESTAMP,
    versao                  INTEGER DEFAULT 1
);

CREATE INDEX IF NOT EXISTS idx_resgate_aplicacao ON aurix.resgates (aplicacao_id);
CREATE INDEX IF NOT EXISTS idx_resgate_cliente ON aurix.resgates (cliente_id);
CREATE INDEX IF NOT EXISTS idx_resgate_status ON aurix.resgates (status);
CREATE INDEX IF NOT EXISTS idx_resgate_tenant ON aurix.resgates (tenant_id);

-- ──────────────────────────────────────────────────────────────
-- 4. TABELA portfolio
-- ──────────────────────────────────────────────────────────────
CREATE TABLE IF NOT EXISTS aurix.portfolio (
    id                      BIGSERIAL PRIMARY KEY,
    tenant_id               VARCHAR(64) NOT NULL,
    cliente_id              BIGINT NOT NULL,
    valor_total_aplicado    NUMERIC(18,2) NOT NULL DEFAULT 0,
    valor_total_bruto       NUMERIC(18,2) NOT NULL DEFAULT 0,
    valor_total_liquido     NUMERIC(18,2) NOT NULL DEFAULT 0,
    rendimento_total        NUMERIC(18,2) NOT NULL DEFAULT 0,
    total_aplicacoes        INTEGER NOT NULL DEFAULT 0,
    data_atualizacao        TIMESTAMP DEFAULT NOW(),
    data_criacao            TIMESTAMP NOT NULL DEFAULT NOW(),
    versao                  INTEGER DEFAULT 1
);

CREATE UNIQUE INDEX IF NOT EXISTS idx_portfolio_cliente ON aurix.portfolio (cliente_id, tenant_id);
CREATE INDEX IF NOT EXISTS idx_portfolio_tenant ON aurix.portfolio (tenant_id);

-- ──────────────────────────────────────────────────────────────
-- 5. TABELA rendimentos
-- ──────────────────────────────────────────────────────────────
CREATE TABLE IF NOT EXISTS aurix.rendimentos (
    id                      BIGSERIAL PRIMARY KEY,
    tenant_id               VARCHAR(64) NOT NULL,
    aplicacao_id            BIGINT NOT NULL REFERENCES aurix.aplicacoes(id),
    cliente_id              BIGINT NOT NULL,
    data_rendimento         DATE NOT NULL,
    valor_rendimento        NUMERIC(18,2) NOT NULL,
    taxa_aplicada           NUMERIC(7,5) NOT NULL,
    saldo_acumulado         NUMERIC(18,2) NOT NULL,
    data_criacao            TIMESTAMP NOT NULL DEFAULT NOW(),
    versao                  INTEGER DEFAULT 1
);

CREATE INDEX IF NOT EXISTS idx_rendimento_aplicacao ON aurix.rendimentos (aplicacao_id);
CREATE INDEX IF NOT EXISTS idx_rendimento_cliente ON aurix.rendimentos (cliente_id);
CREATE INDEX IF NOT EXISTS idx_rendimento_data ON aurix.rendimentos (data_rendimento);
CREATE INDEX IF NOT EXISTS idx_rendimento_tenant ON aurix.rendimentos (tenant_id);
