package com.ads.demo.application.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class CustomerPersistenceExceptionTest {

    @Test
    void deveCriarExcecaoSemParametros() {
        CustomerPersistenceException ex = new CustomerPersistenceException();
        assertNull(ex.getMessage());
        assertNull(ex.getCause());
    }

    @Test
    void deveCriarExcecaoComMensagem() {
        CustomerPersistenceException ex = new CustomerPersistenceException("Erro ao persistir cliente");
        assertEquals("Erro ao persistir cliente", ex.getMessage());
    }

    @Test
    void deveCriarExcecaoComMensagemECausa() {
        Throwable causa = new RuntimeException("Erro original");
        CustomerPersistenceException ex = new CustomerPersistenceException("Erro de persistência", causa);
        assertEquals("Erro de persistência", ex.getMessage());
        assertEquals(causa, ex.getCause());
    }

    @Test
    void deveCriarExcecaoComApenasCausa() {
        Throwable causa = new RuntimeException("Falha ao salvar");
        CustomerPersistenceException ex = new CustomerPersistenceException(causa);
        assertEquals(causa, ex.getCause());
    }

    @Test
    void deveCriarExcecaoComTodosParametros() {
        Throwable causa = new RuntimeException("Falha grave");
        CustomerPersistenceException ex = new CustomerPersistenceException("Erro completo", causa, true, false);

        assertEquals("Erro completo", ex.getMessage());
        assertEquals(causa, ex.getCause());
        // Não conseguimos validar suppression/stackTrace diretamente aqui, mas garantimos que compila
    }
}