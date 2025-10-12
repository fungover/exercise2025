package org.example.exceptionmappers;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
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
						.map(v -> {
							String field = v.getPropertyPath().toString();
							field = field.substring(field.lastIndexOf('.') + 1);

							String message = v.getMessage();
							Object allowed = v.getConstraintDescriptor().getAttributes().get("allowed");

							return new ViolationMessage(field, message, allowed);
						})
						.collect(Collectors.toList());

		Jsonb jsonb = JsonbBuilder.create();
		String json = jsonb.toJson(new Violations(violations));
		logger.info(json);

		return Response.status(Response.Status.BAD_REQUEST)
						.entity(json)
						.type("application/json")
						.build();
	}

	public static class ViolationMessage {
		public String message;
		public String field;
		public Object allowed;

		public ViolationMessage(String field, String message, Object allowed) {
			this.message = message;
			this.field = field;
			this.allowed = allowed;
		}
	}

	public record Violations(List<ViolationMessage> violations) {}
}
