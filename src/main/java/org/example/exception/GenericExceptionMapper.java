package org.example.exception;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.util.HashMap;
import java.util.Map;

@Provider // Registers this class as a provider for JAX-RS, making it discoverable by the framework.
public class GenericExceptionMapper implements ExceptionMapper<Throwable> { // Implements ExceptionMapper to handle all uncaught exceptions

    @Override
    public Response toResponse(Throwable e) {
        Map<String, String> error = new HashMap<>(); // Create a map for the error message
        error.put("error", "Internal server error"); // Generic error message
        error.put("details", e.getMessage()); // Add the exception message to the map

        return Response.status(Response.Status.INTERNAL_SERVER_ERROR) // 500 Internal Server Error
                .entity(error) // Set the response body to the error message
                .build();
    }
}
