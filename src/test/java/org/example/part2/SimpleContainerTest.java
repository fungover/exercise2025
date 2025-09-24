package org.example.part2;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SimpleContainerTest {

    @Test
    void shouldCreateSimpleClassDefaultConstructor() {
        SimpleContainer container = new SimpleContainer();

        SimpleTestClass result = container.getInstance(SimpleTestClass.class);

        assertThat(result).isNotNull();
        assertThat(result.getMessage()).isEqualTo("Created by container!");
    }
}