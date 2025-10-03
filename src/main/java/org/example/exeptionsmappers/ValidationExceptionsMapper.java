package org.example.exeptionsmappers;

import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.jboss.logging.Logger;

@Provider
public class ValidationExceptionsMapper implements ExceptionMapper<ConstraintViolationException> {

  Logger logger = Logger.getLogger(ValidationExceptionsMapper.class);

  @Override
  public Response toResponse(ConstraintViolationException e) {
    logger.info(e.getMessage());
    return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
  }
}
