package exercise6.pets;

import exercise6.service.IdGenerator;

public class Pet {

    private final int id;
    private final String name;
    private final AnimalType animalType;
    private int hunger;
    private int happiness;

    public Pet(String name, AnimalType animalType){

        if(name == null || name.isBlank()){
            throw new IllegalArgumentException("Name can't be null or blank");
        }
        id = IdGenerator.getNextId();
        this.name = name.substring(0, 1).toUpperCase() + name.substring(1);
        this.animalType = animalType;
        this.hunger = 0;
        this.happiness = 0;
    }

    public void setHunger(int hunger) {

        int checkHunger = this.hunger + hunger;

        if(checkHunger == 100 || checkHunger > 100){
            this.hunger = 100;
        }else{
            this.hunger = checkHunger;
        }
    }

    public void setHappiness(int happiness) {
        int checkHappiness = this.happiness + happiness;

        if(checkHappiness == 100 || checkHappiness > 100){
            this.happiness = 100;
        }else{
            this.happiness = checkHappiness;
        }
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public AnimalType getAnimalType() {
        return animalType;
    }

    public int getHunger() {
        return hunger;
    }

    public int getHappiness() {
        return happiness;
    }
}
