package com.ads.demo.adapters.in.helpers;

import com.ads.demo.application.dtos.CustomerDTO;
import com.ads.demo.application.models.entity.CustomerEntity;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;

import static org.junit.jupiter.api.Assertions.*;

class CustomerPersistenceAdapterHelperTest {

    @Test
    void deveConverterDTOParaEntity() {
        CustomerDTO dto = new CustomerDTO("1", "Maria", 30);

        CustomerEntity entity = CustomerPersistenceAdapterHelper.convertDTOToEntity(dto);

        assertEquals(dto.id(), entity.getId());
        assertEquals(dto.name(), entity.getName());
        assertEquals(dto.age(), entity.getAge());
    }

    @Test
    void deveConverterEntityParaDTO() {
        CustomerEntity entity = new CustomerEntity("2", "João", 25);

        CustomerDTO dto = CustomerPersistenceAdapterHelper.convertEntityToDTO(entity);

        assertEquals(entity.getId(), dto.id());
        assertEquals(entity.getName(), dto.name());
        assertEquals(entity.getAge(), dto.age());
    }

    @Test
    void construtorDeveLancarExcecao() {
        Constructor<?>[] constructors = CustomerPersistenceAdapterHelper.class.getDeclaredConstructors();
        constructors[0].setAccessible(true);

        Exception exception = assertThrows(Exception.class, () -> {
            constructors[0].newInstance();
        });

        assertTrue(exception.getCause() instanceof IllegalAccessException);
        assertEquals("Utility class", exception.getCause().getMessage());
    }
}