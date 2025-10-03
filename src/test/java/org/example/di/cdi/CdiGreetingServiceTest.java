package org.example.di.cdi;

import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import org.example.di.common.GreetingsService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class CdiGreetingServiceTest {
    private SeContainer container;

    @BeforeEach
    void setUp() {
        container = SeContainerInitializer.newInstance().initialize();
    }

    @AfterEach
    void tearDown() {
        container.close();
    }

    @Test
    void getGreeting_shouldUseInjectedRepository() {
        GreetingsService service = container.select(GreetingsService.class).get();
        assertThat(service.getGreeting("CDI-Tester")).isEqualTo("Hi there, CDI-Tester!");
    }
}
