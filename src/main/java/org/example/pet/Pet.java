package org.example.pet;

import org.example.validation.ValidName;
import org.example.validation.ValidSpecies;

public interface Pet {
	String getName();

	String getSpecies();

	int getHunger();

	int getHappiness();

	void setName(@ValidName String name);

	void setSpecies(@ValidSpecies String species);

	void setHappiness(int amount);

	void setHunger(int amount);
}
