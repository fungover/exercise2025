package org.example.part2;

import org.example.part1.ComputerBuilder;
import org.example.part1.services.*;
import org.example.part1.repository.*;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class Part1IntegrationTest {

    @Test
    void containerShouldCreateComputerBuilderWithAllDependencies() {
        SimpleContainer container = new SimpleContainer();

        ComputerBuilder builder = container.getInstance(ComputerBuilder.class);

        assertThat(builder).isNotNull();

        String gamingComputer = builder.buildComputer("Gaming");
        String officeComputer = builder.buildComputer("Office");

        assertThat(gamingComputer).isNotNull();
        assertThat(officeComputer).isNotNull();
        assertThat(gamingComputer).isNotEqualTo(officeComputer);

    }

    @Test
    void containerShouldCreateIndividualServices() {
        SimpleContainer container = new SimpleContainer();

        IntelCPUService cpuService = container.getInstance(IntelCPUService.class);
        NvidiaGPUService gpuService = container.getInstance(NvidiaGPUService.class);
        InMemoryComponentRepository repository = container.getInstance(InMemoryComponentRepository.class);

        assertThat(cpuService).isNotNull();
        assertThat(gpuService).isNotNull();
        assertThat(repository).isNotNull();

        String cpu = cpuService.selectCPU("Gaming");
        String gpu = gpuService.selectGPU("Gaming");

        assertThat(cpu).contains("Gaming");
        assertThat(gpu).contains("Gaming");
    }
}
