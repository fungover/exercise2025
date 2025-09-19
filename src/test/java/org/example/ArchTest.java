package org.example;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition;
import org.junit.jupiter.api.Test;

public class ArchTest {

    @Test
    public void disallowDirectDependenciesBetweenPackageAAndPackageB() {
        ArchRule no_direct_dependencies = ArchRuleDefinition
                .noClasses().that().resideInAPackage("..packageA..")
                .should().accessClassesThat().resideInAPackage("..packageB..")
                .as("PackageA must not depend on any classes from packageB");

        JavaClasses importedClasses = new ClassFileImporter().importPackages("org.example");

        no_direct_dependencies.check(importedClasses);
    }

    @Test
    public void onlyDependOnpackageC() {
        ArchRule only_depend_on_packagec = ArchRuleDefinition
                .classes().that().resideInAPackage("..packageA..")
                .should().dependOnClassesThat().resideInAPackage("..packageC..");
        JavaClasses importedClasses = new ClassFileImporter().importPackages("org.example");

        only_depend_on_packagec.check(importedClasses);
    }






}
