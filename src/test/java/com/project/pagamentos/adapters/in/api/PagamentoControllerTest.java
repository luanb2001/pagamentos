package com.project.pagamentos.adapters.in.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.pagamentos.application.usecase.ProcessarPagamentoUseCase;
import com.project.pagamentos.domain.dto.ProcessarPagamentoDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class PagamentoControllerTest {

    @Autowired
    private MockMvc mock;

    @Autowired
    private ObjectMapper mapper;

    @MockitoBean
    private ProcessarPagamentoUseCase processarPagamentoUseCase;

    @Test
    public void processarPagamento() throws Exception {
        ProcessarPagamentoDTO processarPagamentoDTO = new ProcessarPagamentoDTO(UUID.randomUUID(), 100.0);

        String processarPagamentoDtoString = this.mapper.writeValueAsString(processarPagamentoDTO);

        this.mock.perform(post("/pagamento")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(processarPagamentoDtoString))
                .andExpect(status().isOk())
                .andReturn();

        verify(this.processarPagamentoUseCase, times(1)).executar(any(ProcessarPagamentoDTO.class));
    }
}
