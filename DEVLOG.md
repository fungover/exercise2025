## Development Documentation
### Reflections/Learning process
IDE hints about my compact constructor in Product.java.
IntelliJ can’t visually distinguish whether category is 
the parameter or the field, since both have the same name. 

'this.category' is the field and 'category' the parameter. 
This is more evident when using canonical constructors, not 
compact like I do. 



### Assignment
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

* addProduct (Product product): Add a new product(validate name is not empty) 
* updateProduct(String id, String name, Category category, int rating): Modify an existing product 
* getAllProducts(): Retrieve all products 
* getProductById(String id): Retrieve product by ID 
* getProductsByCategorySorted(Category category): Products in a category, sorted A–Z by name 
* getProductsCreatedAfter(LocalDate date): Products created after a given date 
* getModifiedProducts(): Products where createdDate != modifiedDate 

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