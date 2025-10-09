package org.example.api;

import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.*;
import jakarta.ws.rs.ext.*;

@Provider
@Produces(MediaType.APPLICATION_JSON)
public class GenericExceptionMapper implements ExceptionMapper<Throwable> {
    @Override
    public Response toResponse(Throwable ex) {
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(new ErrorResponse("Internal Server Error", ex.getMessage()))
                .build();
    }
}
