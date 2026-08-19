-- ============================================================
-- Flyway: V1 — svc-salary
-- Tabelas de contas salario, convenio empresa e portabilidade.
-- ============================================================

CREATE TABLE IF NOT EXISTS aurix.contas_salario (
    id                      BIGSERIAL PRIMARY KEY,
    tenant_id               VARCHAR(64) NOT NULL,
    conta_corrente_id       BIGINT NOT NULL,
    empresa_id              BIGINT NOT NULL,
    matricula_funcionario   VARCHAR(30),
    cpf_funcionario         VARCHAR(14),
    data_admissao           DATE,
    data_rescisao           DATE,
    valor_salario_bruto     NUMERIC(15,2),
    valor_salario_liquido   NUMERIC(15,2),
    dia_pagamento           INTEGER,
    portabilidade_ativa     BOOLEAN DEFAULT FALSE,
    status                  VARCHAR(20) NOT NULL DEFAULT 'ATIVA',
    data_criacao            TIMESTAMP NOT NULL DEFAULT NOW(),
    data_atualizacao        TIMESTAMP,
    versao                  INTEGER DEFAULT 1
);

CREATE INDEX IF NOT EXISTS idx_salario_tenant ON aurix.contas_salario (tenant_id);
CREATE INDEX IF NOT EXISTS idx_salario_empresa ON aurix.contas_salario (tenant_id, empresa_id);
CREATE INDEX IF NOT EXISTS idx_salario_cpf ON aurix.contas_salario (cpf_funcionario);
CREATE INDEX IF NOT EXISTS idx_salario_status ON aurix.contas_salario (tenant_id, status);

CREATE TABLE IF NOT EXISTS aurix.convenios_empresa (
    id                      BIGSERIAL PRIMARY KEY,
    tenant_id               VARCHAR(64) NOT NULL,
    empresa_id              BIGINT NOT NULL,
    razao_social            VARCHAR(200) NOT NULL,
    cnpj                    VARCHAR(18) NOT NULL,
    convenio_banco          VARCHAR(10),
    convenio_numero         VARCHAR(20),
    status                  VARCHAR(20) NOT NULL DEFAULT 'ATIVO',
    data_criacao            TIMESTAMP NOT NULL DEFAULT NOW(),
    data_atualizacao        TIMESTAMP,
    versao                  INTEGER DEFAULT 1
);

CREATE INDEX IF NOT EXISTS idx_convenio_tenant ON aurix.convenios_empresa (tenant_id);
CREATE INDEX IF NOT EXISTS idx_convenio_empresa ON aurix.convenios_empresa (tenant_id, empresa_id);
CREATE INDEX IF NOT EXISTS idx_convenio_cnpj ON aurix.convenios_empresa (cnpj);

CREATE TABLE IF NOT EXISTS aurix.solicitacoes_portabilidade (
    id                      BIGSERIAL PRIMARY KEY,
    tenant_id               VARCHAR(64) NOT NULL,
    conta_salario_id        BIGINT NOT NULL,
    conta_destino_id        BIGINT NOT NULL,
    banco_destino           VARCHAR(10),
    agencia_destino         VARCHAR(10),
    conta_destino_numero    VARCHAR(20),
    valor_portabilidade     NUMERIC(15,2) NOT NULL,
    status                  VARCHAR(20) NOT NULL DEFAULT 'PENDENTE',
    data_solicitacao        TIMESTAMP NOT NULL DEFAULT NOW(),
    data_aprovacao          TIMESTAMP,
    data_criacao            TIMESTAMP NOT NULL DEFAULT NOW(),
    data_atualizacao        TIMESTAMP,
    versao                  INTEGER DEFAULT 1
);

CREATE INDEX IF NOT EXISTS idx_portabilidade_tenant ON aurix.solicitacoes_portabilidade (tenant_id);
CREATE INDEX IF NOT EXISTS idx_portabilidade_conta ON aurix.solicitacoes_portabilidade (conta_salario_id);
CREATE INDEX IF NOT EXISTS idx_portabilidade_status ON aurix.solicitacoes_portabilidade (tenant_id, status);
