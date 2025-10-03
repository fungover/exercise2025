package org.example.exception;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Provider // Registers this class as a provider for JAX-RS, making it discoverable by the framework.
public class GenericExceptionMapper implements ExceptionMapper<Throwable> { // Implements ExceptionMapper to handle all uncaught exceptions

    @Override
    public Response toResponse(Throwable e) {
        Map<String, Object> error = new HashMap<>();
        error.put("type", "InternalServerError");

        List<Map<String, String>> details = new ArrayList<>();
        Map<String, String> detail = new HashMap<>();
        detail.put("message", e.getMessage() != null ? e.getMessage() : "Unexpected error");
        details.add(detail);

        error.put("details", details);

        Map<String, Object> response = new HashMap<>();
        response.put("error", error);

        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(response)
                .build();
    }
}
