package com.example.park.domain.dto;

import lombok.Data;

@Data
public class UsageRequestDTO {
    private Long carId;
    private Integer pageNum=1;
    private Integer pageSize=20;
}
