package org.example.exception;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.ValidationException;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.util.*;
import java.util.stream.Collectors;

@Provider
public class ExceptionsMapper implements ExceptionMapper<Throwable> {

    @Override
    public Response toResponse(Throwable exception) {

        // Validation from @Valid
        if (exception instanceof ConstraintViolationException cve) {
            List<Map<String, String>> violations = cve.getConstraintViolations().stream()
                    .map(v -> Map.of("field", v.getPropertyPath().toString(), "message", v.getMessage()))
                    .toList();
            return build(Response.Status.BAD_REQUEST, "Validation failed", Map.of("violations", violations));
        }

        // Generic validation errors
        if (exception instanceof ValidationException || exception instanceof BadRequestException) {
            return build(Response.Status.BAD_REQUEST, "Bad request", Map.of("message", exception.getMessage()));
        }

        // Not found
        if (exception instanceof NotFoundException) {
            return build(Response.Status.NOT_FOUND, "Not found", Map.of("message", exception.getMessage()));
        }

        // Default fallback for any unhandled exception
        return build(Response.Status.INTERNAL_SERVER_ERROR, "Internal error", Map.of(
                "message", exception.getMessage() != null ? exception.getMessage() : "Unexpected server error"
        ));
    }

    private Response build(Response.Status status, String error, Map<String, Object> extra) {
        Map<String, Object> base = new LinkedHashMap<>();
        base.put("status", status.getStatusCode());
        base.put("error", error);
        base.putAll(extra);

        return Response.status(status)
                .type(MediaType.APPLICATION_JSON_TYPE)
                .entity(base)
                .build();
    }
}