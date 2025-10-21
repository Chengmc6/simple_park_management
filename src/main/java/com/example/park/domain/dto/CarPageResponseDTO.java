package com.example.park.domain.dto;

import lombok.Data;

@Data
public class CarPageResponseDTO {
    private Long id;
    private String carNumber;
    private Integer status;
    private Long currentUserId;
}
