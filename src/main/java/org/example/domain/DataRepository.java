package org.example.domain;

/**
 * Interface which defines the contract for data repositories.
 * - Does NOT know about DatabaseRepository or FileRepository implementations.
 */

public interface DataRepository {
    String getData();
}
