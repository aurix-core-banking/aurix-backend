-- ============================================================
-- Flyway: V2 — Seguros
-- Integração com seguradoras e SUSEP. Adiciona campos de registro
-- regulatório à tabela apolices.
-- ============================================================

ALTER TABLE aurix.apolices
    ADD COLUMN IF NOT EXISTS seguradora VARCHAR(100),
    ADD COLUMN IF NOT EXISTS protocolo_seguradora VARCHAR(100),
    ADD COLUMN IF NOT EXISTS numero_registro_susep BIGINT;

CREATE INDEX IF NOT EXISTS idx_apolice_numero_reg_susep ON aurix.apolices (numero_registro_susep);
