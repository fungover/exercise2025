package org.example.part1.services;

import org.example.part1.repository.ComponentRepository;

public class NvidiaGPUService implements GPUService {
    private final ComponentRepository repository;

    public NvidiaGPUService(ComponentRepository repository) {
        this.repository = repository;
    }

    @Override
    public String selectGPU(String purpose) {
        if ("Gaming".equals(purpose)) {
            return "Nvidia RTX 5090 Gaming GPU";
        }
        return "Nvidia RTX 2080 Office GPU";
    }
}
