package com.ads.demo.application.dtos;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CustomerDTOTest {

    @Test
    void deveCriarCustomerDTOComSucesso() {
        CustomerDTO dto = new CustomerDTO("1", "Ana", 28);

        assertEquals("1", dto.id());
        assertEquals("Ana", dto.name());
        assertEquals(28, dto.age());
    }

    @Test
    void deveCompararDTOsComEqualsEHashCode() {
        CustomerDTO dto1 = new CustomerDTO("1", "Ana", 28);
        CustomerDTO dto2 = new CustomerDTO("1", "Ana", 28);

        assertEquals(dto1, dto2);
        assertEquals(dto1.hashCode(), dto2.hashCode());
    }

    @Test
    void deveGerarToStringCorreto() {
        CustomerDTO dto = new CustomerDTO("1", "Ana", 28);
        String expected = "CustomerDTO[id=1, name=Ana, age=28]";
        assertEquals(expected, dto.toString());
    }
}