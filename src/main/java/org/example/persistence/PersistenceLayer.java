package org.example.persistence;

public interface PersistenceLayer {
    void save(String data);
    void update(String data);
    void delete(String filename);
}
