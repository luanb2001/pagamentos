package com.project.pagamentos.domain.entity;

import com.project.pagamentos.domain.enums.StatusPagamentoEnum;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PagamentoTest {

    @Test
    void deveCriarPagamentoComDadosValidos() {
        UUID pagamentoId = UUID.randomUUID();
        UUID contaId = UUID.randomUUID();
        Double valor = 100.0;
        StatusPagamentoEnum status = StatusPagamentoEnum.PENDENTE;
        String motivoRecusa = "Pagamento não aprovado";
        LocalDateTime dataCriacao = LocalDateTime.now();
        LocalDateTime dataProcessamento = LocalDateTime.now().plusMinutes(10);

        Pagamento pagamento = new Pagamento();
        pagamento.setId(pagamentoId);
        pagamento.setContaId(contaId);
        pagamento.setValor(valor);
        pagamento.setStatus(status);
        pagamento.setMotivoRecusa(motivoRecusa);
        pagamento.setDataCriacao(dataCriacao);
        pagamento.setDataProcessamento(dataProcessamento);

        assertEquals(pagamentoId, pagamento.getId());
        assertEquals(contaId, pagamento.getContaId());
        assertEquals(valor, pagamento.getValor());
        assertEquals(status, pagamento.getStatus());
        assertEquals(motivoRecusa, pagamento.getMotivoRecusa());
        assertEquals(dataCriacao, pagamento.getDataCriacao());
        assertEquals(dataProcessamento, pagamento.getDataProcessamento());
    }

    @Test
    void deveAlterarValoresDePagamento() {
        Pagamento pagamento = new Pagamento();
        UUID pagamentoId = UUID.randomUUID();
        pagamento.setId(pagamentoId);

        UUID novaContaId = UUID.randomUUID();
        Double novoValor = 200.0;
        StatusPagamentoEnum novoStatus = StatusPagamentoEnum.CONFIRMADO;
        String novoMotivoRecusa = "Pagamento aprovado";
        LocalDateTime novaDataProcessamento = LocalDateTime.now().plusMinutes(5);

        pagamento.setContaId(novaContaId);
        pagamento.setValor(novoValor);
        pagamento.setStatus(novoStatus);
        pagamento.setMotivoRecusa(novoMotivoRecusa);
        pagamento.setDataProcessamento(novaDataProcessamento);

        assertEquals(novaContaId, pagamento.getContaId());
        assertEquals(novoValor, pagamento.getValor());
        assertEquals(novoStatus, pagamento.getStatus());
        assertEquals(novoMotivoRecusa, pagamento.getMotivoRecusa());
        assertEquals(novaDataProcessamento, pagamento.getDataProcessamento());
    }

    @Test
    void deveCriarPagamentoComStatusPendente() {
        Pagamento pagamento = new Pagamento();
        pagamento.setStatus(StatusPagamentoEnum.PENDENTE);

        assertEquals(StatusPagamentoEnum.PENDENTE, pagamento.getStatus());
    }
}
