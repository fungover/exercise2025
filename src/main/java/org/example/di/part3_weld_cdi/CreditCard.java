package org.example.di.part3_weld_cdi;

import jakarta.inject.Qualifier;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

// This is a qualifier annotation a label we can put on classes and constructor parameters
// to tell CDI (Weld) which implementation to inject
@Qualifier
@Retention(RUNTIME)
@Target({FIELD, TYPE, PARAMETER, METHOD})
public @interface CreditCard {
}
