package org.example.part3;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.example.part3.services.AssemblyService;
import org.example.part3.services.CPUService;
import org.example.part3.services.GPUService;

@ApplicationScoped
public class ComputerBuilder {
    private final CPUService cpuService;
    private final GPUService gpuService;
    private final AssemblyService assemblyService;

    @Inject
    public ComputerBuilder(CPUService cpuService, GPUService gpuService, AssemblyService assemblyService) {
        this.cpuService = cpuService;
        this.gpuService = gpuService;
        this.assemblyService = assemblyService;
    }

    public String buildComputer(String purpose) {
        String cpu = cpuService.selectCPU(purpose);
        String gpu = gpuService.selectGPU(purpose);

        return assemblyService.assembleComputer(cpu, gpu, purpose);
    }
}
