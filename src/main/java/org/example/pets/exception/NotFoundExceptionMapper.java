package org.example.pets.exception;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;

@Provider
public class NotFoundExceptionMapper implements ExceptionMapper<NotFoundException> {

    @Override
    public Response toResponse(NotFoundException exception) {
        JsonObject response = Json.createObjectBuilder()
                .add("error", "Resource not found")
                .add("message", exception.getMessage())
                .build();

        return Response.status(Response.Status.NOT_FOUND)
                .entity(response)
                .build();
    }
}
