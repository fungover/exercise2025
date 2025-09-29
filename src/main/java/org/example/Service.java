package org.example;

import jakarta.ws.rs.NotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class Service{
    private final ConcurrentHashMap<Long, PetDTO> pets = new ConcurrentHashMap<>();
    private final AtomicLong idGen = new AtomicLong(1);

    //TODO: manage hunger level
    //TODO: manage happiness level


    // Adopts a new pet
    public PetDTO adopt(PetDTO dto) {
        long id = idGen.getAndIncrement();
        dto.setId(id);
        pets.put(id, dto);
        return dto;
    }

    // List with all pets
    public List<PetDTO> list() {
        return new ArrayList<>(pets.values());
    }

    // Get a pet
    public PetDTO get(Long id) {
        PetDTO pet = pets.get(id);
        if (pet == null) throw new NotFoundException("Pet not found: " + id);
        return pet;
    }
}
