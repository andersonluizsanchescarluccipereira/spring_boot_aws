package com.ads.demo.adapters;

import com.ads.demo.adapters.helpers.CustomerPersistenceAdapterHelper;
import com.ads.demo.application.dtos.CustomerDTO;
import com.ads.demo.application.exceptions.CustomerPersistenceException;
import com.ads.demo.application.models.entity.CustomerEntity;
import com.ads.demo.application.ports.CustomerPersistencePort;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
public class CustomerPersistenceDynamoAdapter implements CustomerPersistencePort {

    private static final Logger logger = LoggerFactory.getLogger(CustomerPersistenceDynamoAdapter.class);
    private final DynamoDbEnhancedClient enhancedClient;
    private DynamoDbTable<CustomerEntity> table;

    public CustomerPersistenceDynamoAdapter(DynamoDbEnhancedClient enhancedClient) {
        this.enhancedClient = enhancedClient;
    }

    @PostConstruct
    public void init() {
        this.table = enhancedClient.table("global01", TableSchema.fromBean(CustomerEntity.class));
    }

    @Override
    public CustomerDTO putCustomer(CustomerDTO customerDTO) {
        try {
            CustomerEntity customerEntity = CustomerPersistenceAdapterHelper.convertDTOToEntity(customerDTO);
            table.putItem(customerEntity);
        } catch (Exception e) {
            logger.error("Error putCustomer " + e);
            throw new CustomerPersistenceException(e);
        }
        return customerDTO;
    }

    @Override
    public CustomerDTO searchCustomerByID(String id) {
        CustomerDTO customerDTO = null;
        try {
            CustomerEntity customerEntity = table.getItem(Key.builder().partitionValue(id).build());
            customerDTO = CustomerPersistenceAdapterHelper.convertEntityToDTO(customerEntity);
            return customerDTO;
        } catch (Exception e) {
            logger.error("Error searchCustomerByID " + e);
            throw new CustomerPersistenceException(e);
        }
    }

    @Override
    public List<CustomerDTO> listCustomers() {
        try {
            return StreamSupport
                    .stream(table.scan().items().spliterator(), false)
                    .map(CustomerPersistenceAdapterHelper::convertEntityToDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("Error searchCustomerByID " + e);
            throw new CustomerPersistenceException(e);
        }
    }

    @Override
    public void deleteCustomerByID(String id) {
        try {
            table.deleteItem(Key.builder().partitionValue(id).build());
        } catch (Exception e) {
            logger.error("Error deleteCustomerByID " + e);
            throw new CustomerPersistenceException(e);
        }
    }
}

