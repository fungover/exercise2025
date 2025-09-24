### Why is constructor injection often preferred over field or setter injection?
- Constructor injection clearly defines all required dependencies when the object is created.
- Ensures an object can't exist in an incomplete state.
- Supports immutability due to final fields.
- Makes testing easier.

### What problems did you encounter writing your own container that Weld solved automatically?
- Had to manually register every class. Telling the container which implementation to use.
- Lots of code just to set up the container.
- Setup code in main looked bloated with all manual registrations.

With Weld I just added annotations and it figured out the rest.

### How does adding scopes (@ApplicationScoped, etc.) change object lifetimes?
- @ApplicationScoped means Weld creates the object once and keeps reusing the same one
- @Dependent means Weld creates a fresh object every time you ask for it
- My custom container always created new objects. It didn't have this choice.
- Scopes let you control whether you get the same instance or a brand new one

I assume you would use @ApplicationScoped for databases perhaps and @Dependent for a shopping cart for example.