package org.example.pet;

public interface Pet {
	String getName();

	String getSpecies();

	Hunger getHunger();

	Happiness getHappiness();

	void setHunger(Hunger hunger);

	void setHappiness(Happiness happiness);
}
