package exercise6.validation;

import exercise6.pets.AnimalType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class PetValidate {
    @NotBlank(message = "Name is mandatory")
    @NotNull(message = "Name is mandatory")
    private String name;
    @NotNull
    private AnimalType animalType;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public AnimalType getAnimalType() {
        return animalType;
    }
    public void setAnimalType(AnimalType animalType) {
        this.animalType = animalType;
    }
}
