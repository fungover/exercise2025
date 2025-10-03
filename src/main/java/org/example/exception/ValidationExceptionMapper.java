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
        List<Map<String, String>> details = new ArrayList<>();


        for (ConstraintViolation<?> violation : e.getConstraintViolations()) {
            Map<String, String> errorDetail = new HashMap<>();
            errorDetail.put("field", violation.getPropertyPath().toString());
            errorDetail.put("message", violation.getMessage());
            details.add(errorDetail);
        }

        Map<String, Object> error = new HashMap<>();
        error.put("type", "ValidationError");
        error.put("details", details);

        Map<String, Object> response = new HashMap<>();
        response.put("error", error);

        return Response.status(Response.Status.BAD_REQUEST)
                .entity(response)
                .build();
    }
}
