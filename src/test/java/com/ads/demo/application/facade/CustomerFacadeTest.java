package com.ads.demo.application.facade;

import com.ads.demo.application.dtos.CustomerDTO;
import com.ads.demo.application.exceptions.CustomerPersistenceException;
import com.ads.demo.application.ports.CustomerINPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CustomerFacadeTest {

    private CustomerINPort inPort;
    private CustomerFacade facade;

    @BeforeEach
    void setUp() {
        inPort = mock(CustomerINPort.class);
        facade = new CustomerFacade(inPort);
    }

    @Nested
    @DisplayName("registerCustomer")
    class RegisterCustomer {

        @Test
        @DisplayName("deve registrar cliente com sucesso")
        void deveRegistrarCliente() {
            CustomerDTO dto = new CustomerDTO("1", "Ana", 28);
            when(inPort.putCustomer(dto)).thenReturn(dto);

            CustomerDTO result = facade.registerCustomer(dto);

            assertNotNull(result, "Resultado não deveria ser nulo");
            assertEquals("Ana", result.name());
            verify(inPort).putCustomer(dto);
        }

        @Test
        @DisplayName("deve lançar exceção se o use case falhar")
        void deveLancarExcecaoAoRegistrar() {
            CustomerDTO dto = new CustomerDTO("1", "Ana", 28);
            when(inPort.putCustomer(dto))
                    .thenThrow(new CustomerPersistenceException("Erro ao salvar"));

            CustomerPersistenceException exception = assertThrows(
                    CustomerPersistenceException.class,
                    () -> facade.registerCustomer(dto)
            );

            assertEquals("Erro ao salvar", exception.getMessage());
            verify(inPort).putCustomer(dto);
        }
    }

    @Nested
    @DisplayName("getAllCustomers")
    class GetAllCustomers {

        @Test
        @DisplayName("deve retornar lista com clientes")
        void deveListarClientes() {
            List<CustomerDTO> lista = List.of(
                    new CustomerDTO("1", "João", 25),
                    new CustomerDTO("2", "Maria", 30)
            );
            when(inPort.listCustomers()).thenReturn(lista);

            List<CustomerDTO> result = facade.getAllCustomers();

            assertEquals(2, result.size());
            assertEquals("Maria", result.get(1).name());
            verify(inPort).listCustomers();
        }

        @Test
        @DisplayName("deve lançar exceção ao listar clientes")
        void deveFalharAoListarClientes() {
            when(inPort.listCustomers())
                    .thenThrow(new CustomerPersistenceException("Erro na listagem"));

            CustomerPersistenceException ex = assertThrows(
                    CustomerPersistenceException.class,
                    facade::getAllCustomers
            );

            assertEquals("Erro na listagem", ex.getMessage());
            verify(inPort).listCustomers();
        }
    }

    @Nested
    @DisplayName("findCustomer")
    class FindCustomer {

        @Test
        @DisplayName("deve encontrar cliente pelo ID")
        void deveBuscarCliente() {
            CustomerDTO dto = new CustomerDTO("3", "Carlos", 40);
            when(inPort.searchCustomerByID("3")).thenReturn(dto);

            CustomerDTO result = facade.findCustomer("3");

            assertEquals("Carlos", result.name());
            verify(inPort).searchCustomerByID("3");
        }

        @Test
        @DisplayName("deve lançar exceção ao buscar cliente")
        void deveFalharAoBuscarCliente() {
            when(inPort.searchCustomerByID("3"))
                    .thenThrow(new CustomerPersistenceException("Cliente não encontrado"));

            CustomerPersistenceException ex = assertThrows(
                    CustomerPersistenceException.class,
                    () -> facade.findCustomer("3")
            );

            assertEquals("Cliente não encontrado", ex.getMessage());
            verify(inPort).searchCustomerByID("3");
        }
    }

    @Nested
    @DisplayName("removeCustomer")
    class RemoveCustomer {

        @Test
        @DisplayName("deve remover cliente sem erro")
        void deveRemoverCliente() {
            doNothing().when(inPort).deleteCustomerByID("5");

            assertDoesNotThrow(() -> facade.removeCustomer("5"));
            verify(inPort).deleteCustomerByID("5");
        }

        @Test
        @DisplayName("deve lançar exceção ao remover cliente")
        void deveFalharAoRemoverCliente() {
            doThrow(new CustomerPersistenceException("Erro ao deletar"))
                    .when(inPort).deleteCustomerByID("5");

            CustomerPersistenceException ex = assertThrows(
                    CustomerPersistenceException.class,
                    () -> facade.removeCustomer("5")
            );

            assertEquals("Erro ao deletar", ex.getMessage());
            verify(inPort).deleteCustomerByID("5");
        }
    }
}