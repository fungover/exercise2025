package org.example.resource;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import org.example.dto.PetDto;
import org.example.service.PetService;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Path("/pets")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)

public class PetResource {
    /// pet is the injected PetService
    @Inject
    PetService pet;

    @POST
    public Response adopt(@Valid PetDto body,
                          @Context UriInfo uri) {
        /// Sends PetDto to service, which provides with new id and saves in heap
        PetDto created = pet.adoptPet(body);

        ///  Build a Location-header: /api/pet/{id}
        URI location = uri.getAbsolutePathBuilder()
                .path(String.valueOf(created.getId()))
                .build();
        ///  Create status 201 + Location, Return the created pet as JSON
        return Response.created(location)
                .entity(created)
                .build();
    }

    /**
     * GET /api/pet -> list all pets
     **/
    @GET
    public List<PetDto> all() {
        ///  pet.getAllPets() returns Map<Long...> converted to JSON for display
        Map<Long, PetDto> map = pet.getAllPets();
        return new ArrayList<>(map.values());
    }

    /**
     * GET /api/pet/{id} -> get one specific pet
     **/
    @GET
    @Path("/{id}")
    public PetDto one(@PathParam("id") Long id) {
        /// foundPet is the pet I got from the Service
        PetDto foundPet = pet.getPetById(id);
        if (foundPet == null) throw new NotFoundException("Pet not found");
        return foundPet;
    }

    /**
     * PUT /api/pet/{id}/feed -> feed: Decrease hunger level, not bellow 0
     **/
    @PUT
    @Path("/{id}/feed")
    public Response feed(@PathParam("id") Long id,
                       @DefaultValue("10") @QueryParam("amount") int amount) {
        PetDto updated = pet.feedPet(id);
        if (updated == null) throw new NotFoundException("Pet not found");
        return Response.noContent().build();
    }

    /**
     * PUT /api/pet/{id}/play -> play: Increase happiness level, not above 100
     **/
    @PUT
    @Path("/{id}/play")
    public Response play(@PathParam("id") Long id,
                       @DefaultValue("10") @QueryParam("amount") int amount) {
        PetDto updated = pet.playWithPet(id);
        if (updated == null) throw new NotFoundException("Pet not found");
        return Response.noContent().build();
    }

    /**
     * DELETE /api/pet/{id} -> release one pet
     **/
    @DELETE
    @Path("/{id}")
    public Response release(@PathParam("id") Long id) {
        boolean removed = pet.releasePet(id);
        if (!removed) throw new NotFoundException("Pet not found");
        return Response.noContent().build();
    }


}
