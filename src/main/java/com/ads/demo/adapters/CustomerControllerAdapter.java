package com.ads.demo.adapters;

import com.ads.demo.adapters.helpers.CustomerResponseAdapterHelper;
import com.ads.demo.application.dtos.CustomerDTO;
import com.ads.demo.application.dtos.CustomerResponseDTO;
import com.ads.demo.application.ports.CustomerINPort;
import io.swagger.v3.oas.annotations.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/v1/customers")
public class CustomerControllerAdapter {
    private static final Logger logger = LoggerFactory.getLogger(CustomerControllerAdapter.class);
    private final CustomerINPort service;

    public CustomerControllerAdapter(CustomerINPort service) {
        this.service = service;
    }
    @Operation(summary = "Created or update customer")
    @PostMapping
    public ResponseEntity<CustomerResponseDTO> putCustomer(@RequestBody CustomerDTO customerDTO) {
        CustomerDTO salva = service.putCustomer(customerDTO);
        CustomerResponseDTO customerResponseDTO = CustomerResponseAdapterHelper.convertDTOToResponseDTO(salva);
        logger.info("putCustomer " + customerResponseDTO);
        return ResponseEntity.ok(customerResponseDTO);
    }

    @Operation(summary = "Search customer by ID")
    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponseDTO> searchCustomerByID(@PathVariable String id) {
        CustomerDTO encontrada = service.searchCustomerByID(id);
        CustomerResponseDTO customerResponseDTO = CustomerResponseAdapterHelper.convertDTOToResponseDTO(encontrada);
        logger.info("searchCustomerByID " + customerResponseDTO);
        return ResponseEntity.ok(customerResponseDTO);
    }
    @Operation(summary = "Get list customers")
    @GetMapping
    public ResponseEntity<List<CustomerResponseDTO>> listCustomers() {
        List<CustomerDTO> pessoas = service.listCustomers();
        List<CustomerResponseDTO> listCustomerResponseDTO = StreamSupport
                .stream(pessoas.spliterator(), false)
                .map(CustomerResponseAdapterHelper::convertDTOToResponseDTO)
                .collect(Collectors.toList());
        logger.info("listCustomers " + listCustomerResponseDTO);
        return ResponseEntity.ok(listCustomerResponseDTO);
    }

    @Operation(summary = "Delete customer by ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomerByID(@PathVariable String id) {
        logger.info("deleteCustomerByID " + id);
        service.deleteCustomerByID(id);
        return ResponseEntity.noContent().build();
    }
}