package org.example.exception;


import org.example.dto.ErrorResponse;

import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import java.util.List;

@Provider
public class GlobalExceptionMapper implements ExceptionMapper<Throwable> {

    @Override
    public Response toResponse(Throwable exception) {
        exception.printStackTrace();
        ErrorResponse err = new ErrorResponse(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(),
                "Internal server error",
                List.of(exception.getMessage() == null ? "Unexpected error" : exception.getMessage()));

        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .type(MediaType.APPLICATION_JSON)
                .entity(err)
                .build();
    }
}