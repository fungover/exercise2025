package org.example.qualifiers;

import jakarta.inject.Qualifier;
import jakarta.enterprise.util.AnnotationLiteral;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Qualifier
@Retention(RUNTIME)
@Target({TYPE, METHOD, FIELD, PARAMETER})
public @interface Simple {
    class Literal extends AnnotationLiteral<Simple> implements Simple {}
}
