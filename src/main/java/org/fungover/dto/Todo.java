package org.fungover.dto;

import java.time.LocalDate;

public record Todo(
        Long id,
        String title,
        String description,
        boolean completed,
        LocalDate dueDate
) {
}
