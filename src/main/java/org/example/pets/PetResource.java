package org.example.pets;

import jakarta.inject.Singleton;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Path("/pets")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Singleton
public class PetResource {
    private final PetService petService = new PetService();

    @POST
    public Response newPet(@Valid PetDTO pet, @Context UriInfo uriInfo) {
        long uuid = UUID.randomUUID().getMostSignificantBits();
        petService.addPet(uuid ,pet);

        UriBuilder builder = uriInfo.getAbsolutePathBuilder().path(Long.toString(uuid));
        return Response.created(builder.build()).entity(pet).build();
    }

    @GET
    public Response listPets() {
        ConcurrentHashMap<Long, PetDTO> pets = petService.getPets();
        return Response.ok(pets).build();
    }

    @GET
    @Path("/{id}")
    public Response getPetById(@PathParam("id") Long id) {
        PetDTO pet = petService.getPet(id);

        return Response.ok(pet).build();
    }

    @PUT
    @Path("/{id}/feed")
    public Response feedPet(@PathParam("id") Long id) {
        boolean success = petService.feedPet(id);

        return success
                ? Response.ok().entity("Pet feed has been successfully").build()
                : Response.status(Response.Status.NOT_FOUND).entity("Incorrect ID pet was not found.").build();
    }

    @PUT
    @Path("/{id}/play")
    public Response playPet(@PathParam("id") Long id) {
        boolean success = petService.playPet(id);

        return success
                ? Response.ok().entity("Pet played with successfully").build()
                : Response.status(Response.Status.NOT_FOUND).entity("Incorrect ID pet was not found.").build();
    }

    @DELETE
    @Path("/{id}")
    public Response deletePet(@PathParam("id") Long id) {
        petService.removePet(id);
        return Response.noContent().build();
    }
}
