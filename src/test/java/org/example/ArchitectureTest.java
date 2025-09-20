package org.example;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.lang.ArchRule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

class ArchitectureTest {

    private JavaClasses importedClasses;

    @BeforeEach
    void setUp() {
        importedClasses = new ClassFileImporter().importPackages("org.example");
    }

    @Test
    void servicePackageShouldNotAccessRepositoryPackage() {

        ArchRule rule = noClasses()
                .that().resideInAPackage("..service..")
                .should().accessClassesThat().resideInAPackage("..repository..")
                .as("Service package must not access repository package.");

        System.out.println("Testing: Service package should not access repository package.");
        rule.check(importedClasses);
        System.out.println("PASSED: Service package does not access repository package.");
    }

}
