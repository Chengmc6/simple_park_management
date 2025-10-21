package com.example.park.common;

import lombok.Getter;
/**
 * レスポンスコードを定義
 * 
 * @author 高明(コウメイ)
 * @since 2025-10-15
 */
@Getter
public enum ResultCode {

    SUCCESS(200,"Success"),
    BAD_REQUEST(400,"Bad Request"),
    UNAUTHORIZED(401,"Unauthorized"),
    FORBIDDEN(403,"Forbidden"),
    NOT_FOUND(404,"Not Found"),
    VALIDATION_ERROR(422,"VALIDATION_ERROR"),
    SERVER_ERROR(500,"Server Error"),
    USERNAME_EXISTS(1001,"ユーザーは既に存在しています"),
    PASSWORD_ERROR(1002,"パスワードは間違いました"),
    USER_NOT_FOUND(1003,"ユーザーは存在しません"),
    PASSWORD_SAME(1004,"新しいパスワードと古いパスワードは同じではないでください"),
    ILLEGAL_PERMISSION(1005,"権限不足"),
    NOT_AVAILABLE(1006,"車輌使用不可");

    private Integer code;
    private String message;

    ResultCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
