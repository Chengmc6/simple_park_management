package com.example.park.domain.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class UsageResponseVO {
    private String carNumber;
    private String username;
    private LocalDateTime rideTime;
    private BigDecimal rideAlcoholLevel;
    private LocalDateTime dropTime;
    private BigDecimal dropAlcoholLevel;
}
