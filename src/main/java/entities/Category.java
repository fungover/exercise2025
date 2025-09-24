package entities;

/**
 * An enum to represent a set of permanent values
 * and to ensure consistency in categories when navigating in the Product object
 **/

public enum Category {
    ELECTRONICS("Electronics"),
    BOOKS("Books"),
    FOOD("Food"),
    HOME("Home"),
    TOYS("Toys"),
    BEAUTY("Beauty"),
    SPORTS("Sports"),
    OTHER("Other");

    private final String displayName;

    Category(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
