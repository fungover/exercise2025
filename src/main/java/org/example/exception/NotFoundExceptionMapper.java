package org.example.exception;

import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Provider // Registers this class as a provider for JAX-RS, making it discoverable by the framework.
public class NotFoundExceptionMapper implements ExceptionMapper<NotFoundException> { // Implements ExceptionMapper to handle NotFoundException

    @Override
    public Response toResponse(NotFoundException e) {
        Map<String, Object> error = new HashMap<>();
        error.put("type", "NotFoundError");

        List<Map<String, String>> details = new ArrayList<>();
        Map<String, String> detail = new HashMap<>();
        detail.put("message", e.getMessage());
        details.add(detail);

        error.put("details", details);

        Map<String, Object> response = new HashMap<>();
        response.put("error", error);

        return Response.status(Response.Status.NOT_FOUND)
                .entity(response)
                .build();
    }
}
