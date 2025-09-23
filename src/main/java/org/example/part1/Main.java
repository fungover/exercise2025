package org.example.part1;

import org.example.part1.services.AmdCPUService;
import org.example.part1.services.CPUService;
import org.example.part1.services.IntelCPUService;

public class Main {
    public static void main(String[] args) {
        System.out.println("\nTesting manual dependency injection\n");

        // Intel
        CPUService intelCPU = new IntelCPUService();
        ComputerBuilder intelBuilder = new ComputerBuilder(intelCPU);

        System.out.println("Intel Gaming: " + intelBuilder.buildComputer("Gaming"));
        System.out.println("Intel Office: " + intelBuilder.buildComputer("Office"));

        // AMD
        CPUService amdCPU = new AmdCPUService();
        ComputerBuilder amdBuilder = new ComputerBuilder(amdCPU);

        System.out.println("AMD Gaming: " + amdBuilder.buildComputer("Gaming"));
        System.out.println("AMD Office: " + amdBuilder.buildComputer("Office"));
    }
}
