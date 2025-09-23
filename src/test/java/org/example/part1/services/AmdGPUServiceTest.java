package org.example.part1.services;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AmdGPUServiceTest {

    @Test
    void shouldSelectGamingGPUForGamingPurpose() {
        AmdGPUService service = new AmdGPUService();

        String gpu = service.selectGPU("Gaming");

        assertEquals("AMD Radeon Gaming GPU", gpu);
    }

    @Test
    void shouldSelectOfficeGPUForOfficePurpose() {
        AmdGPUService service = new AmdGPUService();

        String gpu = service.selectGPU("Office");

        assertEquals("AMD Radeon Office GPU", gpu);
    }
}