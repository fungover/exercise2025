package org.example.mapper;

import jakarta.validation.ValidationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import java.util.HashMap;
import java.util.Map;

@Provider
public class ValidationExceptionMapper implements ExceptionMapper<ValidationException> {

    @Override
    public Response toResponse(ValidationException exception) {
        Map<String, Object> error = new HashMap<>();
        error.put("error", "Validation failed");
        error.put("details", exception.getMessage());

        return Response.status(Response.Status.BAD_REQUEST)
                .entity(error)
                .build();
    }
}
