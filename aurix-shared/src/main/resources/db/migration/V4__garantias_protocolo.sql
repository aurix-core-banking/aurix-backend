-- ============================================================
-- Aurix Core Banking - Evolução do esquema (V4)
-- Adiciona coluna de protocolo de registro externo na tabela
-- garantias (financiamento) e índice de custódia de investimentos.
-- ============================================================
CREATE SCHEMA IF NOT EXISTS aurix;

ALTER TABLE aurix.garantias
    ADD COLUMN IF NOT EXISTS protocoloRegistro varchar(100);
