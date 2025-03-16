package com.project.pagamentos.domain.dto;

import com.project.pagamentos.domain.enums.StatusPagamentoEnum;

import java.time.LocalDateTime;
import java.util.UUID;

public record PagamentoDTO (UUID id, UUID contaId, LocalDateTime dataCriacao, LocalDateTime dataProcessamento, Double valor, StatusPagamentoEnum status, String motivoRecusa) {

}
