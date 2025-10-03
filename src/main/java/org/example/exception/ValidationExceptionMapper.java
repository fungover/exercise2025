package org.example.exception;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Provider
public class ValidationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {
    @Override
    public Response toResponse(ConstraintViolationException exception) {
        Map<String, String> errors = exception.getConstraintViolations()
                .stream()
                .collect(Collectors.toMap(
                        cv -> {
                            String path = cv.getPropertyPath().toString();
                            if (path.contains(".")) {
                                path = path.substring(path.lastIndexOf(".") + 1);
                            }
                            return path;
                        },
                        cv -> cv.getMessage(),
                        (msg1, msg2) -> msg1,
                        LinkedHashMap::new
                ));

        return Response.status(Response.Status.BAD_REQUEST)
                .entity(errors)
                .build();
    }
}
