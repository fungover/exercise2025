package org.example.part3.services;

import org.example.part1.repository.ComponentRepository;
import org.example.part1.services.GPUService;

public class AmdGPUService implements GPUService {
    private final ComponentRepository repository;

    public AmdGPUService(ComponentRepository repository) {
        this.repository = repository;
    }

    @Override
    public String selectGPU(String purpose) {
        return repository.findGPU("AMD", purpose);
    }
}
