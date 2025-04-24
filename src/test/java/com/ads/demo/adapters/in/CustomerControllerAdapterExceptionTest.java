package com.ads.demo.adapters.in;

import com.ads.demo.application.dtos.CustomerDTO;
import com.ads.demo.application.facade.CustomerFacade;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CustomerControllerAdapterExceptionTest {

    private MockMvc mockMvc;
    private CustomerFacade mockFacade;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        mockFacade = Mockito.mock(CustomerFacade.class);
        mockMvc = MockMvcBuilders.standaloneSetup(new CustomerControllerAdapter(mockFacade)).build();
    }

    @Test
    @DisplayName("POST /v1/customers - deve retornar 500 ao lançar exceção")
    void putCustomer_deveRetornar500_EmCasoDeErro() throws Exception {
        CustomerDTO dto = new CustomerDTO("1", "Erro", 99);
        Mockito.when(mockFacade.registerCustomer(Mockito.any())).thenThrow(new RuntimeException("Falha"));

        mockMvc.perform(post("/v1/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isInternalServerError());
    }

    @Test
    @DisplayName("GET /v1/customers/{id} - deve retornar 500 ao lançar exceção")
    void searchCustomerById_deveRetornar500_EmCasoDeErro() throws Exception {
        when(mockFacade.findCustomer("123")).thenThrow(new RuntimeException("Erro inesperado"));

        mockMvc.perform(get("/v1/customers/123"))
                .andExpect(status().isInternalServerError());
    }

    @Test
    @DisplayName("GET /v1/customers - deve retornar 500 ao lançar exceção")
    void listCustomers_deveRetornar500_EmCasoDeErro() throws Exception {
        when(mockFacade.getAllCustomers()).thenThrow(new RuntimeException("Banco caiu"));

        mockMvc.perform(get("/v1/customers"))
                .andExpect(status().isInternalServerError());
    }

    @Test
    @DisplayName("DELETE /v1/customers/{id} - deve retornar 500 ao lançar exceção")
    void deleteCustomerById_deveRetornar500_EmCasoDeErro() throws Exception {
        doThrow(new RuntimeException("Erro na exclusão")).when(mockFacade).removeCustomer("789");

        mockMvc.perform(delete("/v1/customers/789"))
                .andExpect(status().isInternalServerError());
    }
}

