package org.example.exceptionmappers;

import jakarta.ws.rs.ProcessingException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.jboss.logging.Logger;

@Provider
public class ProcessingExceptionMapper implements ExceptionMapper<ProcessingException> {
    Logger logger = Logger.getLogger(ProcessingExceptionMapper.class);

    @Override
    public Response toResponse(ProcessingException e) {
        logger.error(e.getMessage());
        String json = String
                .format("{\"error\":\"Kunde inte processera data\",\"detaljer\":\"%s\"}",
                        e.getMessage());

        return Response.status(Response.Status.BAD_REQUEST)
                .entity(json)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}
