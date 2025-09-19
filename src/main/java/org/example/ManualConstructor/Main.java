package org.example.ManualConstructor;

public class Main {
    void main() {
        /*FilePersistence persistence = new FilePersistence();*/
        AndvancedService service = new AndvancedService(new FilePersistence());
        service.processData("This is a test");
        service.save("example.txt");
        service.update("Hello world!");
        service.delete("example.txt");

        AndvancedService dbService = new AndvancedService(new DataBasePersistence());
        dbService.processData("\nThis is a test for the database");
        dbService.save("example.txt");
        dbService.update("Hello world!");
        dbService.delete("example.txt");


    }
}
