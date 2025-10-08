package org.example.exceptionMappers;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.jboss.logging.Logger;
import java.util.List;


@Provider
public class ValidationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {
    Logger logger = Logger.getLogger(ValidationExceptionMapper.class);

    @Override
    public Response toResponse(ConstraintViolationException e) {
        List<ViolationMessage> violations = e.getConstraintViolations()
                .stream()
                .map(cv -> {
                    String path = cv.getPropertyPath().toString();
                    String fieldName = path.substring(path.lastIndexOf('.') + 1);
                    return new ViolationMessage(fieldName, cv.getMessage());
                })
                /*.map(cv -> new ViolationMessage(cv.getPropertyPath()
                        .toString()
                        .substring(cv.getPropertyPath()
                                .toString()
                                .lastIndexOf(".")+1),
                        cv.getMessage()))*/
                        .toList();
        try(Jsonb jsonb = JsonbBuilder.create()){
            String json = jsonb.toJson(violations);
            logger.info(json);
            return Response.status(Response.Status.BAD_REQUEST).entity(json).build();
        } catch(Exception ex){
            logger.error("Failed to serialize violations", ex);
            throw new RuntimeException("Failed to serialize violations", ex);
        }
        /*Jsonb jsonb = JsonbBuilder.create();
        String json = jsonb.toJson(violations);
        logger.info(json);
        return Response.status(Response.Status.BAD_REQUEST).entity(json).build();*/
    }
    public static class ViolationMessage {
        public String field;
        public String message;

        public ViolationMessage(String field, String message) {
            this.field = field;
            this.message = message;
        }
    }
}
