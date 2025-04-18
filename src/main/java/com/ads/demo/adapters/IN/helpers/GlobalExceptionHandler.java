package com.ads.demo.adapters.IN.helpers;

import com.ads.demo.application.dtos.ErrorResponseDTO;
import com.ads.demo.application.exceptions.CustomerPersistenceException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomerPersistenceException.class)
    public ResponseEntity<ErrorResponseDTO> handleCustomerPersistenceException(
            CustomerPersistenceException ex, HttpServletRequest request) {

        ErrorResponseDTO response = new ErrorResponseDTO(
                LocalDateTime.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Persistence error",
                ex.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    // você pode adicionar mais @ExceptionHandler para outras exceções aqui
}
