package org.example.pet;

public class PetDTO implements Pet {
	private final String name;
	private final String species;
	private Hunger hunger;
	private Happiness happiness;

	public PetDTO(String name, String species) {
		this.name = name;
		this.species = species;
		this.hunger = Hunger.NEUTRAL;
		this.happiness = Happiness.NEUTRAL;
	}

	public String getName() {
		return name;
	}

	public String getSpecies() {
		return species;
	}

	public Hunger getHunger() {
		return hunger;
	}

	public Happiness getHappiness() {
		return happiness;
	}

	public void setHunger(Hunger hunger) {
		this.hunger = hunger;
	}

	public void setHappiness(Happiness happiness) {
		this.happiness = happiness;
	}
}
