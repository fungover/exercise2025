package org.example.tree;

import java.util.HashMap;
import java.util.Map;

public class Leaf {
	private String name;
	private String description;
	private Map<String, Leaf> nextLeaf;

	public Leaf(String name, String description) {
		this.name = name;
		this.description = description;
		this.nextLeaf = new HashMap<>();
	}

	public void setNextLeaf(String direction, Leaf leafName) {
		nextLeaf.put(direction, leafName);
	}

	public String getName() {
		return name;
	}
	public String getDescription() {
		return description;
	}

	public Leaf getNextLeaf(String direction) {
		return nextLeaf.get(direction);
	}


}
