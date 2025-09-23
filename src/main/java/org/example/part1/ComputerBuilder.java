package org.example.part1;

import org.example.part1.services.CPUService;

public class ComputerBuilder {
    private final CPUService cpuService;

    public ComputerBuilder(CPUService cpuService) {
        this.cpuService = cpuService;
    }

    public String buildComputer(String purpose) {
        String cpu = cpuService.selectCPU(purpose);
        return "Computer built with " + cpu;
    }
}
