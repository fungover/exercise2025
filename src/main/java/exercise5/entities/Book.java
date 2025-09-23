package exercise5.entities;

public record Book(String titel, String author, Genre genre, int rating){

    public Book {
        if(rating < 0 || rating > 10) {
            throw new IllegalArgumentException("Invalid rating");
        }
        if(titel == null || titel.isEmpty()) {
            throw new IllegalArgumentException("Invalid titel");
        }
        if(author == null || author.isEmpty()) {
            throw new IllegalArgumentException("Invalid author");
        }
        if(genre == null) {
            throw new IllegalArgumentException("Invalid genre");
        }
    }

}
