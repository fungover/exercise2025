package entities;

import java.time.LocalDateTime;

public record Product(int id, String name, Category category, int rating, LocalDateTime createdAt,
                      LocalDateTime modifiedAt) {

    @Override
    public String toString() {
        return "Product{id=" + id + ", name='" + name + "', category=" + category + ", rating=" + rating
                + "created at=" + createdAt + "modified at=" + modifiedAt + "}";

    }


}
