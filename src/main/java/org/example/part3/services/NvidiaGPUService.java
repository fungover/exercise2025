package org.example.part3.services;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.example.part3.repository.ComponentRepository;

@ApplicationScoped
public class NvidiaGPUService implements GPUService {
    private final ComponentRepository repository;

    @Inject
    public NvidiaGPUService(ComponentRepository repository) {
        this.repository = repository;
    }

    @Override
    public String selectGPU(String purpose) {
        return repository.findGPU("Nvidia", purpose);
    }
}
