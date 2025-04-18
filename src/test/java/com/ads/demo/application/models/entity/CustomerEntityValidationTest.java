package com.ads.demo.application.models.entity;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class CustomerEntityValidationTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void deveValidarEntidadeValida() {
        CustomerEntity entity = new CustomerEntity("1", "Lucas", 25);
        Set<ConstraintViolation<CustomerEntity>> violations = validator.validate(entity);
        assertTrue(violations.isEmpty());
    }

    @Test
    void deveFalharComCamposInvalidos() {
        CustomerEntity entity = new CustomerEntity("", "", -5);
        Set<ConstraintViolation<CustomerEntity>> violations = validator.validate(entity);
        assertEquals(3, violations.size()); // id vazio, name vazio, age negativo
    }

    @Test
    void deveDetectarCamposNulos() {
        CustomerEntity entity = new CustomerEntity();
        Set<ConstraintViolation<CustomerEntity>> violations = validator.validate(entity);
        assertEquals(3, violations.size()); // id, name, age todos nulos
    }
}
