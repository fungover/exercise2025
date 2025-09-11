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
	void testAddingAProductToWarehouseAndWarehousListIncreases() {
		Warehouse warehouse = new Warehouse();
		Product product = new Product("SUPER_COMPUTER", "Computer", Category.COMPUTERS, 8);
		Product product2 = new Product("SUPER_DUPER_COMPUTER", "Computer", Category.COMPUTERS, 8);
		boolean productAdded = warehouse.addProduct(product);
		boolean productAdded2 = warehouse.addProduct(product2);
		Product recievedProduct = warehouse.getProducts().get(1);

		assertThat(warehouse.getProducts().size()).isEqualTo(2);
		assertThat(recievedProduct.ID()).isEqualTo("SUPER_DUPER_COMPUTER");
	}

	@Test
	void testAddingAProductToWarehouseAndRetrievedValuesMatchTheAddedProduct() {
		Warehouse warehouse = new Warehouse();
		Product product = new Product("SUPER_COMPUTER", "Computer", Category.COMPUTERS, 8);
		boolean productAdded = warehouse.addProduct(product);
		Product recievedProduct = warehouse.getProducts().get(0);

		assertThat(recievedProduct.ID()).isEqualTo("SUPER_COMPUTER");
		assertThat(recievedProduct.category()).isEqualTo(Category.COMPUTERS);
		assertThat(recievedProduct.rating()).isEqualTo(8);
	}
}