
Uppdatering: 9/10
Mitt Warehouse består av kläder,t.ex. klänningar, skjortor, kostymer.
Builder Pattern och Repository pattern är implementerat. 
Logiken är testad med JUnit 5.

Build Pattern ligger i Product.

Product p = new Product.Builder()
.id("1")
.name("Red Dress")
.category(Category.DRESS)
.rating(5)
.build();


Tillkommet för Repository Pattern: 
ProductRepository - Kontrakt för dataåtkomsten.
SaveProductRepository - Listan för lagring.
ProductService - sammankopplad med ProductRepository, förser logiken.




//

Projektstrukturen är följande: 
- Entities 
- Produkt
- Kategori
- Service, med klasser för att hantera produkterna, samla eller utesluta osv. 
- Tester för alla metoderna. 


Efterfrågad funktionalitet:
"Implement the following public methods in Warehouse with corresponding 
unit tests (success + failure cases where applicable):"
addProduct(Product product)

updateProduct(String id, String name, Category category, int rating)

getAllProducts()

getProductById(String id)

getProductsByCategorySorted(Category category)

getProductsCreatedAfter(LocalDate date)

getModifiedProducts()


Krav på testerna:
14 godkända tester gjorda.

("Use JUnit 5 for all unit tests. Each public method must have: At least one test 
for successful execution At le ast one test for failure (e.g. invalid input)") 
