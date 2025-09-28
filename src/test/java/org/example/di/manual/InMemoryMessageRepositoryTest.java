package org.example.di.manual;

import org.example.di.common.MessageRepository;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class InMemoryMessageRepositoryTest {
    @Test
    void getMessage_shouldReturnFixedMessage() {
        MessageRepository repo = new InMemoryMessageRepository();
        assertThat(repo.getMessage()).isEqualTo("Hello");
    }
}
