-- Flyway migration: V1__create_catches_table.sql
-- Create table "catches" that matches org.example.entities.Catch

CREATE TABLE catches (
                         catch_id BIGINT PRIMARY KEY AUTO_INCREMENT,
                         species VARCHAR(255) NOT NULL,
                         weight_g DOUBLE NOT NULL,
                         length DOUBLE NOT NULL,
                         caught_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL
);