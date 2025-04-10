package com.ads.demo.application.ports;


import com.ads.demo.application.dtos.CustomerDTO;

import java.util.List;

public interface CustomerPersistencePort {
    CustomerDTO putCustomer(CustomerDTO CustomerDTO);

    CustomerDTO searchCustomerByID(String id);

    List<CustomerDTO> listCustomers();

    void deleteCustomerByID(String id);
}

