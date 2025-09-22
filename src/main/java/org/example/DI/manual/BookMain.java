package org.example.DI.manual;

import org.example.DI.manual.notify.ConsoleNotifier;
import org.example.DI.manual.notify.EmailNotifier;
import org.example.DI.manual.repository.Book;
import org.example.DI.manual.repository.BookRepository;
import org.example.DI.manual.repository.InMemoryBookRepository;
import org.example.DI.manual.service.BookService;

public class BookMain
{
    public static void main(String[] args)
    {
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
