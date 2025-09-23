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
		Product product = new Product.ProductBuilder()
						.setId("PC-2")
						.setName("Personal Computer")
						.setCategory(Category.COMPUTERS)
						.setRating(9)
						.build();
	}

	@Test
	void testNameIsNotEmpty() {
		Product product = new Product.ProductBuilder()
						.setId("SUPER_GAME")
						.setName("Little Leaf Lizards")
						.setCategory(Category.GAMES)
						.setRating(10)
						.build();
		assertThat(product.name()).isEqualTo("Little Leaf Lizards");
	}

	@Test
	void testNameIsEmpty() {
		Exception exception =  assertThrows(IllegalArgumentException.class, () -> {
			Product product = new Product.ProductBuilder()
							.setId("SUPER_GAME")
							.setName("")
							.setCategory(Category.GAMES)
							.setRating(6)
							.build();
		});
		assertThat(exception.getMessage()).isEqualTo("name cannot be null or empty");
	}

	@Test
	void testIdIsNotEmpty() {
		Product product = new Product.ProductBuilder()
						.setId("SUPER_GAME").
						setName("Little Leaf Lizards")
						.setCategory(Category.GAMES)
						.setRating(9)
						.build();
		assertThat(product.id()).isEqualTo("SUPER_GAME");
	}

	@Test
	void testIdIsEmpty() {
		Exception exception =  assertThrows(IllegalArgumentException.class, () -> {
			Product product = new Product.ProductBuilder()
							.setId("").
							setName("Little Leaf Lizards")
							.setCategory(Category.GAMES)
							.setRating(9)
							.build();
		});
		assertThat(exception.getMessage()).isEqualTo("id cannot be null or empty");
	}

	@Test
	void testCategoryIsNotEmpty() {
		Product product = new Product.ProductBuilder()
						.setId("SUPER_GAME").
						setName("Little Leaf Lizards")
						.setCategory(Category.GAMES)
						.setRating(9)
						.build();
		assertThat(product.category()).isEqualTo(Category.GAMES);
	}

	@ParameterizedTest
	@ValueSource(ints = {-1, -6, 11, 15})
	void testRatingIsIllegalAndThrowsError(int invalidRating) {
		Exception exception = assertThrows(IllegalArgumentException.class, () -> {
			Product product = new Product.ProductBuilder()
							.setId("SUPER_GAME").
							setName("Little Leaf Lizards")
							.setCategory(Category.GAMES)
							.setRating(invalidRating)
							.build();
		});
		assertThat(exception.getMessage()).isEqualTo("rating should be between 0 and 10");
	}

	@Test
	void testUpdatingANameToBeEmpty() {
		Exception exception = assertThrows(IllegalArgumentException.class, () -> {
			Product product = new Product.ProductBuilder()
							.setId("Game")
							.setName("Little Leaf Lizards")
							.setCategory(Category.GAMES)
							.setRating(7)
							.build();
			product.updateProduct("", Category.GAMES, 8);
		});
		assertThat(exception.getMessage()).isEqualTo("name cannot be null or empty");
	}

	@Test
	void buildProduct() {
		Product product = new Product.ProductBuilder()
						.setId("Game")
						.setName("Name")
						.setCategory(Category.GAMES)
						.setRating(7)
						.build();

		assertThat(product).isNotNull();
		assertThat(product.name()).isEqualTo("Name");
	}
}