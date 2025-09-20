package org.example;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.lang.ArchRule;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

public class ArchUnitTest {

    @Test
    public void services_should_only_depend_on_interfaces_and_entities() {
        JavaClasses classes = new ClassFileImporter().importPackages("org.example");
        ArchRule rule = classes()
                .that().resideInAPackage("..services..")
                .should().onlyDependOnClassesThat().resideInAnyPackage(
                        "..entities..",
                        "..interfaces..",
                        "java.lang..",
                        "java.util..",
                        "java.io.."
                );

        rule.check(classes);
    }

    @Test
    public void repositories_should_not_depend_on_services(){
        JavaClasses classes = new ClassFileImporter().importPackages("org.example");
        // noClasses() ensures NO repos depend on services
        ArchRule rule = noClasses()
                .that().resideInAPackage("..repositories..")
                .should().dependOnClassesThat().resideInAPackage("..services..");

        rule.check(classes);
    }
}