package org.example.mapper;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.example.exception.PetNotFoundException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Returns 404 Not Found with a JSON error message.
 */
@Provider
public class PetNotFoundExceptionMapper implements ExceptionMapper<PetNotFoundException> {

    @Override
    public Response toResponse(PetNotFoundException exception) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("status", 404);
        errorResponse.put("error", "Not Found");
        errorResponse.put("message", exception.getMessage());
        errorResponse.put("petId", exception.getPetId());
        errorResponse.put("timestamp", LocalDateTime.now().toString());

        return Response
                .status(Response.Status.NOT_FOUND)
                .entity(errorResponse)
                .build();
    }
}
