package org.example.tree;

public class Leaves {
public Leaves(){
	createLeaves();
}

	private Leaf currentLeaf;

	public void setCurrentLeaf(Leaf currentLeaf) {
		this.currentLeaf = currentLeaf;
	}

	public Leaf getCurrentLeaf() {
		return currentLeaf;
	}

	public Leaf move(Leaf currentLeaf, String move) {
			if (currentLeaf.getNextLeaf(move) != null) {
				setCurrentLeaf(currentLeaf.getNextLeaf(move));
				return currentLeaf.getNextLeaf(move);
			} else {
				System.out.println("No leaf there");
				return currentLeaf;
			}
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

		setCurrentLeaf(greenLeaf);
	}
}
