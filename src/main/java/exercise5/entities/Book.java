package exercise5.entities;

public record Book(String titel, String author, Genre genre, int rating){

    /**
     * *
     * @param titel specifies the titel of the book
     * @param author specifies the author of the book
     * @param genre enum, specifies the genre of the book
     * @param rating 0-10, specifies the rating of the book
     */

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
