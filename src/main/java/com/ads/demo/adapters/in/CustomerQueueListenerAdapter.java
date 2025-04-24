package com.ads.demo.adapters.in;

import com.ads.demo.application.dtos.CustomerDTO;
import com.ads.demo.application.facade.CustomerFacade;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.DeleteMessageRequest;
import software.amazon.awssdk.services.sqs.model.GetQueueUrlRequest;
import software.amazon.awssdk.services.sqs.model.Message;
import software.amazon.awssdk.services.sqs.model.ReceiveMessageRequest;

import java.util.List;

@Component
public class CustomerQueueListenerAdapter {

    protected final org.slf4j.Logger logger = LoggerFactory.getLogger(getClass());

    private final SqsClient sqsClient;
    private final CustomerFacade customerFacade;
    private final ObjectMapper objectMapper;

    private String queueUrl;

    public CustomerQueueListenerAdapter(SqsClient sqsClient, CustomerFacade customerFacade, ObjectMapper objectMapper) {
        this.sqsClient = sqsClient;
        this.customerFacade = customerFacade;
        this.objectMapper = objectMapper;
    }

    @PostConstruct
    public void init() {
        this.queueUrl = sqsClient.getQueueUrl(GetQueueUrlRequest.builder()
                .queueName("minha-fila")
                .build()).queueUrl();
    }

    @Scheduled(fixedDelay = 5000)
    public void listenToQueue() {
        ReceiveMessageRequest request = ReceiveMessageRequest.builder()
                .queueUrl(queueUrl)
                .maxNumberOfMessages(10)
                .waitTimeSeconds(5)
                .build();

        List<Message> messages = sqsClient.receiveMessage(request).messages();

        for (Message message : messages) {
            try {
                logger.info("Mensagem recebida da fila: {}", message.body());

                // Converte JSON recebido para DTO
                CustomerDTO dto = objectMapper.readValue(message.body(), CustomerDTO.class);

                // Usa a facade para orquestrar o fluxo
                customerFacade.registerCustomer(dto);

                // Remove a mensagem da fila após o processamento
                sqsClient.deleteMessage(DeleteMessageRequest.builder()
                        .queueUrl(queueUrl)
                        .receiptHandle(message.receiptHandle())
                        .build());

            } catch (Exception e) {
                logger.error("Erro ao processar mensagem da fila", e);
            }
        }
    }
}
