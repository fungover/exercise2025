package org.example.ManualConstructor;

public class AndvancedService implements Service {
    private final PersistenceLayer persistence;

    public AndvancedService(PersistenceLayer persistence) {
        this.persistence = persistence;
    }

    public void save (String data){
        persistence.save(data);
    }

    public void update (String data){
        persistence.update(data);
    }

    public void delete (String filename){
        persistence.delete(filename);
    }

    @Override
    public void processData(String data) {
        System.out.println("Processing data: " + data);
    }

}
