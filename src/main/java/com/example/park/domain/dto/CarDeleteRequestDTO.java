package com.example.park.domain.dto;

import java.util.List;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CarDeleteRequestDTO {

    @NotNull(message = "idはNULLにすることはできません")
    private List<Long> ids;
}
