package org.example.exception;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Provider // Registers this class as a provider for JAX-RS, this makes it discoverable by the framework.
public class ValidationExceptionMapper implements ExceptionMapper<ConstraintViolationException> { // Implements ExceptionMapper to handle ConstraintViolationException

    @Override
    public Response toResponse(ConstraintViolationException e) {
        List<Map<String, String>> errors = new ArrayList<>();

        // Collect all validation errors
        for (ConstraintViolation<?> violation : e.getConstraintViolations()) {
            Map<String, String> error = new HashMap<>(); // Create a map for each error
            error.put("field", violation.getPropertyPath().toString()); // Field that caused the violation
            error.put("message", violation.getMessage()); // Violation message
            errors.add(error); // Add the error map to the list
        }

        Map<String, Object> response = new HashMap<>(); // Create a response map
        response.put("errors", errors); // Add the list of errors to the response
        return Response.status(Response.Status.BAD_REQUEST) // 400 Bad Request
                .entity(response) // Set the response body to the error details
                .build();
    }
}
