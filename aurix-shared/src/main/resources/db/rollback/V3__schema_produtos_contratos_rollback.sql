-- ============================================================
-- Aurix Core Banking - Rollback da migração V2
-- Remove as tabelas de produtos e contratos
-- ============================================================

-- Chaves estrangeiras
ALTER TABLE aurix.contratos_assinaturas DROP CONSTRAINT IF EXISTS FK_contratos_assinaturas_contrato;
ALTER TABLE aurix.contratos_versoes DROP CONSTRAINT IF EXISTS FK_contratos_versoes_contrato;
ALTER TABLE aurix.tarifas_produto DROP CONSTRAINT IF EXISTS FK_tarifas_produto_produto;
ALTER TABLE aurix.regras_elegibilidade DROP CONSTRAINT IF EXISTS FK_regras_elegibilidade_produto;
ALTER TABLE aurix.versoes_produto DROP CONSTRAINT IF EXISTS FK_versoes_produto_produto;

-- Tabelas de contratos
DROP TABLE IF EXISTS aurix.templates_contrato;
DROP TABLE IF EXISTS aurix.contratos_versoes;
DROP TABLE IF EXISTS aurix.contratos_assinaturas;
DROP TABLE IF EXISTS aurix.contratos;

-- Tabelas de produtos
DROP TABLE IF EXISTS aurix.versoes_produto;
DROP TABLE IF EXISTS aurix.tarifas_produto;
DROP TABLE IF EXISTS aurix.regras_elegibilidade;
DROP TABLE IF EXISTS aurix.produtos;
