package org.example.exceptionmappers;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.jboss.logging.Logger;

import java.util.List;
import java.util.stream.Collectors;

@Provider
public class ValidationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {
  Logger logger = Logger.getLogger(ValidationExceptionMapper.class);

  @Override
  public Response toResponse(ConstraintViolationException e) {
    List<ViolationMessage> violations = e.getConstraintViolations().stream()
            .map(v -> new ViolationMessage(
                    v.getPropertyPath().toString().substring( v.getPropertyPath().toString().lastIndexOf(".")+1), v.getMessage()))
            .collect(Collectors.toList());


    Jsonb jsonb = JsonbBuilder.create();
    String json = jsonb.toJson(new ErrorResponse(violations));
    logger.info(json);

    return Response.status(Response.Status.BAD_REQUEST)
            .entity(json)
            .type("application/json")
            .build();
  }
  public static class ViolationMessage {
    public String field;
    public String message;

    public ViolationMessage(String field, String message) {
      this.field = field;
      this.message = message;
    }
  }
  public record ErrorResponse(List<ViolationMessage> violations){}
}