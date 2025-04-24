package com.ads.demo.adapters.in;

import com.ads.demo.application.dtos.CustomerDTO;
import com.ads.demo.application.facade.CustomerFacade;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class CustomerQueueListenerAdapterTest {

    private SqsClient sqsClient;
    private CustomerFacade facade;
    private ObjectMapper objectMapper;
    private CustomerQueueListenerAdapter listener;

    @BeforeEach
    void setUp() {
        sqsClient = mock(SqsClient.class);
        facade = mock(CustomerFacade.class);
        objectMapper = mock(ObjectMapper.class);

        listener = new CustomerQueueListenerAdapter(sqsClient, facade, objectMapper);
    }

    @Test
    void deveInicializarQueueUrlCorretamente() {
        // Arrange
        String urlEsperada = "http://localhost:4566/000000000000/minha-fila";
        when(sqsClient.getQueueUrl(any(GetQueueUrlRequest.class)))
                .thenReturn(GetQueueUrlResponse.builder().queueUrl(urlEsperada).build());

        // Act
        listener.init();

        // Assert
        // Não conseguimos acessar o atributo privado diretamente, então verificamos se o método foi chamado
        verify(sqsClient).getQueueUrl(any(GetQueueUrlRequest.class));
    }

    @Test
    void deveProcessarMensagensComSucesso() throws Exception {
        // Arrange
        String queueUrl = "http://localhost:4566/000000000000/minha-fila";
        String body = "{\"id\":\"1\",\"name\":\"Maria\",\"age\":25}";

        Message message = Message.builder().body(body).receiptHandle("abc123").build();

        when(sqsClient.getQueueUrl(any(GetQueueUrlRequest.class)))
                .thenReturn(GetQueueUrlResponse.builder().queueUrl(queueUrl).build());
        listener.init();

        when(sqsClient.receiveMessage(any(ReceiveMessageRequest.class)))
                .thenReturn(ReceiveMessageResponse.builder().messages(List.of(message)).build());

        CustomerDTO dto = new CustomerDTO("1", "Maria", 25);
        when(objectMapper.readValue(body, CustomerDTO.class)).thenReturn(dto);

        // Act
        listener.listenToQueue();

        // Assert
        verify(facade).registerCustomer(dto);
        verify(sqsClient).deleteMessage(any(DeleteMessageRequest.class));
    }

    @Test
    void deveLidarComErroAoProcessarMensagem() throws Exception {
        // Arrange
        String queueUrl = "http://localhost:4566/000000000000/minha-fila";
        String body = "{\"id\":\"1\",\"name\":\"Maria\",\"age\":25}";

        Message message = Message.builder().body(body).receiptHandle("abc123").build();

        when(sqsClient.getQueueUrl(any(GetQueueUrlRequest.class)))
                .thenReturn(GetQueueUrlResponse.builder().queueUrl(queueUrl).build());
        listener.init();

        when(sqsClient.receiveMessage(any(ReceiveMessageRequest.class)))
                .thenReturn(ReceiveMessageResponse.builder().messages(List.of(message)).build());

        // Força erro no JSON
        when(objectMapper.readValue(body, CustomerDTO.class))
                .thenThrow(new RuntimeException("Erro de parsing"));

        // Act
        listener.listenToQueue();

        // Assert: espera-se que a exceção seja capturada internamente e o fluxo continue
        verify(facade, never()).registerCustomer(any());
        verify(sqsClient, never()).deleteMessage((DeleteMessageRequest) any());
    }
}