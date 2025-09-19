package org.example.game;

import org.example.tree.Leaf;
import org.example.tree.Leaves;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LeavesTest {

	@Test
	void testLeaves() {
		Leaves leaves = new Leaves();
	}

	@Test
	void testCurrentLeaf() {
		Leaves leaves = new Leaves();
		String currentLeaf = leaves.getCurrentLeaf().getName();
		assertEquals("Greenwood", currentLeaf);
	}

	@Test
	void testJumpFromLeafToLeaf() {
		Leaves leaves = new Leaves();
		Leaf currentLeaf = leaves.getCurrentLeaf();
		Leaf anotherLeaf = leaves.move(currentLeaf, "l");

		assertEquals("Amberfield", anotherLeaf.getName());
	}
}