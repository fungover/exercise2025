package org.example.service;

import jakarta.ws.rs.NotFoundException;
import org.example.dto.PetDTO;
import org.example.repo.InMemoryPetRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PetServiceTest {

    private PetService service;

    @BeforeEach
    void setup() {
        service = new PetService(new InMemoryPetRepository()); // real in-memory repo
    }

    @Test
    void adopt_and_get_roundtrip() {
        PetDTO created = service.adopt(new PetDTO(null, "Cat name", "cat", 50, 60));
        assertNotNull(created.getId());
        PetDTO fetched = service.get(created.getId());
        assertEquals("Cat name", fetched.getName());
        assertEquals("cat", fetched.getSpecies());
    }

    @Test
    void feed_clamps_to_zero() {
        PetDTO p = service.adopt(new PetDTO(null, "Dog name", "dog", 5, 20));
        PetDTO fed = service.feed(p.getId(), 10);
        assertEquals(0, fed.getHungerLevel());
    }

    @Test
    void play_clamps_to_100() {
        PetDTO p = service.adopt(new PetDTO(null, "Cat name", "cat", 30, 95));
        PetDTO played = service.play(p.getId(), 10);
        assertEquals(100, played.getHappiness());
    }

    @Test
    void delete_removes_pet_from_list() {
        PetDTO p = service.adopt(new PetDTO(null, "Parrot name", "parrot", 40, 40));

        service.release(p.getId());

        var page = service.list(null, "id", "asc", 0, 100);
        boolean stillThere = page.items().stream().anyMatch(it -> it.getId().equals(p.getId()));
        assertFalse(stillThere, "pet should be removed after delete");
    }

}
