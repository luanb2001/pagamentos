package com.project.pagamentos.domain.entity;

import com.project.pagamentos.domain.dto.PagamentoRespostaDTO;
import com.project.pagamentos.domain.dto.ProcessarPagamentoDTO;
import com.project.pagamentos.domain.enums.StatusPagamentoEnum;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "PAGAMENTO")
public class Pagamento {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private UUID contaId;

    private LocalDateTime dataCriacao;

    private LocalDateTime dataProcessamento;

    private Double valor;

    @Enumerated(EnumType.STRING)
    private StatusPagamentoEnum status;

    private String motivoRecusa;

    public static Pagamento cadastrarPagamento(ProcessarPagamentoDTO processarPagamentoDTO) {
        Pagamento pagamento = new Pagamento();
        pagamento.setContaId(processarPagamentoDTO.contaId());
        pagamento.setDataCriacao(LocalDateTime.now());
        pagamento.setValor(processarPagamentoDTO.valor());
        pagamento.setStatus(StatusPagamentoEnum.PENDENTE);

        return pagamento;
    }

    public static Pagamento atualizarPagamento(Pagamento pagamento, PagamentoRespostaDTO pagamentoRespostaDTO) {
        pagamento.setDataProcessamento(pagamentoRespostaDTO.dataProcessamento());
        pagamento.setStatus(pagamentoRespostaDTO.status());

        if (!StatusPagamentoEnum.CONFIRMADO.equals(pagamentoRespostaDTO.status())) {
            pagamento.setMotivoRecusa(pagamentoRespostaDTO.mensagem());
        }

        return pagamento;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getContaId() {
        return contaId;
    }

    public void setContaId(UUID contaId) {
        this.contaId = contaId;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public LocalDateTime getDataProcessamento() {
        return dataProcessamento;
    }

    public void setDataProcessamento(LocalDateTime dataProcessamento) {
        this.dataProcessamento = dataProcessamento;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public StatusPagamentoEnum getStatus() {
        return status;
    }

    public void setStatus(StatusPagamentoEnum status) {
        this.status = status;
    }

    public String getMotivoRecusa() {
        return motivoRecusa;
    }

    public void setMotivoRecusa(String motivoRecusa) {
        this.motivoRecusa = motivoRecusa;
    }
}
