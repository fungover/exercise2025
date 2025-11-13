package org.example;

public record MovieDTO(Integer id, String title, String genre, String description, int year, int runtimeMinutes) {
}
