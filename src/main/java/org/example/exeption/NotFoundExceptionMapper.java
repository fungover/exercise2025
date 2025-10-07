package org.example.exeption;

import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class NotFoundExceptionMapper implements ExceptionMapper<NotFoundException> {

    @Override public Response toResponse(NotFoundException e) {
        return Response.status(Response.Status.NOT_FOUND)
                       .entity(new ErrorMessage(e.getMessage()))
                       .type(MediaType.APPLICATION_JSON)
                       .build();
    }

    public static record ErrorMessage(String error) {}
}
