package com.project.pagamentos.application.usecase;

import com.project.pagamentos.domain.entity.Pagamento;

import java.util.UUID;

public interface BuscarPagamentoPorIdUseCase {

    Pagamento executar(UUID pagamentoId);
}
