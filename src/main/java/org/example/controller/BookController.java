package org.example.controller;

import org.example.dto.Book;
import org.example.repository.BookRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api")
public class BookController {

    private final BookRepository bookRepository;

    public BookController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @GetMapping("books")
    public List<Book> getAllBooks() {
        return bookRepository.findAll().stream()
                .map(book -> new Book(book.getTitle(), book.getGenre(), book.getRating(), book.getAuthor(), book.getLanguage())).toList();
    }

}
