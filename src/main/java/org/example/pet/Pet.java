package org.example.pet;

public interface Pet {
	 String getName();
	 String getSpecies();
	 int getHunger();
	 int getHappiness();

	void setHappiness(int amount);
	void setHunger(int amount);
}
