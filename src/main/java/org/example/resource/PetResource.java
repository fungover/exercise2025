package org.example.resource;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.example.dto.PetDTO;
import org.example.exception.InvalidPetOperationException;
import org.example.exception.PetNotFoundException;
import org.example.service.PetService;

import java.net.URI;
import java.util.List;

/**
 * Provides RESTful endpoints for managing pets.
 */
@Path("/pets")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PetResource {

    @Inject
    private PetService petService;

    // --pets - Adopt a new pet--
    @POST
    public Response adoptPet(@Valid PetDTO pet) {
        PetDTO adoptedPet = petService.adoptPet(pet);
        return Response
                .created(URI.create("/pets/" + adoptedPet.getId()))
                .entity(adoptedPet)
                .build();
    }

    // --GET /pets - List all pets--
    @GET
    public Response getAllPets(
            @QueryParam("species") String species,
            @QueryParam("sortBy") String sortBy,
            @QueryParam("order") @DefaultValue("asc") String order,
            @QueryParam("offset") @DefaultValue("0") @Min(0) int offset,
            @QueryParam("limit") @DefaultValue("100") @Min(1) int limit) {

        List<PetDTO> pets;
        if (species != null || sortBy != null) {
            pets = petService.getPetsWithFilters(species, sortBy, order, offset, limit);
        } else if (offset > 0 || limit != 100) {
            pets = petService.getPetsWithPagination(offset, limit);
        } else {
            pets = petService.getAllPets();
        }

        return Response.ok(pets).build();
    }

    // -- view pet status--
    @GET
    @Path("/{id}")
    public Response getPetById(@PathParam("id") Long id) {
        PetDTO pet = petService.getPetById(id)
                .orElseThrow(() -> new PetNotFoundException(id));
        return Response.ok(pet).build();
    }

    // --Feed the pet--
    @PUT
    @Path("/{id}/feed")
    public Response feedPet(
            @PathParam("id") Long id,
            @QueryParam("amount") @DefaultValue("10") @Min(1) int amount) {

        if (amount < 1 || amount > 100) {
            throw new InvalidPetOperationException("feed",
                    "Feed amount must be between 1 and 100");
        }

        PetDTO updatedPet = petService.feedPet(id, amount)
                .orElseThrow(() -> new PetNotFoundException(id));

        return Response.ok(updatedPet).build();
    }

    //-- play with pets---
    @PUT
    @Path("/{id}/play")
    public Response playWithPet(
            @PathParam("id") Long id,
            @QueryParam("amount") @DefaultValue("10") @Min(1) int amount) {

        if (amount < 1 || amount > 100) {
            throw new InvalidPetOperationException("play",
                    "Play amount must be between 1 and 100");
        }

        PetDTO updatedPet = petService.playWithPet(id, amount)
                .orElseThrow(() -> new PetNotFoundException(id));

        return Response.ok(updatedPet).build();
    }

    // -- Release the pet---
    @DELETE
    @Path("/{id}")
    public Response releasePet(@PathParam("id") Long id) {
        boolean deleted = petService.releasePet(id);

        if (!deleted) {
            throw new PetNotFoundException(id);
        }

        return Response.noContent().build();
    }

    //-- Get total count of pets--
    @GET
    @Path("/count")
    public Response getPetCount(@QueryParam("species") String species) {
        long count;

        if (species != null && !species.trim().isEmpty()) {
            count = petService.getPetCountBySpecies(species);
        } else {
            count = petService.getPetCount();
        }

        return Response.ok(new CountResponse(count)).build();
    }

    // --count endpoint--
    public static class CountResponse {
        private long count;

        public CountResponse() {
        }

        public CountResponse(long count) {
            this.count = count;
        }

        public long getCount() {
            return count;
        }

        public void setCount(long count) {
            this.count = count;
        }
    }
}
