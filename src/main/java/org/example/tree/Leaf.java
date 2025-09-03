package org.example.tree;

import java.util.HashMap;
import java.util.Map;

public class Leaf {
	private String name;
	private Map<String, Leaf> nextLeaf;

	public Leaf(String name) {
		this.name = name;
		this.nextLeaf = new HashMap<String, Leaf>();
	}

	public void setNextLeaf(String direction, Leaf leafName) {
		nextLeaf.put(direction, leafName);
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public Leaf getNextLeaf(String direction) {
		return nextLeaf.get(direction);
	}


}
