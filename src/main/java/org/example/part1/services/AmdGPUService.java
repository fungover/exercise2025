package org.example.part1.services;

import org.example.part1.repository.ComponentRepository;

public class AmdGPUService implements GPUService {
    private final ComponentRepository repository;

    public AmdGPUService(ComponentRepository repository) {
        this.repository = repository;
    }

    @Override
    public String selectGPU(String purpose) {
        if ("Gaming".equals(purpose)) {
            return "AMD Radeon Gaming GPU";
        }
        return "AMD Radeon Office GPU";
    }
}
