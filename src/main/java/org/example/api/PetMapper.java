package org.example.api;

import org.example.domain.Pet;
import org.example.dto.PetDTO;

public final class PetMapper {
    private PetMapper() {}

    public static Pet toDomainNew(PetDTO dto) {
        return new Pet(
                null,
                dto.getName().trim(),
                dto.getSpecies().trim(),
                dto.getHungerLevel(),
                dto.getHappiness()
        );
    }

    public static PetDTO toDTO(Pet pet) {
        return new PetDTO(
                pet.id(),
                pet.name(),
                pet.species(),
                pet.hungerLevel(),
                pet.happiness()
        );
    }
}
