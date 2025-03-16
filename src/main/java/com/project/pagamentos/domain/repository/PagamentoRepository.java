package com.project.pagamentos.domain.repository;

import com.project.pagamentos.domain.dto.PagamentoDTO;
import com.project.pagamentos.domain.entity.Pagamento;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PagamentoRepository {

    public Pagamento save(Pagamento pagamento);

    public Optional<Pagamento> findById(UUID id);

    public Pagamento buscarPagamentoDTOPorId(UUID id);

    public Double carregarValorPagoPorPeriodo(LocalDateTime dataInicial, LocalDateTime dataFinal);

    public List<PagamentoDTO> listarPagamentosPorFiltro(UUID contaId, LocalDateTime dataCriacao, LocalDateTime dataProcessamento);
}
