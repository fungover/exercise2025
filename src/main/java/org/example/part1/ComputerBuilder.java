package org.example.part1;

import org.example.part1.services.CPUService;
import org.example.part1.services.GPUService;

public class ComputerBuilder {
    private final CPUService cpuService;
    private final GPUService gpuService;

    public ComputerBuilder(CPUService cpuService, GPUService gpuService) {
        this.cpuService = cpuService;
        this.gpuService = gpuService;
    }

    public String buildComputer(String purpose) {
        String cpu = cpuService.selectCPU(purpose);
        String gpu = gpuService.selectGPU(purpose);

        return "Computer built with " + cpu + " and " + gpu;
    }
}
