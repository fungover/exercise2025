package org.example.part1.services;

public class NvidiaGPUService implements GPUService {

    @Override
    public String selectGPU(String purpose) {
        if ("Gaming".equals(purpose)) {
            return "Nvidia RTX 5090 Gaming GPU";
        }
        return "Nvidia RTX 2080 Office GPU";
    }
}
