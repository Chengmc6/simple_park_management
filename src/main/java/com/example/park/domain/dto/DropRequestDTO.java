package com.example.park.domain.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class DropRequestDTO {
    private Long carId;
    private Long userId;
    private LocalDateTime dropTime;
    private BigDecimal dropAlcoholLevel;
}
