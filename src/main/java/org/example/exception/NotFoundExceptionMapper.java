package org.example.exception;

import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.util.HashMap;
import java.util.Map;

@Provider // Registers this class as a provider for JAX-RS, making it discoverable by the framework.
public class NotFoundExceptionMapper implements ExceptionMapper<NotFoundException> { // Implements ExceptionMapper to handle NotFoundException

    @Override
    public Response toResponse(NotFoundException e) {
        Map<String, String> error = new HashMap<>(); // Create a map for the error message
        error.put("error", e.getMessage()); // Add the exception message to the map

        return Response.status(Response.Status.NOT_FOUND) // 404 Not Found
                .entity(error) // Set the response body to the error message
                .build();
    }
}
