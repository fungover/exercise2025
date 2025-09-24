package org.example.qualifiers;

import jakarta.enterprise.util.AnnotationLiteral;
import jakarta.inject.Qualifier;

import java.io.Serial;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;

/**
 * A qualifier is a way to distinguish between different implementations of the same interface.
 * In this case, we have two implementations of MessageService: EmailMessageService and SmsMessageService.
 * By using the @Email qualifier, we can tell the CDI container which implementation to inject.
 */

@Qualifier
@Retention(RetentionPolicy.RUNTIME)
@Target({TYPE, METHOD, FIELD, PARAMETER})
public @interface Email {
    class Literal extends AnnotationLiteral<Email> implements Email {
        @Serial
        private static final long serialVersionUID = 1L;
    }
}
