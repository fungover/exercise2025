# This is my reflections on PR 171, branch: RobTrb/exercise5

## What I did to test code quality:
[✅] Added Checkstyle and SpotBugs linters in Maven to detect bugs, 
formatting issues, and naming inconsistencies

[✅] Check if 'mvn verify' builds correct with Java 24

[✅] Check security and quality of main method 
_(Commands: java -cp target/classes org.example.MainDIContainer
java -cp target/classes org.example.MainManualInjection)_

-> Each main method have great SRP and a good use of try-with-resources for safe container shutdown. 
-> The structure is clean — main() only starts the application and delegates logic to another class.
-> This is really robust and follows solid java and DI design principles. 


[✅] Verified that package names follow Java conventions (all lowercase)
❌Suggested improvement:
Rename 'org.example.DIContainer' -> 'org.example.di' or 'org.example.dicontainer'

## What I did test
[✅] Weld/CDI container lifecycle: Ensured that it starts and shuts down correctly (This is real enterprise ID 😍)
[✅] CDI-managed instance creation: Ensured that an instance of WeldInjection is created 
automatically by the container using dependency discovery, reflection, and recursion.
[✅] Dependency injection validation: Confirmed that a valid implementation of ChatService 
is successfully injected into WeldInjection, proving that qualifiers and scopes are 
configured correctly.

[✅] All tests (mvn test) pass with Java 24, which confirms full dependency injection functionality 
with all three approaches. (manual injection, custom DI container, CDI/Weld framework)

To handle multiple implementations of the ChatService interface, 
I used qualifiers so CDI knows which dependency to inject.
For example: 
-> If you ask for ChatService with @MainChatServiceQualifier, you really get MainChatService.
-> If you ask for ChatService with @LoggingChatServiceQualifier, you really get LoggingChatService.




