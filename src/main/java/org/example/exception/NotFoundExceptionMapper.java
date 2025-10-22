package org.example.exception;

import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class NotFoundExceptionMapper implements ExceptionMapper<NotFoundException> {

    @Context
    UriInfo uri;

    @Override
    public Response toResponse(NotFoundException ex) {
        ErrorResponse body = new ErrorResponse()
                .status(Response.Status.NOT_FOUND.getStatusCode())
                .error("Not Found")
                .message(ex.getMessage())
                .path(uri != null ? uri.getRequestUri().getPath() : null);

        return Response.status(Response.Status.NOT_FOUND)
                .type(MediaType.APPLICATION_JSON)
                .entity(body)
                .build();
    }
}