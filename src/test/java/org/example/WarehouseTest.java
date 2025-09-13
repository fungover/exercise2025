package org.example;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class WarehouseTest {
	@Test
	void testWarehouse() {
		Warehouse warehouse = new Warehouse();
	}

	@Test
	void testAddingAProductToWarehouseAndWarehousListIncreases() {
		Warehouse warehouse = new Warehouse();
		Product product = new Product("SUPER_COMPUTER", "Computer", Category.COMPUTERS, 8);
		Product product2 = new Product("SUPER_DUPER_COMPUTER", "Computer", Category.COMPUTERS, 8);
		warehouse.addProduct(product);
		warehouse.addProduct(product2);
		Product recievedProduct = warehouse.getAllProducts().get(1);

		assertThat(warehouse.getAllProducts().size()).isEqualTo(2);
		assertThat(recievedProduct.id()).isEqualTo("SUPER_DUPER_COMPUTER");
	}

	@Test
	void testAddingAProductToWarehouseAndRetrievedValuesMatchTheAddedProduct() {
		Warehouse warehouse = new Warehouse();
		Product product = new Product("SUPER_COMPUTER", "Computer", Category.COMPUTERS, 8);
		warehouse.addProduct(product);
		Product recievedProduct = warehouse.getAllProducts().get(0);

		assertThat(recievedProduct.id()).isEqualTo("SUPER_COMPUTER");
		assertThat(recievedProduct.category()).isEqualTo(Category.COMPUTERS);
		assertThat(recievedProduct.rating()).isEqualTo(8);
	}

	@Test
	void testUpdatingAProduct() {
		Warehouse warehouse = new Warehouse();
		Product product = new Product("SUPER_GAME", "Mage", Category.GAMES, 8);
		warehouse.addProduct(product);

		assertThat(warehouse.getAllProducts().get(0).name()).isEqualTo("Mage");

		warehouse.updateProduct("SUPER_GAME", "Game", Category.GAMES, 10);

		assertThat(warehouse.getAllProducts().get(0).name()).isEqualTo("Game");
	}

	@Test
	void testRetrieveAllProductsInWarehouse() {
		Warehouse warehouse = new Warehouse();

		Product product1 = new Product("SUPER_COMPUTER", "Computer", Category.COMPUTERS, 8);
		Product product2 = new Product("SUPER_GAME", "Game", Category.GAMES, 9);
		Product product3 = new Product("SUPER_ACCESSORY", "Accessory", Category.ACCESSORIES, 6);

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
		warehouse.addProduct(new Product("SUPER_GAME", "Game", Category.GAMES, 9));
		warehouse.addProduct(new Product("SUPER_ACCESSORY", "Accessory", Category.ACCESSORIES, 6));
		warehouse.addProduct(new Product("SUPER_COMPUTER", "Computer", Category.COMPUTERS, 8));

		var gameProduct = warehouse.getProductById("SUPER_GAME");

		assertThat(gameProduct.category()).isEqualTo(Category.GAMES);
	}

	@Test
	void testRetrieveAllProductsInWarehouseByCategory() {
		Warehouse warehouse = new Warehouse();
		warehouse.addProduct(new Product("SUPER_GAME", "BGame", Category.GAMES, 9));
		warehouse.addProduct(new Product("SUPER_COMPUTER", "Computer", Category.COMPUTERS, 8));
		warehouse.addProduct(new Product("A" + "SUPER_GAME", "AGame", Category.GAMES, 8));
		warehouse.addProduct(new Product("SUPER_ACCESSORY", "Accessory", Category.ACCESSORIES, 6));

		List<Product> products = warehouse.getProductsByCategorySorted(Category.GAMES);

		assertThat(products.size()).isEqualTo(2);
		assertThat(products.get(0).rating()).isEqualTo(8);
		assertThat(products.get(1).rating()).isEqualTo(9);
	}

	@Test
	void testRetrieveAllProductsCreatedAfterACertainDate() throws InterruptedException {
		Warehouse warehouse = new Warehouse();
		warehouse.addProduct(new Product("SUPER_GAME", "BGame", Category.GAMES, 9));
		warehouse.addProduct(new Product("SUPER_COMPUTER", "Computer", Category.COMPUTERS, 8));
		LocalDateTime now = LocalDateTime.now();
		Thread.sleep(1000);
		warehouse.addProduct(new Product("ASUPER_GAME", "AGame", Category.GAMES, 8));
		warehouse.addProduct(new Product("SUPER_ACCESSORY", "Accessory", Category.ACCESSORIES, 6));

		List<Product> products = warehouse.getProductsCreatedAfter(now);

		assertThat(products.size()).isEqualTo(2);
		assertThat(products.get(0).id()).isEqualTo("ASUPER_GAME");
		assertThat(products.get(1).id()).isEqualTo("SUPER_ACCESSORY");
	}

	@Test
	void testRetrieveAllProductsWhereModifiedIsNotEqualsToCreated() throws InterruptedException {
		Warehouse warehouse = new Warehouse();

		warehouse.addProduct(new Product("SUPER_GAMEE", "BGame", Category.GAMES, 9));
		warehouse.addProduct(new Product("SUPER_GAME", "AGame", Category.GAMES, 8));
		warehouse.addProduct(new Product("ASUPER_GAME", "AC Game", Category.GAMES, 8));
		Thread.sleep(5000);
		warehouse.updateProduct("SUPER_GAMEE", "B: Game", Category.GAMES, 7);
		warehouse.updateProduct("ASUPER_GAME", "Assassin's Creed - Game", Category.GAMES, 10);

		List<Product> modifiedProducts = warehouse.getModifiedProducts();
		assertThat(modifiedProducts.size()).isEqualTo(2);

	}
}