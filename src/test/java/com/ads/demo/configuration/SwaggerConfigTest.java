package com.ads.demo.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class SwaggerConfigTest {

    @Test
    void deveCriarOpenApiComInformacoesCorretas() {
        SwaggerConfig config = new SwaggerConfig();
        OpenAPI openAPI = config.microserviceOpenAPI();

        assertNotNull(openAPI);
        Info info = openAPI.getInfo();

        assertNotNull(info);
        assertEquals("Customer Microservice API", info.getTitle());
        assertEquals("v1", info.getVersion());
        assertEquals("API for managment customers using hexagonal architecture with AWS DynamoDB", info.getDescription());
    }
}