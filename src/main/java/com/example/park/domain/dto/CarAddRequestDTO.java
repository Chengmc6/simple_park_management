package com.example.park.domain.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CarAddRequestDTO {

    @NotBlank(message = "車輌番号を入力してください")
    private String carNumber;
}
