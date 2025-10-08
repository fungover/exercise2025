package exercise6.pets;

import exercise6.annotations.Play;
import exercise6.repository.PetRepository;
import exercise6.service.HandlePetValue;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;

@RequestScoped
@Play
public class PlayWithPet implements HandlePetValue {

    @Inject
    PetRepository petRepository;

    @Override
    public String increaseValue(int id, int value) {
        Pet petToHandle = petRepository.getPetById(id);
        petToHandle.setHappiness(value);
        String petName = petToHandle.getName();

        if (petToHandle.getHappiness() == 100) {
            return petName +" is already happy, find a friend to play with";
        }else{
            return petName +" is partly happy and is eager for more fun";
        }

    }
}
