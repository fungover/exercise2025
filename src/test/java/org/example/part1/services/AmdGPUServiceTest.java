package org.example.part1.services;

import org.example.part1.repository.ComponentRepository;
import org.example.part1.repository.InMemoryComponentRepository;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class AmdGPUServiceTest {

    @Test
    void shouldSelectGamingGPUForGamingPurpose() {
        ComponentRepository repository = new InMemoryComponentRepository();
        AmdGPUService service = new AmdGPUService(repository);

        String gpu = service.selectGPU("Gaming");

        assertThat(gpu).contains("Gaming");
        assertThat(gpu).contains("AMD");
    }

    @Test
    void shouldSelectOfficeGPUForOfficePurpose() {
        ComponentRepository repository = new InMemoryComponentRepository();
        AmdGPUService service = new AmdGPUService(repository);

        String gpu = service.selectGPU("Office");

        assertThat(gpu).contains("Office");
        assertThat(gpu).contains("AMD");
    }
}