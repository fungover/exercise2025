package entities;

import java.time.LocalDate;

public record Product (    //Record för att köra immutable (objekt som inte kan ändras) samt renare och kortare kod.
    String id,
    String name,
    Category category,
    int rating,
    LocalDate createdDate,
    LocalDate modifiedDate
) { }
