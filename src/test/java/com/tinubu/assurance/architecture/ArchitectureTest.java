package com.tinubu.assurance.architecture;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.lang.ArchRule;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

public class ArchitectureTest {

    /**
     * Vérifie que les classes du package "domain" ne dépendent pas des couches
     * "infrastructure", "application" ou "api", conformément aux principes DDD ou de l'architecture hexagonale.
     * Cette règle garantit l'indépendance du modèle métier (domain) vis-à-vis des couches techniques ou exposées.
     */
    @Test
    public void packagesDependenciesChecks() {
        JavaClasses importedClasses =
                new ClassFileImporter()
                        .withImportOption(new ImportOption.DoNotIncludeTests())
                        .importPackages("com.tinubu.assurance.domain");

        ArchRule rule =
                noClasses()
                        .that()
                        .resideInAPackage("com.tinubu.assurance.domain..")
                        .should()
                        .dependOnClassesThat()
                        .resideInAnyPackage(
                                "..infrastructure..",
                                "..application..",
                                "..api..")
                        .as("Les classes du domaine ne doivent pas dépendre des couches infrastructure, application ou API");

        rule.check(importedClasses);
    }
}
