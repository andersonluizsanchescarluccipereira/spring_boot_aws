package com.ads.demo.adapters.IN.helpers;

import com.ads.demo.application.exceptions.CustomerPersistenceException;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GlobalExceptionHandlerTest {

    @Test
    void deveRetornarErroCustomizadoParaCustomerPersistenceException() {
        GlobalExceptionHandler handler = new GlobalExceptionHandler();
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setRequestURI("/v1/customers");

        CustomerPersistenceException exception = new CustomerPersistenceException("Erro ao persistir");

        ResponseEntity<?> response = handler.handleCustomerPersistenceException(exception, request);

        assertEquals(500, response.getStatusCodeValue());
        assertTrue(response.getBody().toString().contains("Erro ao persistir"));
    }
}