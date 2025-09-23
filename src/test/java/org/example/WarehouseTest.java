package org.example;

import org.junit.jupiter.api.Test;

import java.awt.color.ProfileDataException;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertThrows;

class WarehouseTest {
	@Test
	void testWarehouse() {
		Warehouse warehouse = new Warehouse();
	}

	@Test
	void testAddingAProductToWarehouseAndWarehousListIncreases() {
		Warehouse warehouse = new Warehouse();
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
		warehouse.addProduct(product);
		warehouse.addProduct(product2);
		Product recievedProduct = warehouse.getAllProducts().get(1);

		assertThat(warehouse.getAllProducts().size()).isEqualTo(2);
		assertThat(recievedProduct.id()).isEqualTo("SUPER_DUPER_COMPUTER");
	}

	@Test
	void testAddingAProductToWarehouseAndRetrievedValuesMatchTheAddedProduct() {
		Warehouse warehouse = new Warehouse();
		Product product = new Product.ProductBuilder()
						.setId("SUPER_COMPUTER")
						.setName("Super Computer")
						.setCategory(Category.COMPUTERS)
						.setRating(8)
						.build();
		warehouse.addProduct(product);
		Product recievedProduct = warehouse.getAllProducts().get(0);

		assertThat(recievedProduct.id()).isEqualTo("SUPER_COMPUTER");
		assertThat(recievedProduct.category()).isEqualTo(Category.COMPUTERS);
		assertThat(recievedProduct.rating()).isEqualTo(8);
	}

	@Test
	void testUpdatingAProduct() {
		Warehouse warehouse = new Warehouse();
		Product product = new Product.ProductBuilder()
						.setId("SUPER_GAME")
						.setName("Mage")
						.setCategory(Category.GAMES)
						.setRating(8)
						.build();
		warehouse.addProduct(product);

		assertThat(warehouse.getAllProducts().get(0).name()).isEqualTo("Mage");

		warehouse.updateProduct("SUPER_GAME", "Game", Category.GAMES, 10);

		assertThat(warehouse.getAllProducts().get(0).name()).isEqualTo("Game");
	}

	@Test
	void testUpdatingAProductWithAnIdThatDoesNotExist() {
		Warehouse warehouse = new Warehouse();
		Product product = new Product.ProductBuilder()
						.setId("SUPER_GAME")
						.setName("Mage")
						.setCategory(Category.GAMES)
						.setRating(8)
						.build();
		warehouse.addProduct(product);

		Exception exception = assertThrows(IllegalArgumentException.class, () -> {
			warehouse.updateProduct("SUPER_GAMER", "Mage", Category.GAMES, 10);
		});
		assertThat(exception.getMessage()).isEqualTo("Product ID does not exist");
	}

	@Test
	void testRetrieveAllProductsInWarehouse() {
		Warehouse warehouse = new Warehouse();

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

		warehouse.addProduct(product1);
		warehouse.addProduct(product2);
		warehouse.addProduct(product3);

		List<Product> products = warehouse.getAllProducts();
		assertThat(products.size()).isEqualTo(3);
		assertThat(products.get(1).rating()).isEqualTo(9);
	}

	@Test
	void testRetrieveOneProductInWarehouseByID() {
		Warehouse warehouse = new Warehouse();
		warehouse.addProduct(new Product.ProductBuilder()
						.setId("SUPER_GAME")
						.setName("Game")
						.setCategory(Category.GAMES)
						.setRating(9)
						.build());
		warehouse.addProduct(new Product.ProductBuilder()
						.setId("SUPER_ACCESSORY")
						.setName("Accessory")
						.setCategory(Category.ACCESSORIES)
						.setRating(6)
						.build());
		warehouse.addProduct(new Product.ProductBuilder()
						.setId("SUPER_COMPUTER")
						.setName("Computer")
						.setCategory(Category.COMPUTERS)
						.setRating(8)
						.build());

		var gameProduct = warehouse.getProductById("SUPER_GAME");

		assertThat(gameProduct.category()).isEqualTo(Category.GAMES);
	}

	@Test
	void testRetrieveAllProductsInWarehouseByCategory() {
		Warehouse warehouse = new Warehouse();
		warehouse.addProduct(new Product.ProductBuilder()
						.setId("SUPER_GAME")
						.setName("BGame")
						.setCategory(Category.GAMES)
						.setRating(9)
						.build());

		warehouse.addProduct(new Product.ProductBuilder()
						.setId("SUPER_COMPUTER")
						.setName("Computer")
						.setCategory(Category.COMPUTERS)
						.setRating(8)
						.build());

		warehouse.addProduct(new Product.ProductBuilder()
						.setId("ASUPER_GAME")
						.setName("AGame")
						.setCategory(Category.GAMES)
						.setRating(8)
						.build());

		warehouse.addProduct(new Product.ProductBuilder()
						.setId("ASUPER_ACCESSORY")
						.setName("Accessory")
						.setCategory(Category.ACCESSORIES)
						.setRating(6)
						.build());

		List<Product> products = warehouse.getProductsByCategorySorted(Category.GAMES);

		assertThat(products.size()).isEqualTo(2);
		assertThat(products.get(0).rating()).isEqualTo(8);
		assertThat(products.get(1).rating()).isEqualTo(9);
	}

	@Test
	void testRetrieveAllProductsCreatedAfterACertainDate() throws InterruptedException {
		Warehouse warehouse = new Warehouse();
		warehouse.addProduct(new Product.ProductBuilder()
						.setId("SUPER_GAME")
						.setName("BGame")
						.setCategory(Category.GAMES)
						.setRating(9)
						.build());
		warehouse.addProduct(new Product.ProductBuilder()
						.setId("SUPER_COMPUTER")
						.setName("Computer")
						.setCategory(Category.COMPUTERS)
						.setRating(8)
						.build());
		LocalDateTime now = LocalDateTime.now();
		Thread.sleep(1000);
		warehouse.addProduct(new Product.ProductBuilder()
						.setId("ASUPER_GAME")
						.setName("AGame")
						.setCategory(Category.GAMES)
						.setRating(8)
						.build());
		warehouse.addProduct(new Product.ProductBuilder()
						.setId("SUPER_ACCESSORY")
						.setName("Accessory")
						.setCategory(Category.ACCESSORIES)
						.setRating(6)
						.build());

		List<Product> products = warehouse.getProductsCreatedAfter(now);

		assertThat(products.size()).isEqualTo(2);
		assertThat(products.get(0).id()).isEqualTo("ASUPER_GAME");
		assertThat(products.get(1).id()).isEqualTo("SUPER_ACCESSORY");
	}

	@Test
	void testRetrieveAllProductsWhereModifiedIsNotEqualsToCreated() throws InterruptedException {
		Warehouse warehouse = new Warehouse();
		warehouse.addProduct(new Product.ProductBuilder()
						.setId("SUPER_GAME")
						.setName("Mage")
						.setCategory(Category.GAMES)
						.setRating(8)
						.build());
		warehouse.addProduct(new Product.ProductBuilder()
						.setId("ASUPER_GAME")
						.setName("AGame")
						.setCategory(Category.GAMES)
						.setRating(7)
						.build());
		warehouse.addProduct(new Product.ProductBuilder()
						.setId("SUPER_GAME")
						.setName("Game")
						.setCategory(Category.GAMES)
						.setRating(10)
						.build());

		Thread.sleep(100);
		warehouse.updateProduct("SUPER_GAME", "Mage", Category.GAMES, 10);
		warehouse.updateProduct("ASUPER_GAME", "Assassin's Creed", Category.GAMES, 10);

		List<Product> modifiedProducts = warehouse.getModifiedProducts();
		assertThat(modifiedProducts.size()).isEqualTo(2);

	}
}