package org.example.di.manual;

import org.example.di.common.GreetingsService;
import org.example.di.common.MessageRepository;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class SimpleGreetingServiceTest {
    @Test
    void getGreeting_shouldCombineMessageAndName() {
        MessageRepository repo = new InMemoryMessageRepository();
        GreetingsService service = new SimpleGreetingService(repo);
        assertThat(service.getGreeting("World")).isEqualTo("Hello, World!");

    }
}
