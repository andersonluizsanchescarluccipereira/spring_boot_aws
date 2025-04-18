package com.ads.demo.application.models.entity;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CustomerEntityJsonTest {

    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    void deveSerializarParaJson() throws Exception {
        CustomerEntity entity = new CustomerEntity("123", "Fernando", 33);

        String json = mapper.writeValueAsString(entity);
        assertTrue(json.contains("\"id\":\"123\""));
        assertTrue(json.contains("\"name\":\"Fernando\""));
        assertTrue(json.contains("\"age\":33"));
    }

    @Test
    void deveDeserializarDeJson() throws Exception {
        String json = """
                {
                  "id": "abc",
                  "name": "Bianca",
                  "age": 21
                }
                """;

        CustomerEntity entity = mapper.readValue(json, CustomerEntity.class);

        assertEquals("abc", entity.getId());
        assertEquals("Bianca", entity.getName());
        assertEquals(21, entity.getAge());
    }
}
