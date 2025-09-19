package org.example.ManualConstructor;

public interface PersistenceLayer {
    void save(String data);
    void update(String data);
    void delete(String filename);
}
