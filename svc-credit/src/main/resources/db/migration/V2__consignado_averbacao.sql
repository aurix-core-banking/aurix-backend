-- Averbação de contratos consignados junto ao órgão pagador (INSS/SIAPE/eSocial/SRCC)
ALTER TABLE contratos_consignados ADD COLUMN IF NOT EXISTS protocolo_averbacao VARCHAR(100);

CREATE INDEX IF NOT EXISTS idx_contratos_consignados_protocolo_averbacao
    ON contratos_consignados (protocolo_averbacao);