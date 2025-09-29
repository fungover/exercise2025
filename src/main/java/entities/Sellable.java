package entities;

//Interface for items that can be sold.
public interface Sellable {

    //Gets the product name
    String getName();

    //Gets the product's price
    double getPrice();

    //Retrieves the product's unique ID
    String getId();

    //Retrieves a description of the product
    String getDescription();
}
