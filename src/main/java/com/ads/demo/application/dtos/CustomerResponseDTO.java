package com.ads.demo.application.dtos;

import java.time.LocalDateTime;

public record CustomerResponseDTO(String id,
                                  String name,
                                  Integer age,
                                  LocalDateTime dateTimeLastUpdate
                                 ) {
}
