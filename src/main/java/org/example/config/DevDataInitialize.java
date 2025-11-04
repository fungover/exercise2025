package org.example.config;

import org.example.BookRepository;
import org.example.Genre;
import org.example.entities.Book;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Profile("dev")
public class DevDataInitialize implements ApplicationRunner {

    private final BookRepository bookRepository;

    public DevDataInitialize(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
    if(bookRepository.count()==0){
        bookRepository.saveAll(List.of(
          new Book("En man som heter Ove", "Fredrik Backman", Genre.FICTION, 10),
          new Book("Min mormor hälsar och säger förlåt", "Fredrik Backman", Genre.FICTION, 10)
                ));
    }
    }
}
