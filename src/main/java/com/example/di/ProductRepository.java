package com.example.di;

import java.util.List;

/**
 * An interface with methods that all repositories have access to
 **/

public interface ProductRepository {
    List<String> findAll();
}
