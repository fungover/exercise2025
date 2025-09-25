package org.example.part1.repository;

public class InMemoryComponentRepository implements ComponentRepository {

    @Override
    public String findCPU(String brand, String purpose) {
        if ("Intel".equals(brand)) {
            return "Gaming".equals(purpose) ?
                    "Intel i7-13900K Gaming CPU" : "Intel i5-13400 Office CPU";
        } else if ("AMD".equals(brand)) {
            return "Gaming".equals(purpose) ?
                    "AMD Ryzen 7 7700X Gaming CPU" : "AMD Ryzen 5 7500 Office CPU";
        }
        return "Generic CPU";
    }

    @Override
    public String findGPU(String brand, String purpose) {
        if ("Nvidia".equals(brand)) {
            return "Gaming".equals(purpose) ?
                    "Nvidia RTX 5090 Gaming GPU" : "Nvidia RTX 2080 Office GPU";
        } else if ("AMD".equals(brand)) {
            return "Gaming".equals(purpose) ?
                    "AMD RX 7800 XT Gaming GPU" : "AMD RX 6600 Office GPU";
        }
        return "Generic GPU";
    }

}
