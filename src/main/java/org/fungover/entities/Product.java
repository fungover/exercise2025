package org.fungover.entities;

import java.util.UUID;
import java.time.Instant;

public record Product(
        UUID identifier,
        String name,
        int rating,
        Category category,
        Instant createdDate,
        Instant lastModifiedDate) {
}
