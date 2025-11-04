package org.example;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("api")
public class BookController {

    private final BookRepository bookRepository;

    public BookController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @GetMapping("books")
    public List<Book> getAllBooks() {
        return bookRepository.findAll().stream()
                .map(book -> new Book(book.getTitle(), book.getAuthor(), book.getGenre(), book.getRating())).toList();
    }

}
