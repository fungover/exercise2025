package org.example.part1.services;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NvidiaGPUServiceTest {

    @Test
    void shouldSelectGamingGPUForGamingPurpose() {
        NvidiaGPUService service = new NvidiaGPUService();

        String gpu = service.selectGPU("Gaming");

        assertEquals("Nvidia RTX 5090 Gaming GPU", gpu);
    }

    @Test
    void shouldSelectOfficeGPUForOfficePurpose() {
        NvidiaGPUService service = new NvidiaGPUService();

        String gpu = service.selectGPU("Office");

        assertEquals("Nvidia RTX 2080 Office GPU", gpu);
    }
}