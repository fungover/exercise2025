package org.example.controller;

import jakarta.validation.Valid;
import org.example.Genre;
import org.example.dto.BookDto;
import org.example.entities.Book;
import org.example.repository.BookRepository;
import org.example.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

//Returns response body JSON
@RestController
@RequestMapping("/api")
public class BookController {

    private final BookService bookService;
    private final BookRepository bookRepository;

    public BookController(BookRepository bookRepository, BookService bookService) {
        this.bookRepository = bookRepository;
        this.bookService = bookService;
    }

    @GetMapping("/books")
    public List<BookDto> getAllBooks() {
        return bookRepository.findBooksBy().stream()
                .map(book -> new BookDto(book.getTitle(), book.getGenre(), book.getRating(), book.getAuthor(), book.getLanguage())).toList();
    }

    @GetMapping("/books/id/{id}")
    public Book getBookById(@PathVariable Integer id) {
        return bookRepository.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found"));
    }

    @GetMapping("/books/genre/{genre}")
    public List<BookDto> getBooksByGenre(@PathVariable("genre") Genre genre) {
        return bookRepository.findByGenre(genre).stream()
                .map(book -> new BookDto(book.getTitle(), book.getGenre(), book.getRating(), book.getAuthor(), book.getLanguage())).toList();
    }

    @PostMapping("/books/add")
    @ResponseStatus(HttpStatus.CREATED)
    public Book createBook(@Valid @RequestBody BookDto bookDto) {
        return bookService.createBook(bookDto);
    }

    @PutMapping("/books/update/{id}")
    public Book updateBook(@PathVariable Integer id, @Valid @RequestBody BookDto bookDto) {
        Book currentBook = bookRepository.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found"));

        currentBook.setTitle(bookDto.title());
        currentBook.setGenre(bookDto.genre());
        currentBook.setRating(bookDto.rating());
        currentBook.setAuthor(bookDto.author());
        currentBook.setLanguage(bookDto.language());

        return bookRepository.save(currentBook);
    }

    @DeleteMapping("/books/delete/{id}")
    public void removeBook(@PathVariable Integer id) {
        bookRepository.deleteById(id);
    }
}
