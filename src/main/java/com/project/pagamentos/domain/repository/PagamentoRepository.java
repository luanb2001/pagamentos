package com.project.pagamentos.domain.repository;

import com.project.pagamentos.domain.dto.PagamentoDTO;
import com.project.pagamentos.domain.entity.Pagamento;
import com.project.pagamentos.domain.enums.StatusPagamentoEnum;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PagamentoRepository {

    public Pagamento save(Pagamento pagamento);

    public Optional<Pagamento> findById(UUID id);

    public List<PagamentoDTO> listarPagamentosPorFiltro(LocalDateTime dataCriacaoInicial, LocalDateTime dataCriacaoFinal, StatusPagamentoEnum statusPagamentoEnum);
}
