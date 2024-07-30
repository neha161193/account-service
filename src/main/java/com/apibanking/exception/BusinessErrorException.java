package com.apibanking.exception;

public class BusinessErrorException extends RuntimeException  {
    private final String detail;

    public BusinessErrorException(String detail) {
        super("Business Validation Error : Bad Request");
        this.detail = detail;
    }

    public String getDetail() {
        return detail;
    }
}
