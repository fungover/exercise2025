package org.example.di.container;
import org.example.di.common.GreetingsService;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class SimpleContainerTest {
    @Test
    void getInstance_shouldResolveClassWithoutDependencies() {
        SimpleContainer container = new SimpleContainer();
        MessageRepoImp repo = container.getInstance(MessageRepoImp.class);
        assertThat(repo).isNotNull();
        assertThat(repo.getMessage()).isEqualTo("Hi");
    }
    @Test
    void getInstance_shouldRecursivelyResolveDependencies() {
        SimpleContainer container = new SimpleContainer();
        GreetingsService service = container.getInstance(GreetingServiceImp.class);
        assertThat(service).isNotNull();
        assertThat(service.getGreeting("Test")).isEqualTo("Hi, Test!");
    }
}
