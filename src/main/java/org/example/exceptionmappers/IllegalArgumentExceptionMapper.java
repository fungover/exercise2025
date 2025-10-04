package org.example.exceptionmappers;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.example.validation.ErrorResponse;

@Provider
public class IllegalArgumentExceptionMapper implements ExceptionMapper<IllegalArgumentException> {
  @Override
  public Response toResponse(IllegalArgumentException e) {
    ErrorResponse errorResponse = new ErrorResponse(
            e.getMessage(),
            Response.Status.BAD_REQUEST.getStatusCode()
    );

    return Response.status(Response.Status.BAD_REQUEST)
            .entity(errorResponse)
            .build();
  }
}
