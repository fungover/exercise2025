package entities;

import java.time.LocalDateTime;

public class Product {

    private final int id;
    private String name;
    private Category category;
    private int rating;

    public Product(int id, String name, Category category, int rating, LocalDateTime now, LocalDateTime localDateTime) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.rating = rating;
    }

    public String getName(){
        return this.name;
    }

    public void setName(String name){
        this.name = name;
    }

    public int getId() {
        return this.id;
    }

    public int getRating(){
        return this.rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public Category getCategory(){
        return this.category;
    }

    public Category setCategory(Category category){
       return this.category;
    }

    @Override
    public String toString() {
        return "Product{id=" + id + ", name='" + name + "', category=" + category + ", rating=" + rating + "}";
    }
}
