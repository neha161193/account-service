package com.apibanking.exception;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class BusinessErrorExceptionMapper implements ExceptionMapper<BusinessErrorException> {
    @Override
    public Response toResponse(BusinessErrorException exception) {
        return Response.status(Response.Status.BAD_REQUEST)
                       .entity(new ErrorResponse(exception.getMessage(), exception.getDetail()))
                       .build();
    }
}
