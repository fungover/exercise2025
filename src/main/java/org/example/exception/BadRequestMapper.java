package org.example.exception;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.util.HashMap;
import java.util.Map;

@Provider
public class BadRequestMapper implements ExceptionMapper<BadRequestException> {

    @Override
    public Response toResponse(BadRequestException exception) {
        Map<String, String> error = new HashMap<>();
        error.put("message", exception.getMessage());

        return Response
                .status(Response.Status.BAD_REQUEST)
                .entity(error)
                .build();
    }
}
