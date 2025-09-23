package org.example.di;

import org.example.di.notify.ConsoleNotifier;
import org.example.di.notify.EmailNotifier;
import org.example.di.model.Book;
import org.example.di.repository.BookRepository;
import org.example.di.repository.InMemoryBookRepository;
import org.example.di.service.BookService;

public class BookMain
{
    public static void main(String[] args)
    {
        System.out.println("====== Manual DI ======");
        BookRepository bookRepository = new InMemoryBookRepository();

        ConsoleNotifier consoleNotifier = new ConsoleNotifier();
        BookService service = new BookService(bookRepository, consoleNotifier);
        Book book = new Book("id1" , "Lord of the Rings");
        service.addBook(book);
        service.listBooks().forEach(b ->
                System.out.println(" - " + b.title())
        );

        EmailNotifier emailNotifier = new EmailNotifier("test@gmail.com");
        BookService service2 = new BookService(bookRepository, emailNotifier);
        Book book2 = new Book("id2" , "Harry Potter");
        service2.addBook(book2);
        service2.listBooks().forEach(b ->
                System.out.println(" - " + b.title())
        );

    }
}
