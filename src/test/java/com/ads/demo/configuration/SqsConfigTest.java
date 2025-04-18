package com.ads.demo.configuration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.SqsClientBuilder;

import java.net.URI;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class SqsConfigMockTest {

    private SqsConfig config;

    @BeforeEach
    void setUp() {
        config = new SqsConfig();
    }

    @Test
    void deveCriarSqsClientComBuilderMockado() {
        try (MockedStatic<SqsClient> staticMock = mockStatic(SqsClient.class)) {
            SqsClientBuilder mockBuilder = mock(SqsClientBuilder.class);
            SqsClient mockClient = mock(SqsClient.class);

            // Mockando métodos do builder
            staticMock.when(SqsClient::builder).thenReturn(mockBuilder);
            when(mockBuilder.endpointOverride(any())).thenReturn(mockBuilder);
            when(mockBuilder.region(any())).thenReturn(mockBuilder);
            when(mockBuilder.build()).thenReturn(mockClient);

            // Executa o método do seu config
            SqsClient result = config.sqsClient();

            // Validações
            assertNotNull(result);
            verify(mockBuilder).endpointOverride(URI.create("http://localhost:4566"));
            verify(mockBuilder).region(Region.of("ap-south-1"));
            verify(mockBuilder).build();
        }
    }
}