package org.example;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.lang.ArchRule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
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

    @Test
    void repositoryPackageShouldNotAccessServicePackage() {

        ArchRule rule = noClasses()
                .that().resideInAPackage("..repository..")
                .should().accessClassesThat().resideInAPackage("..service..")
                .as("Repository package must not access service package.");

        System.out.println("Testing: Repository package should not access service package.");
        rule.check(importedClasses);
        System.out.println("PASSED: Repository package does not access service package.");
    }

    @Test
    void repositoryPackageShouldOnlyDependOnDomainPackage() {

        ArchRule rule = classes()
                .that().resideInAPackage("..repository..")
                .should().onlyDependOnClassesThat().resideInAnyPackage(
                        "java..",
                        "..domain..",
                        "..repository.."
                )
                .as("Repository package should only depend on domain package");

        System.out.println("Testing: Repository package should only depend on domain package.");
        rule.check(importedClasses);
        System.out.println("PASSED: Repository package only depends on domain package.");
    }

    @Test
    void domainInterfacesShouldNotImportConcreteImplementations() {

        ArchRule rule = noClasses()
                .that().resideInAPackage("..domain..")
                .should().accessClassesThat().resideInAnyPackage(
                        "..service..",
                        "..repository.."
                )
                .as("Domain interfaces must not import concrete implementations");

        System.out.println("Testing: Domain package should not import concrete service/repository classes");
        rule.check(importedClasses);
        System.out.println("PASSED: Domain interfaces correctly does not import concrete implementations.");
    }

    @Test
    void appClassShouldBeOnlyPlaceToWireEverythingTogether() {

        ArchRule rule = noClasses()
                .that().resideInAnyPackage("..service..", "..repository..", "..domain..")
                .should().accessClassesThat().resideInAPackage("org.example")
                .andShould().accessClassesThat().haveSimpleName("App")
                .as("Only App class should wire everything together.");

        System.out.println("Testing: Only App class should access all packages for wiring.");
        rule.check(importedClasses);
        System.out.println("PASSED: Only app class correctly wires dependencies.");
    }

}
