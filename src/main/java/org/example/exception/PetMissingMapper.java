package org.example.exception;


import org.example.dto.ErrorResponse;

import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.util.List;

@Provider
public class PetMissingMapper implements ExceptionMapper<NotFoundException> {

    @Override
    public Response toResponse(NotFoundException exception) {
        ErrorResponse err = new ErrorResponse(Response.Status.NOT_FOUND.getStatusCode(),
                exception.getMessage() == null ? "Not Found" : exception.getMessage(),
                List.of());

        return Response.status(Response.Status.NOT_FOUND)
                .type(MediaType.APPLICATION_JSON)
                .entity(err)
                .build();
    }
}
