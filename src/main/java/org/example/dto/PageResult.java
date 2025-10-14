package org.example.dto;

import java.util.List;

public record PageResult<T>(List<T> items, int total) {}
