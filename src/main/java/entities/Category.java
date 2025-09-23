package entities;

public enum Category {
    ELECTRONICS("Electronics"),
    CLOTHING("Clothing"),
    FOOD("Food"),
    BOOKS("Books"),
    TOYS("Toys"),
    HOME("Home"),
    SPORTS("Sports");

    private final String displayName;

    // Constructor to give each category a readable name
    Category(String displayName) {
        this.displayName = displayName;
    }

    // Gets to get the readable name
    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
