package com.example.park.common;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * APIレスポンス結果を統一
 * 
 * @author 高明(コウメイ)
 * @since 2025-10-15
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse<T> {
    private int code;
    private String message;
    private T data;

    //success response with data
    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(200, "Success", data);
    }

    //success response
    public static <T> ApiResponse<T> success(){
        return new ApiResponse<>(200,"Success",null);
    }

    //success response with data and message
    public static <T> ApiResponse<T> success(T data,String message){
        return new ApiResponse<>(200,message,data);
    }
    //error with notice
    public static <T> ApiResponse<T> error(String message){
        return new ApiResponse<>(500,message,null);
    }
    //error with code and notice
    public static <T> ApiResponse<T> error(int code,String message){
        return new ApiResponse<>(code,message,null);
    }
    public static <T> ApiResponse<T> error(HttpStatus status,String message){
        return new ApiResponse<>(status.value(), message, null);
    }
    //error with user_defined_code
    public static <T> ApiResponse<T> error(ResultCode resultCode){
        return new ApiResponse<>(resultCode.getCode(),resultCode.getMessage(),null);
    }
}
