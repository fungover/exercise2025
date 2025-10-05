package org.example.exception;

import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.example.dto.PetResponseDTO;

@Provider
public class PetStateExceptionMapper implements ExceptionMapper<PetStateException> {
    @Override
    public Response toResponse(PetStateException e) {
        return Response.status(Response.Status.CONFLICT)
                .entity(new PetResponseDTO(e.getMessage()))
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}
