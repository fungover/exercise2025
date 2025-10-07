package org.example.exception;

import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.util.Map;

@Provider
public class IllegalArgumentExceptionMapper implements ExceptionMapper<IllegalArgumentException> {
    @Override
    public Response toResponse(IllegalArgumentException e) {
        final String message = e.getMessage() != null ? e.getMessage() : "Bad request";
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(Map.of("error", message))
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}
