package com.project.pagamentos.adapters.out;

import com.project.pagamentos.domain.entity.Pagamento;
import com.project.pagamentos.domain.repository.PagamentoRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PagamentoRepositoryJpa extends JpaRepository<Pagamento, UUID>, PagamentoRepository {
}
