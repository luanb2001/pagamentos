package com.project.pagamentos.domain.dto;

import com.project.pagamentos.domain.enums.StatusPagamentoEnum;

import java.time.LocalDateTime;
import java.util.UUID;

public record PagamentoRespostaDTO(
        UUID pagamentoId,
        UUID contaId,
        LocalDateTime dataProcessamento,
        StatusPagamentoEnum status,
        String mensagem
) {}