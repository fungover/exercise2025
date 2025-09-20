package org.example;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import org.junit.jupiter.api.BeforeEach;

class ArchitectureTest {

    private JavaClasses importedClasses;

    @BeforeEach
    void setUp() {
        importedClasses = new ClassFileImporter().importPackages("org.example");
    }

}
