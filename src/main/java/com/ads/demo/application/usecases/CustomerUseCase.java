package com.ads.demo.application.usecases;

import com.ads.demo.application.dtos.CustomerDTO;
import com.ads.demo.application.ports.CustomerINPort;
import com.ads.demo.application.ports.CustomerPersistencePort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerUseCase implements CustomerINPort {

    private static final Logger logger = LoggerFactory.getLogger(CustomerUseCase.class);
    private final CustomerPersistencePort repository;

    public CustomerUseCase(CustomerPersistencePort repository) {
        this.repository = repository;
    }

    @Override
    public CustomerDTO putCustomer(CustomerDTO customerDTO) {
        logger.info("putCustomerUseCase " + customerDTO);
        return repository.putCustomer(customerDTO);
    }

    @Override
    public CustomerDTO searchCustomerByID(String id) {
        logger.info("searchCustomerByIDUseCase " + id);
        return repository.searchCustomerByID(id);
    }

    @Override
    public List<CustomerDTO> listCustomers() {
        logger.info("listCustomersUseCase ");
        return repository.listCustomers();
    }

    @Override
    public void deleteCustomerByID(String id) {
        logger.info("deleteCustomerByID " + id);
        repository.deleteCustomerByID(id);
    }
}