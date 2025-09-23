# Exercise 5 – Dependency Injection

---

## Part 1 – Manual Constructor Injection

Dependencies are manually wired using constructors:

- `CrimeArchive` implements `Crime`
- `SherlockHolmes` implements `Detective` and receives a `Crime` instance
- `Investigation` receives a `Detective` instance

This part demonstrates how dependencies can be resolved without any framework.

---

## Part 2 – Custom DI Container

A `Container` class is built to:

- Automatically instantiate classes using reflection
- Resolve constructor dependencies recursively
- Handle interface-to-implementation mapping

This part shows how a basic DI engine can be implemented manually.

---

## Part 3 – CDI with Weld

Weld is introduced as a CDI framework:

- Classes are annotated with `@Inject` and `@ApplicationScoped`
- A `beans.xml` file activates CDI
- Weld resolves dependencies automatically

This part compares manual DI with a real-world framework.

---

## Tests Overview

### Functional Tests (`DependencyInjectionTest.java`)
These tests verify that:

- Manual wiring works correctly
- The custom container can resolve dependencies
- The investigation flow runs without exceptions

### Architectural Tests (`ArchUnitTest.java`)
These tests ensure that:

- Packages follow clean dependency boundaries
- `detective` does not depend on `container`
- `container` is isolated from `detective` and `repository`

Together, they validate both runtime behavior and structural integrity.

---
