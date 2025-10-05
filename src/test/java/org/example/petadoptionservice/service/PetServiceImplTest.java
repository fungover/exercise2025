package org.example.petadoptionservice.service;

import org.example.petadoptionservice.dto.PetDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class PetServiceImplTest {
    private PetService service;

    @BeforeEach
    void setUp() {
        service = new PetServiceImpl();
    }

    @Test
    void adopt_validPet_assignsIdAndStoresInDatabase() {
        PetDTO input = new PetDTO(null, "Jack", "Dog", 20, 90);
        PetDTO adopted = service.adopt(input);

        assertNotNull(adopted.id());
        assertEquals("Jack", adopted.name());
        assertEquals(adopted, service.getById(adopted.id()));
    }
}
