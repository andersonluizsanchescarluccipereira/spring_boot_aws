package com.ads.demo.application.facade;

import com.ads.demo.application.dtos.CustomerDTO;
import com.ads.demo.application.ports.CustomerINPort;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CustomerFacade {
    private final CustomerINPort customerUseCase;

    public CustomerFacade(CustomerINPort customerUseCase) {
        this.customerUseCase = customerUseCase;
    }

    public CustomerDTO registerCustomer(CustomerDTO dto) {
        // Aqui você poderia aplicar validação, logging, conversão, etc.
        return customerUseCase.putCustomer(dto);
    }

    public List<CustomerDTO> getAllCustomers() {
        return customerUseCase.listCustomers();
    }

    public CustomerDTO findCustomer(String id) {
        return customerUseCase.searchCustomerByID(id);
    }

    public void removeCustomer(String id) {
        customerUseCase.deleteCustomerByID(id);
    }
}
