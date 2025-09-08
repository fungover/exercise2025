package org.example.entities;

import java.util.Date;

public record Product(String id, String name, CategoryType category,
                      int rating, Date createdDate, Date modifiedDate) {}
