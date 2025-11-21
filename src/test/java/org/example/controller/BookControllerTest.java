package org.example.controller;

import org.example.Genre;
import org.example.entities.Author;
import org.example.entities.Book;
import org.example.entities.Language;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class BookControllerTest extends TestSetup {

    @Autowired
    TestRestTemplate restTemplate;

    private TestRestTemplate loggedInUser(){
        return restTemplate.withBasicAuth("admin", "admin");
    }

    @Test
    void shouldReturnResponseStatusOK(){
        ResponseEntity<String> response = loggedInUser().getForEntity("/api/books", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void shouldReturnAllBooks(){
        Book[]  books = loggedInUser().getForObject("/api/books", Book[].class);
        assertThat(books.length).isEqualTo(5);
    }

    @Test
    void shouldFindBookById() {
        ResponseEntity<Book> response = loggedInUser().exchange("/api/books/id/1", HttpMethod.GET, null, Book.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getTitle()).isEqualTo("En man som heter Ove");
    }

    @Test
    void shouldReturnErrorMessageIfNoBookIsFound() {
        ResponseEntity<String> response = loggedInUser().getForEntity("/api/books/id/100", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isEqualTo("Error 404: Book not found");
    }

    @Test
    void shouldReturnListOfBooksOfGivenGenre(){
        Book[] booksOfGenre = loggedInUser().getForObject("/api/books/genre/FICTION", Book[].class);
        assertThat(booksOfGenre.length).isEqualTo(4);
    }

    @Test
    void shouldReturnCreatedIfBookIsCreated() {
        Author author = new Author("Fredrik", "Backman");
        Language language = new Language("swedish");

        Book book = new Book();
        book.setTitle("Mina Vänner");
        book.setGenre(Genre.FICTION);
        book.setRating(8);
        book.setLanguage(language);
        book.setAuthor(author);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Book> request = new HttpEntity<>(book, headers);
        ResponseEntity<String> response = loggedInUser().exchange("/api/books/add", HttpMethod.POST, request, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    void shouldReturnErrorMessageIfBookWithTitleAlreadyExists(){
        Author author = new Author("Fredrik", "Backman");
        Language language = new Language("swedish");

        Book book = new Book();
        book.setTitle("En man som heter Ove");
        book.setGenre(Genre.FICTION);
        book.setRating(8);
        book.setLanguage(language);
        book.setAuthor(author);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Book> request = new HttpEntity<>(book, headers);
        ResponseEntity<String> response = loggedInUser().exchange("/api/books/add", HttpMethod.POST, request, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
        assertThat(response.getBody()).isEqualTo("Book already exists");
    }

    @Test
    void shouldReturnErrorMessageIfTitleIsNull() {
        Author author = new Author("Fredrik", "Backman");
        Language language = new Language("swedish");
        Book book = new Book(null,Genre.FICTION,2,author,language);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Book> request = new HttpEntity<>(book, headers);
        ResponseEntity<String> response = loggedInUser().exchange("/api/books/add", HttpMethod.POST, request, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isEqualTo("title: Name is mandatory");

    }

    @Test
    void shouldUpdateBook(){
        Author author = new Author("Fredrik", "Backman");
        Language language = new Language("swedish");
        Book updateBook = new Book("Mina Vänner",Genre.FICTION,2,author,language);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Book> request = new HttpEntity<>(updateBook, headers);
        ResponseEntity<String> response = loggedInUser().exchange("/api/books/update/3", HttpMethod.PUT, request, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void shouldDeleteBook(){
        ResponseEntity<String> response = loggedInUser().exchange("/api/books/delete/3", HttpMethod.DELETE, null, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

}