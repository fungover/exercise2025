package dto;

import jakarta.validation.constraints.*;

// Data Transfer Object representing a Tamagotchi virtual pet
public class TamagotchiDTO {

    // Unique identifier for each Tamagotchi
    private Long id;

    // Name must be between 2-20 characters
    @NotBlank(message = "Tamagotchi måste ha ett namn!")
    @Size(min = 2, max = 20, message = "Namnet måste vara mellan 2-20 tecken")
    private String name;

    // Character type (e.g., Mametchi, Kuchipatchi, Memetchi)
    @NotBlank(message = "Karaktärstyp måste anges (t.ex. Mametchi, Kuchipatchi)")
    private String character;

    // Age in days (0-30)
    @Min(value = 0, message = "Ålder kan inte vara negativ")
    @Max(value = 30, message = "Max ålder är 30 dagar")
    private int age;

    // Hunger level: 0 = full, 100 = starving
    @Min(value = 0, message = "Hunger måste vara mellan 0-100")
    @Max(value = 100, message = "Hunger kan max vara 100")
    private int hunger;

    // Happiness level: 0 = sad, 100 = very happy
    @Min(value = 0, message = "Lycka måste vara mellan 0-100")
    @Max(value = 100, message = "Lycka kan max vara 100")
    private int happiness;

    // Health level: 0 = sick, 100 = healthy
    @Min(value = 0, message = "Hälsa måste vara mellan 0-100")
    @Max(value = 100, message = "Hälsa kan max vara 100")
    private int health;

    // Energy level: 0 = exhausted, 100 = energetic
    @Min(value = 0, message = "Energi måste vara mellan 0-100")
    @Max(value = 100, message = "Energi kan max vara 100")
    private int energy;

    // Flag indicating if Tamagotchi is currently sleeping
    private boolean isSleeping;

    // Flag indicating if Tamagotchi needs cleaning
    private boolean needsCleaning;

    // Current status description (e.g., "Healthy", "Sick", "Tired")
    private String status;

    // Default constructor
    public TamagotchiDTO() {}

    // Constructor for creating a new Tamagotchi with initial values
    public TamagotchiDTO(Long id, String name, String character) {
        this.id = id;
        this.name = name;
        this.character = character;
        this.age = 0;
        this.hunger = 50;
        this.happiness = 50;
        this.health = 100;
        this.energy = 100;
        this.isSleeping = false;
        this.needsCleaning = false;
        this.status = "Happy";
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCharacter() {
        return character;
    }

    public void setCharacter(String character) {
        this.character = character;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getHunger() {
        return hunger;
    }

    public void setHunger(int hunger) {
        this.hunger = hunger;
    }

    public int getHappiness() {
        return happiness;
    }

    public void setHappiness(int happiness) {
        this.happiness = happiness;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getEnergy() {
        return energy;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }

    public boolean isSleeping() {
        return isSleeping;
    }

    public void setSleeping(boolean sleeping) {
        isSleeping = sleeping;
    }

    public boolean isNeedsCleaning() {
        return needsCleaning;
    }

    public void setNeedsCleaning(boolean needsCleaning) {
        this.needsCleaning = needsCleaning;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
