package org.example.part1.services;

public class IntelCPUService implements CPUService {

    @Override
    public String selectCPU(String purpose) {
        if ("Gaming".equals(purpose)) {
            return "Intel i7 Gaming CPU";
        }
        return "Intel i5 Office CPU";
    }
}
