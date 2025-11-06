package org.example;

import org.flywaydb.core.Flyway;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.context.annotation.Bean;
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationStrategy;

@SpringBootApplication
public class Exercise2025Application {

    public static void main(String[] args) {
        SpringApplication.run(Exercise2025Application.class, args);
    }

    /**
     * Kör Flyway-migreringarna innan Hibernate EntityManagerFactory byggs.
     */
    /*@Bean
    public FlywayMigrationStrategy flywayMigrationStrategy() {
        return Flyway::migrate;
    }*/
}
