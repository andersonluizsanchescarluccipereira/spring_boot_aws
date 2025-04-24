package com.ads.demo.adapters.in;

import com.ads.demo.adapters.in.helpers.ControllerBaseHelper;
import com.ads.demo.adapters.in.helpers.CustomerResponseAdapterHelper;
import com.ads.demo.application.dtos.CustomerDTO;
import com.ads.demo.application.dtos.CustomerResponseDTO;
import com.ads.demo.application.facade.CustomerFacade;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Tag(name = "Customers", description = "Customer opeartions relation")
@RestController
@RequestMapping("/v1/customers")
public class CustomerControllerAdapter extends ControllerBaseHelper {
    private final CustomerFacade service;

    public CustomerControllerAdapter(CustomerFacade service) {
        this.service = service;
    }
    @Operation(summary = "Created or update customer")
    @PostMapping
    public ResponseEntity<CustomerResponseDTO> putCustomer(@RequestBody CustomerDTO customerDTO) {
        try {
            CustomerDTO salva = service.registerCustomer(customerDTO);
            CustomerResponseDTO customerResponseDTO = CustomerResponseAdapterHelper.convertDTOToResponseDTO(salva);
            logger.info("putCustomer " + customerResponseDTO);
            return ok(customerResponseDTO);
        } catch (Exception e) {
            logger.error("Error on putCustomer {}", customerDTO, e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @Operation(summary = "Search customer by ID")
    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponseDTO> searchCustomerByID(@PathVariable String id) {
        try {
            CustomerDTO encontrada = service.findCustomer(id);
            CustomerResponseDTO customerResponseDTO = CustomerResponseAdapterHelper.convertDTOToResponseDTO(encontrada);
            logger.info("searchCustomerByID " + customerResponseDTO);
            return ok(customerResponseDTO);
        } catch (Exception e) {
            logger.error("Error on searchCustomerByID {}", id, e);
            return ResponseEntity.internalServerError().build();
        }
    }
    @Operation(summary = "Get list customers")
    @GetMapping
    public ResponseEntity<List<CustomerResponseDTO>> listCustomers() {
        try {
            List<CustomerDTO> pessoas = service.getAllCustomers();
            List<CustomerResponseDTO> listCustomerResponseDTO = StreamSupport
                    .stream(pessoas.spliterator(), false)
                    .map(CustomerResponseAdapterHelper::convertDTOToResponseDTO)
                    .collect(Collectors.toList());
            logger.info("listCustomers " + listCustomerResponseDTO);
            return ok(listCustomerResponseDTO);
        } catch (Exception e) {
            logger.error("Error on listCustomers {}", "listCustomers", e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @Operation(summary = "Delete customer by ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomerByID(@PathVariable String id) {
        try {
            logger.info("deleteCustomerByID " + id);
            service.removeCustomer(id);
            return noContent();
        } catch (Exception e) {
            logger.error("Error on listCustomers {}", "listCustomers", e);
            return ResponseEntity.internalServerError().build();
        }
    }
}