package org.example.part3.services;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.example.part3.repository.ComponentRepository;

@ApplicationScoped
public class IntelCPUService implements CPUService {
    private final ComponentRepository repository;

    @Inject
    public IntelCPUService(ComponentRepository repository) {
        this.repository = repository;
    }

    @Override
    public String selectCPU(String purpose) {
        return repository.findCPU("Intel", purpose);
    }
}
