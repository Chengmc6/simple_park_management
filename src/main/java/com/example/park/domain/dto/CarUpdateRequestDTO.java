package com.example.park.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CarUpdateRequestDTO {
    @NotNull
    private Long id;
    @NotBlank(message = "車輌番号はNULLにすることはできません")
    private String carNumber;
}
