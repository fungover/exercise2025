package org.example.pets;

public class PetService {

    public Pets getPets(String pets) {
        if ((pets == null) || pets.trim().isEmpty()) {
            pets = "Generic pet";
        }

        return new Pets(pets);
    }
}
