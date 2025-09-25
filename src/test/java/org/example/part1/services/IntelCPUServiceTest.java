package org.example.part1.services;

import org.example.part1.repository.ComponentRepository;
import org.example.part1.repository.InMemoryComponentRepository;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class IntelCPUServiceTest {

    @Test
    void shouldSelectGamingCPUForGamingPurpose() {
        ComponentRepository repository = new InMemoryComponentRepository();
        IntelCPUService service = new IntelCPUService(repository);

        String cpu = service.selectCPU("Gaming");

        assertThat(cpu).contains("Gaming");
        assertThat(cpu).contains("Intel");
    }

    @Test
    void shouldSelectOfficeCPUForOfficePurpose() {
        ComponentRepository repository = new InMemoryComponentRepository();
        IntelCPUService service = new IntelCPUService(repository);

        String cpu = service.selectCPU("Office");

        assertThat(cpu).contains("Office");
        assertThat(cpu).contains("Intel");
    }
}