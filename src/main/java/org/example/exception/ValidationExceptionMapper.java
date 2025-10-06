package org.example.exception;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;

/**
 * ValidationExceptionMapper converts Bean validation errors
 * into a JSON response with status 400 Bad Request.
 */
@Provider // Marks this class as a JAX-RS provider so it will be picked up automatically

public class ValidationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {

    @Override
    public Response toResponse(ConstraintViolationException exception) {
        // Collect all violation messages into a JSON array
                JsonArrayBuilder errorsArray = Json.createArrayBuilder();
                exception.getConstraintViolations()
                        .stream()
                        .map(ConstraintViolation::getMessage)
                        .forEach(errorsArray::add);

                String json = Json.createObjectBuilder()
                        .add("errors", errorsArray)
                        .build()
                        .toString();

        return Response.status(Response.Status.BAD_REQUEST)
                .entity(json)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}

