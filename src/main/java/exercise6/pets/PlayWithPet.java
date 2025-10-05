package exercise6.pets;

import exercise6.repository.PetRepository;
import exercise6.service.Play;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class PlayWithPet implements Play {

    @Inject
    PetRepository petRepository;

    @Override
    public String playWithPet(String id, int play) {
        String name =  petRepository.getUniqPet(id).getName();
       return name + petRepository.getUniqPet(id).setHappiness(play);
    }
}
