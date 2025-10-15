package org.example.exceptionmappers;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.json.bind.JsonbException;
import jakarta.ws.rs.ProcessingException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.jboss.logging.Logger;

import java.util.Map;

@Provider
public class ProcessingExceptionMapper implements ExceptionMapper<ProcessingException> {
    Logger logger = Logger.getLogger(ProcessingExceptionMapper.class);

    @Override
    public Response toResponse(ProcessingException e) {
        logger.error(e.getMessage());
        Jsonb jsonb = JsonbBuilder.create();
        String json;
        try {
            json = jsonb.toJson(Map.of(
                    "error", "Kunde inte processera data",
                    "detaljer", e.getMessage()
            ));
        } catch (JsonbException ex) {
            throw new RuntimeException(ex);
        }

        return Response.status(Response.Status.BAD_REQUEST)
                .entity(json)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}
