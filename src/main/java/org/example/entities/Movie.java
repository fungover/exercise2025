package org.example.entities;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Objects;


@Entity
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    String title;
    String genre;
    String description;
    int year;
    int runtimeMinutes;

    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    protected Movie() {}
    public Movie(String title, String genre, String description, int year, int runtimeMinutes) {
        this.title = title;
        this.genre = genre;
        this.description = description;
        this.year = year;
        this.runtimeMinutes = runtimeMinutes;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        Movie movie = (Movie) o;
        return year == movie.year && runtimeMinutes == movie.runtimeMinutes && id.equals(movie.id) && Objects.equals(title, movie.title) && Objects.equals(genre, movie.genre) && Objects.equals(description, movie.description) && createdAt.equals(movie.createdAt) && updatedAt.equals(movie.updatedAt);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + Objects.hashCode(title);
        result = 31 * result + Objects.hashCode(genre);
        result = 31 * result + Objects.hashCode(description);
        result = 31 * result + year;
        result = 31 * result + runtimeMinutes;
        result = 31 * result + createdAt.hashCode();
        result = 31 * result + updatedAt.hashCode();
        return result;
    }

    //Getters & Setters
    public Integer getId() {return id;}
    public void setId(Integer id) {this.id = id;}

    public String getTitle() {return title;}
    public void setTitle(String title) {this.title = title;}

    public String getGenre() {return genre;}
    public void setGenre(String genre) {this.genre = genre;}

    public String getDescription() {return description;}
    public void setDescription(String description) {this.description = description;}

    public int getYear() {return year;}
    public void setYear(int year) {this.year = year;}

    public int getRuntimeMinutes() {return runtimeMinutes;}
    public void setRuntimeMinutes(int runtimeMinutes) {this.runtimeMinutes = runtimeMinutes;}

    public LocalDateTime getCreatedAt() {return createdAt;}
    public void setCreatedAt(LocalDateTime createdAt) {this.createdAt = createdAt;}

    public LocalDateTime getUpdatedAt() {return updatedAt;}
    public void setUpdatedAt(LocalDateTime updatedAt) {this.updatedAt = updatedAt;}

}