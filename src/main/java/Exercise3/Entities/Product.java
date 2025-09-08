package Exercise3.Entities;

import java.time.LocalDate;

public record Product(String id, String name, Category category, int rating, LocalDate createdDate,
                      LocalDate modifiedDate) {

}
