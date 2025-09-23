package org.example.part1.services;

import org.example.part1.repository.ComponentRepository;

public class IntelCPUService implements CPUService {
    private final ComponentRepository repository;

    public IntelCPUService(ComponentRepository repository) {
        this.repository = repository;
    }

    @Override
    public String selectCPU(String purpose) {
        return repository.findCPU("Intel", purpose);
    }
}
