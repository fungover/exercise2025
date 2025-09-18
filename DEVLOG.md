# Development Documentation
_______________________
### Assignment 4 (_Refactor previous exercise, by implementing design patterns_)

### Reflections/Learning process:
By implementing the creational pattern called "Builder pattern" I will make my code 
easier to read and modify. I will not have to remember the order of parameters in my constructor.
Builder can be used to construct unrelated products that does not even share interface, but in my case
builder will only be able to go through my Product constructor. It can therefore only build products.
The builder will be a so called 'nested public static class' in my current Product class. 


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
[] Create a Builder Class: Inside your Product class/record, create a public static nested class named Builder.

[] Add Fields to the Builder: 
The Builder class should have fields that correspond to the attributes of the Product 
(e.g., id, name, category, rating).

[] Implement "Setter" Methods: 
For each field, create a public method that sets the value and returns the Builder instance (this). 
This allows for method chaining.
Example: public Builder name(String name) { ... }

[] Implement the build() Method: Create a public build() method in the Builder. 
This method will be responsible for:
- Calling the Product's constructor with the values stored in the builder.
- Returning the newly created, immutable Product instance.
- This is the ideal place to handle validation (e.g., checking if name is empty) 
or setting default values (like createdDate and modifiedDate).

[] Make the Product Constructor Private: 
To ensure that Product objects can only be created via the builder, 
make the original Product constructor private.

[] Update Your Code: 
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
[] Create a ProductRepository Interface:
[] Create a new interface named ProductRepository in a repository package.
[] Define the contract for data access operations. It should include methods like:
void addProduct(Product product)
Optional<Product> getProductById(String id)
List<Product> getAllProducts()
void updateProduct(Product product)
[] Create an InMemoryProductRepository:
[] Create a new class InMemoryProductRepository that implements ProductRepository.
[] Move the List or Map that stores your products from the Warehouse into this new class.
[]Implement the interface methods. This class will now be solely responsible for managing the in-memory collection.

Refactor Warehouse into a Service:
[] Rename the Warehouse class to ProductService. This better reflects its new role.
[] Remove the product collection from the ProductService.
[] Add a private final field for ProductRepository and initialize it via the constructor (this is called Dependency Injection).
[] Update all methods in ProductService to use the repository for data access. For example, getAllProducts() will now simply call productRepository.getAllProducts().
[] Update Application Startup: In your Main class (or wherever you start the application), you will now need to:
[] Create an instance of InMemoryProductRepository.
[] Create an instance of ProductService, passing the repository instance to its constructor.

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
### Assignment 3 (_Build a warehouse Manager_)

### Reflections/Learning process:
IDE hints about my compact constructor in Product.java.
IntelliJ can’t visually distinguish whether category is 
the parameter or the field, since both have the same name. 

'this.category' is the field and 'category' the parameter. 
This is more evident when using canonical constructors, not 
compact like I do. Validation is taken care of only in the constructor, 
so my with methods does not need any checks. 



### About Assignment 3
Exercise 3 – Java Programming: Warehouse Product Manager 
🧠 Objective Implement a small Java application for managing 
products using Java 8 streams and JUnit 5 for unit testing. 

The public interface will be a class called Warehouse 
in the service package. All public methods must be covered 
by unit tests. Use this projects code as a base and 
implement your solution in a branch. 

Pair programming is allowed. 

For joint commits, use the Co-authored-by: 
tag as described in GitHub's co-authoring guide. 

✅ Requirements for Pass (G) 
📁 Entities Create a Product class in the entities package with the following attributes: 
id: Unique identifier 
name: Product name 
category: Enum type 
rating: Integer (0–10) 
createdDate: Immutable creation date 
modifiedDate: Last modified date 

Use Java records or immutable objects to prevent external mutation. 
Avoid returning internal references like ArrayList directly. 

🛠️ Warehouse Functionality Implement the following public 
methods in Warehouse with corresponding unit tests (success + failure cases where applicable): 

[x] addProduct (Product product): Add a new product(validate name is not empty) 
[x] updateProduct(String id, String name, Category category, int rating): Modify an existing product 
[x] getAllProducts(): Retrieve all products 
[x] getProductById(String id): Retrieve product by ID 
[x] getProductsByCategorySorted(Category category): Products in a category, sorted A–Z by name 
[x] getProductsCreatedAfter(LocalDate date): Products created after a given date 
[x] getModifiedProducts(): Products where createdDate != modifiedDate 

Use Java 8 streams where appropriate. 

🌟 Requirements for Distinction (VG) 
Extend Warehouse with the following methods: 
* getCategoriesWithProducts(): Return all categories with at least one product 
* countProductsInCategory(Category category): Return number of products in a category 
* getProductInitialsMap(): Return a Map<Character, Integer> of first letters in product names and their counts 
* getTopRatedProductsThisMonth(): Products with max rating, created this month, sorted by newest first 

🧪 Testing Use JUnit 5 for all unit tests. 
Each public method must have: 
At least one test for successful execution 
At least one test for failure (e.g. invalid input)

