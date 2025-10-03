//package org.example.submissions;
//
//import jakarta.validation.ConstraintViolationException;
//import jakarta.ws.rs.core.Response;
//import jakarta.ws.rs.ext.ExceptionMapper;
//import jakarta.ws.rs.ext.Provider;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Provider
//public class ValidationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {
//
//    @Override
//    public Response toResponse(ConstraintViolationException ex) {
//        List<String> errors = ex.getConstraintViolations().stream()
//            .map(v -> v.getPropertyPath() + ": " + v.getMessage())
//            .collect(Collectors.toList());
//
//        return Response.status(Response.Status.BAD_REQUEST)
//                       .entity(errors)
//                       .build();
//    }
//}