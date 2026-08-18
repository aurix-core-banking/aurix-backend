-- V2__api_keys.sql — Tabela de API keys com hash SHA-256
-- Armazenamento seguro: key NUNCA em plaintext no DB

CREATE TABLE IF NOT EXISTS api_keys (
    id BIGSERIAL PRIMARY KEY,
    prefixo VARCHAR(16) NOT NULL UNIQUE,
    key_hash VARCHAR(64) NOT NULL UNIQUE,
    nome VARCHAR(100) NOT NULL,
    tenant_id VARCHAR(50) NOT NULL,
    plano VARCHAR(30) NOT NULL DEFAULT 'free',
    ativo BOOLEAN NOT NULL DEFAULT TRUE,
    data_criacao TIMESTAMP NOT NULL DEFAULT NOW(),
    data_expiracao TIMESTAMP,
    data_ultimo_uso TIMESTAMP,
    data_rotacao TIMESTAMP,
    uso_total INTEGER DEFAULT 0,
    rate_limit_rpm INTEGER DEFAULT 10
);

CREATE INDEX idx_api_keys_prefixo ON api_keys(prefixo);
CREATE INDEX idx_api_keys_hash ON api_keys(key_hash);
CREATE INDEX idx_api_keys_tenant ON api_keys(tenant_id);
CREATE INDEX idx_api_keys_ativo ON api_keys(ativo);
