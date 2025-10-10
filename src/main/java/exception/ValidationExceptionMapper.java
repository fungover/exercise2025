package exception;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

// Exception mapper to handle Bean Validation errors
// Converts ConstraintViolationException into user-friendly JSON responses
@Provider
public class ValidationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {

    @Override
    public Response toResponse(ConstraintViolationException exception) {
        // Collect all validation errors
        Map<String, List<String>> errors = new LinkedHashMap<>();

        // Iterate through each violation and extract field name and message
        for (ConstraintViolation<?> violation : exception.getConstraintViolations()) {
            String field = violation.getPropertyPath().toString();
            String message = violation.getMessage();
            errors.computeIfAbsent(field, key -> new ArrayList<>()).add(message);
        }

        // Build response body
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("status", 400);
        response.put("message", "Valideringsfel");
        response.put("errors", errors);

        // Return 400 Bad Request with error details
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(response)
                .build();
    }
}

