## Development Documentation

#### Assignment 4: _Refactor previous exercise, by implementing design patterns_

### Reflections/Learning process:
### Creational pattern (Builder pattern) 
will make my code easier to read and modify. 
I will not have to remember the order of parameters in my constructor.
Builder can be used to construct unrelated products that does not even share interface, 
but in my case builder will only be able to go through my Product constructor. 
It can therefore only build products. The builder will be a so called 'nested public static class' in my current Product class.
### Structural pattern (Repository pattern) 
is not found in this documentation: https://refactoring.guru/design-patterns/structural-patterns
But the main reason to use this pattern is to separate the way we store and retrieve data,
from how we internally work with the data. https://medium.com/@hunterfreas/repository-pattern-what-is-it-and-why-do-we-use-it-6a6a8e781711
My first step is to separate the responsibilities (SRP) in my Warehouse.

### Metaperspective of what I need to do to solve this issue:
[x] an interface that defines a contract for storage. This will put a layer between my data and logic. Here
I will define abstract operations.

[x] to implement the above interface - this is the storage (HashMap). All data is handled here. I separate
"CRUD" (create, read, update, delete) from business logic. This makes it easy for me to try different
implementations (Local or database)

[x] A product service that handles only business logic. Here is where the validation, Building and Streams
happens.

_Some thoughts documented before creating my PR, since this process got a bit messy and complex
to comprehend fully mid-refactoring._
First I made an immutable model (product) with final fields that were only accessible from the
constructor. Changes were made with the "with"- methods but were soon to be optimized with Builder.
When implementing a repository pattern I delegated responsibilities to different parts of 
the codebase. InMemoryProductRepository I handled storage with CRUD in a Map.
ProductService handled business logic, like validation, sorting, filtering and streams. 
ProductService got its repository through a Dependency injection in the constructor. 
This makes it easy to connect storage with a future Database, without messing up the business logic.
My Product object can not be changed post creation. It is also secured with validation
protecting the data even further. 


### About Assignment 4:
🎯 Objective
The goal of this assignment is to refactor the existing Product Management application by implementing
several common design patterns. This will improve the application's structure,
making it more flexible, maintainable, and testable.

### ✅ Part 1: The Builder Pattern (Creational)
Currently, creating a Product object requires a constructor with many parameters.
This can become hard to read and inflexible if we add more attributes in the future.
We will solve this using the Builder Pattern.

## Your Task
[x] Create a Builder Class: Inside your Product class/record, create a public static nested class named Builder.

[x] Add Fields to the Builder:
The Builder class should have fields that correspond to the attributes of the Product
(e.g., id, name, category, rating).

[x] Implement "Setter" Methods:
For each field, create a public method that sets the value and returns the Builder instance (this).
This allows for method chaining.
Example: public Builder name(String name) { ... }

[x] Implement the build() Method: Create a public build() method in the Builder.
This method will be responsible for:
- Calling the Product's constructor with the values stored in the builder.
- Returning the newly created, immutable Product instance.
- This is the ideal place to handle validation (e.g., checking if name is empty)
  or setting default values (like createdDate and modifiedDate).

[x] Make the Product Constructor Private:
To ensure that Product objects can only be created via the builder,
make the original Product constructor private.

[x] Update Your Code:
Refactor your Warehouse class and your unit tests
to use the new builder for creating products.

## Before:
Product product = new Product
("id1", "SuperWidget", Category.TOOLS, 8, LocalDate.now(), LocalDate.now());

## After (Example Usage):
Product product = new Product.Builder()
.id("id1")
.name("SuperWidget")
.category(Category.TOOLS)
.rating(8)
.build();

### ✅ Part 2: The Repository Pattern (Structural)
The Warehouse class currently has two responsibilities:
it manages the product collection (data access) and implements business logic (filtering, sorting).
We will use the Repository Pattern to separate these concerns.

## Your Task
[x] Create a ProductRepository Interface:
Create a new interface named ProductRepository in a repository package.

[x] Define the contract for data access operations. It should include methods like:
void addProduct(Product product)
Optional<Product> getProductById(String id)
List<Product> getAllProducts()
void updateProduct(Product product)

[x] Create an InMemoryProductRepository:
Create a new class InMemoryProductRepository that implements ProductRepository.

[x] Move the List or Map that stores your products from the Warehouse into this new class.

[x]Implement the interface methods. This class will now be solely responsible for managing the in-memory collection.

Refactor Warehouse into a Service:
[x] Rename the Warehouse class to ProductService. This better reflects its new role.

[x] Remove the product collection from the ProductService.

[x] Add a private final field for ProductRepository and initialize it via the constructor (this is called Dependency Injection).

[x] Update all methods in ProductService to use the repository for data access. For example, getAllProducts() will now simply call productRepository.getAllProducts().

[x] Update Application Startup: In your Main class (or wherever you start the application), you will now need to:

[x] Create an instance of InMemoryProductRepository.

[x] Create an instance of ProductService, passing the repository instance to its constructor.

___________________________
### ⭐ Part 3: Extra Credits - The Decorator Pattern (Structural)
The Decorator Pattern allows you to add new functionality to an object dynamically
without altering its class structure.
This is useful when you want to add responsibilities to individual objects, not to the entire class.
For this exercise, we will add a price to our Product and then "decorate" it with a discount.

## Your Task
Add Price to Product:
[] First, modify your Product record/class and its Builder to include a price attribute (e.g., a double or BigDecimal). Update the constructor and builder accordingly.

Create a Sellable Interface:
[] Create a new interface named Sellable.
It should define methods that a sellable item would have, for instance:
- String getName()
- double getPrice()
- String getId()

Implement the Interface:
[] Make your Product class implement the Sellable interface.

Create an Abstract ProductDecorator:
[] Create an abstract class named ProductDecorator that also implements Sellable.
It should hold a reference to a Sellable object (e.g., protected Sellable decoratedProduct;).
The constructor should accept a Sellable object to wrap.

[] Implement the interface methods by delegating the calls to the decoratedProduct.
For example, getPrice() would simply call decoratedProduct.getPrice().
[] Create a Concrete DiscountDecorator:
[] Create a new class DiscountDecorator that extends ProductDecorator.
It should have a field for the discount percentage (e.g., private double discountPercentage).
Its constructor should accept a Sellable product and the discount percentage.
[] Override the getPrice() method. This is where the new functionality is added.
The method should get the original price from the decorated product and return the new, discounted price.
Show it in Action:
[] In a test or your Main class, demonstrate how you can take a regular Product and wrap it
with your DiscountDecorator to change its price dynamically.

Example Usage:
// Assuming 'productService' can retrieve a product
Product laptop = productService.getProductById("some-id"); // Original price is 1000.0

// Now, decorate it with a 20% discount
Sellable discountedLaptop = new DiscountDecorator(laptop, 20);

System.out.println("Laptop's original price: " + laptop.getPrice()); // Should print 1000.0
System.out.println("Laptop's discounted price: " + discountedLaptop.getPrice()); // Should print 800.0

🧪 Testing
All existing unit tests must be updated to work with the new ProductService and Product.Builder.
You may need to pass a mock or a real InMemoryProductRepository instance to your ProductService during testing.
Ensure any new public methods are fully covered by unit tests.
Good luck!

_______________________