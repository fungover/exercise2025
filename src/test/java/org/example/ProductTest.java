package org.example;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
		Product product = new Product("SUPER_GAME", "", Category.GAMES, 10);
		assertThat(product.name()).isEmpty();
	}

	@ParameterizedTest
	@ValueSource(ints = {-1, -6, 11, 15})
	void testRatingIsIllegalAndThrowsError(int invalidRating) {
		Exception exception = assertThrows(IllegalArgumentException.class, () -> {
			new Product("SUPER_GAME", "Little Leaf Lizards", Category.GAMES, invalidRating);
		});
		assertThat(exception.getMessage()).isEqualTo("Invalid rating");
	}

	@Test
	void testUpdateProductWithLegalValues() {
		assertDoesNotThrow(() -> {
			new Product("GAME", "Little Leaf Lizards", Category.GAMES, 10);
		});
	}

	@ParameterizedTest
	@ValueSource(ints = {-1, -6, 11, 15})
	void testUpdateProductWithIllegalName(int invalidRating) {
		Exception exception = assertThrows(IllegalArgumentException.class, () -> {
			Product product = new Product("GAME", "Little Leaf Lizards", Category.GAMES, 10);
			product.updateProduct("Little Leaf Lizards", Category.GAMES, invalidRating);
		});
		assertThat(exception.getMessage()).isEqualTo("Invalid rating");
	}

	@Test
	void testUpdateProductWithIllegalRating() {
		Exception exception = assertThrows(IllegalArgumentException.class, () -> {
			Product product = new Product("GAME", "Little Leaf Lizards", Category.GAMES, 10);
			product.updateProduct("", Category.GAMES, 8);
		});
		assertThat(exception.getMessage()).isEqualTo("Product name cannot be empty");
	}
}