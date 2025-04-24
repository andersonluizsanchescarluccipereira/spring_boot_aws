package com.ads.demo.adapters.in;

import com.ads.demo.application.dtos.CustomerDTO;
import com.ads.demo.application.facade.CustomerFacade;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class CustomerControllerAdapterTest {

    private CustomerFacade facade;
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        facade = Mockito.mock(CustomerFacade.class);
        CustomerControllerAdapter controller = new CustomerControllerAdapter(facade);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void deveCriarCustomerComSucesso() throws Exception {
        CustomerDTO dto = new CustomerDTO("1", "Maria", 30);
        Mockito.when(facade.registerCustomer(any())).thenReturn(dto);

        mockMvc.perform(post("/v1/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Maria"));
    }

    @Test
    void deveBuscarCustomerPorId() throws Exception {
        CustomerDTO dto = new CustomerDTO("1", "José", 40);
        Mockito.when(facade.findCustomer("1")).thenReturn(dto);

        mockMvc.perform(get("/v1/customers/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("José"));
    }

    @Test
    void deveListarCustomers() throws Exception {
        List<CustomerDTO> lista = List.of(
                new CustomerDTO("1", "João", 25),
                new CustomerDTO("2", "Maria", 32)
        );
        Mockito.when(facade.getAllCustomers()).thenReturn(lista);

        mockMvc.perform(get("/v1/customers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void deveDeletarCustomerPorId() throws Exception {
        Mockito.doNothing().when(facade).removeCustomer("1");

        mockMvc.perform(delete("/v1/customers/1"))
                .andExpect(status().isNoContent());
    }
}