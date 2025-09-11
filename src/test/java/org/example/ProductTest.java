package org.example;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ProductTest {
	@Test
	void test() {
		Product product = new Product("SUPER_GAME", "Little Leaf Lizards", Category.GAMES, 10);
	}

	@Test
	void testNameIsNotEmpty() {
		Product product = new Product("SUPER_GAME", "Little Leaf Lizards", Category.GAMES, 10);
		assertThat(product.name()).isNotEmpty();
	}

	@Test
	void testNameIsEmpty() {
		Product product = new Product("SUPER_GAME", "Little Leaf Lizards", Category.GAMES, 10);
		assertThat(product.name()).isEmpty();
	}
}