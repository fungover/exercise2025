package org.example;

import org.example.repository.InMemoryProductRepository;
import org.example.repository.ProductRepository;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ProductServiceTest {
	@Test
	void testWarehouse() {
		ProductRepository productRepository = new InMemoryProductRepository();
		ProductService productService = new ProductService(productRepository);
		assertThat(productService).isNotNull();
		assertThat(productRepository.getAllProducts().isEmpty());
	}

	@Test
	void testAddingAProductToWarehouseAndWarehousListIncreases() {
		ProductRepository productRepository = new InMemoryProductRepository();
		ProductService productService = new ProductService(productRepository);
		Product product = new Product.ProductBuilder()
						.setId("SUPER_GAME")
						.setName("Little Leaf Lizards")
						.setCategory(Category.GAMES)
						.setRating(10)
						.build();
		Product product2 = new Product.ProductBuilder()
						.setId("SUPER_DUPER_COMPUTER")
						.setName("Super Computer")
						.setCategory(Category.COMPUTERS)
						.setRating(7)
						.build();
		productRepository.addProduct(product);
		productRepository.addProduct(product2);
		Product recievedProduct = productRepository.getAllProducts().get(1);

		assertThat(productRepository.getAllProducts().size()).isEqualTo(2);
		assertThat(recievedProduct.id()).isEqualTo("SUPER_DUPER_COMPUTER");
	}

	@Test
	void testAddingAProductToWarehouseAndRetrievedValuesMatchTheAddedProduct() {
		ProductRepository productRepository = new InMemoryProductRepository();
		ProductService productService = new ProductService(productRepository);
		Product product = new Product.ProductBuilder()
						.setId("SUPER_COMPUTER")
						.setName("Super Computer")
						.setCategory(Category.COMPUTERS)
						.setRating(8)
						.build();
		productRepository.addProduct(product);
		Product recievedProduct = productRepository.getAllProducts().get(0);

		assertThat(recievedProduct.id()).isEqualTo("SUPER_COMPUTER");
		assertThat(recievedProduct.category()).isEqualTo(Category.COMPUTERS);
		assertThat(recievedProduct.rating()).isEqualTo(8);
	}

	@Test
	void testUpdatingAProduct() {
		ProductRepository productRepository = new InMemoryProductRepository();
		ProductService productService = new ProductService(productRepository);
		Product product = new Product.ProductBuilder()
						.setId("SUPER_GAME")
						.setName("Mage")
						.setCategory(Category.GAMES)
						.setRating(8)
						.build();
		productRepository.addProduct(product);

		assertThat(productRepository.getAllProducts().get(0).name()).isEqualTo("Mage");

		productRepository.updateProduct("SUPER_GAME", "Game", Category.GAMES, 10);

		assertThat(productRepository.getAllProducts().get(0).name()).isEqualTo("Game");
	}

	@Test
	void testUpdatingAProductWithAnIdThatDoesNotExist() {
		ProductRepository productRepository = new InMemoryProductRepository();
		ProductService productService = new ProductService(productRepository);
		Product product = new Product.ProductBuilder()
						.setId("SUPER_GAME")
						.setName("Mage")
						.setCategory(Category.GAMES)
						.setRating(8)
						.build();
		productRepository.addProduct(product);

		Exception exception = assertThrows(IllegalArgumentException.class, () -> {
			productRepository.updateProduct("SUPER_GAMER", "Mage", Category.GAMES, 10);
		});
		assertThat(exception.getMessage()).isEqualTo("Product ID does not exist");
	}

	@Test
	void testRetrieveAllProductsInWarehouse() {
		ProductRepository productRepository = new InMemoryProductRepository();
		ProductService productService = new ProductService(productRepository);

		Product product1 = new Product.ProductBuilder()
						.setId("SUPER_COMPUTER")
						.setName("Computer")
						.setCategory(Category.COMPUTERS)
						.setRating(8)
						.build();
		Product product2 = new Product.ProductBuilder()
						.setId("SUPER_GAME")
						.setName("Game")
						.setCategory(Category.GAMES)
						.setRating(9)
						.build();
		Product product3 = new Product.ProductBuilder()
						.setId("SUPER_ACCESSORY")
						.setName("Accessory")
						.setCategory(Category.ACCESSORIES)
						.setRating(6)
						.build();

		productRepository.addProduct(product1);
		productRepository.addProduct(product2);
		productRepository.addProduct(product3);

		List<Product> products = productRepository.getAllProducts();
		assertThat(products.size()).isEqualTo(3);
		assertThat(products.get(1).rating()).isEqualTo(9);
	}

	@Test
	void testRetrieveOneProductInWarehouseByID() {
		ProductRepository productRepository = new InMemoryProductRepository();
		ProductService productService = new ProductService(productRepository);
		productRepository.addProduct(new Product.ProductBuilder()
						.setId("SUPER_GAME")
						.setName("Game")
						.setCategory(Category.GAMES)
						.setRating(9)
						.build());
		productRepository.addProduct(new Product.ProductBuilder()
						.setId("SUPER_ACCESSORY")
						.setName("Accessory")
						.setCategory(Category.ACCESSORIES)
						.setRating(6)
						.build());
		productRepository.addProduct(new Product.ProductBuilder()
						.setId("SUPER_COMPUTER")
						.setName("Computer")
						.setCategory(Category.COMPUTERS)
						.setRating(8)
						.build());

		var gameProduct = productRepository.getProductById("SUPER_GAME").orElseThrow(() -> new NoSuchElementException("Not Found"));

		assertThat(gameProduct.category()).isEqualTo(Category.GAMES);
	}

	@Test
	void testRetrieveAllProductsInWarehouseByCategory() {
		ProductRepository productRepository = new InMemoryProductRepository();
		ProductService productService = new ProductService(productRepository);
		productRepository.addProduct(new Product.ProductBuilder()
						.setId("SUPER_GAME")
						.setName("BGame")
						.setCategory(Category.GAMES)
						.setRating(9)
						.build());

		productRepository.addProduct(new Product.ProductBuilder()
						.setId("SUPER_COMPUTER")
						.setName("Computer")
						.setCategory(Category.COMPUTERS)
						.setRating(8)
						.build());

		productRepository.addProduct(new Product.ProductBuilder()
						.setId("ASUPER_GAME")
						.setName("AGame")
						.setCategory(Category.GAMES)
						.setRating(8)
						.build());

		productRepository.addProduct(new Product.ProductBuilder()
						.setId("ASUPER_ACCESSORY")
						.setName("Accessory")
						.setCategory(Category.ACCESSORIES)
						.setRating(6)
						.build());

		List<Product> products = productService.getProductsByCategorySorted(Category.GAMES);

		assertThat(products.size()).isEqualTo(2);
		assertThat(products.get(0).rating()).isEqualTo(8);
		assertThat(products.get(1).rating()).isEqualTo(9);
	}

	@Test
	void testRetrieveAllProductsCreatedAfterACertainDate() throws InterruptedException {
		ProductRepository productRepository = new InMemoryProductRepository();
		ProductService productService = new ProductService(productRepository);
		productRepository.addProduct(new Product.ProductBuilder()
						.setId("SUPER_GAME")
						.setName("BGame")
						.setCategory(Category.GAMES)
						.setRating(9)
						.build());
		productRepository.addProduct(new Product.ProductBuilder()
						.setId("SUPER_COMPUTER")
						.setName("Computer")
						.setCategory(Category.COMPUTERS)
						.setRating(8)
						.build());
		LocalDateTime now = LocalDateTime.now();
		Thread.sleep(1000);
		productRepository.addProduct(new Product.ProductBuilder()
						.setId("ASUPER_GAME")
						.setName("AGame")
						.setCategory(Category.GAMES)
						.setRating(8)
						.build());
		productRepository.addProduct(new Product.ProductBuilder()
						.setId("SUPER_ACCESSORY")
						.setName("Accessory")
						.setCategory(Category.ACCESSORIES)
						.setRating(6)
						.build());

		List<Product> products = productService.getProductsCreatedAfter(now);

		assertThat(products.size()).isEqualTo(2);
		assertThat(products.get(0).id()).isEqualTo("ASUPER_GAME");
		assertThat(products.get(1).id()).isEqualTo("SUPER_ACCESSORY");
	}

	@Test
	void testRetrieveAllProductsWhereModifiedIsNotEqualsToCreated() throws InterruptedException {
		ProductRepository productRepository = new InMemoryProductRepository();
		ProductService productService = new ProductService(productRepository);
		productRepository.addProduct(new Product.ProductBuilder()
						.setId("SUPER_GAME")
						.setName("Mage")
						.setCategory(Category.GAMES)
						.setRating(8)
						.build());
		productRepository.addProduct(new Product.ProductBuilder()
						.setId("ASUPER_GAME")
						.setName("AGame")
						.setCategory(Category.GAMES)
						.setRating(7)
						.build());
		productRepository.addProduct(new Product.ProductBuilder()
						.setId("SUPER_GAME")
						.setName("Game")
						.setCategory(Category.GAMES)
						.setRating(10)
						.build());

		Thread.sleep(100);
		productRepository.updateProduct("SUPER_GAME", "Mage", Category.GAMES, 10);
		productRepository.updateProduct("ASUPER_GAME", "Assassin's Creed", Category.GAMES, 10);

		List<Product> modifiedProducts = productService.getModifiedProducts();
		assertThat(modifiedProducts.size()).isEqualTo(2);

	}
}