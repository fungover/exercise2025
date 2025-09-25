package org.example.part1.services;

import org.example.part1.repository.ComponentRepository;
import org.example.part1.repository.InMemoryComponentRepository;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class AmdCPUServiceTest {

    @Test
    void shouldSelectGamingCPUForGamingPurpose() {
        ComponentRepository repository = new InMemoryComponentRepository();
        AmdCPUService service = new AmdCPUService(repository);

        String cpu = service.selectCPU("Gaming");

        assertThat(cpu).contains("Gaming");
        assertThat(cpu).contains("AMD");
    }

    @Test
    void shouldSelectOfficeCPUForOfficePurpose() {
        ComponentRepository repository = new InMemoryComponentRepository();
        AmdCPUService service = new AmdCPUService(repository);

        String cpu = service.selectCPU("Office");

        assertThat(cpu).contains("Office");
        assertThat(cpu).contains("AMD");
    }
}
