package org.example.part1.services;

import org.example.part1.repository.ComponentRepository;
import org.example.part1.repository.InMemoryComponentRepository;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NvidiaGPUServiceTest {

    @Test
    void shouldSelectGamingGPUForGamingPurpose() {
        ComponentRepository repository = new InMemoryComponentRepository();
        NvidiaGPUService service = new NvidiaGPUService(repository);

        String gpu = service.selectGPU("Gaming");

        assertEquals("Nvidia RTX 5090 Gaming GPU", gpu);
    }

    @Test
    void shouldSelectOfficeGPUForOfficePurpose() {
        ComponentRepository repository = new InMemoryComponentRepository();
        NvidiaGPUService service = new NvidiaGPUService(repository);

        String gpu = service.selectGPU("Office");

        assertEquals("Nvidia RTX 2080 Office GPU", gpu);
    }
}