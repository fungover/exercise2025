package org.example.part1.services;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class IntelCPUServiceTest {

    @Test
    void shouldSelectGamingCPUForGamingPurpose() {
        IntelCPUService service = new IntelCPUService();

        String cpu = service.selectCPU("Gaming");

        assertThat(cpu).contains("Gaming");
        assertThat(cpu).contains("Intel");
    }

    @Test
    void shouldSelectOfficeCPUForOfficePurpose() {
        IntelCPUService service = new IntelCPUService();

        String cpu = service.selectCPU("Office");

        assertThat(cpu).contains("Office");
        assertThat(cpu).contains("Intel");
    }
}