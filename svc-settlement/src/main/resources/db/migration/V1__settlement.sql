-- ============================================================
-- Flyway: V1 — svc-settlement
-- Tabela de liquidacoes e itens de liquidacao.
-- ============================================================

CREATE TABLE IF NOT EXISTS aurix.liquidacoes (
    id                          BIGSERIAL PRIMARY KEY,
    tenant_id                   VARCHAR(64) NOT NULL,
    codigo_liquidacao           VARCHAR(30) NOT NULL,
    transacao_id                BIGINT NOT NULL,
    tipo_liquidacao             VARCHAR(30) NOT NULL,
    status                      VARCHAR(20) NOT NULL DEFAULT 'PENDENTE',
    valor_liquidacao            NUMERIC(15,2) NOT NULL,
    valor_taxa                  NUMERIC(15,2) DEFAULT 0.00,
    valor_iof                   NUMERIC(15,2) DEFAULT 0.00,
    valor_total                 NUMERIC(15,2) NOT NULL,
    conta_origem                VARCHAR(20),
    conta_destino               VARCHAR(20),
    codigo_retorno              VARCHAR(50),
    mensagem_retorno            VARCHAR(500),
    codigo_spi                  VARCHAR(50),
    codigo_str                  VARCHAR(50),
    codigo_bacen                VARCHAR(100),
    protocolo_sistema           VARCHAR(100),
    processamento_automatico    BOOLEAN DEFAULT TRUE,
    reversivel                  BOOLEAN DEFAULT TRUE,
    tentativas_liquidacao       INTEGER DEFAULT 0,
    observacoes                 TEXT,
    erro_liquidacao             TEXT,
    data_liquidacao             TIMESTAMP NOT NULL DEFAULT NOW(),
    data_processamento          TIMESTAMP,
    data_conclusao              TIMESTAMP,
    data_criacao                TIMESTAMP NOT NULL DEFAULT NOW(),
    data_atualizacao            TIMESTAMP,
    versao                      INTEGER DEFAULT 1
);

CREATE INDEX IF NOT EXISTS idx_liquidacao_tenant ON aurix.liquidacoes (tenant_id);
CREATE INDEX IF NOT EXISTS idx_liquidacao_codigo ON aurix.liquidacoes (tenant_id, codigo_liquidacao);
CREATE INDEX IF NOT EXISTS idx_liquidacao_transacao ON aurix.liquidacoes (transacao_id);
CREATE INDEX IF NOT EXISTS idx_liquidacao_status ON aurix.liquidacoes (tenant_id, status);
CREATE INDEX IF NOT EXISTS idx_liquidacao_tipo ON aurix.liquidacoes (tenant_id, tipo_liquidacao);
CREATE INDEX IF NOT EXISTS idx_liquidacao_data ON aurix.liquidacoes (tenant_id, data_liquidacao);

CREATE TABLE IF NOT EXISTS aurix.liquidacoes_itens (
    id                      BIGSERIAL PRIMARY KEY,
    tenant_id               VARCHAR(64) NOT NULL,
    liquidacao_id           BIGINT NOT NULL REFERENCES aurix.liquidacoes(id),
    conta_id                BIGINT NOT NULL,
    tipo_movimento          VARCHAR(10) NOT NULL,
    valor_movimento         NUMERIC(15,2) NOT NULL,
    saldo_anterior          NUMERIC(15,2),
    saldo_posterior         NUMERIC(15,2),
    descricao_movimento     VARCHAR(500),
    codigo_movimento        VARCHAR(50),
    processado              BOOLEAN DEFAULT FALSE,
    data_movimento          TIMESTAMP NOT NULL DEFAULT NOW(),
    data_criacao            TIMESTAMP NOT NULL DEFAULT NOW(),
    data_atualizacao        TIMESTAMP,
    versao                  INTEGER DEFAULT 1
);

CREATE INDEX IF NOT EXISTS idx_liquidacao_item_liquidacao ON aurix.liquidacoes_itens (liquidacao_id);
CREATE INDEX IF NOT EXISTS idx_liquidacao_item_conta ON aurix.liquidacoes_itens (conta_id);
CREATE INDEX IF NOT EXISTS idx_liquidacao_item_tenant ON aurix.liquidacoes_itens (tenant_id);
