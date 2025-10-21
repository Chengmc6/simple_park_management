package com.example.park.domain.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserLoginRequestDTO {
    @NotBlank(message = "ユーザー名を入力してください")
    private String username;
    @NotBlank(message = "パスワードを入力してください")
    private String password;
}
