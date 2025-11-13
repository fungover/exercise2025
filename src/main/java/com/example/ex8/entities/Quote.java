package com.example.ex8.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "quotes")
public class Quote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String quote;

    private String movie;

    @Column(name = "character_name")
    private String characterName;

    private Integer year;

    public Quote() {
    }

    public Quote(String quote, String movie, String characterName, Integer year) {
        this.quote = quote;
        this.movie = movie;
        this.characterName = characterName;
        this.year = year;
    }

    public Long getId() {
        return id;
    }

    public String getQuote() {
        return quote;
    }

    public void setQuote(String quote) {
        this.quote = quote;
    }

    public String getMovie() {
        return movie;
    }

    public void setMovie(String movie) {
        this.movie = movie;
    }

    public String getCharacterName() {
        return characterName;
    }

    public void setCharacterName(String characterName) {
        this.characterName = characterName;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public void setText(String testquoteFromTest) {
    }
}
