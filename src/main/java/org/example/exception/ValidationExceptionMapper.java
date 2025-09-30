package org.example.exception;

import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.util.stream.Collectors;

@Provider
public class ValidationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {
    @Override
    public Response toResponse(ConstraintViolationException exception) {
        String errorMessage = exception.getConstraintViolations()
                .stream()
                .map(cv -> {
                    String path = cv.getPropertyPath().toString();
                    if (path.contains(".")) {
                        path = path.substring(path.lastIndexOf(".") + 1);
                    }
                    return path + ": " + cv.getMessage();
                })
                .collect(Collectors.joining(", "));

        return Response.status(Response.Status.BAD_REQUEST)
                .entity("{\"error\": \"" + errorMessage + "\"}")
                .build();
    }
}
