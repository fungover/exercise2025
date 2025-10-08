package exercise6.exceptionmapper;

import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class PetValidationExceptionMapper implements ExceptionMapper <ConstraintViolationException> {

    @Override
    public Response toResponse(ConstraintViolationException e) {
        var violations  = e.getConstraintViolations().stream()
                //KOLLA UPP getPropertyPath
                .map(a -> "{\"message\":\"" + a.getMessage()
                        .replace("\"", "\\\"") + "\"}")
                .collect(java.util.stream.Collectors.joining(",", "[", "]"));

        return Response.status(Response.Status.BAD_REQUEST)
                .entity(violations)
                .type("application/json")
                .build();
    }
}
