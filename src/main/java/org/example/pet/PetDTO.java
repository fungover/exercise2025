package org.example.pet;

import org.example.validation.ValidAmount;
import org.example.validation.ValidName;
import org.example.validation.ValidSpecies;

public class PetDTO implements Pet {
	@ValidName
	private String name;
	@ValidSpecies
	private String species;
	@ValidAmount
	private int hunger = 5;
	@ValidAmount
	private int happiness = 5;

	public PetDTO() {
	}

	public PetDTO(@ValidName String name, @ValidSpecies String species) {
		this.name = name;
		this.species = species;
	}

	public String getName() { return name; }
	public String getSpecies() { return species;	}
	public int getHunger() { return hunger; }
	public int getHappiness() {	return happiness;	}

	public void setName( String name) { this.name = name; }
	public void setSpecies( String species) { this.species = species; }
	public void setHunger(int hunger) { this.hunger = hunger; }
	public void setHappiness(int happiness) { this.happiness = happiness; }
}
