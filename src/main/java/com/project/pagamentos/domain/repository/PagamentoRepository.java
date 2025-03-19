package com.project.pagamentos.domain.repository;

import com.project.pagamentos.domain.entity.Pagamento;

import java.util.Optional;
import java.util.UUID;

public interface PagamentoRepository {

    public Pagamento save(Pagamento pagamento);

    public Optional<Pagamento> findById(UUID id);
}
