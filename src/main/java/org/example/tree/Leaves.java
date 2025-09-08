package org.example.tree;

public class Leaves {
	private Leaf currentLeaf;

	public Leaf currentLeaf() {
		return currentLeaf;
	}

	public void move() {
		do {
			System.out.println("Your current leaf: " + currentLeaf.getName());
			System.out.print("Your move (" + currentLeaf.getDescription() + "): ");
			String move = System.console().readLine();
			if (currentLeaf.getNextLeaf(move) != null) {
				currentLeaf = currentLeaf.getNextLeaf(move);
				break;
			} else {
				System.out.println("No leaf there");
			}
		} while (true);
	}

	public void move(String move) {
		currentLeaf = currentLeaf.getNextLeaf(move);
	}

	public void createLeaves() {
		Leaf greenLeaf = new Leaf("Greenwood", "[U]p or [L]eft");
		Leaf redLeaf = new Leaf("Red Hollow", "[R]ight or [D]own");
		Leaf orangeLeaf = new Leaf("Amberfield", "[R]ight or [L]eft");
		Leaf yellowLeaf = new Leaf("Yellow Meadow", "[U]p or [L]eft");
		Leaf goldenLeaf = new Leaf("Goldgrove", "[R]ight");
		Leaf purpleLeaf = new Leaf("Purple Vale", "[D]own or [H]ome");

		greenLeaf.setNextLeaf("u", redLeaf);
		greenLeaf.setNextLeaf("l", orangeLeaf);

		redLeaf.setNextLeaf("r", yellowLeaf);
		redLeaf.setNextLeaf("d", greenLeaf);

		orangeLeaf.setNextLeaf("r", greenLeaf);
		orangeLeaf.setNextLeaf("l", goldenLeaf);

		goldenLeaf.setNextLeaf("r", orangeLeaf);

		yellowLeaf.setNextLeaf("u", purpleLeaf);
		yellowLeaf.setNextLeaf("l", redLeaf);

		purpleLeaf.setNextLeaf("d", yellowLeaf);
		purpleLeaf.setNextLeaf("h", greenLeaf);

		currentLeaf = greenLeaf;
	}
}
