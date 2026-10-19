-- ============================================================
-- Flyway: V2 — Investimentos
-- Integração com índices de mercado (SELIC/CDI/IMAB) via API
-- de Séries Temporais do BACEN (SGS). Adiciona colunas de
-- indexação em produtos_investimento e a tabela indices_mercado.
-- ============================================================

-- ──────────────────────────────────────────────────────────────
-- 1. Índices de mercado (SELIC/CDI/IMAB)
-- ──────────────────────────────────────────────────────────────
CREATE TABLE IF NOT EXISTS aurix.indices_mercado (
    id                      BIGSERIAL PRIMARY KEY,
    tipo_indice             VARCHAR(10) NOT NULL,
    valor                   NUMERIC(12,6) NOT NULL,
    data_referencia         DATE NOT NULL,
    codigo_serie_bacen      VARCHAR(10),
    fonte_dados             VARCHAR(100),
    data_atualizacao_bacen  TIMESTAMP,
    data_criacao            TIMESTAMP NOT NULL DEFAULT NOW(),
    data_atualizacao        TIMESTAMP,
    versao                  INTEGER DEFAULT 1
);

CREATE INDEX IF NOT EXISTS idx_indice_tipo_data ON aurix.indices_mercado (tipo_indice, data_referencia DESC);

-- ──────────────────────────────────────────────────────────────
-- 2. Indexação de produtos de investimento
-- ──────────────────────────────────────────────────────────────
ALTER TABLE aurix.produtos_investimento
    ADD COLUMN IF NOT EXISTS indexador VARCHAR(10),
    ADD COLUMN IF NOT EXISTS percentual_indexador NUMERIC(7,5);

CREATE INDEX IF NOT EXISTS idx_produto_inv_indexador ON aurix.produtos_investimento (indexador);
