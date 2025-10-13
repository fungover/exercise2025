package org.example.pets;

import jakarta.enterprise.context.ApplicationScoped;
import org.jboss.logging.Logger;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@ApplicationScoped
public class PetsService {

    private final ConcurrentHashMap<Long, PetDTO> pets = new ConcurrentHashMap<>();

    Logger logger = Logger.getLogger(PetsService.class);

    public void adoptPet(PetDTO petDTO) {
        Long uuid = UUID.randomUUID().getMostSignificantBits();
        pets.put(uuid, petDTO);
    }

    public List<PetDTO> getPets() {
        if (pets.isEmpty()) {
            logger.warn("No pets found");
            return Collections.emptyList();
        }
        return List.copyOf(pets.values());
    }

    private List<Long> getPetIds() {
        if (pets.isEmpty()) {
            logger.warn("No pets found");
            return Collections.emptyList();
        }
        return List.copyOf(pets.keySet());
    }

    public PetDTO getPetById(int id) {
        return getPets().get(--id);
    }

    public void feedPetById(int id) {
        PetDTO pet = getPetById(id);
        PetDTO fedPet = pet.feed();
        pets.put(petId(id), fedPet);
    }

    public void playWithPetById(int id) {
        PetDTO pet = getPetById(id);
        PetDTO happierPet = pet.play();
        pets.put(petId(id), happierPet);
    }

    public void releasePetById(int id) {
        pets.remove(petId(id));
    }

    private Long petId(int id) {
        return getPetIds().get(--id);
    }
}
