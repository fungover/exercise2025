package org.example.di.notify;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import org.example.di.qualifiers.Email;

@ApplicationScoped
public class NotifierConfig {
    @Produces
    @Email
    public String emailFromAddress() {
        return "test@gmail.com";
    }
}
