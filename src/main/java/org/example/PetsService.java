package org.example;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class PetsService {
    private final Map<Long, PetDTO> pets = new ConcurrentHashMap<>();

    public List<PetDTO> getPets() {
        return List.copyOf(pets.values());
    }
}
