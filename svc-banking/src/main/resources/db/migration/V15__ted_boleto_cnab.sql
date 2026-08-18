-- ============================================================
-- Flyway: V15 — TED, Boleto, CNAB, Extrato
-- Tabelas para transferencias TED, boletos registrados,
-- arquivos CNAB240 (remessa/retorno) e extratos bancarios.
-- ============================================================

-- ──────────────────────────────────────────────────────────────
-- 1. TABELA ted_transferencias
-- ──────────────────────────────────────────────────────────────
CREATE TABLE IF NOT EXISTS aurix.ted_transferencias (
    id                      BIGSERIAL PRIMARY KEY,
    tenant_id               VARCHAR(64),
    conta_origem_id         BIGINT NOT NULL,
    conta_origem_numero     VARCHAR(20) NOT NULL,
    ispb_destino            VARCHAR(8) NOT NULL,
    conta_destino_agencia   VARCHAR(4) NOT NULL,
    conta_destino_conta     VARCHAR(20) NOT NULL,
    conta_destino_nome      VARCHAR(200),
    conta_destino_documento VARCHAR(20),
    valor                   NUMERIC(15,2) NOT NULL,
    descricao               VARCHAR(500),
    status                  VARCHAR(20) NOT NULL DEFAULT 'PENDENTE',
    data_processamento      TIMESTAMP,
    data_confirmacao        TIMESTAMP,
    codigo_banco_destino    VARCHAR(3),
    agencia_destino         VARCHAR(4),
    motivo_falha            VARCHAR(500),
    spi_protocolo           VARCHAR(50),
    data_criacao            TIMESTAMP NOT NULL DEFAULT NOW(),
    data_atualizacao        TIMESTAMP,
    versao                  INTEGER DEFAULT 1
);

CREATE INDEX IF NOT EXISTS idx_ted_conta_origem ON aurix.ted_transferencias (conta_origem_id);
CREATE INDEX IF NOT EXISTS idx_ted_status ON aurix.ted_transferencias (status);
CREATE INDEX IF NOT EXISTS idx_ted_tenant ON aurix.ted_transferencias (tenant_id);

-- ──────────────────────────────────────────────────────────────
-- 2. TABELA boletos_registrados
-- ──────────────────────────────────────────────────────────────
CREATE TABLE IF NOT EXISTS aurix.boletos_registrados (
    id                      BIGSERIAL PRIMARY KEY,
    tenant_id               VARCHAR(64),
    codigo_barras           VARCHAR(44) NOT NULL,
    linha_digitavel         VARCHAR(54) NOT NULL,
    conta_id                BIGINT NOT NULL,
    conta_numero            VARCHAR(20),
    valor                   NUMERIC(15,2) NOT NULL,
    data_vencimento         DATE NOT NULL,
    status                  VARCHAR(20) NOT NULL DEFAULT 'PENDENTE',
    multa_percentual        NUMERIC(5,2) DEFAULT 2.00,
    juros_percentual_mes    NUMERIC(5,2) DEFAULT 1.00,
    valor_multa             NUMERIC(15,2),
    valor_juros             NUMERIC(15,2),
    valor_total_pago        NUMERIC(15,2),
    data_pagamento          TIMESTAMP,
    data_baixa              TIMESTAMP,
    data_protesto           TIMESTAMP,
    nosso_numero            VARCHAR(20),
    beneficiario_nome       VARCHAR(200),
    beneficiario_documento  VARCHAR(20),
    pagador_nome            VARCHAR(200),
    pagador_documento       VARCHAR(20),
    descricao               VARCHAR(500),
    qtde_dias_protesto      INTEGER,
    aceite                  BOOLEAN DEFAULT FALSE,
    data_criacao            TIMESTAMP NOT NULL DEFAULT NOW(),
    data_atualizacao        TIMESTAMP,
    versao                  INTEGER DEFAULT 1
);

CREATE INDEX IF NOT EXISTS idx_boleto_codigo_barras ON aurix.boletos_registrados (codigo_barras);
CREATE INDEX IF NOT EXISTS idx_boleto_conta ON aurix.boletos_registrados (conta_id);
CREATE INDEX IF NOT EXISTS idx_boleto_status ON aurix.boletos_registrados (status);
CREATE INDEX IF NOT EXISTS idx_boleto_vencimento ON aurix.boletos_registrados (data_vencimento);
CREATE INDEX IF NOT EXISTS idx_boleto_tenant ON aurix.boletos_registrados (tenant_id);

-- ──────────────────────────────────────────────────────────────
-- 3. TABELA cnab_remessas
-- ──────────────────────────────────────────────────────────────
CREATE TABLE IF NOT EXISTS aurix.cnab_remessas (
    id                      BIGSERIAL PRIMARY KEY,
    tenant_id               VARCHAR(64),
    tipo                    VARCHAR(20) NOT NULL,
    arquivo_nome            VARCHAR(255) NOT NULL,
    conteudo_arquivo        TEXT,
    status                  VARCHAR(20) NOT NULL DEFAULT 'GERADO',
    total_registros         INTEGER,
    data_geracao            TIMESTAMP DEFAULT NOW(),
    data_envio              TIMESTAMP,
    data_criacao            TIMESTAMP NOT NULL DEFAULT NOW(),
    data_atualizacao        TIMESTAMP,
    versao                  INTEGER DEFAULT 1
);

CREATE INDEX IF NOT EXISTS idx_cnab_remessa_tenant ON aurix.cnab_remessas (tenant_id);
CREATE INDEX IF NOT EXISTS idx_cnab_remessa_tipo ON aurix.cnab_remessas (tipo);
CREATE INDEX IF NOT EXISTS idx_cnab_remessa_status ON aurix.cnab_remessas (status);

-- ──────────────────────────────────────────────────────────────
-- 4. TABELA cnab_retornos
-- ──────────────────────────────────────────────────────────────
CREATE TABLE IF NOT EXISTS aurix.cnab_retornos (
    id                      BIGSERIAL PRIMARY KEY,
    tenant_id               VARCHAR(64),
    remessa_id              BIGINT NOT NULL REFERENCES aurix.cnab_remessas(id),
    arquivo_nome            VARCHAR(255) NOT NULL,
    conteudo_arquivo        TEXT,
    processado              BOOLEAN NOT NULL DEFAULT FALSE,
    data_processamento      TIMESTAMP,
    total_registros         INTEGER,
    total_erros             INTEGER,
    data_criacao            TIMESTAMP NOT NULL DEFAULT NOW(),
    data_atualizacao        TIMESTAMP,
    versao                  INTEGER DEFAULT 1
);

CREATE INDEX IF NOT EXISTS idx_cnab_retorno_remessa ON aurix.cnab_retornos (remessa_id);
CREATE INDEX IF NOT EXISTS idx_cnab_retorno_tenant ON aurix.cnab_retornos (tenant_id);

-- ──────────────────────────────────────────────────────────────
-- 5. TABELA extratos
-- ──────────────────────────────────────────────────────────────
CREATE TABLE IF NOT EXISTS aurix.extratos (
    id                      BIGSERIAL PRIMARY KEY,
    tenant_id               VARCHAR(64),
    conta_id                BIGINT NOT NULL,
    data_inicio             DATE NOT NULL,
    data_fim                DATE NOT NULL,
    pdf_path                VARCHAR(500),
    saldo_anterior          NUMERIC(15,2),
    saldo_final             NUMERIC(15,2),
    total_creditos          NUMERIC(15,2),
    total_debitos           NUMERIC(15,2),
    quantidade_movimentacoes INTEGER,
    data_geracao            TIMESTAMP DEFAULT NOW(),
    data_criacao            TIMESTAMP NOT NULL DEFAULT NOW(),
    data_atualizacao        TIMESTAMP,
    versao                  INTEGER DEFAULT 1
);

CREATE INDEX IF NOT EXISTS idx_extrato_conta ON aurix.extratos (conta_id);
CREATE INDEX IF NOT EXISTS idx_extrato_tenant ON aurix.extratos (tenant_id);
CREATE INDEX IF NOT EXISTS idx_extrato_periodo ON aurix.extratos (data_inicio, data_fim);
