package org.example.part3.services;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ProffesionalAssemblyService implements AssemblyService {

    @Override
    public String assembleComputer(String cpu, String gpu, String purpose) {
        return String.format("""
                %s Computer assembly complete:
                CPU: %s
                GPU: %s
                Professional quality testing passed!
                """, purpose, cpu, gpu);
    }
}
