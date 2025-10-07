package org.example.exeption;

import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.util.List;
import java.util.stream.Collectors;

@Provider
public class ConstraintViolationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {

    @Override public Response toResponse(ConstraintViolationException e) {
        List<String> errors = e.getConstraintViolations()
                               .stream()
                               .map(
                                 cv -> cv.getPropertyPath() + " " + cv.getMessage())
                               .collect(Collectors.toList());
        return Response.status(Response.Status.BAD_REQUEST)
                       .entity(new ErrorResponse(errors))
                       .type(MediaType.APPLICATION_JSON)
                       .build();
    }

    public static record ErrorResponse(List<String> errors) {}
}
