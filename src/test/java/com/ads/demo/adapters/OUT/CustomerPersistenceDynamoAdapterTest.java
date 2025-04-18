package com.ads.demo.adapters.OUT;

import static org.junit.jupiter.api.Assertions.*;

import com.ads.demo.application.dtos.CustomerDTO;
import com.ads.demo.application.exceptions.CustomerPersistenceException;
import com.ads.demo.application.models.entity.CustomerEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import software.amazon.awssdk.core.pagination.sync.SdkIterable;
import software.amazon.awssdk.enhanced.dynamodb.*;
import software.amazon.awssdk.enhanced.dynamodb.model.GetItemEnhancedRequest;
import software.amazon.awssdk.enhanced.dynamodb.model.PageIterable;
import software.amazon.awssdk.enhanced.dynamodb.model.PutItemEnhancedRequest;

import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CustomerPersistenceDynamoAdapterTest {

    private DynamoDbEnhancedClient enhancedClient;
    private DynamoDbTable<CustomerEntity> table;
    private CustomerPersistenceDynamoAdapter adapter;

    @BeforeEach
    void setUp() {
        enhancedClient = mock(DynamoDbEnhancedClient.class);
        table = mock(DynamoDbTable.class);
        when(enhancedClient.table(eq("global01"), any(TableSchema.class))).thenReturn(table);

        adapter = new CustomerPersistenceDynamoAdapter(enhancedClient);
        adapter.init(); // executa o @PostConstruct
    }

    @Test
    @DisplayName("putCustomer deve chamar putItem e retornar DTO")
    void putCustomer_sucesso() {
        CustomerDTO dto = new CustomerDTO("1", "Maria", 25);
        doNothing().when(table).putItem(any(CustomerEntity.class));

        CustomerDTO result = adapter.putCustomer(dto);

        assertNotNull(result);
        assertEquals("Maria", result.name());
        verify(table).putItem(any(CustomerEntity.class));
    }

    @Test
    @DisplayName("putCustomer deve lançar CustomerPersistenceException ao falhar")
    void putCustomer_deveLancarExcecao() {
        // Arrange
        CustomerDTO dto = new CustomerDTO("1", "Erro", 99);

        // Correto para métodos void
        doThrow(new RuntimeException("Falha ao salvar no Dynamo"))
                .when(table)
                .putItem(any(CustomerEntity.class));

        // Act + Assert
        assertThrows(CustomerPersistenceException.class, () -> adapter.putCustomer(dto));
    }

    @Test
    @DisplayName("searchCustomerByID deve retornar DTO")
    void searchCustomerByID_sucesso() {
        CustomerEntity entity = new CustomerEntity("abc", "Carlos", 30);
        when(table.getItem(any(Key.class))).thenReturn(entity);

        CustomerDTO result = adapter.searchCustomerByID("abc");

        assertEquals("Carlos", result.name());
        verify(table).getItem(any(Key.class));
    }

    @Test
    @DisplayName("searchCustomerByID deve lançar CustomerPersistenceException")
    void searchCustomerByID_erro() {
        when(table.getItem((GetItemEnhancedRequest) any())).thenThrow(new RuntimeException("Erro"));

        assertThrows(CustomerPersistenceException.class, () -> adapter.searchCustomerByID("404"));
    }

    @Test
    @DisplayName("listCustomers deve retornar lista convertida")
    void listCustomers_sucesso() {
        CustomerEntity c1 = new CustomerEntity("1", "A", 10);
        CustomerEntity c2 = new CustomerEntity("2", "B", 20);

        SdkIterable<CustomerEntity> mockIterable = () -> List.of(c1, c2).iterator();
        PageIterable<CustomerEntity> mockPage = mock(PageIterable.class);
        when(mockPage.items()).thenReturn(mockIterable);
        when(table.scan()).thenReturn(mockPage);

        List<CustomerDTO> list = adapter.listCustomers();

        assertEquals(2, list.size());
        assertEquals("A", list.get(0).name());
        verify(table).scan();
    }

    @Test
    @DisplayName("listCustomers deve lançar CustomerPersistenceException")
    void listCustomers_erro() {
        when(table.scan()).thenThrow(new RuntimeException("Scan falhou"));

        assertThrows(CustomerPersistenceException.class, () -> adapter.listCustomers());
    }

    @Test
    @DisplayName("deleteCustomerByID deve chamar deleteItem")
    void deleteCustomerByID_sucesso() {
        when(table.deleteItem(any(Key.class))).thenReturn(null); // ← CORRIGIDO AQUI

        assertDoesNotThrow(() -> adapter.deleteCustomerByID("123"));
        verify(table).deleteItem(any(Key.class));
    }

    @Test
    @DisplayName("deleteCustomerByID deve lançar CustomerPersistenceException")
    void deleteCustomerByID_erro() {
        doThrow(new RuntimeException("delete erro")).when(table).deleteItem(any(Key.class));

        assertThrows(CustomerPersistenceException.class, () -> adapter.deleteCustomerByID("erro"));
    }
}