package org.example.repository;

import org.example.Genre;
import org.example.entities.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
class StoreRepositoryTest {

    @Container
    @ServiceConnection
    static MySQLContainer<?> mysql = new MySQLContainer<>("mysql:8.0.44")
            .withUsername("admin")
            .withPassword("admin")
            .withDatabaseName("books");

    @Test
    void containerIsUpAndRunning(){
        assertThat(mysql.isCreated());
        assertThat(mysql.isRunning());
    }

    @Autowired
    StoreRepository storeRepository;
    @Autowired
    AuthorRepository authorRepository;
    @Autowired
    LanguageRepository languageRepository;
    @Autowired
    BookRepository bookRepository;
    @Autowired
    InventoryRepository inventoryRepository;

    @BeforeEach
    void setUp(){
        Store storeBooks = new Store("Books of books");
        Store storeRead = new Store("Books to read");
        storeRepository.saveAll(List.of(storeBooks, storeRead));

        Author authorFB = new Author("Fredrik", "Backman");
        Author authorSRB = new Author("Sofia", "Rutbäck Eriksson");
        authorRepository.saveAll(List.of(authorFB,authorSRB));

        Language swedish = new Language("swedish");
        languageRepository.saveAll(List.of(swedish));

        Book book1 = new Book("En man som heter Ove", Genre.FICTION, 10, authorFB, swedish);
        Book book2 = new Book("Min mormor hälsar och säger förlåt", Genre.FICTION, 10, authorFB, swedish);
        Book book3 = new Book("Folk med ångest", Genre.FICTION, 9, authorFB, swedish);
        Book book4 = new Book("Mord överbord", Genre.COZY_CRIME, 9, authorSRB, swedish);

        bookRepository.saveAll(List.of(book1, book2, book3, book4));

        Inventory invent1 = new Inventory(book1, storeBooks, 5);
        Inventory invent2 = new Inventory(book2, storeBooks, 5);
        Inventory invent3 = new Inventory(book3, storeBooks, 5);
        Inventory invent4 = new Inventory(book2, storeRead, 4);
        Inventory invent5 = new Inventory(book3, storeRead, 4);
        Inventory invent6 = new Inventory(book4, storeRead, 4);

        inventoryRepository.saveAll(List.of(invent1, invent2, invent3, invent4, invent5, invent6));
    }

    @Test
    void findStoreByAuthorName() {
        List<String> stores = storeRepository.findStoreByAuthorName("Sofia");
        assertThat(stores.size()).isEqualTo(1);
        assertThat(stores.contains("Books to read")).isTrue();
    }

    @Test
    void findByStoreName() {
        List<Store> store = storeRepository.findByStoreName("Books of books");
        assertThat(store.size()).isEqualTo(1);
    }
}