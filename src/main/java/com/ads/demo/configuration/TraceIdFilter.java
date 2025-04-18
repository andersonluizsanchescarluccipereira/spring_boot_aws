package com.ads.demo.configuration;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;

import java.io.IOException;
import java.util.logging.Filter;
import java.util.logging.LogRecord;

public interface TraceIdFilter extends Filter {
    void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException;

    @Override
    boolean isLoggable(LogRecord record);
}
