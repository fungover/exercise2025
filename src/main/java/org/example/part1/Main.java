package org.example.part1;

import org.example.part1.services.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("\nTesting manual dependency injection");

        CPUService intelCPU = new IntelCPUService();
        CPUService amdCPU = new AmdCPUService();
        GPUService nvidiaGPU = new NvidiaGPUService();
        GPUService amdGPU = new AmdGPUService();

        // Intel + Nvidia
        ComputerBuilder intelBuilder = new ComputerBuilder(intelCPU, nvidiaGPU);

        System.out.println("\nIntel + Nvidia Gaming: " + intelBuilder.buildComputer("Gaming"));
        System.out.println("Intel + Nvidia Office: " + intelBuilder.buildComputer("Office"));

        // AMD + AMD
        ComputerBuilder amdBuilder = new ComputerBuilder(amdCPU, amdGPU);

        System.out.println("\nAMD + AMD Gaming: " + amdBuilder.buildComputer("Gaming"));
        System.out.println("AMD + AMD Office: " + amdBuilder.buildComputer("Office"));

        // AMD + Nvidia Gaming / Intel + AMD Office
        ComputerBuilder amdNvidiaBuilder = new ComputerBuilder(amdCPU, nvidiaGPU);
        ComputerBuilder intelAmdBuilder = new ComputerBuilder(intelCPU, amdGPU);
        System.out.println("\nAMD + Nvidia Gaming: " + amdNvidiaBuilder.buildComputer("Gaming"));
        System.out.println("Intel + AMD Office: " + intelAmdBuilder.buildComputer("Office"));
    }
}
