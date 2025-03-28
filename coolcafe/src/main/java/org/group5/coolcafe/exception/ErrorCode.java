package org.group5.coolcafe.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

public enum ErrorCode {
    USER_EXISTED(400,"User existed", HttpStatus.BAD_REQUEST),
    USER_NOT_EXISTED(400,"User not existed",HttpStatus.NOT_FOUND),
    PASSWORD_INVALID(400,"Password must be at least 10",HttpStatus.BAD_REQUEST),
    KEY_INVALID(400,"Key invalid",HttpStatus.BAD_REQUEST),
    UNAUTHORIZED(401,"Unauthorized",HttpStatus.UNAUTHORIZED),
    FORBIDDEN(403,"Forbidden",HttpStatus.FORBIDDEN),
    NOT_FOUND(404,"Not Found",HttpStatus.NOT_FOUND),
    UNCATEGORIZED(500,"Error not defined",HttpStatus.INTERNAL_SERVER_ERROR),
    PASSWORD_EXISTED(400,"Password already existed",HttpStatus.CONFLICT),
    EMAIL_EXISTED(400,"Email already existed",HttpStatus.CONFLICT),
    USERNAME_EXISTED(400,"Username already existed",HttpStatus.CONFLICT),
    PHONENUMBER_EXISTED(400,"Phone number already existed",HttpStatus.CONFLICT),
    OTP_INVALID(400,"OTP invalid",HttpStatus.CONFLICT),
    NOT_ENOUGH_QUANTITY(400,"Not enough quantity!",HttpStatus.BAD_REQUEST),
    TIME_CANT_NOT_BE_NULL(400,"Must be enter time!",HttpStatus.BAD_REQUEST),
    TABLE_NOT_FOUND(404,"Table not exist!",HttpStatus.NOT_FOUND),
    ;
    private int code;
    private String message;
    private HttpStatusCode httpStatusCode;

    ErrorCode(int code, String message,HttpStatusCode httpStatusCode) {
        this.code = code;
        this.message = message;
        this.httpStatusCode = httpStatusCode;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public HttpStatusCode getHttpStatusCode() {
        return httpStatusCode;
    }

    public void setHttpStatusCode(HttpStatusCode httpStatusCode) {
        this.httpStatusCode = httpStatusCode;
    }
}