package org.example;


import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

public class ArchTest {
  @Test
  public void disallowDirectDependenciesBetweenPackageAAndB() {
    ArchRule no_direct_dependencies = ArchRuleDefinition
            .noClasses()
            .that()
            .resideInAPackage("..repository..")
            .should()
            .accessClassesThat()
            .resideInAPackage("..service..")
            .as("no_direct_dependencies");

    JavaClasses importedClasses = new ClassFileImporter().importPackages("org.example");
    no_direct_dependencies.check(importedClasses);
  }

  @Test
  public void onlyDependOnPackageC() {
    ArchRule only_depend_on_packageC = ArchRuleDefinition
            .classes().that().resideInAPackage("..repository..")
            .should().onlyDependOnClassesThat()
            .resideInAnyPackage("..repository..", "..users..", "java..", "jakarta..");

    JavaClasses importedClasses = new ClassFileImporter().importPackages("org.example");
    only_depend_on_packageC.check(importedClasses);
  }
}
