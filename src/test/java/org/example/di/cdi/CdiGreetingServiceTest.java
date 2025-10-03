package org.example.di.cdi;

import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import org.example.di.common.GreetingsService;
import org.example.di.common.MessageRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class CdiGreetingServiceTest {
    private SeContainer container;

    @BeforeEach
    void setUp() {
        CdiMessageRepository.resetCounter();
        container = SeContainerInitializer.newInstance().initialize();
    }

    @AfterEach
    void tearDown() {
        if (container != null) {
            container.close();
        }
    }

    @Test
    void getGreeting_shouldUseInjectedRepository() {
        GreetingsService service = container.select(GreetingsService.class).get();
        assertThat(service.getGreeting("CDI-Tester")).isEqualTo("Hi there, CDI-Tester!");
    }

    @Test
    void getGreeting_shouldUseSameRepositoryForEveryInstance() {
        GreetingsService service1 = container.select(GreetingsService.class).get();
        GreetingsService service2 = container.select(GreetingsService.class).get();
        service1.getGreeting("CDI-Tester1");
        service2.getGreeting("CDI-Tester2");
        MessageRepository repo = container.select(MessageRepository.class).get();
        MessageRepository repo2 = container.select(MessageRepository.class).get();
        assertThat(service1).isNotSameAs(service2);
        assertThat(repo).isSameAs(repo2);
        assertThat(CdiMessageRepository.getCounter()).isEqualTo(1);
    }
}
