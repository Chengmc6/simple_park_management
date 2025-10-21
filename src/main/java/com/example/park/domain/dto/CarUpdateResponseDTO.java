package com.example.park.domain.dto;

import lombok.Data;

@Data
public class CarUpdateResponseDTO {
    private Long id;
    private String carNumber;
    private Integer status;
}
