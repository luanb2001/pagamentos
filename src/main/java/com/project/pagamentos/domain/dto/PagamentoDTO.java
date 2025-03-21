package com.project.pagamentos.domain.dto;

import com.project.pagamentos.domain.entity.Pagamento;
import com.project.pagamentos.domain.enums.StatusPagamentoEnum;

import java.time.LocalDateTime;
import java.util.UUID;

public record PagamentoDTO (
        UUID id,
        UUID contaId,
        LocalDateTime dataCriacao,
        LocalDateTime dataProcessamento,
        Double valor,
        StatusPagamentoEnum status,
        String motivoRecusa
) {

    public static PagamentoDTO fromEntity(Pagamento pagamento) {
        return new PagamentoDTO(
                pagamento.getId(),
                pagamento.getContaId(),
                pagamento.getDataCriacao(),
                pagamento.getDataProcessamento(),
                pagamento.getValor(),
                pagamento.getStatus(),
                pagamento.getMotivoRecusa()
        );
    }
}
