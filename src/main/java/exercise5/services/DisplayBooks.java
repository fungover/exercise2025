package exercise5.services;

import exercise5.repsitory.InMemoryBookRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class DisplayBooks implements Library{

    private final InMemoryBookRepository inMemoryBookRepository;

    @Inject
    public DisplayBooks(InMemoryBookRepository inMemoryBookRepository) {
        this.inMemoryBookRepository = inMemoryBookRepository;
    }

    @Override
    public void displayAllBooks() {
        System.out.println("All Books: "+inMemoryBookRepository.getAllBooks());
    }

}
