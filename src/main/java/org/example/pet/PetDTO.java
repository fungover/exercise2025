package org.example.pet;

import org.example.validation.ValidName;
import org.example.validation.ValidSpecies;

public class PetDTO implements Pet {
	@ValidName
	private String name;
	@ValidSpecies
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

	public void setName(@ValidName String name) { this.name = name; }
	public void setSpecies(@ValidSpecies String species) { this.species = species; }
	public void setHunger(Hunger hunger) { this.hunger = hunger; }
	public void setHappiness(Happiness happiness) {	this.happiness = happiness;	}
}
