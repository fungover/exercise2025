package exercise5.services;

import exercise5.entities.Book;

public class DescriptionOfBook implements Description {

    private Book book;

    public DescriptionOfBook(Book book) {
        this.book = book;
    }

    @Override
    public void getDescriptionOfBook() {
        System.out.println("This is a book written by "+book.author()+" with titel of "+book.titel());
    }
}
