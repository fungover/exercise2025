package org.example.exception;

import jakarta.json.bind.JsonbException;
import jakarta.ws.rs.ProcessingException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import jakarta.ws.rs.core.Context;

@Provider
public class ProcessingExceptionMapper implements ExceptionMapper<ProcessingException> {

    @Context
    UriInfo uri;

    @Override
    public Response toResponse(ProcessingException ex) {
        Throwable cause = ex.getCause();
        if (cause instanceof JsonbException) {
            String msg = cause.getMessage() != null ? cause.getMessage() : "Invalid JSON";
            ErrorResponse body = new ErrorResponse()
                    .status(Response.Status.BAD_REQUEST.getStatusCode())
                    .error("Bad Request")
                    .message("Malformed JSON: " + msg)
                    .path(uri != null ? uri.getRequestUri().getPath() : null);
            return Response.status(Response.Status.BAD_REQUEST)
                    .type(MediaType.APPLICATION_JSON)
                    .entity(body)
                    .build();
        }

        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .type(MediaType.APPLICATION_JSON)
                .entity(new ErrorResponse()
                        .status(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode())
                        .error("Internal Server Error")
                        .message("Unexpected error")
                        .path(uri != null ? uri.getRequestUri().getPath() : null))
                .build();
    }
}
