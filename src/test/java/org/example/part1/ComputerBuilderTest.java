package org.example.part1;

import org.example.part1.repository.ComponentRepository;
import org.example.part1.repository.InMemoryComponentRepository;
import org.example.part1.services.*;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ComputerBuilderTest {

    @Test
    void shouldInjectCPUAndGPUServices() {
        ComponentRepository repository = new InMemoryComponentRepository();
        CPUService cpuService = new IntelCPUService(repository);
        GPUService gpuService = new NvidiaGPUService(repository);
        ComputerBuilder builder = new ComputerBuilder(cpuService, gpuService);

        String result = builder.buildComputer("Gaming");

        assertThat(result).contains("Intel");
        assertThat(result).contains("Nvidia");
        assertThat(result).contains("Gaming");
    }

    @Test
    void canSwitchCPUImplementationWithoutChangingTheBuilder() {
        ComponentRepository repository = new InMemoryComponentRepository();
        GPUService gpuService = new NvidiaGPUService(repository);

        ComputerBuilder intelBuilder = new ComputerBuilder(new IntelCPUService(repository), gpuService);
        ComputerBuilder amdBuilder = new ComputerBuilder(new AmdCPUService(repository), gpuService);

        String intelResult = intelBuilder.buildComputer("Gaming");
        String amdResult = amdBuilder.buildComputer("Gaming");

        assertThat(intelResult).contains("Intel");
        assertThat(amdResult).contains("AMD");
        assertThat(intelResult).contains("Nvidia");
        assertThat(amdResult).contains("Nvidia");

    }
}