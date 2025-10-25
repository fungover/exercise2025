package org.example.mapper;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.example.exception.InvalidPetOperationException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Returns 400 Bad Request with a JSON error message.
 */
@Provider
public class InvalidPetOperationExceptionMapper implements ExceptionMapper<InvalidPetOperationException> {

    @Override
    public Response toResponse(InvalidPetOperationException exception) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("status", 400);
        errorResponse.put("error", "Bad Request");
        errorResponse.put("message", exception.getMessage());
        errorResponse.put("operation", exception.getOperation());
        errorResponse.put("timestamp", LocalDateTime.now().toString());

        return Response
                .status(Response.Status.BAD_REQUEST)
                .entity(errorResponse)
                .build();
    }
}
