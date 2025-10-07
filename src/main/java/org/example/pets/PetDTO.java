package org.example.pets;

public class PetDTO {
    private Long id;
    private String name;
    private String type;
    private int hungerLevel;
    private int happinessLevel;

    // Default constructor
    public PetDTO() {
    }

    public PetDTO(Long id, String name, String type, int hungerLevel, int happinessLevel) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.hungerLevel = hungerLevel;
        this.happinessLevel = happinessLevel;
    }

    // Getters
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

    // Setters
    public void setId(Long id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setType(String type) {
        this.type = type;
    }
    public void setHungerLevel(int hungerLevel) {
        this.hungerLevel = hungerLevel;
    }
    public void setHappinessLevel(int happinessLevel) {
        this.happinessLevel = happinessLevel;
    }


}
