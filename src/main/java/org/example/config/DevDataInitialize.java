package org.example.config;

import jakarta.transaction.Transactional;
import org.example.Authority;
import org.example.entities.*;
import org.example.repository.*;
import org.example.Genre;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Profile({"developer", "test"})
public class DevDataInitialize implements ApplicationRunner {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final LanguageRepository languageRepository;
    private final StoreRepository storeRepository;
    private final InventoryRepository inventoryRepository;
    private final RoleRepository roleRepository;
    private final UserRepository  userRepository;
    private final PasswordEncoder passwordEncoder;

    public DevDataInitialize(BookRepository bookRepository,
                             AuthorRepository authorRepository,
                             LanguageRepository languageRepository,
                             StoreRepository storeRepository,
                             InventoryRepository inventoryRepository,
                             RoleRepository roleRepository,
                             UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.languageRepository = languageRepository;
        this.storeRepository = storeRepository;
        this.inventoryRepository = inventoryRepository;
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void run(ApplicationArguments args){

    if(bookRepository.count()==0){

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

        if(roleRepository.count()==0){
            Role admin = new Role(Authority.ADMIN);
            Role user = new Role(Authority.USER);
            roleRepository.saveAll(List.of(admin, user));
            roleRepository.flush();
        }

        if(userRepository.count()==0){
            CustomizedUser admin = new CustomizedUser();
            admin.setUserName("admin");
            admin.setPassword(passwordEncoder.encode("admin"));
            admin.getRoles().add(roleRepository.findByAuthority(Authority.ADMIN));
            userRepository.save(admin);

            CustomizedUser visitor = new CustomizedUser();
            visitor.setUserName("visitor");
            visitor.setPassword(passwordEncoder.encode("visitor"));
            visitor.getRoles().add(roleRepository.findByAuthority(Authority.USER));
            userRepository.save(visitor);

        }

    }
}
