package org.example.resource;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.example.dto.PetDTO;
import org.example.service.PetService;

import java.util.Collection;

@Path("pets")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)

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
        return petService.getAllPetsFiltered(offset, limit, species, sortBy, order);
    }

    @GET
    @Path("{id}")
    public Response getPet(@PathParam("id") long id) {
        PetDTO pet = petService.getPet(id);
        if (pet == null) return Response.status(Response.Status.NOT_FOUND).build();
        return Response.ok(pet).build();
    }

    @POST
    public Response adoptPet(@Valid PetDTO pet) {
        PetDTO newPet = petService.addPet(pet);
        return Response.status(Response.Status.CREATED).entity(newPet).build();
    }

    @PUT
    @Path("{id}/feed")
    public Response feedPet(@PathParam("id") long id) {
        PetDTO pet = petService.feedPet(id);
        if (pet == null) return Response.status(Response.Status.NOT_FOUND).build();
        return Response.ok(pet).build();
    }

    @PUT
    @Path("{id}/play")
    public Response playWithPet(@PathParam("id") long id) {
        PetDTO pet = petService.playWithPet(id);
        if (pet == null) return Response.status(Response.Status.NOT_FOUND).build();
        return Response.ok(pet).build();
    }

    @DELETE
    @Path("{id}")
    public Response releasePet(@PathParam("id") long id) {
        PetDTO removed = petService.removePet(id);
        if (removed == null) return Response.status(Response.Status.NOT_FOUND).build();
        return Response.noContent().build();
    }
}
