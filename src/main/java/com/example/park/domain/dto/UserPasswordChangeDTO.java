package com.example.park.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserPasswordChangeDTO {

    @NotBlank(message = "旧パスワードを入力してください")
    private String oldPassword;

    @NotBlank(message = "新パスワードを入力してください")
    @Size(min = 6,max = 18,message = "パスワードは6~18文で入力してください")
    private String newPassword;
}
