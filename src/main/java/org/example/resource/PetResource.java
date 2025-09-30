package org.example.resource;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.example.dto.PetDTO;
import org.example.service.PetService;

import java.net.URI;

@Path("/pets")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PetResource {

    @Inject
    private PetService petService;

    @POST
    public Response create(@Valid PetDTO pet) {
        PetDTO created = petService.createPet(pet);
        return Response.created(URI.create("/api/pets/" + created.getId()))
                .entity(created)
                .build();
    }

    @GET
    public Response getAll() {
        return Response.ok(petService.getAllPets()).build();
    }

    @GET
    @Path("/{id}")
    public Response getById(@PathParam("id") Long id) {
        return petService.getPetById(id)
                .map(p -> Response.ok(p).build())
                .orElseThrow(() -> new NotFoundException("Pet with ID " + id + " does not exist."));
    }

    @PUT
    @Path("/{id}/feed")
    public Response feed(@PathParam("id") Long id) {
        return petService.feedPet(id)
                .map(p -> Response.ok(p).build())
                .orElseThrow(() -> new NotFoundException("Pet with ID " + id + " does not exist."));
    }

    @PUT
    @Path("/{id}/play")
    public Response play(@PathParam("id") Long id) {
        return petService.playWithPet(id)
                .map(p -> Response.ok(p).build())
                .orElseThrow(() -> new NotFoundException("Pet with ID " + id + " does not exist."));
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        boolean deleted = petService.deletePet(id);
        if (!deleted) {
            throw new NotFoundException("Pet with ID " + id + " does not exist.");
        }
        return Response.noContent().build();
    }

    @GET
    @Path("/search")
    public Response searchPets(
            @QueryParam("offset") @DefaultValue("0") int offset,
            @QueryParam("limit") @DefaultValue("10") int limit,
            @QueryParam("species") String species,
            @QueryParam("sortBy") @DefaultValue("id") String sortBy,
            @QueryParam("order") @DefaultValue("asc") String order
    ) {
        return Response.ok(
                petService.searchPets(offset, limit, species, sortBy, order)
        ).build();

    }


}
