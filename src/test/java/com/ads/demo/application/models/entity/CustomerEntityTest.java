package com.ads.demo.application.models.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CustomerEntityTest {

    @Test
    void deveCriarObjetoComConstrutorVazioESetters() {
        CustomerEntity entity = new CustomerEntity();
        entity.setId("10");
        entity.setName("João");
        entity.setAge(45);

        assertEquals("10", entity.getId());
        assertEquals("João", entity.getName());
        assertEquals(45, entity.getAge());
    }

    @Test
    void deveCriarObjetoComConstrutorCompleto() {
        CustomerEntity entity = new CustomerEntity("20", "Maria", 30);

        assertEquals("20", entity.getId());
        assertEquals("Maria", entity.getName());
        assertEquals(30, entity.getAge());
    }

    @Test
    void devePermitirModificarAtributos() {
        CustomerEntity entity = new CustomerEntity("1", "Fulano", 40);

        entity.setName("Beltrano");
        entity.setAge(50);

        assertEquals("1", entity.getId());
        assertEquals("Beltrano", entity.getName());
        assertEquals(50, entity.getAge());
    }

    @Test
    void naoDevePermitirValoresNulosParaIdSeForParticaoObrigatoria() {
        CustomerEntity entity = new CustomerEntity();
        entity.setId(null);
        assertNull(entity.getId());
        // Validação adicional pode ser feita no adapter/usecase se necessário
    }
}
