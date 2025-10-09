package org.example;

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
        logger.infov("Adopted pet: {0}", petDTO.name());
    }

    public List<PetDTO> getPets() {
        if (pets.isEmpty()) {
            logger.warn("No pets found");
            return Collections.emptyList();
        }
        return List.copyOf(pets.values());
    }
}
