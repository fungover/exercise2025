package org.example.exception;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Provider
public class ValidationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {
    @Override
    public Response toResponse(ConstraintViolationException e) {
        List<Object> violations = e.getConstraintViolations()
                .stream()
                .map(cv -> new Object() {
                    public final String field = cv.getPropertyPath().toString()
                            .replaceAll("^.*\\.", "");
                    public final String message = cv.getMessage();
                })
                .collect(Collectors.toList());

        Jsonb jsonb = JsonbBuilder.create();
        String json = jsonb.toJson(new Object() {
            public final List<Object> violationsList = violations;
        });

        return Response.status(Response.Status.BAD_REQUEST)
                .entity(json)
                .type("application/json")
                .build();
    }

}
