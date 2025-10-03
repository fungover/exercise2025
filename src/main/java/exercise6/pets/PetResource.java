package exercise6.pets;

import exercise6.repository.PetRepository;
import exercise6.service.Feed;
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
    public Object getUniqPet(@PathParam("id") String id) {
        return petRepository.getUniqPet(id);
    }

    @PUT()
    @Path("/{id}/feed")
    @Produces(MediaType.TEXT_PLAIN)
    public String feedPet(@PathParam("id") String id) {
        return feed.feedPet(id);
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Pet> removePet(@PathParam("id") String id) {
        return petRepository.removePet(id);
    }
}
