-- ============================================================
-- Flyway: V1 — Seguros
-- Tabelas para produtos de seguro, apólices, sinistros
-- e documentos de sinistro.
-- ============================================================

-- ──────────────────────────────────────────────────────────────
-- 1. TABELA produtos_seguro
-- ──────────────────────────────────────────────────────────────
CREATE TABLE IF NOT EXISTS aurix.produtos_seguro (
    id                              BIGSERIAL PRIMARY KEY,
    tenant_id                       VARCHAR(64) NOT NULL,
    nome                            VARCHAR(100) NOT NULL,
    descricao                       VARCHAR(500),
    tipo                            VARCHAR(20) NOT NULL,
    cobertura_padrao                VARCHAR(20) NOT NULL,
    taxa_base                       NUMERIC(7,5) NOT NULL,
    premio_minimo                   NUMERIC(18,2) NOT NULL,
    carencia_meses                  NUMERIC(5,2) NOT NULL DEFAULT 0,
    prazo_analise_dias              INTEGER NOT NULL DEFAULT 15,
    prazo_pagamento_sinistro_dias   INTEGER NOT NULL DEFAULT 5,
    ativo                           BOOLEAN NOT NULL DEFAULT TRUE,
    data_criacao                    TIMESTAMP NOT NULL DEFAULT NOW(),
    data_atualizacao                TIMESTAMP,
    versao                          INTEGER DEFAULT 1
);

CREATE INDEX IF NOT EXISTS idx_produto_seg_tenant ON aurix.produtos_seguro (tenant_id);
CREATE INDEX IF NOT EXISTS idx_produto_seg_tipo ON aurix.produtos_seguro (tipo);
CREATE INDEX IF NOT EXISTS idx_produto_seg_ativo ON aurix.produtos_seguro (ativo);

-- ──────────────────────────────────────────────────────────────
-- 2. TABELA apolices
-- ──────────────────────────────────────────────────────────────
CREATE TABLE IF NOT EXISTS aurix.apolices (
    id                      BIGSERIAL PRIMARY KEY,
    tenant_id               VARCHAR(64) NOT NULL,
    cliente_id              BIGINT NOT NULL,
    produto_id              BIGINT NOT NULL REFERENCES aurix.produtos_seguro(id),
    produto_tipo            VARCHAR(20) NOT NULL,
    cobertura               VARCHAR(20) NOT NULL,
    valor_segurado          NUMERIC(18,2) NOT NULL,
    premio                  NUMERIC(18,2) NOT NULL,
    premio_mensal           NUMERIC(18,2) NOT NULL,
    data_inicio             DATE NOT NULL,
    data_fim                DATE NOT NULL,
    data_cancelamento       DATE,
    valor_restituido        NUMERIC(18,2),
    status                  VARCHAR(20) NOT NULL DEFAULT 'EMITIDA',
    renovacao_automatica    BOOLEAN NOT NULL DEFAULT TRUE,
    idade_segurado          INTEGER NOT NULL,
    uf                      VARCHAR(2),
    sexo                    VARCHAR(1),
    profissao               VARCHAR(100),
    numero_apolice          VARCHAR(50),
    data_criacao            TIMESTAMP NOT NULL DEFAULT NOW(),
    data_atualizacao        TIMESTAMP,
    versao                  INTEGER DEFAULT 1
);

CREATE INDEX IF NOT EXISTS idx_apolice_cliente ON aurix.apolices (cliente_id);
CREATE INDEX IF NOT EXISTS idx_apolice_produto ON aurix.apolices (produto_id);
CREATE INDEX IF NOT EXISTS idx_apolice_status ON aurix.apolices (status);
CREATE INDEX IF NOT EXISTS idx_apolice_tenant ON aurix.apolices (tenant_id);
CREATE INDEX IF NOT EXISTS idx_apolice_vencimento ON aurix.apolices (data_fim);
CREATE INDEX IF NOT EXISTS idx_apolice_numero ON aurix.apolices (numero_apolice);

-- ──────────────────────────────────────────────────────────────
-- 3. TABELA sinistros
-- ──────────────────────────────────────────────────────────────
CREATE TABLE IF NOT EXISTS aurix.sinistros (
    id                      BIGSERIAL PRIMARY KEY,
    tenant_id               VARCHAR(64) NOT NULL,
    apolice_id              BIGINT NOT NULL REFERENCES aurix.apolices(id),
    cliente_id              BIGINT NOT NULL,
    produto_id              BIGINT NOT NULL,
    produto_tipo            VARCHAR(20) NOT NULL,
    descricao_evento        VARCHAR(200) NOT NULL,
    data_evento             DATE NOT NULL,
    data_abertura           DATE NOT NULL,
    valor_solicitado        NUMERIC(18,2),
    valor_aprovado          NUMERIC(18,2),
    status                  VARCHAR(20) NOT NULL DEFAULT 'ABERTO',
    motivo_reprovacao       VARCHAR(500),
    data_analise            DATE,
    data_aprovacao          DATE,
    data_pagamento          DATE,
    data_criacao            TIMESTAMP NOT NULL DEFAULT NOW(),
    data_atualizacao        TIMESTAMP,
    versao                  INTEGER DEFAULT 1
);

CREATE INDEX IF NOT EXISTS idx_sinistro_apolice ON aurix.sinistros (apolice_id);
CREATE INDEX IF NOT EXISTS idx_sinistro_cliente ON aurix.sinistros (cliente_id);
CREATE INDEX IF NOT EXISTS idx_sinistro_status ON aurix.sinistros (status);
CREATE INDEX IF NOT EXISTS idx_sinistro_tenant ON aurix.sinistros (tenant_id);
CREATE INDEX IF NOT EXISTS idx_sinistro_data_evento ON aurix.sinistros (data_evento);

-- ──────────────────────────────────────────────────────────────
-- 4. TABELA sinistro_documentos
-- ──────────────────────────────────────────────────────────────
CREATE TABLE IF NOT EXISTS aurix.sinistro_documentos (
    id                      BIGSERIAL PRIMARY KEY,
    sinistro_id             BIGINT NOT NULL REFERENCES aurix.sinistros(id),
    tipo_documento          VARCHAR(100) NOT NULL,
    nome_arquivo            VARCHAR(500) NOT NULL,
    caminho_arquivo         VARCHAR(1000),
    descricao               VARCHAR(200) NOT NULL,
    data_upload             TIMESTAMP DEFAULT NOW(),
    versao                  INTEGER DEFAULT 1
);

CREATE INDEX IF NOT EXISTS idx_doc_sinistro ON aurix.sinistro_documentos (sinistro_id);
