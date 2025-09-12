package org.example;

import org.junit.jupiter.api.Test;

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
		assertThat(recievedProduct.ID()).isEqualTo("SUPER_DUPER_COMPUTER");
	}

	@Test
	void testAddingAProductToWarehouseAndRetrievedValuesMatchTheAddedProduct() {
		Warehouse warehouse = new Warehouse();
		Product product = new Product("SUPER_COMPUTER", "Computer", Category.COMPUTERS, 8);
		warehouse.addProduct(product);
		Product recievedProduct = warehouse.getAllProducts().get(0);

		assertThat(recievedProduct.ID()).isEqualTo("SUPER_COMPUTER");
		assertThat(recievedProduct.category()).isEqualTo(Category.COMPUTERS);
		assertThat(recievedProduct.rating()).isEqualTo(8);
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

		Product gameProduct = warehouse.getProductById("SUPER_GAME");

		assertThat(gameProduct.category()).isEqualTo(Category.GAMES);
	}
}