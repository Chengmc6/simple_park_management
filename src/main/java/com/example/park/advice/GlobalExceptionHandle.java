package com.example.park.advice;

import java.util.Optional;

import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.example.park.common.ApiResponse;
import com.example.park.common.ResultCode;
import com.example.park.common.SystemException;

@ControllerAdvice
public class GlobalExceptionHandle {

    @ExceptionHandler(SystemException.class)
    public ApiResponse<String> handleSystemException(SystemException ex){
        return ApiResponse.error(ex.getCode(),ex.getMessage());
    }

    public ApiResponse<String> handleIllegalArgumentException(MethodArgumentNotValidException ex){
        String message = Optional.ofNullable(ex.getBindingResult().getFieldError())
                         .map(FieldError::getDefaultMessage)
                         .orElse("参数校验失败");
        return ApiResponse.error(ResultCode.VALIDATION_ERROR.getCode(), message);
    }

    @ExceptionHandler(Exception.class)
    public ApiResponse<String> handleException(Exception ex) {
        ex.printStackTrace();//日志打印
        return ApiResponse.error(ResultCode.SERVER_ERROR.getCode(), ResultCode.SERVER_ERROR.getMessage());
    } 
}
