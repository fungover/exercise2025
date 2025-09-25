package org.example.part2;

public class TestClassWithDependency {
    private final SimpleTestClass dependency;

    // Constructor with parameter
    public TestClassWithDependency(SimpleTestClass dependency) {
        this.dependency = dependency;
    }

    public String getMessage() {
        return "I have dependency: " + dependency.getMessage();
    }
}
