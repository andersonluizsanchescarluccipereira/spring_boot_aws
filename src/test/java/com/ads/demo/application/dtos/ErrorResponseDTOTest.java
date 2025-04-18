package com.ads.demo.application.dtos;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ErrorResponseDTOTest {

    @Test
    void deveCriarErrorResponseDTOComSucesso() {
        LocalDateTime timestamp = LocalDateTime.of(2025, 4, 10, 18, 30);
        ErrorResponseDTO dto = new ErrorResponseDTO(
                timestamp,
                500,
                "Erro interno",
                "Falha ao salvar cliente",
                "/v1/customers"
        );

        assertEquals(timestamp, dto.timestamp());
        assertEquals(500, dto.status());
        assertEquals("Erro interno", dto.error());
        assertEquals("Falha ao salvar cliente", dto.message());
        assertEquals("/v1/customers", dto.path());
    }

    @Test
    void deveCompararDTOsComEqualsEHashCode() {
        LocalDateTime timestamp = LocalDateTime.of(2025, 4, 10, 12, 0);

        ErrorResponseDTO dto1 = new ErrorResponseDTO(timestamp, 404, "Not Found", "Cliente não encontrado", "/v1/customers/99");
        ErrorResponseDTO dto2 = new ErrorResponseDTO(timestamp, 404, "Not Found", "Cliente não encontrado", "/v1/customers/99");

        assertEquals(dto1, dto2);
        assertEquals(dto1.hashCode(), dto2.hashCode());
    }

    @Test
    void deveGerarToStringCorreto() {
        LocalDateTime timestamp = LocalDateTime.of(2025, 4, 10, 12, 0);

        ErrorResponseDTO dto = new ErrorResponseDTO(timestamp, 400, "Bad Request", "Requisição inválida", "/v1/customers");

        String expected = "ErrorResponseDTO[timestamp=2025-04-10T12:00, status=400, error=Bad Request, message=Requisição inválida, path=/v1/customers]";
        assertEquals(expected, dto.toString());
    }
}