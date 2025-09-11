package org.example;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class WarehouseTest {
	@Test
	void testWarehouse() {
		Warehouse warehouse = new Warehouse();
	}

	@Test
	void testAddingAProductToWarehouse() {
		Warehouse warehouse = new Warehouse();
		Product product = new Product("SUPER_COMPUTER", "Computer");
		boolean productAdded = warehouse.addProduct(product);

		assertThat(productAdded).isTrue();
	}

	@Test
	void testAddingAProductToWarehouseAndNameIsNotEmpty() {
		Warehouse warehouse = new Warehouse();
		Product product = new Product("SUPER_COMPUTER", "Computer");
		boolean productAdded = warehouse.addProduct(product);

		assertThat(productAdded).isTrue();
	}

	@Test
	void testAddingAProductToWarehouseAndNameIsEmpty() {
		Warehouse warehouse = new Warehouse();
		Product product = new Product("SUPER_COMPUTER", "");
		boolean productAdded = warehouse.addProduct(product);

		assertThat(productAdded).isFalse();
	}

	@Test
	void testAddingAProductToWarehouseAndWarehousListIncreases() {
		Warehouse warehouse = new Warehouse();
		Product product = new Product("SUPER_COMPUTER", "Computer");
		Product product2 = new Product("SUPER_DUPER_COMPUTER", "Computer");
		boolean productAdded = warehouse.addProduct(product);
		boolean productAdded2 = warehouse.addProduct(product2);

		assertThat(warehouse.getProducts().size()).isEqualTo(2);
	}

	@Test
	void testAddingAProductToWarehouseAndTheIDIsNotEmpty() {
		Warehouse warehouse = new Warehouse();
		Product product = new Product("SUPER_COMPUTER", "Computer");
		boolean productAdded = warehouse.addProduct(product);
		Product recievedProduct = warehouse.getProducts().get(0);

		assertThat(recievedProduct.getId()).isEqualTo("SUPER_COMPUTER");
	}
}