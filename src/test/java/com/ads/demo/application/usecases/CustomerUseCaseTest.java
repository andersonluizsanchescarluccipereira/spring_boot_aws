package com.ads.demo.application.usecases;

import static org.junit.jupiter.api.Assertions.*;

import com.ads.demo.application.dtos.CustomerDTO;
import com.ads.demo.application.exceptions.CustomerPersistenceException;
import com.ads.demo.application.ports.CustomerPersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CustomerUseCaseTest {

    private CustomerPersistencePort repository;
    private CustomerUseCase useCase;

    @BeforeEach
    void setUp() {
        repository = mock(CustomerPersistencePort.class);
        useCase = new CustomerUseCase(repository);
    }

    @Nested
    class HappyPath {

        @Test
        @DisplayName("Deve inserir ou atualizar um cliente com sucesso")
        void putCustomer_deveRetornarDTO() {
            CustomerDTO input = new CustomerDTO("1", "Joana", 30);
            when(repository.putCustomer(input)).thenReturn(input);

            CustomerDTO result = useCase.putCustomer(input);

            assertEquals("Joana", result.name());
            verify(repository).putCustomer(input);
        }

        @Test
        @DisplayName("Deve buscar um cliente pelo ID com sucesso")
        void searchCustomerByID_deveRetornarCliente() {
            CustomerDTO dto = new CustomerDTO("2", "Carlos", 40);
            when(repository.searchCustomerByID("2")).thenReturn(dto);

            CustomerDTO result = useCase.searchCustomerByID("2");

            assertEquals("Carlos", result.name());
            assertEquals("2", result.id());
            verify(repository).searchCustomerByID("2");
        }

        @Test
        @DisplayName("Deve listar todos os clientes")
        void listCustomers_deveRetornarLista() {
            List<CustomerDTO> lista = List.of(
                    new CustomerDTO("1", "Maria", 25),
                    new CustomerDTO("2", "Pedro", 35)
            );
            when(repository.listCustomers()).thenReturn(lista);

            List<CustomerDTO> result = useCase.listCustomers();

            assertEquals(2, result.size());
            verify(repository).listCustomers();
        }

        @Test
        @DisplayName("Deve remover cliente por ID")
        void deleteCustomerByID_deveChamarRepository() {
            doNothing().when(repository).deleteCustomerByID("3");

            assertDoesNotThrow(() -> useCase.deleteCustomerByID("3"));
            verify(repository).deleteCustomerByID("3");
        }
    }

    @Nested
    class ExceptionPath {

        @Test
        @DisplayName("Deve propagar exceção no putCustomer")
        void putCustomer_deveLancarExcecao() {
            CustomerDTO dto = new CustomerDTO("4", "Erro", 55);
            when(repository.putCustomer(dto))
                    .thenThrow(new CustomerPersistenceException("Erro ao salvar"));

            CustomerPersistenceException ex = assertThrows(CustomerPersistenceException.class, () ->
                    useCase.putCustomer(dto)
            );

            assertEquals("Erro ao salvar", ex.getMessage());
            verify(repository).putCustomer(dto);
        }

        @Test
        @DisplayName("Deve propagar exceção no searchCustomerByID")
        void searchCustomerByID_deveLancarExcecao() {
            when(repository.searchCustomerByID("999"))
                    .thenThrow(new CustomerPersistenceException("Cliente não encontrado"));

            CustomerPersistenceException ex = assertThrows(CustomerPersistenceException.class, () ->
                    useCase.searchCustomerByID("999")
            );

            assertEquals("Cliente não encontrado", ex.getMessage());
            verify(repository).searchCustomerByID("999");
        }

        @Test
        @DisplayName("Deve propagar exceção em listCustomers")
        void listCustomers_deveLancarExcecao() {
            when(repository.listCustomers())
                    .thenThrow(new CustomerPersistenceException("Falha ao buscar lista"));

            CustomerPersistenceException ex = assertThrows(CustomerPersistenceException.class, () ->
                    useCase.listCustomers()
            );

            assertEquals("Falha ao buscar lista", ex.getMessage());
            verify(repository).listCustomers();
        }

        @Test
        @DisplayName("Deve propagar exceção em deleteCustomerByID")
        void deleteCustomerByID_deveLancarExcecao() {
            doThrow(new CustomerPersistenceException("Falha ao deletar"))
                    .when(repository).deleteCustomerByID("8");

            CustomerPersistenceException ex = assertThrows(CustomerPersistenceException.class, () ->
                    useCase.deleteCustomerByID("8")
            );

            assertEquals("Falha ao deletar", ex.getMessage());
            verify(repository).deleteCustomerByID("8");
        }
    }
}