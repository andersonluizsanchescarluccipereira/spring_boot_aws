package com.ads.demo.configuration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.DynamoDbClientBuilder;

import java.net.URI;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DynamoDbConfigMockTest {

    private DynamoDbConfig config;

    @BeforeEach
    void setup() {
        config = new DynamoDbConfig();

        // Injetar manualmente os valores @Value simulados
        setField(config, "region", "us-east-1");
        setField(config, "dynamoEndpoint", "http://localhost:4566");
    }

    @Test
    void deveCriarDynamoDbClientComBuilderMockado() {
        try (MockedStatic<DynamoDbClient> staticMock = mockStatic(DynamoDbClient.class)) {
            DynamoDbClientBuilder mockBuilder = mock(DynamoDbClientBuilder.class);
            DynamoDbClient mockClient = mock(DynamoDbClient.class);

            staticMock.when(DynamoDbClient::builder).thenReturn(mockBuilder);
            when(mockBuilder.region(any())).thenReturn(mockBuilder);
            when(mockBuilder.endpointOverride(any())).thenReturn(mockBuilder);
            when(mockBuilder.build()).thenReturn(mockClient);

            DynamoDbClient result = config.dynamoDbClient();

            assertNotNull(result);
            verify(mockBuilder).region(Region.of("us-east-1"));
            verify(mockBuilder).endpointOverride(URI.create("http://localhost:4566"));
            verify(mockBuilder).build();
        }
    }

    @Test
    void deveCriarDynamoDbEnhancedClientComBuilderMockado() {
        try (MockedStatic<DynamoDbEnhancedClient> staticMock = mockStatic(DynamoDbEnhancedClient.class)) {
            DynamoDbEnhancedClient.Builder mockBuilder = mock(DynamoDbEnhancedClient.Builder.class);
            DynamoDbEnhancedClient mockEnhancedClient = mock(DynamoDbEnhancedClient.class);
            DynamoDbClient mockClient = mock(DynamoDbClient.class);

            staticMock.when(DynamoDbEnhancedClient::builder).thenReturn(mockBuilder);
            when(mockBuilder.dynamoDbClient(mockClient)).thenReturn(mockBuilder);
            when(mockBuilder.build()).thenReturn(mockEnhancedClient);

            DynamoDbEnhancedClient result = config.dynamoDbEnhancedClient(mockClient);

            assertNotNull(result);
            verify(mockBuilder).dynamoDbClient(mockClient);
            verify(mockBuilder).build();
        }
    }

    // Utilitário para injetar valores em campos privados com reflection
    private void setField(Object target, String fieldName, String value) {
        try {
            var field = target.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(target, value);
        } catch (Exception e) {
            fail("Erro ao setar campo via reflection: " + e.getMessage());
        }
    }
}