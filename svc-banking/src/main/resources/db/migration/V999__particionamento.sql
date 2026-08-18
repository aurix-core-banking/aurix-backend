-- ============================================================
-- Flyway: Particionamento das tabelas mais pesadas
-- Particionamento por RANGE em data_transacao/data_criacao
-- Cria particoes mensais para 24 meses
-- ============================================================

-- ──────────────────────────────────────────────────────────────
-- 1. TABELA transacoes — RANGE(data_transacao)
-- ──────────────────────────────────────────────────────────────
CREATE TABLE IF NOT EXISTS aurix.transacoes_partitioned (
    id                  BIGSERIAL,
    conta_id            BIGINT NOT NULL,
    tipo_transacao      VARCHAR(30) NOT NULL,
    valor               NUMERIC(15,2) NOT NULL,
    moeda               VARCHAR(3) DEFAULT 'BRL',
    descricao           VARCHAR(500),
    status_transacao    VARCHAR(20) NOT NULL,
    data_transacao      TIMESTAMP NOT NULL,
    data_atualizacao    TIMESTAMP DEFAULT NOW(),
    PRIMARY KEY (id, data_transacao)
) PARTITION BY RANGE (data_transacao);

-- Criar particoes mensais (24 meses)
CREATE TABLE aurix.transacoes_2025_01 PARTITION OF aurix.transacoes_partitioned
    FOR VALUES FROM ('2025-01-01') TO ('2025-02-01');
CREATE TABLE aurix.transacoes_2025_02 PARTITION OF aurix.transacoes_partitioned
    FOR VALUES FROM ('2025-02-01') TO ('2025-03-01');
CREATE TABLE aurix.transacoes_2025_03 PARTITION OF aurix.transacoes_partitioned
    FOR VALUES FROM ('2025-03-01') TO ('2025-04-01');
CREATE TABLE aurix.transacoes_2025_04 PARTITION OF aurix.transacoes_partitioned
    FOR VALUES FROM ('2025-04-01') TO ('2025-05-01');
CREATE TABLE aurix.transacoes_2025_05 PARTITION OF aurix.transacoes_partitioned
    FOR VALUES FROM ('2025-05-01') TO ('2025-06-01');
CREATE TABLE aurix.transacoes_2025_06 PARTITION OF aurix.transacoes_partitioned
    FOR VALUES FROM ('2025-06-01') TO ('2025-07-01');
CREATE TABLE aurix.transacoes_2025_07 PARTITION OF aurix.transacoes_partitioned
    FOR VALUES FROM ('2025-07-01') TO ('2025-08-01');
CREATE TABLE aurix.transacoes_2025_08 PARTITION OF aurix.transacoes_partitioned
    FOR VALUES FROM ('2025-08-01') TO ('2025-09-01');
CREATE TABLE aurix.transacoes_2025_09 PARTITION OF aurix.transacoes_partitioned
    FOR VALUES FROM ('2025-09-01') TO ('2025-10-01');
CREATE TABLE aurix.transacoes_2025_10 PARTITION OF aurix.transacoes_partitioned
    FOR VALUES FROM ('2025-10-01') TO ('2025-11-01');
CREATE TABLE aurix.transacoes_2025_11 PARTITION OF aurix.transacoes_partitioned
    FOR VALUES FROM ('2025-11-01') TO ('2025-12-01');
CREATE TABLE aurix.transacoes_2025_12 PARTITION OF aurix.transacoes_partitioned
    FOR VALUES FROM ('2025-12-01') TO ('2026-01-01');
CREATE TABLE aurix.transacoes_2026_01 PARTITION OF aurix.transacoes_partitioned
    FOR VALUES FROM ('2026-01-01') TO ('2026-02-01');
CREATE TABLE aurix.transacoes_2026_02 PARTITION OF aurix.transacoes_partitioned
    FOR VALUES FROM ('2026-02-01') TO ('2026-03-01');
CREATE TABLE aurix.transacoes_2026_03 PARTITION OF aurix.transacoes_partitioned
    FOR VALUES FROM ('2026-03-01') TO ('2026-04-01');
CREATE TABLE aurix.transacoes_2026_04 PARTITION OF aurix.transacoes_partitioned
    FOR VALUES FROM ('2026-04-01') TO ('2026-05-01');
CREATE TABLE aurix.transacoes_2026_05 PARTITION OF aurix.transacoes_partitioned
    FOR VALUES FROM ('2026-05-01') TO ('2026-06-01');
CREATE TABLE aurix.transacoes_2026_06 PARTITION OF aurix.transacoes_partitioned
    FOR VALUES FROM ('2026-06-01') TO ('2026-07-01');
CREATE TABLE aurix.transacoes_2026_07 PARTITION OF aurix.transacoes_partitioned
    FOR VALUES FROM ('2026-07-01') TO ('2026-08-01');
CREATE TABLE aurix.transacoes_2026_08 PARTITION OF aurix.transacoes_partitioned
    FOR VALUES FROM ('2026-08-01') TO ('2026-09-01');
CREATE TABLE aurix.transacoes_2026_09 PARTITION OF aurix.transacoes_partitioned
    FOR VALUES FROM ('2026-09-01') TO ('2026-10-01');
CREATE TABLE aurix.transacoes_2026_10 PARTITION OF aurix.transacoes_partitioned
    FOR VALUES FROM ('2026-10-01') TO ('2026-11-01');
CREATE TABLE aurix.transacoes_2026_11 PARTITION OF aurix.transacoes_partitioned
    FOR VALUES FROM ('2026-11-01') TO ('2026-12-01');
CREATE TABLE aurix.transacoes_2026_12 PARTITION OF aurix.transacoes_partitioned
    FOR VALUES FROM ('2026-12-01') TO ('2027-01-01');

CREATE INDEX idx_transacoes_part_conta ON aurix.transacoes_partitioned (conta_id);
CREATE INDEX idx_transacoes_part_tipo ON aurix.transacoes_partitioned (tipo_transacao);
CREATE INDEX idx_transacoes_part_status ON aurix.transacoes_partitioned (status_transacao);

-- ──────────────────────────────────────────────────────────────
-- 2. TABELA logs_auditoria — RANGE(data_criacao)
-- ──────────────────────────────────────────────────────────────
CREATE TABLE IF NOT EXISTS aurix.logs_auditoria_partitioned (
    id                  BIGSERIAL,
    acao                VARCHAR(30) NOT NULL,
    entidade            VARCHAR(100) NOT NULL,
    entidade_id         VARCHAR(100),
    usuario_id          BIGINT,
    ip_origem           VARCHAR(45),
    user_agent          VARCHAR(500),
    dados_anteriores    JSONB,
    dados_novos         JSONB,
    resultado           VARCHAR(20),
    categoria           VARCHAR(30),
    nivel               VARCHAR(20) DEFAULT 'INFO',
    data_criacao        TIMESTAMP NOT NULL DEFAULT NOW(),
    PRIMARY KEY (id, data_criacao)
) PARTITION BY RANGE (data_criacao);

CREATE TABLE aurix.logs_auditoria_2025_01 PARTITION OF aurix.logs_auditoria_partitioned
    FOR VALUES FROM ('2025-01-01') TO ('2025-02-01');
CREATE TABLE aurix.logs_auditoria_2025_02 PARTITION OF aurix.logs_auditoria_partitioned
    FOR VALUES FROM ('2025-02-01') TO ('2025-03-01');
CREATE TABLE aurix.logs_auditoria_2025_03 PARTITION OF aurix.logs_auditoria_partitioned
    FOR VALUES FROM ('2025-03-01') TO ('2025-04-01');
CREATE TABLE aurix.logs_auditoria_2025_04 PARTITION OF aurix.logs_auditoria_partitioned
    FOR VALUES FROM ('2025-04-01') TO ('2025-05-01');
CREATE TABLE aurix.logs_auditoria_2025_05 PARTITION OF aurix.logs_auditoria_partitioned
    FOR VALUES FROM ('2025-05-01') TO ('2025-06-01');
CREATE TABLE aurix.logs_auditoria_2025_06 PARTITION OF aurix.logs_auditoria_partitioned
    FOR VALUES FROM ('2025-06-01') TO ('2025-07-01');
CREATE TABLE aurix.logs_auditoria_2025_07 PARTITION OF aurix.logs_auditoria_partitioned
    FOR VALUES FROM ('2025-07-01') TO ('2025-08-01');
CREATE TABLE aurix.logs_auditoria_2025_08 PARTITION OF aurix.logs_auditoria_partitioned
    FOR VALUES FROM ('2025-08-01') TO ('2025-09-01');
CREATE TABLE aurix.logs_auditoria_2025_09 PARTITION OF aurix.logs_auditoria_partitioned
    FOR VALUES FROM ('2025-09-01') TO ('2025-10-01');
CREATE TABLE aurix.logs_auditoria_2025_10 PARTITION OF aurix.logs_auditoria_partitioned
    FOR VALUES FROM ('2025-10-01') TO ('2025-11-01');
CREATE TABLE aurix.logs_auditoria_2025_11 PARTITION OF aurix.logs_auditoria_partitioned
    FOR VALUES FROM ('2025-11-01') TO ('2025-12-01');
CREATE TABLE aurix.logs_auditoria_2025_12 PARTITION OF aurix.logs_auditoria_partitioned
    FOR VALUES FROM ('2025-12-01') TO ('2026-01-01');
CREATE TABLE aurix.logs_auditoria_2026_01 PARTITION OF aurix.logs_auditoria_partitioned
    FOR VALUES FROM ('2026-01-01') TO ('2026-02-01');
CREATE TABLE aurix.logs_auditoria_2026_02 PARTITION OF aurix.logs_auditoria_partitioned
    FOR VALUES FROM ('2026-02-01') TO ('2026-03-01');
CREATE TABLE aurix.logs_auditoria_2026_03 PARTITION OF aurix.logs_auditoria_partitioned
    FOR VALUES FROM ('2026-03-01') TO ('2026-04-01');
CREATE TABLE aurix.logs_auditoria_2026_04 PARTITION OF aurix.logs_auditoria_partitioned
    FOR VALUES FROM ('2026-04-01') TO ('2026-05-01');
CREATE TABLE aurix.logs_auditoria_2026_05 PARTITION OF aurix.logs_auditoria_partitioned
    FOR VALUES FROM ('2026-05-01') TO ('2026-06-01');
CREATE TABLE aurix.logs_auditoria_2026_06 PARTITION OF aurix.logs_auditoria_partitioned
    FOR VALUES FROM ('2026-06-01') TO ('2026-07-01');
CREATE TABLE aurix.logs_auditoria_2026_07 PARTITION OF aurix.logs_auditoria_partitioned
    FOR VALUES FROM ('2026-07-01') TO ('2026-08-01');
CREATE TABLE aurix.logs_auditoria_2026_08 PARTITION OF aurix.logs_auditoria_partitioned
    FOR VALUES FROM ('2026-08-01') TO ('2026-09-01');
CREATE TABLE aurix.logs_auditoria_2026_09 PARTITION OF aurix.logs_auditoria_partitioned
    FOR VALUES FROM ('2026-09-01') TO ('2026-10-01');
CREATE TABLE aurix.logs_auditoria_2026_10 PARTITION OF aurix.logs_auditoria_partitioned
    FOR VALUES FROM ('2026-10-01') TO ('2026-11-01');
CREATE TABLE aurix.logs_auditoria_2026_11 PARTITION OF aurix.logs_auditoria_partitioned
    FOR VALUES FROM ('2026-11-01') TO ('2026-12-01');
CREATE TABLE aurix.logs_auditoria_2026_12 PARTITION OF aurix.logs_auditoria_partitioned
    FOR VALUES FROM ('2026-12-01') TO ('2027-01-01');

CREATE INDEX idx_logs_aud_entidade ON aurix.logs_auditoria_partitioned (entidade);
CREATE INDEX idx_logs_aud_usuario ON aurix.logs_auditoria_partitioned (usuario_id);
CREATE INDEX idx_logs_aud_nivel ON aurix.logs_auditoria_partitioned (nivel);

-- ──────────────────────────────────────────────────────────────
-- 3. TABELA movimentos_conta — RANGE(data_movimento)
-- ──────────────────────────────────────────────────────────────
CREATE TABLE IF NOT EXISTS aurix.movimentos_conta_partitioned (
    id                  BIGSERIAL,
    conta_id            BIGINT NOT NULL,
    tipo_movimento      VARCHAR(30) NOT NULL,
    valor               NUMERIC(15,2) NOT NULL,
    saldo_anterior      NUMERIC(15,2),
    saldo_posterior     NUMERIC(15,2),
    descricao           VARCHAR(500),
    data_movimento      TIMESTAMP NOT NULL,
    PRIMARY KEY (id, data_movimento)
) PARTITION BY RANGE (data_movimento);

CREATE TABLE aurix.movimentos_conta_2025_01 PARTITION OF aurix.movimentos_conta_partitioned
    FOR VALUES FROM ('2025-01-01') TO ('2025-02-01');
CREATE TABLE aurix.movimentos_conta_2025_02 PARTITION OF aurix.movimentos_conta_partitioned
    FOR VALUES FROM ('2025-02-01') TO ('2025-03-01');
CREATE TABLE aurix.movimentos_conta_2025_03 PARTITION OF aurix.movimentos_conta_partitioned
    FOR VALUES FROM ('2025-03-01') TO ('2025-04-01');
CREATE TABLE aurix.movimentos_conta_2025_04 PARTITION OF aurix.movimentos_conta_partitioned
    FOR VALUES FROM ('2025-04-01') TO ('2025-05-01');
CREATE TABLE aurix.movimentos_conta_2025_05 PARTITION OF aurix.movimentos_conta_partitioned
    FOR VALUES FROM ('2025-05-01') TO ('2025-06-01');
CREATE TABLE aurix.movimentos_conta_2025_06 PARTITION OF aurix.movimentos_conta_partitioned
    FOR VALUES FROM ('2025-06-01') TO ('2025-07-01');
CREATE TABLE aurix.movimentos_conta_2025_07 PARTITION OF aurix.movimentos_conta_partitioned
    FOR VALUES FROM ('2025-07-01') TO ('2025-08-01');
CREATE TABLE aurix.movimentos_conta_2025_08 PARTITION OF aurix.movimentos_conta_partitioned
    FOR VALUES FROM ('2025-08-01') TO ('2025-09-01');
CREATE TABLE aurix.movimentos_conta_2025_09 PARTITION OF aurix.movimentos_conta_partitioned
    FOR VALUES FROM ('2025-09-01') TO ('2025-10-01');
CREATE TABLE aurix.movimentos_conta_2025_10 PARTITION OF aurix.movimentos_conta_partitioned
    FOR VALUES FROM ('2025-10-01') TO ('2025-11-01');
CREATE TABLE aurix.movimentos_conta_2025_11 PARTITION OF aurix.movimentos_conta_partitioned
    FOR VALUES FROM ('2025-11-01') TO ('2025-12-01');
CREATE TABLE aurix.movimentos_conta_2025_12 PARTITION OF aurix.movimentos_conta_partitioned
    FOR VALUES FROM ('2025-12-01') TO ('2026-01-01');
CREATE TABLE aurix.movimentos_conta_2026_01 PARTITION OF aurix.movimentos_conta_partitioned
    FOR VALUES FROM ('2026-01-01') TO ('2026-02-01');
CREATE TABLE aurix.movimentos_conta_2026_02 PARTITION OF aurix.movimentos_conta_partitioned
    FOR VALUES FROM ('2026-02-01') TO ('2026-03-01');
CREATE TABLE aurix.movimentos_conta_2026_03 PARTITION OF aurix.movimentos_conta_partitioned
    FOR VALUES FROM ('2026-03-01') TO ('2026-04-01');
CREATE TABLE aurix.movimentos_conta_2026_04 PARTITION OF aurix.movimentos_conta_partitioned
    FOR VALUES FROM ('2026-04-01') TO ('2026-05-01');
CREATE TABLE aurix.movimentos_conta_2026_05 PARTITION OF aurix.movimentos_conta_partitioned
    FOR VALUES FROM ('2026-05-01') TO ('2026-06-01');
CREATE TABLE aurix.movimentos_conta_2026_06 PARTITION OF aurix.movimentos_conta_partitioned
    FOR VALUES FROM ('2026-06-01') TO ('2026-07-01');
CREATE TABLE aurix.movimentos_conta_2026_07 PARTITION OF aurix.movimentos_conta_partitioned
    FOR VALUES FROM ('2026-07-01') TO ('2026-08-01');
CREATE TABLE aurix.movimentos_conta_2026_08 PARTITION OF aurix.movimentos_conta_partitioned
    FOR VALUES FROM ('2026-08-01') TO ('2026-09-01');
CREATE TABLE aurix.movimentos_conta_2026_09 PARTITION OF aurix.movimentos_conta_partitioned
    FOR VALUES FROM ('2026-09-01') TO ('2026-10-01');
CREATE TABLE aurix.movimentos_conta_2026_10 PARTITION OF aurix.movimentos_conta_partitioned
    FOR VALUES FROM ('2026-10-01') TO ('2026-11-01');
CREATE TABLE aurix.movimentos_conta_2026_11 PARTITION OF aurix.movimentos_conta_partitioned
    FOR VALUES FROM ('2026-11-01') TO ('2026-12-01');
CREATE TABLE aurix.movimentos_conta_2026_12 PARTITION OF aurix.movimentos_conta_partitioned
    FOR VALUES FROM ('2026-12-01') TO ('2027-01-01');

CREATE INDEX idx_movimentos_conta_id ON aurix.movimentos_conta_partitioned (conta_id);
CREATE INDEX idx_movimentos_tipo ON aurix.movimentos_conta_partitioned (tipo_movimento);

-- ──────────────────────────────────────────────────────────────
-- 4. Funcao para criacao automatica de particoes futuras
-- ──────────────────────────────────────────────────────────────
CREATE OR REPLACE FUNCTION aurix.criar_particoes_futuras()
RETURNS void AS $$
DECLARE
    mes_atual DATE := date_trunc('month', CURRENT_DATE);
    mes_futuro DATE;
    nome_tabela TEXT;
    nome_particao TEXT;
    data_inicio TEXT;
    data_fim TEXT;
BEGIN
    FOR i IN 1..6 LOOP
        mes_futuro := mes_atual + (i || ' months')::interval;
        data_inicio := to_char(mes_futuro, 'YYYY-MM-DD');
        data_fim := to_char(mes_futuro + interval '1 month', 'YYYY-MM-DD');
        nome_particao := to_char(mes_futuro, 'YYYY_MM');

        -- Transacoes
        nome_tabela := 'aurix.transacoes_' || nome_particao;
        IF NOT EXISTS (SELECT 1 FROM pg_class WHERE relname = nome_tabela) THEN
            EXECUTE format('CREATE TABLE %s PARTITION OF aurix.transacoes_partitioned FOR VALUES FROM (%L) TO (%L)',
                nome_tabela, data_inicio, data_fim);
            RAISE NOTICE 'Criada particao: %', nome_tabela;
        END IF;

        -- Logs auditoria
        nome_tabela := 'aurix.logs_auditoria_' || nome_particao;
        IF NOT EXISTS (SELECT 1 FROM pg_class WHERE relname = nome_tabela) THEN
            EXECUTE format('CREATE TABLE %s PARTITION OF aurix.logs_auditoria_partitioned FOR VALUES FROM (%L) TO (%L)',
                nome_tabela, data_inicio, data_fim);
            RAISE NOTICE 'Criada particao: %', nome_tabela;
        END IF;

        -- Movimentos
        nome_tabela := 'aurix.movimentos_conta_' || nome_particao;
        IF NOT EXISTS (SELECT 1 FROM pg_class WHERE relname = nome_tabela) THEN
            EXECUTE format('CREATE TABLE %s PARTITION OF aurix.movimentos_conta_partitioned FOR VALUES FROM (%L) TO (%L)',
                nome_tabela, data_inicio, data_fim);
            RAISE NOTICE 'Criada particao: %', nome_tabela;
        END IF;
    END LOOP;
END;
$$ LANGUAGE plpgsql;

-- ──────────────────────────────────────────────────────────────
-- 5. Retencao — drop particoes antigas (>24 meses)
-- ──────────────────────────────────────────────────────────────
CREATE OR REPLACE FUNCTION aurix.remover_particoes_antigas()
RETURNS void AS $$
DECLARE
    limite DATE := date_trunc('month', CURRENT_DATE - interval '24 months');
    nome_particao TEXT;
    nome_tabela TEXT;
    r RECORD;
BEGIN
    FOR r IN SELECT schemaname, tablename FROM pg_tables
             WHERE schemaname = 'aurix'
             AND (tablename LIKE 'transacoes_%' OR tablename LIKE 'logs_auditoria_%' OR tablename LIKE 'movimentos_conta_%')
             AND tablename ~ '\d{4}_\d{2}$'
    LOOP
        -- Extrair data do nome da tabela
        nome_particao := r.tablename;
        BEGIN
            -- Verificar se a particao e antiga
            IF (regexp_replace(nome_particao, '.*_(\d{4})_(\d{2})$', '\1-\2-01'))::date < limite THEN
                EXECUTE format('DROP TABLE IF EXISTS %I.%I', r.schemaname, nome_particao);
                RAISE NOTICE 'Particao removida: %.%', r.schemaname, nome_particao;
            END IF;
        EXCEPTION WHEN OTHERS THEN
            RAISE NOTICE 'Erro ao processar particao %: %', nome_particao, SQLERRM;
        END;
    END LOOP;
END;
$$ LANGUAGE plpgsql;
