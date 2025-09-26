## Exercise 5:
Dependency Injection with Java and Weld
Goal: Learn the basics of Dependency Injection (DI) in Java:

[] Understand constructor injection by hand.
[] Build a minimal DI container that resolves dependencies recursively.
[] Use Weld (CDI) to handle injection automatically.
# Part 1 – Manual Constructor Injection
[] Create at least two interfaces and multiple implementations (e.g., a service and a persistence layer).
[] In a Main class, manually instantiate the dependencies and pass them into constructors.
[] Run the application and confirm that dependencies are correctly wired.
Hint: Focus on constructor injection — no setters, no static factories.

# Part 2 – A Minimal DI Container
[] Create a simple “container” class with a method to return an instance of a requested class.
[] If the requested class has a constructor with parameters, the container should recursively request those dependencies as well.
[] Demonstrate this by asking the container only for a top-level class and verifying that the entire dependency graph is created automatically.
Hint: Reflection will be useful. You can assume each class has only one constructor.

# Part 3 – Using Weld (CDI)
[] Add Weld as a dependency to your project.

[] If you use Maven, include weld-se-core.
[] Create a beans.xml file under META-INF (or WEB-INF if you later move to a web application).

The file can be empty but must exist for CDI to be activated.
[] Annotate your classes with CDI annotations.

In Java SE, you can use the following:

@Inject – mark a constructor, method, or field where a dependency should be injected.
→ In this lab, focus on constructor injection.
@ApplicationScoped – the bean lives for the entire lifecycle of the application (singleton-like).
@Dependent – default scope, a new instance is created each time.
@Singleton – alternative to @ApplicationScoped.
Do not use @RequestScoped, @SessionScoped, or @ConversationScoped — they require a web container.

[] Replace your custom container with Weld. Start Weld and ask it for your top-level class.

[] Run the application and compare the behavior with Part 1 and Part 2.

What to Think About
- Why is constructor injection often preferred over field or setter injection?
- What problems did you encounter writing your own container that Weld solved automatically?
- How does adding scopes (@ApplicationScoped, etc.) change object lifetimes?
- Code Review
- When your lab work is complete, push your code to your repository and create a Pull Request (PR).

# Ask at least one other student to review your PR.
They should check readability, clarity of class design, and correct use of constructor injection.
They should also verify that you followed the allowed CDI annotations.
After receiving feedback, update your code if needed and finalize your PR.

Goal: Practice peer review and learn how to evaluate design decisions in other people’s code.


