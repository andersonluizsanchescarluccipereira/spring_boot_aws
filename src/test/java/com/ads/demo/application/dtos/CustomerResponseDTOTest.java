package com.ads.demo.application.dtos;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CustomerResponseDTOTest {

    @Test
    void deveCriarCustomerResponseDTOComSucesso() {
        LocalDateTime now = LocalDateTime.now();
        CustomerResponseDTO dto = new CustomerResponseDTO("123", "João", 35, now);

        assertEquals("123", dto.id());
        assertEquals("João", dto.name());
        assertEquals(35, dto.age());
        assertEquals(now, dto.dateTimeLastUpdate());
    }

    @Test
    void deveCompararDTOsComEqualsEHashCode() {
        LocalDateTime date = LocalDateTime.of(2024, 12, 25, 10, 30);
        CustomerResponseDTO dto1 = new CustomerResponseDTO("1", "Maria", 28, date);
        CustomerResponseDTO dto2 = new CustomerResponseDTO("1", "Maria", 28, date);

        assertEquals(dto1, dto2);
        assertEquals(dto1.hashCode(), dto2.hashCode());
    }

    @Test
    void deveGerarToStringCorreto() {
        LocalDateTime date = LocalDateTime.of(2025, 4, 10, 12, 0);
        CustomerResponseDTO dto = new CustomerResponseDTO("1", "Carlos", 45, date);

        String expected = "CustomerResponseDTO[id=1, name=Carlos, age=45, dateTimeLastUpdate=2025-04-10T12:00]";
        assertEquals(expected, dto.toString());
    }
}