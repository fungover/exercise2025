package org.example.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;

public record PricePoint(OffsetDateTime start, BigDecimal price) {}
