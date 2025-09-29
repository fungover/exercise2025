package org.example.di.container;
import org.example.di.common.GreetingsService;
import org.example.di.common.MessageRepository;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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
    @Test
    void getInstance_shouldResolveInterfaceWithRegisteredImplementation() {
        SimpleContainer container = new SimpleContainer();
        MessageRepository repo = container.getInstance(MessageRepository.class);
        assertThat(repo).isNotNull();
        assertThat(repo.getMessage()).isEqualTo("Hi");
    }
    @Test
    void getInstance_shouldThrowForUnregisteredInterface() {
        SimpleContainer container = new SimpleContainer();
        assertThatThrownBy(() -> container.getInstance(Runnable.class))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("No implementation found for java.lang.Runnable");
    }
}