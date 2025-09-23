package org.example;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.lang.ArchRule;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

public class ArchUnitTest {

    @Test
    public void container_should_not_depend_on_detective_or_repository() {
        JavaClasses classes = new ClassFileImporter().importPackages("org.example");

        ArchRule rule = noClasses()
                .that().resideInAPackage("..container..")
                .and().areNotAssignableTo(org.example.container.Container.class)
                .should().dependOnClassesThat()
                .resideInAnyPackage("..detective..", "..repository..")
                .allowEmptyShould(true);

        rule.check(classes);
    }

    @Test
    public void detective_should_only_depend_on_repository_and_java() {
        JavaClasses classes = new ClassFileImporter().importPackages("org.example");

        ArchRule rule = classes()
                .that().resideInAPackage("..detective..")
                .should().onlyDependOnClassesThat()
                .resideInAnyPackage("..detective..", "..repository..", "java..", "jakarta.inject..", "jakarta.enterprise.context..");

        rule.check(classes);
    }
}

