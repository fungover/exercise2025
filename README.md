Mitt Warehouse består av kläder, det kommer att vara min produkt men skall kategoriseras i typ, färg osv. 

Strukturen ska vara följande: 
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
Use JUnit 5 for all unit tests. Each public method must have: At least one test for successful execution At least one test for failure (e.g. invalid input)