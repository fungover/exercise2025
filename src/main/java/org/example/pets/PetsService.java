package org.example.pets;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.NotFoundException;
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

    public PetDTO getPetById(long id) {
        PetDTO pet = pets.get(id);
        if (pet == null) throw new NotFoundException("Pet not found: " + id);
        return pet;
    }

    public void feedPetById(long id) { pets.compute(id, (k, v) -> ensure(v).feed()); }

    public void playWithPetById(long id) { pets.compute(id, (k, v) -> ensure(v).play()); }

    public void releasePetById(long id) {
        if (pets.remove(id) == null) throw new NotFoundException("Pet not found: " + id);
    }

    private static PetDTO ensure(PetDTO v) {
        if (v == null) throw new NotFoundException("Pet not found");
        return v;
    }
}
