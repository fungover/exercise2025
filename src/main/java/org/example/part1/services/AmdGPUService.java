package org.example.part1.services;

public class AmdGPUService implements GPUService {
    @Override
    public String selectGPU(String purpose) {
        if ("Gaming".equals(purpose)) {
            return "AMD Radeon Gaming GPU";
        }
        return "AMD Radeon Office GPU";
    }
}
