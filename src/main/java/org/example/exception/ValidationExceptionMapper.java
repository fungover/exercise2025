package org.example.exception;

import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Path;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.util.List;

@Provider
public class ValidationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {

    @Override
    public Response toResponse(ConstraintViolationException e) {
        List<ErrorDetail> violations = e.getConstraintViolations().stream()
                .map(v -> new ViolationMessage(
                        extractFieldName(v.getPropertyPath()),
                        v.getMessage()))
                .map(v -> (ErrorDetail) v)
                .toList();

        ErrorResponse errorResponse = new ErrorResponse("ValidationError", violations);

        return Response.status(Response.Status.BAD_REQUEST)
                .entity(errorResponse)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }

    private String extractFieldName(Path propertyPath) {
        String fieldName = "";
        for (Path.Node node : propertyPath) {
            fieldName = node.getName();
        }
        return fieldName;
    }
}
