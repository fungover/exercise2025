## What to think about

Questions and answers for the assignment below.

### Why is constructor injection often preferred over field or setter injection?

Based on my code in Part 1:
````java
public class IntelCPUService implements CPUService {
    private final ComponentRepository repository;

    public IntelCPUService(ComponentRepository repository) {
        this.repository = repository; // Guaranteed to exist when object is created
    }
    //...
}
````
- Immutable dependencies: Can use ```final``` fields.
- If dependencies are missing, you get errors at object creation time, not later during runtime.
- You can immediately see what a class needs to function.
- Easier to create objects in tests by sending in mock-dependencies.

Compare this to field injection:
````java
@Inject
private ComponentRepository repository; // Could be null during construction
````

### What problems did you encounter writing your own container that Weld solved automatically?

- Interface mapping, in my own container you need to hardcode which implementations to use.
````java
// Part2 example:
interfaceBindings.put(org.example.part1.services.CPUService.class,
                org.example.part1.services.IntelCPUService.class);
````

- When I had multiple GPU services, my container didn't know which one to use.
- I had to manually configure all mappings.
#### Weld solved this:
- Automatically finds classes with ```@ApplicationScoped``` annotation.
- Only annotations are needed, no hardcoded mappings.


### How does adding scopes (```@ApplicationScoped```, etc.) change object lifetimes?
````java
// Example without scope in Part 2:
if (singletonInstances.containsKey(clazz)) {
return (T) singletonInstances.get(clazz); // Reused
}
```` 
With ```@ApplicationScoped```:
- Weld handles the lifecycle of the object.
- One instance per application lifecycle.
- In my tests, I saw that the same ```InMemoryComponentRepository``` instance was reused:
````java
assertThat(repo1).isSameAs(repo2); // Same instance with @ApplicationScoped
````
- With `@ApplicationScoped` on the `ComponentRepository`,
all the services get the same repository instance.

### Conclusion
This lab showed the evolution from manual dependency management to professional frameworks.
Each part built understanding:
- Part 1 showed the principles.
- Part 2 revealed the complexity of building DI infrastructure.
- Part 3 demonstrated how frameworks like Weld handle this complexity automatically.