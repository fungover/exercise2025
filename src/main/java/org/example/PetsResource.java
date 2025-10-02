package org.example;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/pets")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PetsResource {

    Service service;

    public PetsResource() {}

    @Inject
    public PetsResource(Service service) {
        this.service = service;
    }

    // GET /api/pets → List all pets
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<PetDTO> listPets(
            @QueryParam("offset") @DefaultValue("0") int offset,
            @QueryParam("limit") @DefaultValue("3") int limit) {
        return service.list(offset, limit);
    }


    // POST api/pets → Adopt a new pet
    @POST
    public Response adopt(@Valid PetDTO dto) {
        PetDTO saved = service.adopt(dto);
        return Response.status(Response.Status.CREATED).entity(saved).build();
    }

    @GET
    @Path("/{id}")
    public Response getPet(@PathParam("id") Long id) {
        PetDTO pet = service.getPet(id);
        return Response.ok(pet).build();
    }

    @PUT
    @Path("/{id}/feed")
    public Response feedPet(@PathParam("id") Long id) {
        PetDTO pet = service.feedPet(id);
        return Response.ok(pet).build();
    }

    @PUT
    @Path("/{id}/play")
    public Response playPet(@PathParam("id") Long id) {
        PetDTO pet = service.playWithPet(id);
        return Response.ok(pet).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deletePet(@PathParam("id") Long id) {
        PetDTO removed = service.deletePet(id);
        return Response.ok(removed).build();
    }
}
