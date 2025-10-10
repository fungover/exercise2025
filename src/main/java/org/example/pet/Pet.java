package org.example.pet;

public interface Pet {
	public String getName();
	public String getSpecies();
	public Hunger getHunger();
	public Happiness getHappiness();
	public void setHunger(Hunger hunger);
	public void setHappiness(Happiness happiness);
}
