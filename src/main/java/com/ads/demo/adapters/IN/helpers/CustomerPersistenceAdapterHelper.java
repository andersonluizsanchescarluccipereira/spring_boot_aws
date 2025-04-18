package com.ads.demo.adapters.IN.helpers;

import com.ads.demo.application.dtos.CustomerDTO;
import com.ads.demo.application.models.entity.CustomerEntity;

public class CustomerPersistenceAdapterHelper {
    protected CustomerPersistenceAdapterHelper() throws IllegalAccessException {
        throw new IllegalAccessException("Utility class");
    }

    public static CustomerEntity convertDTOToEntity(CustomerDTO customerDTO) {
      return new CustomerEntity(
              customerDTO.id(),
              customerDTO.name(),
              customerDTO.age()
      );
    }
    public static CustomerDTO convertEntityToDTO(CustomerEntity customerEntity) {
        return new CustomerDTO(
                customerEntity.getId(),
                customerEntity.getName(),
                customerEntity.getAge()
        );
    }
}
