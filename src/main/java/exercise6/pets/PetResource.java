package exercise6.pets;

import exercise6.annotations.Feed;
import exercise6.annotations.IdCheck;
import exercise6.annotations.Play;
import exercise6.repository.PetRepository;
import exercise6.service.HandlePetValue;
import exercise6.service.PetService;
import exercise6.validation.ValueValidate;
import exercise6.validation.PetValidate;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
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
                             @QueryParam("order") String value,
                             @QueryParam("offset") @Min(0) Integer page,
                             @QueryParam("limit") Integer pageSize
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

        if(page != null) {
            return petService.petPagination(page, pageSize);
        }

        return  petRepository.getPets();
    }

    @GET()
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Pet getUniqPet(@PathParam("id") @IdCheck int id) {
        return petRepository.getUniqPet(id);
    }

    @Path("/{id}/feed")
    @PUT()
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String feedPet(@PathParam("id") @IdCheck int id, @Valid ValueValidate request) {
        return handlePetFeed.increaseValue(id, request.getRequestValue());
        }

    @PUT
    @Path("/{id}/play")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String playPet(@PathParam("id")@IdCheck int id, @Valid ValueValidate request) {
        return handlePetPlay.increaseValue(id, request.getRequestValue());
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Pet> removePet(@PathParam("id") @IdCheck int id) {
        return petRepository.removePet(id);
    }
}
