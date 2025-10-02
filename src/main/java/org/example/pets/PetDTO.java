package org.example.pets;

public class PetDTO {
    private Long id;
    private String name;
    private String type;
    private int hungerLevel;
    private int happinessLevel;


    public PetDTO(Long id, String name, String type, int hungerLevel, int happinessLevel) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.hungerLevel = hungerLevel;
        this.happinessLevel = happinessLevel;
    }


    public Long getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getType() {
        return type;
    }
    public int getHungerLevel() {
        return hungerLevel;
    }
    public int getHappinessLevel() {
        return happinessLevel;
    }
}
