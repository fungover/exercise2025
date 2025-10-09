package org.example.weld;

import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import org.example.service.*;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class WeldInjectionTest {

    @Test
    void shouldInjectMainChatServiceWithQualifier() {
        try (SeContainer c = SeContainerInitializer.newInstance().initialize()) {
            ChatService chat = c.select(ChatService.class, new MainChatServiceQualifierLiteral()).get();
            assertThat(chat).isNotNull();
            assertThat(chat).isInstanceOf(MainChatService.class);
        }
    }

    @Test
    void shouldInjectLoggingChatServiceWithQualifier() {
        try (SeContainer c = SeContainerInitializer.newInstance().initialize()) {
            ChatService chat = c.select(ChatService.class, new LoggingChatServiceQualifierLiteral()).get();
            assertThat(chat).isNotNull();
            assertThat(chat).isInstanceOf(LoggingChatService.class);
        }
    }
}
