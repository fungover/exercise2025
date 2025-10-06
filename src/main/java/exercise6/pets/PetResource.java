package exercise6.pets;

import exercise6.annotations.Feed;
import exercise6.annotations.Play;
import exercise6.repository.PetRepository;
import exercise6.service.HandlePetValue;
import exercise6.service.PetService;
import exercise6.validation.ValueValidate;
import exercise6.validation.PetValidate;
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
    PetService petService;
    @Inject @Feed
    HandlePetValue handlePetFeed;
    @Inject @Play
    HandlePetValue handlePetPlay;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String addPet(@Valid PetValidate petValidate) {
        return  petRepository.addPet(petValidate.getName(), petValidate.getAnimalType());
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Pet> getPets(@QueryParam("species") String animalType,
                             @QueryParam("sortBy") String happy,
                             @QueryParam("order") String value
                             ){
        if(animalType != null) {
            AnimalType petSpecies = AnimalType.valueOf(animalType.toUpperCase());
            return petService.sortPetByType(petSpecies);
        }

        if("happiness".equals(happy) ) {
            if("desc".equals(value)) {
                return petService.sortPetByHappinessDesc();
            }
            if("asc".equals(value)) {
                return petService.sortPetByHappinessAsc();
            }

        }

        return  petRepository.getPets();
    }

    @GET()
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Pet getUniqPet(@PathParam("id")  int id) {
        return petRepository.getUniqPet(id);
    }

    @Path("/{id}/feed")
    @PUT()
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String feedPet(@PathParam("id") int id, @Valid ValueValidate request) {
        return handlePetFeed.increaseValue(id, request.getRequestValue());
        }

    @PUT
    @Path("/{id}/play")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String playPet(@PathParam("id") int id, @Valid ValueValidate request) {
        return handlePetPlay.increaseValue(id, request.getRequestValue());
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Pet> removePet(@PathParam("id") int id) {
        return petRepository.removePet(id);
    }
}
