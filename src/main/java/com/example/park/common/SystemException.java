package com.example.park.common;

import lombok.Getter;

@Getter
public class SystemException extends RuntimeException{
    private int code;

    public SystemException(int code,String message){
        super(message);
        this.code=code;
    }

    public SystemException(ResultCode resultCode){
        super(resultCode.getMessage());
        this.code=resultCode.getCode();
    }
}
