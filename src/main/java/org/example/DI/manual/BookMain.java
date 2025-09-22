package org.example.DI.manual;

import org.example.DI.manual.repository.Book;
import org.example.DI.manual.repository.InMemoryBookRepository;
import org.example.DI.manual.service.BookService;

public class BookMain
{
    public static void main(String[] args)
    {
        InMemoryBookRepository bookRepository = new InMemoryBookRepository();
        BookService service = new BookService(bookRepository);

        Book book = new Book("id1" , "Lord of the Rings");

        service.addBook(book);
    }
}
