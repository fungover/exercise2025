package org.example.exception;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.util.List;

@Provider
public class ValidationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {

    @Context
    UriInfo uri;

    @Override
    public Response toResponse(ConstraintViolationException ex) {
        List<ErrorResponse.Violation> violations = ex.getConstraintViolations().stream()
                .map(this::toViolation)
                .toList();

        ErrorResponse body = new ErrorResponse()
                .status(Response.Status.BAD_REQUEST.getStatusCode())
                .error("Bad Request")
                .message("Validation failed")
                .path(uri != null ? uri.getRequestUri().getPath() : null)
                .violations(violations);

        return Response.status(Response.Status.BAD_REQUEST)
                .type(MediaType.APPLICATION_JSON)
                .entity(body)
                .build();
    }

    private ErrorResponse.Violation toViolation(ConstraintViolation<?> v) {
        String field = v.getPropertyPath() != null ? v.getPropertyPath().toString() : "";
        return new ErrorResponse.Violation(field, v.getMessage());
    }
}
