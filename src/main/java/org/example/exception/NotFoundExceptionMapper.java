package org.example.exception;

import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

/**
 * NotFoundExceptionMapper converts JAX-RS NotFoundException
 * into a JSON response with status 404 Not Found.
 */
@Provider
public class NotFoundExceptionMapper implements ExceptionMapper<NotFoundException> {

    @Override
    public Response toResponse(NotFoundException exception) {
        String json = "{ \"error\": \"Resource not found\" }";

        return Response.status(Response.Status.NOT_FOUND)
                .entity(json)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}
