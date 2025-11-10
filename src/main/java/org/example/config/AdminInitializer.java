package org.example.config;

import org.example.services.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.logging.Logger;

@Configuration
public class AdminInitializer {
    private final Logger log = Logger.getLogger(AdminInitializer.class.getName());

    @Bean
    public CommandLineRunner initAdmin(UserService userService) {
        return args -> {
            try {
                if (userService.findByName("admin") == null) {
                    userService.createUser("admin", "adminpassword", "ROLE_ADMIN", true);
                    log.warning("Admin user created with DEFAULT PASSWORD 'adminpassword'. CHANGE IMMEDIATELY!");
                } else {
                    log.info("Admin user already exists!");
                }
            } catch (Exception e) {
                log.severe("Error creating admin user: " + e.getMessage());
            }
        };
    }
}
