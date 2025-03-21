package com.project.pagamentos.application.usecase;

import com.project.pagamentos.domain.entity.Pagamento;

import java.util.Optional;
import java.util.UUID;

public interface BuscarPagamentoPorIdUseCase {

    Optional<Pagamento> executar(UUID pagamentoId);
}
