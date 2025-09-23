package org.example.part1.services;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class AmdCPUServiceTest {

    @Test
    void shouldSelectGamingCPUForGamingPurpose() {
     AmdCPUService service = new AmdCPUService();

     String cpu = service.selectCPU("Gaming");

     assertThat(cpu).contains("Gaming");
     assertThat(cpu).contains("AMD");
    }

    @Test
    void shouldSelectOfficeCPUForOfficePurpose() {
        AmdCPUService service = new AmdCPUService();

        String cpu = service.selectCPU("Office");

        assertThat(cpu).contains("Office");
        assertThat(cpu).contains("AMD");
    }
}
