package org.example.pets.exception;

import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.util.Set;

@Provider
public class ValidationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {

    @Override
    public Response toResponse(ConstraintViolationException exception) {
        Set<ConstraintViolation<?>> violations = exception.getConstraintViolations();
        JsonArrayBuilder errors = Json.createArrayBuilder();

        for (ConstraintViolation<?> violation : violations) {
            errors.add(Json.createObjectBuilder()
                .add("field", violation.getPropertyPath().toString())
                .add("message", violation.getMessage()));
        }

        JsonObject response = Json.createObjectBuilder()
                .add("error", "Validation failed")
                .add("details", errors)
                .build();

        return Response.status(Response.Status.BAD_REQUEST).entity(response).build();
    }
}
