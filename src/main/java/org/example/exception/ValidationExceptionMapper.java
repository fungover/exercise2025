package org.example.exception;

import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.util.List;

@Provider
public class ValidationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {

    @Override
    public Response toResponse(ConstraintViolationException e) {
        List<ViolationMessage> violations = e.getConstraintViolations().stream()
                .map(v -> new ViolationMessage(
                        v.getPropertyPath().toString()
                                .substring(v.getPropertyPath().toString().lastIndexOf(".") + 1),
                        v.getMessage()))
                .toList();

        ErrorResponse errorResponse = new ErrorResponse("ValidationError", violations);

        return Response.status(Response.Status.BAD_REQUEST)
                .entity(errorResponse)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}
