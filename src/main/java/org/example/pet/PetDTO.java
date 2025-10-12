package org.example.pet;

public class PetDTO implements Pet {
	private String name;
	private String species;
	private Hunger hunger = Hunger.NEUTRAL;
	private Happiness happiness = Happiness.NEUTRAL;

	public PetDTO() {
	}

	public PetDTO(String name, String species) {
		this.name = name;
		this.species = species;
	}

	public String getName() { return name; }
	public String getSpecies() { return species;	}
	public Hunger getHunger() { return hunger; }
	public Happiness getHappiness() {	return happiness;	}

	public void setName(String name) { this.name = name; }
	public void setSpecies(String species) { this.species = species; }
	public void setHunger(Hunger hunger) { this.hunger = hunger; }
	public void setHappiness(Happiness happiness) {	this.happiness = happiness;	}
}
