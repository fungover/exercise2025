package org.example.exception;

import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.util.List;

@Provider // Registers this class as a provider for JAX-RS, making it discoverable by the framework.
public class NotFoundExceptionMapper implements ExceptionMapper<NotFoundException> {

    @Override
    public Response toResponse(NotFoundException e) {
        ErrorResponse errorResponse = new ErrorResponse(
                "NotFoundError",
                List.of(new MessageDetail(
                        e.getMessage() != null ? e.getMessage() : "Resource not found"
                ))
        );

        return Response.status(Response.Status.NOT_FOUND)
                .entity(errorResponse)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}
