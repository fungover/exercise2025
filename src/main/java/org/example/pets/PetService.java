package org.example.pets;

import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@ApplicationScoped
public class PetService {

    List<String> petsList = new CopyOnWriteArrayList<>();

    public Pets getPets(String pets) {
        if ((pets == null) || pets.trim().isEmpty()) {
            pets = "Generic pet";
        }
        else
            petsList.add(pets);

        return new Pets(pets);
    }

    public List<String> petsList() {
        return List.copyOf(petsList);
    }
}
