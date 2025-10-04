package exercise6.pets;

import exercise6.repository.PetRepository;
import exercise6.service.Feed;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class FeedPet implements Feed {

    @Inject
    private PetRepository petRepository;

    @Override
    public String feedPet(String id, int feedPet) {
        String name = petRepository.getUniqPet(id).getName();

        return name + petRepository.getUniqPet(id).setHunger(feedPet);
    }
}
