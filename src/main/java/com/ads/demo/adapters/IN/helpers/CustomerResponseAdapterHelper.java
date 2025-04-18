package com.ads.demo.adapters.IN.helpers;

import com.ads.demo.application.dtos.CustomerDTO;
import com.ads.demo.application.dtos.CustomerResponseDTO;

import java.time.LocalDateTime;

public class CustomerResponseAdapterHelper {
    protected CustomerResponseAdapterHelper() throws IllegalAccessException {
        throw new IllegalAccessException("Utility Response class");
    }

    public static CustomerDTO convertResponseDTOToDTO(CustomerResponseDTO customerResponseDTO){
        return new CustomerDTO(
                customerResponseDTO.id(),
                customerResponseDTO.name(),
                customerResponseDTO.age()
        );
    }

    public static CustomerResponseDTO convertDTOToResponseDTO(CustomerDTO customerDTO) {
        return new CustomerResponseDTO(
                customerDTO.id(),
                customerDTO.name(),
                customerDTO.age(),
                LocalDateTime.now()
        );
    }

}
