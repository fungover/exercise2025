package org.example.part1.services;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class AssemblyServiceTest {

    @Test
    void assemblyServiceShouldFormatOutputCorrectly() {
        AssemblyService assemblyService = new ProffesionalAssemblyService();

        String result = assemblyService.assembleComputer("Intel i7", "RTX 4080", "Gaming");

        assertThat(result).contains("Intel i7");
        assertThat(result).contains("RTX 4080");
        assertThat(result).contains("Gaming");
        assertThat(result).contains("Professional quality testing passed!");
    }
}
