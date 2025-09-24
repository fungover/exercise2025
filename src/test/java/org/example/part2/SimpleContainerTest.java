package org.example.part2;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SimpleContainerTest {

    @Test
    void shouldCreateSimpleClassWithNoConstructor() {
        SimpleContainer container = new SimpleContainer();

        String result = container.getInstance(String.class);

//        assertThat(result).isNotNull();
        System.out.println("Test ran, Todo: actual logic here");
    }
}