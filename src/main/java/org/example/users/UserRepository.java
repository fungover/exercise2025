package org.example.users;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.validation.Valid;

@ApplicationScoped
public class UserRepository {
    public void save(User user) {
        System.out.println("Saving user to database...");
    }
}
