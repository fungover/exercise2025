package org.example.game;

import org.example.tree.Leaf;

import java.util.Scanner;

public class Game {
	private Leaf currentLeaf;

	public Game() {
		createLeaves();
	}

	public void createLeaves() {
		Leaf greenLeaf = new Leaf("Green Leaf");
		Leaf redLeaf = new Leaf("Red Leaf");
		Leaf orangeLeaf = new Leaf("Orange Leaf");
		Leaf yellowLeaf = new Leaf("Yellow Leaf");
		Leaf purpleLeaf = new Leaf("Purple Leaf");
		Leaf goldenLeaf = new Leaf("Golden Leaf");

		greenLeaf.setNextLeaf("u", redLeaf);
		greenLeaf.setNextLeaf("d", orangeLeaf);
		redLeaf.setNextLeaf("u", yellowLeaf);
		redLeaf.setNextLeaf("d", greenLeaf);
		orangeLeaf.setNextLeaf("u", greenLeaf);
		orangeLeaf.setNextLeaf("r", goldenLeaf);
		yellowLeaf.setNextLeaf("u", purpleLeaf);
		yellowLeaf.setNextLeaf("d", redLeaf);
		purpleLeaf.setNextLeaf("d", yellowLeaf);

		currentLeaf = greenLeaf;
	}

	public void run() {
		Scanner input = new Scanner(System.in);
		System.out.println("🦎 Little Leaf Lizards 🍃");
		System.out.print("Your reptile name?: ");
		String name = input.nextLine();
		System.out.println("Hello, mighty " + name + "!");

		System.out.println("Your leaf " + currentLeaf.getName());

		boolean gameOn = true;
		while (gameOn) {
			System.out.print("Your move ([U]p, [D]own, [R]ight, [L]eft): ");
			String move = input.nextLine().toLowerCase().trim();
			if (currentLeaf.getNextLeaf(move) != null) {
				currentLeaf = currentLeaf.getNextLeaf(move);
				System.out.println(currentLeaf.getName());
			} else {
				System.out.println("No leaf there");
			}
		}
	}
}
