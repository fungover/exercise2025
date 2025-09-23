package org.example.part1.services;

public class AmdCPUService implements CPUService {

    @Override
    public String selectCPU(String purpose) {
        if ("Gaming".equals(purpose)) {
            return "AMD Ryzen 7 Gaming CPU";
        }
        return "AMD Ryzen 5 Office CPU";
    }
}
