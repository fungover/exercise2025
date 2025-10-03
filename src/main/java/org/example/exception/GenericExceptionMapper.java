package org.example.exception;

import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.util.List;

@Provider // Registers this class as a provider for JAX-RS, making it discoverable by the framework.
public class GenericExceptionMapper implements ExceptionMapper<Throwable> {

    @Override
    public Response toResponse(Throwable e) {
        ErrorResponse errorResponse = new ErrorResponse(
                "InternalServerError",
                List.of(new MessageDetail(
                        e.getMessage() != null ? e.getMessage() : "Unexpected error"
                ))
        );

        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(errorResponse)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}
