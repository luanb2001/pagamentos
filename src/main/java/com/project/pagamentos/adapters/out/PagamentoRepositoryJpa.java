package com.project.pagamentos.adapters.out;

import com.project.pagamentos.domain.dto.PagamentoDTO;
import com.project.pagamentos.domain.entity.Pagamento;
import com.project.pagamentos.domain.enums.StatusPagamentoEnum;
import com.project.pagamentos.domain.repository.PagamentoRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface PagamentoRepositoryJpa extends PagamentoRepository, CrudRepository<Pagamento, UUID> {

    @Query(value = "SELECT new com.project.pagamentos.domain.dto.PagamentoDTO(o.id, o.contaId, o.dataCriacao, o.dataProcessamento, o.valor, o.status, o.motivoRecusa)"
            + " FROM Pagamento o WHERE (o.dataCriacao BETWEEN :dataCriacaoInicial AND :dataCriacaoFinal) " +
            "AND (:statusPagamentoEnum IS NULL OR o.status = :statusPagamentoEnum)")
    List<PagamentoDTO> listarPagamentosPorFiltro(LocalDateTime dataCriacaoInicial, LocalDateTime dataCriacaoFinal, StatusPagamentoEnum statusPagamentoEnum);
}
