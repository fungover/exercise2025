package org.example.resource;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.example.dto.PetDTO;
import org.example.service.PetService;

import java.util.Collection;

@Path("pets")
@Produces(MediaType.APPLICATION_JSON)

public class PetResource {

    @Inject
    private PetService petService;

    @GET
    public Collection<PetDTO> getAllPets(
            @QueryParam("offset") @DefaultValue("0") int offset,
            @QueryParam("limit") @DefaultValue("10") int limit,
            @QueryParam("species") String species,
            @QueryParam("sortBy") @DefaultValue("id") String sortBy,
            @QueryParam("order") @DefaultValue("asc") String order
    ) {
        if (offset < 0) {
            throw new BadRequestException("Offset must be non-negative");
        }
        if (limit < 0) {
            throw new BadRequestException("Limit must be non-negative");
        }
        return petService.getAllPetsFiltered(offset, limit, species, sortBy, order);
    }

    @GET
    @Path("{id}")
    public PetDTO getPet(@PathParam("id") long id) {
        return petService.getPet(id);
    }

    @POST
    public PetDTO adoptPet(@Valid PetDTO pet) {
        return petService.addPet(pet);
    }

    @PUT
    @Path("{id}/feed")
    public PetDTO feedPet(@PathParam("id") long id) {
        return petService.feedPet(id);
    }

    @PUT
    @Path("{id}/play")
    public PetDTO playWithPet(@PathParam("id") long id) {
        return petService.playWithPet(id);
    }

    @DELETE
    @Path("{id}")
    public void releasePet(@PathParam("id") long id) {
        petService.removePet(id);
    }
}
