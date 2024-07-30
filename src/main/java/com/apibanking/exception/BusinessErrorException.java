package com.apibanking.exception;

import jakarta.ws.rs.core.Response.Status;

public class BusinessErrorException extends RuntimeException  {
    private final String detail;
    private final Status statusCode;

    public BusinessErrorException(String detail, Status statusCode) {
        super(statusCode.name());
        this.detail = detail;
        this.statusCode = statusCode;
    }

    public String getDetail() {
        return detail;
    }
    public Status getStatusCode() {
        return statusCode;
    }
}
