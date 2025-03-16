package com.project.pagamentos.domain.dto;

import java.util.UUID;

public record ProcessarPagamentoDTO(UUID contaId, Double valor) {
}
