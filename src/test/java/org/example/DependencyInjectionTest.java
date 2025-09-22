package org.example;

import org.example.detective.Detective;
import org.example.detective.SherlockHolmes;
import org.example.repository.Crime;
import org.example.repository.CrimeArchive;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;

class DependencyInjectionTest {

    @Test
    void shouldCreateCrimeArchiveAndReturnCrimeDetails() {
        // Given
        Crime crimeRepo = new CrimeArchive();

        // When
        String result = crimeRepo.getCrimeDetails("TEST-CASE");

        // Then
        assertThat(result).isEqualTo("Crime scene: Baker street. Witness: Mrs Hudson.");
    }

    @Test
    void shouldCreateSherlockHolmesWithCrimeDependency() {
        // Given
        Crime crimeRepo = new CrimeArchive();
        Detective detective = new SherlockHolmes(crimeRepo);

        // When
        String result = detective.investigate("CASE-221B");

        // Then
        assertThat(result).contains("Investigating case number: CASE-221B");
        assertThat(result).contains("Crime scene: Baker street. Witness: Mrs Hudson.");
    }

    @Test
    void shouldCreateCompleteInvestigationChain() {
        // Given - Manual dependency injection like in App.main()
        Crime crimeRepo = new CrimeArchive();
        Detective detective = new SherlockHolmes(crimeRepo);
        Investigation investigation = new Investigation(detective);

        // When & Then - Should not throw any exceptions and dependencies should work
        assertThatNoException().isThrownBy(() -> investigation.startInvestigation("CASE-TEST"));
    }

    @Test
    void shouldUseConstructorInjection() {
        // Given
        Crime crimeRepo = new CrimeArchive();

        // When - Constructor injection should work
        Detective detective = new SherlockHolmes(crimeRepo);
        Investigation investigation = new Investigation(detective);

        // Then - Objects should be created successfully
        assertThat(detective).isNotNull();
        assertThat(investigation).isNotNull();
    }

    @Test
    void shouldDemonstrateManualDependencyWiring() {
        // This test demonstrates manual wire dependencies

        // Given - Manual instantiation in correct order
        var crimeRepo = new CrimeArchive();           // No dependencies
        var detective = new SherlockHolmes(crimeRepo);  // Depends on Crime
        var engine = new Investigation(detective);      // Depends on Detective

        // When
        // This should work because we manually resolved the dependency chain
        assertThatNoException().isThrownBy(() -> engine.startInvestigation("MANUAL-TEST"));

        // Then - All dependencies should be properly injected
        assertThat(crimeRepo).isNotNull();
        assertThat(detective).isNotNull();
        assertThat(engine).isNotNull();
    }
}
