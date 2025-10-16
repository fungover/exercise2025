package org.example.exceptionmappers;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.json.bind.JsonbException;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.jboss.logging.Logger;

import java.util.Map;

@Provider
public class NotFoundExceptionMapper implements ExceptionMapper<NotFoundException> {
    Logger logger = Logger.getLogger(NotFoundExceptionMapper.class);

    @Override
    public Response toResponse(NotFoundException e) {
        logger.error(e.getLocalizedMessage());
        String json;
        try (Jsonb jsonb = JsonbBuilder.create()) {
            json = jsonb.toJson(Map.of(
                    "error", "Kunde inte hitta resursen",
                    "detaljer", e.getMessage()
            ));
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }

        return Response.status(Response.Status.NOT_FOUND)
                .entity(json)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}
