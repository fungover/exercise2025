package org.example.service;

import org.example.dto.BookDto;
import org.example.entities.Author;
import org.example.entities.Book;
import org.example.entities.Language;
import org.example.repository.AuthorRepository;
import org.example.repository.BookRepository;
import org.example.repository.LanguageRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BookService {

    private final AuthorRepository authorRepository;
    private final LanguageRepository languageRepository;
    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository,  AuthorRepository authorRepository,  LanguageRepository languageRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.languageRepository = languageRepository;
    }

    //The controller sends a bookDto that is mapped to a book entity to be stored in repository
    @Transactional
    public Book createBook(BookDto bookDto){

        if(bookRepository.findBookByTitle(bookDto.title()).isPresent()){
            throw new RuntimeException("Book already exists");
        }

        //Checks if author or language already exist in other case its saved
        Author author = authorRepository.findByFirstNameAndLastName(bookDto.author().getFirstName(), bookDto.author().getLastName())
                .orElseGet(() ->{
                            Author newAuthor = new Author();
                            newAuthor.setFirstName(bookDto.author().getFirstName());
                            return authorRepository.save(newAuthor);
                        });

        Language language = languageRepository.findByTextLanguage(bookDto.language().getTextLanguage())
                .orElseGet(() -> {
                    Language newLanguage = new Language();
                    newLanguage.setTextLanguage(bookDto.language().getTextLanguage());
                    return languageRepository.save(newLanguage);
                });

        Book book = new Book();
        book.setTitle(bookDto.title());
        book.setGenre(bookDto.genre());
        book.setRating(bookDto.Rating());
        book.setAuthor(author);
        book.setLanguage(language);

        return bookRepository.save(book);
    }

}