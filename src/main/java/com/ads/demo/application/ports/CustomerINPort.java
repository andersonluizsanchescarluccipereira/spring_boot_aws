package com.ads.demo.application.ports;

import com.ads.demo.application.dtos.CustomerDTO;

import java.util.List;

public interface CustomerINPort {
    CustomerDTO putCustomer(CustomerDTO customerEntity);

    CustomerDTO searchCustomerByID(String id);

    List<CustomerDTO> listCustomers();

    void deleteCustomerByID(String id);
}

