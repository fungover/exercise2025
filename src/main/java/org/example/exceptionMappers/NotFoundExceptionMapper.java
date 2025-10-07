package org.example.exceptionMappers;

import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import java.util.HashMap;
import java.util.Map;

@Provider
public class NotFoundExceptionMapper implements ExceptionMapper<NotFoundException> {
    @Override
    public Response toResponse(NotFoundException e) {
        Map<String, Object> error = new HashMap<>();
        error.put("error", "Not found");
        error.put("message", e.getMessage());
        return Response.status(Response.Status.NOT_FOUND).entity(error).build();
    }
}
