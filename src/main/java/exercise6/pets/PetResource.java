package exercise6.pets;

import exercise6.annotations.Feed;
import exercise6.annotations.Play;
import exercise6.repository.PetRepository;
import exercise6.service.HandlePetValue;
import exercise6.validation.SetValueDTO;
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
    @Inject @Feed
    HandlePetValue handlePetFeed;
    @Inject @Play
    HandlePetValue handlePetPlay;


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

    @Path("/{id}/feed")
    @PUT()
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String feedPet(@PathParam("id") String id, @Valid SetValueDTO request) {
            return handlePetFeed.increaseValue(id, request.getRequestValue());
        }

    @PUT
    @Path("/{id}/play")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String playPet(@PathParam("id") String id, @Valid SetValueDTO request) {
        return handlePetPlay.increaseValue(id, request.getRequestValue());
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Pet> removePet(@PathParam("id") String id) {
        return petRepository.removePet(id);
    }
}
