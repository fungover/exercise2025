package org.example.exceptionmappers;

import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.jboss.logging.Logger;

@Provider
public class NotFoundExceptionMapper implements ExceptionMapper<NotFoundException> {
    Logger logger = Logger.getLogger(NotFoundExceptionMapper.class);

    @Override
    public Response toResponse(NotFoundException e) {
        logger.error(e.getLocalizedMessage());
        String json = String
                .format("{\"error\":\"Kunde inte hitta resursen\",\"detaljer\":\"%s\"}",
                        e.getMessage());

        return Response.status(Response.Status.NOT_FOUND)
                .entity(json)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}
