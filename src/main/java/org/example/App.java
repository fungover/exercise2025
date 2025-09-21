package org.example;

import org.example.ManualConstructor.persistence.DataBasePersistence;
import org.example.ManualConstructor.persistence.FilePersistence;
import org.example.ManualConstructor.persistence.PersistenceLayer;
import org.example.ManualConstructor.service.AdvancedService;


public class App {

    public static void main(String[] args) throws Exception {
        System.out.println("Hello There!");
//        Container container = new Container();
//        container.bind(PersistenceLayer.class ,FilePersistence.class);
//        container.bind(PersistenceLayer.class ,DataBasePersistence.class);



        /*AdvancedService car = container.create(AdvancedService.class);*/

        //System.out.println(container.getBindings());
        /*service.test();*/
        /*container.bind(PersistenceLayer.class ,DataBasePersistence.class);*/

        /*container.create(AdvancedService.class);*/


        /*System.out.println(AdvancedService.class.save("hej"));*/

        /*AdvancedService service = container.create(AdvancedService.class);
        service.processData("This is a test");*/
        Container container = new Container();

        // FilePersistence
        container.bind(PersistenceLayer.class, FilePersistence.class);
        /*AdvancedService fileService = container.create(AdvancedService.class);
        fileService.save("file-data.txt");*/

        System.out.println("\n--- Switching to database ---\n");

        // DataBasePersistence-variant
        container.bind(PersistenceLayer.class, DataBasePersistence.class);
        AdvancedService dbService = container.create(AdvancedService.class);
        dbService.save("db-data.txt");

        Car car = container.create(Car.class);
        Transmission transmission = container.create(Transmission.class);
        System.out.println(car);
        System.out.println(transmission);
    }
}
