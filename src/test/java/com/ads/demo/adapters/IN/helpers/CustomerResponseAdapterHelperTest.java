package com.ads.demo.adapters.IN.helpers;

import com.ads.demo.application.dtos.CustomerDTO;
import com.ads.demo.application.dtos.CustomerResponseDTO;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class CustomerResponseAdapterHelperTest {

    @Test
    void deveConverterDTOParaResponseDTO() {
        CustomerDTO dto = new CustomerDTO("1", "Maria", 30);

        CustomerResponseDTO response = CustomerResponseAdapterHelper.convertDTOToResponseDTO(dto);

        assertEquals(dto.id(), response.id());
        assertEquals(dto.name(), response.name());
        assertEquals(dto.age(), response.age());
        assertNotNull(response.dateTimeLastUpdate());
        assertTrue(response.dateTimeLastUpdate().isBefore(LocalDateTime.now().plusSeconds(1)));
    }

    @Test
    void deveConverterResponseDTOParaDTO() {
        LocalDateTime timestamp = LocalDateTime.now();
        CustomerResponseDTO response = new CustomerResponseDTO("2", "João", 25, timestamp);

        CustomerDTO dto = CustomerResponseAdapterHelper.convertResponseDTOToDTO(response);

        assertEquals(response.id(), dto.id());
        assertEquals(response.name(), dto.name());
        assertEquals(response.age(), dto.age());
    }

    @Test
    void construtorDeveLancarExcecao() {
        Constructor<?>[] constructors = CustomerResponseAdapterHelper.class.getDeclaredConstructors();
        constructors[0].setAccessible(true);

        Exception exception = assertThrows(Exception.class, () -> {
            constructors[0].newInstance();
        });

        assertTrue(exception.getCause() instanceof IllegalAccessException);
        assertEquals("Utility Response class", exception.getCause().getMessage());
    }
}