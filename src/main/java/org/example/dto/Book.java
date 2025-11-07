package org.example.dto;

import org.example.Genre;
import org.example.entities.Author;
import org.example.entities.Language;

public record Book(String title, Genre genre, int Rating, Author author, Language language) {
}
