package org.example.part1;

import org.example.part1.repository.ComponentRepository;
import org.example.part1.repository.InMemoryComponentRepository;
import org.example.part1.services.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("\nManual dependency injection\n");

        ComponentRepository repository = new InMemoryComponentRepository();

        CPUService intelCPU = new IntelCPUService(repository);
        CPUService amdCPU = new AmdCPUService(repository);
        GPUService nvidiaGPU = new NvidiaGPUService(repository);
        GPUService amdGPU = new AmdGPUService(repository);
        AssemblyService assemblyService = new ProffesionalAssemblyService();

        ComputerBuilder intelNvidiaBuilder = new ComputerBuilder(intelCPU, nvidiaGPU, assemblyService);
        ComputerBuilder amdAmdBuilder = new ComputerBuilder(amdCPU, amdGPU, assemblyService);
        ComputerBuilder amdNvidiaBuilder = new ComputerBuilder(amdCPU, nvidiaGPU, assemblyService);

        System.out.println(intelNvidiaBuilder.buildComputer("Gaming"));
        System.out.println(amdAmdBuilder.buildComputer("Office"));
        System.out.println(amdNvidiaBuilder.buildComputer("Gaming"));
    }
}
