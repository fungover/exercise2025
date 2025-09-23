package org.example.part1.services;

import org.example.part1.repository.ComponentRepository;
import org.example.part1.repository.InMemoryComponentRepository;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AmdGPUServiceTest {

    @Test
    void shouldSelectGamingGPUForGamingPurpose() {
        ComponentRepository repository = new InMemoryComponentRepository();
        AmdGPUService service = new AmdGPUService(repository);

        String gpu = service.selectGPU("Gaming");

        assertEquals("AMD Radeon Gaming GPU", gpu);
    }

    @Test
    void shouldSelectOfficeGPUForOfficePurpose() {
        ComponentRepository repository = new InMemoryComponentRepository();
        AmdGPUService service = new AmdGPUService(repository);

        String gpu = service.selectGPU("Office");

        assertEquals("AMD Radeon Office GPU", gpu);
    }
}