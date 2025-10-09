package exercise6.pets;

import exercise6.annotations.Feed;
import exercise6.repository.PetRepository;
import exercise6.service.HandlePetValue;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;

@RequestScoped
@Feed
public class FeedPet implements HandlePetValue {

    @Inject
    private PetRepository petRepository;


    @Override
    public String increaseValue(int id, int value) {
        Pet petToHandle = petRepository.getPetById(id);
        petToHandle.setHunger(value);
        String petName = petToHandle.getName();

        if (petToHandle.getHunger() == 100) {
            return petName +" is fully fed, find a friend to feed";
        }else{
            return petName +" is partly fed and is eager for more food";
        }
    }
}
