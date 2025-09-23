package org.example.part1.services;

import org.example.part1.repository.ComponentRepository;

public class AmdCPUService implements CPUService {
    private final ComponentRepository repository;

    public AmdCPUService(ComponentRepository repository) {
        this.repository = repository;
    }


    @Override
    public String selectCPU(String purpose) {
        if ("Gaming".equals(purpose)) {
            return "AMD Ryzen 7 Gaming CPU";
        }
        return "AMD Ryzen 5 Office CPU";
    }
}
