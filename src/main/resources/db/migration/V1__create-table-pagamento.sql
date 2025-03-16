CREATE TABLE IF NOT EXISTS pagamento (
    id UUID NOT NULL,
    conta_id UUID NOT NULL,  -- Corrigido aqui
    data_criacao TIMESTAMP,
    data_processamento TIMESTAMP,
    valor DECIMAL(10,2) NOT NULL,
    status VARCHAR(20) NOT NULL,
    motivo_recusa TEXT NULL,

    CONSTRAINT pk_pagamento PRIMARY KEY (id)
);

