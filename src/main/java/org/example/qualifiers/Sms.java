package org.example.qualifiers;

import jakarta.enterprise.util.AnnotationLiteral;
import jakarta.inject.Qualifier;

import java.io.Serial;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Qualifier // Marks this annotation as a CDI qualifier
@Retention(RUNTIME) // Specifies that this annotation will be available at runtime
@Target({TYPE, METHOD, FIELD, PARAMETER}) // Specifies where this annotation can be applied
public @interface Sms {
    class Literal extends AnnotationLiteral<Sms> implements Sms {
        @Serial
        private static final long serialVersionUID = 1L; // Unique identifier for serialization
    }
}
