package org.example.ManualConstructor.persistence;

public class FilePersistence implements PersistenceLayer {
    @Override
    public void save(String file) {System.out.println("The data has been saved to a file: " + file);}
    @Override
    public void update(String data) {System.out.println("You're file has been updated with: " + data);}
    @Override
    public void delete(String filename) {System.out.println("You're file: " + filename + ", has been deleted.");}
}
