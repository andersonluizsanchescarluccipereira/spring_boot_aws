package com.ads.demo.adapters.in.helpers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class ControllerBaseHelperTest {

    private ControllerBaseHelper helper;

    // Classe concreta mínima para testar a abstrata
    private static class ControllerBaseHelperImpl extends ControllerBaseHelper {}

    @BeforeEach
    void setUp() {
        helper = new ControllerBaseHelperImpl();
    }

    @Test
    void deveRetornarOkComBody() {
        String body = "Sucesso";

        ResponseEntity<String> response = helper.ok(body);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(body, response.getBody());
    }

    @Test
    void deveRetornarNoContent() {
        ResponseEntity<Void> response = helper.noContent();

        assertEquals(204, response.getStatusCodeValue());
        assertNull(response.getBody());
    }

    @Test
    void deveRetornarInternalServerErrorComMensagem() {
        String mensagem = "Erro interno simulado";
        Exception ex = new RuntimeException("Falha");

        ResponseEntity<String> response = helper.internalServerError(mensagem, ex);

        assertEquals(500, response.getStatusCodeValue());
        assertEquals(mensagem, response.getBody());
    }
}