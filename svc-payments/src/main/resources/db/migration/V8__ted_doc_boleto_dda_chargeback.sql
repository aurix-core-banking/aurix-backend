-- V8__ted_doc_boleto_dda_chargeback.sql
-- Tabelas para TED, DOC, Boleto Registrado, DDA e Chargeback

-- ============================================================
-- TED / DOC
-- ============================================================
CREATE TABLE IF NOT EXISTS aurix.pagamentos_ted (
    id                  BIGSERIAL PRIMARY KEY,
    tenant_id           VARCHAR(64) NOT NULL,
    codigo_ted          VARCHAR(100) NOT NULL UNIQUE,
    conta_origem_id     BIGINT NOT NULL REFERENCES aurix.contas(id),
    tipo_pagamento      VARCHAR(10) NOT NULL DEFAULT 'TED',          -- TED ou DOC
    nome_destinatario   VARCHAR(140) NOT NULL,
    cpf_cnpj_destino    VARCHAR(14),
    banco_destino       VARCHAR(10) NOT NULL,
    agencia_destino     VARCHAR(10) NOT NULL,
    conta_destino       VARCHAR(20) NOT NULL,
    ispb_destino        VARCHAR(8),
    valor               NUMERIC(15,2) NOT NULL CHECK (valor > 0),
    descricao           VARCHAR(140),
    status              VARCHAR(20) NOT NULL DEFAULT 'PENDENTE',
    data_agendamento    TIMESTAMP,
    data_processamento  TIMESTAMP,
    codigo_retorno      VARCHAR(10),
    mensagem_retorno    VARCHAR(500),
    dados_adicionais    JSONB,
    data_criacao        TIMESTAMP NOT NULL DEFAULT NOW(),
    data_atualizacao    TIMESTAMP,
    versao              INTEGER NOT NULL DEFAULT 1
);

CREATE INDEX idx_pagamentos_ted_conta ON aurix.pagamentos_ted (conta_origem_id);
CREATE INDEX idx_pagamentos_ted_status ON aurix.pagamentos_ted (status);
CREATE INDEX idx_pagamentos_ted_agendamento ON aurix.pagamentos_ted (data_agendamento);

-- ============================================================
-- Boleto Registrado
-- ============================================================
CREATE TABLE IF NOT EXISTS aurix.pagamentos_boleto (
    id                      BIGSERIAL PRIMARY KEY,
    tenant_id               VARCHAR(64) NOT NULL,
    codigo_boleto           VARCHAR(100) NOT NULL UNIQUE,
    nosso_numero            VARCHAR(20) NOT NULL,
    carteira                VARCHAR(10) NOT NULL,
    convenio                VARCHAR(20),
    conta_cedente_id        BIGINT NOT NULL REFERENCES aurix.contas(id),
    sacado_nome             VARCHAR(140) NOT NULL,
    sacado_documento        VARCHAR(14),
    sacado_endereco         VARCHAR(255),
    sacado_cidade           VARCHAR(100),
    sacado_uf               VARCHAR(2),
    sacado_cep              VARCHAR(9),
    valor_original          NUMERIC(15,2) NOT NULL CHECK (valor_original > 0),
    valor_desconto          NUMERIC(15,2) DEFAULT 0,
    valor_juros             NUMERIC(15,2) DEFAULT 0,
    valor_multa             NUMERIC(15,2) DEFAULT 0,
    valor_pago              NUMERIC(15,2),
    data_emissao            TIMESTAMP NOT NULL DEFAULT NOW(),
    data_vencimento         TIMESTAMP NOT NULL,
    data_pagamento          TIMESTAMP,
    data_baixa              TIMESTAMP,
    data_protesto           TIMESTAMP,
    data_limite_desconto   TIMESTAMP,
    status                  VARCHAR(20) NOT NULL DEFAULT 'REGISTRADO',
    tipo_documento          VARCHAR(20) NOT NULL DEFAULT 'BOLETO',
    instrucoes              TEXT,
    codigo_barras           VARCHAR(54),
    linha_digitavel         VARCHAR(54),
    codigo_retorno          VARCHAR(10),
    mensagem_retorno        VARCHAR(500),
    dados_adicionais        JSONB,
    data_criacao            TIMESTAMP NOT NULL DEFAULT NOW(),
    data_atualizacao        TIMESTAMP,
    versao                  INTEGER NOT NULL DEFAULT 1
);

CREATE INDEX idx_pagamentos_boleto_cedente ON aurix.pagamentos_boleto (conta_cedente_id);
CREATE INDEX idx_pagamentos_boleto_status ON aurix.pagamentos_boleto (status);
CREATE INDEX idx_pagamentos_boleto_vencimento ON aurix.pagamentos_boleto (data_vencimento);
CREATE INDEX idx_pagamentos_boleto_nosso_numero ON aurix.pagamentos_boleto (nosso_numero);

-- ============================================================
-- DDA - Débito Direto Autorizado
-- ============================================================
CREATE TABLE IF NOT EXISTS aurix.dda_autorizacoes (
    id                      BIGSERIAL PRIMARY KEY,
    tenant_id               VARCHAR(64) NOT NULL,
    codigo_autorizacao      VARCHAR(100) NOT NULL UNIQUE,
    conta_debitada_id       BIGINT NOT NULL REFERENCES aurix.contas(id),
    documento_cpf_cnpj      VARCHAR(14) NOT NULL,
    nome_titular            VARCHAR(140) NOT NULL,
    cnpj_beneficiario       VARCHAR(14) NOT NULL,
    nome_beneficiario       VARCHAR(140) NOT NULL,
    codigo_convenio         VARCHAR(20) NOT NULL,
    valor_maximo_debito     NUMERIC(15,2) CHECK (valor_maximo_debito > 0),
    data_autorizacao        TIMESTAMP NOT NULL DEFAULT NOW(),
    data_revogacao          TIMESTAMP,
    status                  VARCHAR(20) NOT NULL DEFAULT 'ATIVA',
    observacoes             VARCHAR(500),
    dados_adicionais        JSONB,
    data_criacao            TIMESTAMP NOT NULL DEFAULT NOW(),
    data_atualizacao        TIMESTAMP,
    versao                  INTEGER NOT NULL DEFAULT 1
);

CREATE INDEX idx_dda_autorizacoes_conta ON aurix.dda_autorizacoes (conta_debitada_id);
CREATE INDEX idx_dda_autorizacoes_beneficiario ON aurix.dda_autorizacoes (cnpj_beneficiario);
CREATE INDEX idx_dda_autorizacoes_status ON aurix.dda_autorizacoes (status);

CREATE TABLE IF NOT EXISTS aurix.dda_debitos (
    id                      BIGSERIAL PRIMARY KEY,
    tenant_id               VARCHAR(64) NOT NULL,
    codigo_debito           VARCHAR(100) NOT NULL UNIQUE,
    autorizacao_id          BIGINT NOT NULL REFERENCES aurix.dda_autorizacoes(id),
    conta_debitada_id       BIGINT NOT NULL REFERENCES aurix.contas(id),
    cnpj_beneficiario       VARCHAR(14) NOT NULL,
    nome_beneficiario       VARCHAR(140) NOT NULL,
    valor_debito            NUMERIC(15,2) NOT NULL CHECK (valor_debito > 0),
    data_vencimento         TIMESTAMP NOT NULL,
    data_notificacao        TIMESTAMP,
    data_debito             TIMESTAMP,
    data_processamento      TIMESTAMP,
    status                  VARCHAR(20) NOT NULL DEFAULT 'AGENDADO',
    descricao               VARCHAR(255),
    codigo_retorno          VARCHAR(10),
    mensagem_retorno        VARCHAR(500),
    dados_adicionais        JSONB,
    data_criacao            TIMESTAMP NOT NULL DEFAULT NOW(),
    data_atualizacao        TIMESTAMP,
    versao                  INTEGER NOT NULL DEFAULT 1
);

CREATE INDEX idx_dda_debitos_conta ON aurix.dda_debitos (conta_debitada_id);
CREATE INDEX idx_dda_debitos_autorizacao ON aurix.dda_debitos (autorizacao_id);
CREATE INDEX idx_dda_debitos_status ON aurix.dda_debitos (status);
CREATE INDEX idx_dda_debitos_vencimento ON aurix.dda_debitos (data_vencimento);

-- ============================================================
-- Chargeback
-- ============================================================
CREATE TABLE IF NOT EXISTS aurix.chargebacks (
    id                      BIGSERIAL PRIMARY KEY,
    tenant_id               VARCHAR(64) NOT NULL,
    codigo_chargeback       VARCHAR(100) NOT NULL UNIQUE,
    conta_id                BIGINT NOT NULL REFERENCES aurix.contas(id),
    transacao_origem_id     BIGINT,
    tipo_origem             VARCHAR(30) NOT NULL,       -- PIX, TED, BOLETO, CARTAO
    documento_origem        VARCHAR(100),
    valor_original          NUMERIC(15,2) NOT NULL CHECK (valor_original > 0),
    valor_chargeback        NUMERIC(15,2) NOT NULL CHECK (valor_chargeback > 0),
    motivo                  VARCHAR(30) NOT NULL,       -- FRAUDE, NAO_RECONHECIDO, PRODUTO_NAO_RECEBIDO, ERRO_VALOR, DUPLICIDADE
    descricao_motivo        VARCHAR(500),
    status                  VARCHAR(20) NOT NULL DEFAULT 'ABERTO',
    data_transacao_origem   TIMESTAMP,
    data_solicitacao        TIMESTAMP NOT NULL DEFAULT NOW(),
    data_analise            TIMESTAMP,
    data_contestacao        TIMESTAMP,
    data_resolucao          TIMESTAMP,
    resultado               VARCHAR(30),                -- DEFERIDO, INDEFERIDO, PARCIAL
    justificativa_resolucao VARCHAR(500),
    prazo_limite            TIMESTAMP NOT NULL,
    dados_adicionais        JSONB,
    data_criacao            TIMESTAMP NOT NULL DEFAULT NOW(),
    data_atualizacao        TIMESTAMP,
    versao                  INTEGER NOT NULL DEFAULT 1
);

CREATE INDEX idx_chargebacks_conta ON aurix.chargebacks (conta_id);
CREATE INDEX idx_chargebacks_status ON aurix.chargebacks (status);
CREATE INDEX idx_chargebacks_solicitacao ON aurix.chargebacks (data_solicitacao);
CREATE INDEX idx_chargebacks_transacao ON aurix.chargebacks (transacao_origem_id);

CREATE TABLE IF NOT EXISTS aurix.chargeback_evidencias (
    id                  BIGSERIAL PRIMARY KEY,
    tenant_id           VARCHAR(64) NOT NULL,
    chargeback_id       BIGINT NOT NULL REFERENCES aurix.chargebacks(id),
    tipo_evidencia      VARCHAR(30) NOT NULL,       -- COMPROVANTE, CORRESPONDENCIA, PRINT_TELA, OUTROS
    descricao           VARCHAR(255),
    url_documento       VARCHAR(500),
    conteudo_texto      TEXT,
    data_upload         TIMESTAMP NOT NULL DEFAULT NOW(),
    data_criacao        TIMESTAMP NOT NULL DEFAULT NOW(),
    data_atualizacao    TIMESTAMP,
    versao              INTEGER NOT NULL DEFAULT 1
);

CREATE INDEX idx_chargeback_evidencias_chargeback ON aurix.chargeback_evidencias (chargeback_id);
