package org.example.exception;

import jakarta.json.bind.JsonbException;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class GenericExceptionMapper implements ExceptionMapper<Throwable> {

    @Context
    UriInfo uri;

    @Override
    public Response toResponse(Throwable ex) {
        ex.printStackTrace();

        Throwable c = ex;
        while (c != null) {
            if (c instanceof JsonbException) {
                String msg = c.getMessage() != null ? c.getMessage() : "Invalid JSON";
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
            c = c.getCause();
        }

        // 500-svar
        ErrorResponse body = new ErrorResponse()
                .status(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode())
                .error("Internal Server Error")
                .message("Unexpected error")
                .path(uri != null ? uri.getRequestUri().getPath() : null);

        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .type(MediaType.APPLICATION_JSON)
                .entity(body)
                .build();
    }
}
