package org.example.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Constraint(validatedBy = {})
@Min(0)
@Max(10)
@NotNull
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidAmount {
	String message() default "Choose between 0 and 10";
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};
}
