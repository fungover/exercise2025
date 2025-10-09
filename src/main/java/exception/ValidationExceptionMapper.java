package exception;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import java.util.HashMap;
import java.util.Map;

// Exception mapper to handle Bean Validation errors
// Converts ConstraintViolationException into user-friendly JSON responses
@Provider
public class ValidationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {

    @Override
    public Response toResponse(ConstraintViolationException exception) {
        // Collect all validation errors
        Map<String, String> errors = new HashMap<>();

        // Iterate through each violation and extract field name and message
        for (ConstraintViolation<?> violation : exception.getConstraintViolations()) {
            String field = violation.getPropertyPath().toString();
            String message = violation.getMessage();
            errors.put(field, message);
        }

        // Build response body
        Map<String, Object> response = new HashMap<>();
        response.put("status", 400);
        response.put("message", "Valideringsfel");
        response.put("errors", errors);

        // Return 400 Bad Request with error details
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(response)
                .build();
    }
}

