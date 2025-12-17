package com.example.park.advice;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.example.park.common.ApiResponse;
import com.example.park.common.ResultCode;
import com.example.park.common.SystemException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    // 1. 处理业务系统异常 (SystemException)
    @ExceptionHandler(SystemException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST) // 业务错误通常返回 400
    public ApiResponse<String> handleSystemExceptions(SystemException se){
        logger.warn("System Exception caught: Code={}, Message={}", se.getCode(), se.getMessage());
        return ApiResponse.error(se.getCode(), se.getMessage());
    }

    // 2. 处理参数校验失败 (MethodArgumentNotValidException)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST) // 参数校验失败返回 400
    public ApiResponse<String> handleIllegalArgumentException(MethodArgumentNotValidException ex){
        String message= Optional.of(ex.getBindingResult().getFieldError())
                .map(FieldError::getDefaultMessage)
                .orElse("参数校验失败");

        logger.warn("Validation Error: {}", message);
        // 使用 ResultCode.VALIDATION_ERROR 提供的错误码
        return ApiResponse.error(ResultCode.VALIDATION_ERROR.getCode(), message);
    }

    // 3. 处理 404 Not Found (防止 404 触发内部转发循环)
    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiResponse<Void> handleNotFoundException(NoHandlerFoundException ex) {
        logger.warn("404 Not Found: {}", ex.getRequestURL());
        return ApiResponse.error(HttpStatus.NOT_FOUND.value(), "请求的资源不存在。");
    }

    // 4. 处理所有未被捕获的其它异常 (Exception.class)
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR) // 服务器内部错误返回 500
    public ApiResponse<String> handleException(Exception ex){
        // 保持打印堆栈信息以便调试
        ex.printStackTrace();
        logger.error("Unhandled Internal Server Error: {}", ex.getMessage(), ex);
        // 使用 ResultCode.SERVER_ERROR 提供的错误码
        return ApiResponse.error(ResultCode.SERVER_ERROR);
    }
}
