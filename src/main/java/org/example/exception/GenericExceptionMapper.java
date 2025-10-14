package org.example.exception;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.util.Map;

/**
 * Catches any unhandled server error (500) and returns JSON problem details
 **/

@Provider
public class GenericExceptionMapper implements ExceptionMapper<Throwable> {

    @Override
    public Response toResponse(Throwable e) {
        var body = Map.of(
                "status", 500,
                "title", "Internal Server Error",
                "detail", e.getMessage() != null ? e.getMessage() : "Unexpected error"
        );

        return Response.status(500)
                .type("application/problem+json")
                .entity(body)
                .build();
    }

}
