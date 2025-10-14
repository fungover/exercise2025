package org.example.exception;

import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.util.Map;

/**
 * For a nice 404 return in JSON, application/problem+json is a standard media type in a REST API
 * (Problem Details for HTTP APIs) This makes it easier for the user to understand what went wrong and why
 **/

@Provider
public class NotFoundExceptionMapper implements ExceptionMapper<NotFoundException> {
    @Override
    public Response toResponse(NotFoundException e) {
        var payload = Map.of(
                "status", 404,
                "title", "Not found",
                "detail", e.getMessage() != null ? e.getMessage() : "Resource not found"
        );

        return Response.status(Response.Status.NOT_FOUND)
                .type("application/problem+json")
                .entity(payload)
                .build();
    }
}
