package org.example.part3;

import org.example.part3.repository.InMemoryComponentRepository;
import org.example.part3.services.IntelCPUService;
import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class WeldCDITest {

    private Weld weld;
    private WeldContainer container;

    @AfterEach
    void cleanup() {
        if (weld != null) {
            weld.shutdown();
        }
    }

    @Test
    void weldShouldInjectDependenciesAutomatically() {
        weld = new Weld();
        container = weld.initialize();

        IntelCPUService service = container.select(IntelCPUService.class).get();

        assertThat(service).isNotNull();

        String cpu = service.selectCPU("Gaming");

        assertThat(cpu).contains("Gaming");
        assertThat(cpu).contains("Intel");

        System.out.println("Weld injected dependencies successfully!");
        System.out.println("CPU: " + cpu);
    }

    @Test
    void weldShouldUseSameRepositoryInstance() {
        weld = new Weld();
        container = weld.initialize();

        InMemoryComponentRepository repo1 = container.select(InMemoryComponentRepository.class).get();
        InMemoryComponentRepository repo2 = container.select(InMemoryComponentRepository.class).get();

        assertThat(repo1).isSameAs(repo2);

        System.out.println("Weld correctly uses singleton @ApplicationScoped beans!");

    }

}
