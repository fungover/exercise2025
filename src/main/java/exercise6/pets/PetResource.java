package exercise6.pets;

import exercise6.repository.PetRepository;
import exercise6.service.Feed;
import exercise6.validation.SetValueDTO;
import exercise6.service.Play;
import exercise6.validation.PetDTO;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.List;

@Path("pets")
public class PetResource {

    @Inject
    PetRepository petRepository;
    @Inject
    Feed feed;
    @Inject
    Play play;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String addPet(@Valid PetDTO petDTO) {
        return  petRepository.addPet(petDTO.getName(), petDTO.getAnimalType());
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Pet> getPets(@QueryParam("species") String animalType) {
        if(animalType != null) {
            AnimalType petSpecies = AnimalType.valueOf(animalType.toUpperCase());
            return petRepository.sortPetByType(petSpecies);
        }
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
    public String feedPet(@PathParam("id") String id, @Valid SetValueDTO request) {
        return feed.feedPet(id, request.getRequestValue());
    }

    @PUT
    @Path("/{id}/play")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String playPet(@PathParam("id") String id, @Valid SetValueDTO request) {
        return play.playWithPet(id, request.getRequestValue());
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Pet> removePet(@PathParam("id") String id) {
        return petRepository.removePet(id);
    }
}
