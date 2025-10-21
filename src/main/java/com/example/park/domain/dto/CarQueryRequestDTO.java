package com.example.park.domain.dto;

import lombok.Data;

@Data
public class CarQueryRequestDTO {

    private String carNumber;

    private Integer pageNum=1;

    private Integer pageSize=20;
}
