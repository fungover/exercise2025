package org.example.persistence;

import jakarta.enterprise.context.ApplicationScoped;
import org.example.qualifiers.DatabaseStore;

@ApplicationScoped
@DatabaseStore
public class DataBasePersistence implements PersistenceLayer {
    @Override
    public void save(String data) {System.out.println("The data has been saved to a database: " + data);}
    @Override
    public void update(String data) {System.out.println("You're data has been updated with: " + data);}
    @Override
    public void delete(String filename) {System.out.println("You're data: " + filename + ", has been deleted.");}
}
