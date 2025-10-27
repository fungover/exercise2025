package org.example.exception;

import jakarta.json.bind.JsonbException;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class BadRequestExceptionMapper implements ExceptionMapper<BadRequestException> {

    @Context
    UriInfo uri;

    @Override
    public Response toResponse(BadRequestException ex) {
        String msg = ex.getMessage();
        if (ex.getCause() instanceof JsonbException je && je.getMessage() != null) {
            msg = "Malformed JSON: " + je.getMessage();
        }
        ErrorResponse body = new ErrorResponse()
                .status(Response.Status.BAD_REQUEST.getStatusCode())
                .error("Bad Request")
                .message(msg != null ? msg : "Invalid request")
                .path(uri != null ? uri.getRequestUri().getPath() : null);

        return Response.status(Response.Status.BAD_REQUEST)
                .type(MediaType.APPLICATION_JSON)
                .entity(body)
                .build();
    }
}
