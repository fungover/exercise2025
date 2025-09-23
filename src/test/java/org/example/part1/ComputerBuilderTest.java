package org.example.part1;

import org.example.part1.services.*;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ComputerBuilderTest {

    @Test
    void shouldInjectCPUAndGPUServices() {
        CPUService cpuService = new IntelCPUService();
        GPUService gpuService = new NvidiaGPUService();
        ComputerBuilder builder = new ComputerBuilder(cpuService, gpuService);

        String result = builder.buildComputer("Gaming");

        assertThat(result).contains("Intel");
        assertThat(result).contains("Nvidia");
        assertThat(result).contains("Gaming");
    }

    @Test
    void canSwitchCPUImplementationWithoutChangingTheBuilder() {
        GPUService gpuService = new NvidiaGPUService();

        ComputerBuilder intelBuilder = new ComputerBuilder(new IntelCPUService(), gpuService);
        ComputerBuilder amdBuilder = new ComputerBuilder(new AmdCPUService(), gpuService);

        String intelResult = intelBuilder.buildComputer("Gaming");
        String amdResult = amdBuilder.buildComputer("Gaming");

        assertThat(intelResult).contains("Intel");
        assertThat(amdResult).contains("AMD");
        assertThat(intelResult).contains("Nvidia");
        assertThat(amdResult).contains("Nvidia");

    }
}