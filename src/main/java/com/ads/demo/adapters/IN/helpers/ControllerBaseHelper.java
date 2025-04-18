package com.ads.demo.adapters.IN.helpers;

import com.ads.demo.adapters.IN.CustomerControllerAdapter;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;

import java.util.logging.Logger;

/**
 * Base para todas as controllers, fornecendo utilitários comuns.
 */
public abstract class ControllerBaseHelper {
    protected final org.slf4j.Logger logger = LoggerFactory.getLogger(getClass());

    protected <T> ResponseEntity<T> ok(T body) {
        return ResponseEntity.ok(body);
    }

    protected ResponseEntity<Void> noContent() {
        return ResponseEntity.noContent().build();
    }

    protected ResponseEntity<String> internalServerError(String message, Exception e) {
        logger.error("Erro interno: {}", message, e);
        return ResponseEntity.internalServerError().body(message);
    }
}
