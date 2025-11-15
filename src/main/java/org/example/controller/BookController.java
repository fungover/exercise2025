package org.example.controller;

import org.example.Genre;
import org.example.dto.BookDto;
import org.example.entities.Book;
import org.example.repository.BookRepository;
import org.example.service.BookService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//Returns response body JSON
@RestController
@RequestMapping("api")
public class BookController {

    private final BookService bookService;
    private final BookRepository bookRepository;

    public BookController(BookRepository bookRepository, BookService bookService) {
        this.bookRepository = bookRepository;
        this.bookService = bookService;
    }

    @GetMapping("books")
    public List<BookDto> getAllBooks() {
        return bookRepository.findAll().stream()
                .map(book -> new BookDto(book.getTitle(), book.getGenre(), book.getRating(), book.getAuthor(), book.getLanguage())).toList();
    }

    @GetMapping("books/{genre}")
    public List<BookDto> getBooksByGenre(@PathVariable("genre") Genre genre) {

        return bookRepository.findByGenre(genre).stream()
                .map(book -> new BookDto(book.getTitle(), book.getGenre(), book.getRating(), book.getAuthor(), book.getLanguage())).toList();
    }

    @GetMapping("books/find")
    public List<BookDto> findBooksByFind() {
        return bookRepository.findBooksBy().stream()
                .map(book -> new BookDto(book.getTitle(), book.getGenre(), book.getRating(), book.getAuthor(), book.getLanguage())).toList();
    }

    @PostMapping("/books/add")
    public Book createBook(@RequestBody BookDto bookDto) {
        return bookService.createBook(bookDto);
    }
}
