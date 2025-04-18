package com.ads.demo.configuration;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import org.jboss.logging.MDC;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.UUID;
import java.util.logging.LogRecord;

@Component
public class TraceIdFilterImpl implements TraceIdFilter {

    private static final String TRACE_ID = "traceId";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        try {
            String traceId = UUID.randomUUID().toString();
            MDC.put(TRACE_ID, traceId);
            chain.doFilter(request, response);
        } finally {
            MDC.remove(TRACE_ID);
        }
    }

    @Override
    public boolean isLoggable(LogRecord record) {
        return false;
    }
}
