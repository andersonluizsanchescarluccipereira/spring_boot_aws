package com.ads.demo.configuration;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import org.jboss.logging.MDC;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.logging.LogRecord;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TraceIdFilterImplTest {

    private final TraceIdFilterImpl filter = new TraceIdFilterImpl();

    @AfterEach
    void limparMdc() {
        MDC.clear();
    }

    @Test
    void deveAdicionarTraceIdAoMdcEDesencadearOFiltro() throws Exception {
        // Arrange
        ServletRequest request = mock(ServletRequest.class);
        ServletResponse response = mock(ServletResponse.class);
        FilterChain chain = mock(FilterChain.class);

        // Act
        filter.doFilter(request, response, chain);

        // Assert
        String traceId = (String) MDC.get("traceId");
        assertNotNull(traceId, "Trace ID deve estar no MDC");
        assertFalse(traceId.isBlank(), "Trace ID não pode estar em branco");

        // Verifica se o filtro continuou
        verify(chain, times(1)).doFilter(request, response);
    }

    @Test
    void deveRemoverTraceIdMesmoComExcecao() throws Exception {
        ServletRequest request = mock(ServletRequest.class);
        ServletResponse response = mock(ServletResponse.class);

        FilterChain chain = mock(FilterChain.class);
        doThrow(new ServletException("Erro simulado")).when(chain).doFilter(request, response);

        assertThrows(ServletException.class, () -> {
            filter.doFilter(request, response, chain);
        });

        assertNull(MDC.get("traceId"), "Trace ID deve ser removido do MDC mesmo em erro");
    }

    @Test
    void isLoggableDeveRetornarFalse() {
        LogRecord record = new LogRecord(java.util.logging.Level.INFO, "Teste");
        assertFalse(filter.isLoggable(record));
    }
}