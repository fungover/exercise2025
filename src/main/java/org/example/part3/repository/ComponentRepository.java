package org.example.part3.repository;

public interface ComponentRepository {
    String findCPU(String brand, String purpose);
    String findGPU(String brand, String purpose);
}
