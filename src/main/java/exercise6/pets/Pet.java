package exercise6.pets;

import exercise6.service.IdGenerator;

public record Pet(int id, String name, AnimalType animalType) {

    public Pet{
        if(name == null || name.isBlank()){
            throw new IllegalArgumentException("Name can't be null or blank");
        }
        id = IdGenerator.getNextId();
        name = name.substring(0, 1).toUpperCase() + name.substring(1);
    }

}
