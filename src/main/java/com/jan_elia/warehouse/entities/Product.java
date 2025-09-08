// Product is immutable
// Fields: id, name, category, rating, createdDate, modifiedDate

package com.jan_elia.warehouse.entities;

import java.time.LocalDate;

public class Product {
    private final String id;
    private final String name;
    private final Category category;
    private final int rating;
    private final LocalDate createdDate;
    private final LocalDate modifiedDate;

    // TODO: constructor, getters, no setters
}