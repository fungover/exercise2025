package exercise6.pets;

import exercise6.repository.PetRepository;
import exercise6.service.Feed;
import exercise6.service.PetRequest;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.List;

@Path("pets")
public class PetResource {

    @Inject
    PetRepository petRepository;
    @Inject
    Feed feed;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String addPet(String name,  AnimalType animalType) {
        return petRepository.addPet(name, animalType);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Pet> getPets() {
        return  petRepository.getPets();
    }

    @GET()
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Pet getUniqPet(@PathParam("id") String id) {
        return petRepository.getUniqPet(id);
    }

    @PUT()
    @Path("/{id}/feed")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String feedPet(@PathParam("id") String id, PetRequest request) {
        int feedThePet = request.feedPet;
        return feed.feedPet(id, feedThePet);
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Pet> removePet(@PathParam("id") String id) {
        return petRepository.removePet(id);
    }
}
