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

        ///  Build a Location-header: /api/pets/{id}
        URI location = uri.getAbsolutePathBuilder()
                .path(String.valueOf(created.getId()))
                .build();
        ///  Create status 201 + Location, Return the created pet as JSON
        return Response.created(location)
                .entity(created)
                .build();
    }

    /**
     * GET /api/pets -> list + filter/sorting/pagination
     **/
    @GET
    public List<PetDto> all(
            @QueryParam("species") String species,
            @DefaultValue("0") @QueryParam("offset") int offset, /// pagination start
            @DefaultValue("10") @QueryParam("limit") int limit, /// Shows maximum 10 at a time
            @DefaultValue("id") @QueryParam("sortBy") String sortBy, ///  Sorted by fields: id, name, happiness, hungerLevel
            @DefaultValue("desc") @QueryParam("order") String order /// Falling order, Happy dog -> Less happy dog
    ) {
        /// Protection against weird values
        offset = Math.max(0, offset);
        limit = Math.max(0, limit);

        ///  Get all pets from the Service
        var petsList = new ArrayList<>(pet.getAllPets().values());

        ///  Filter by species
        if (species != null && !species.isBlank()) {
            petsList.removeIf(p -> !p.getSpecies().equalsIgnoreCase(species));
        }
        ///  Sort the displayed List
        petsList.sort((a, b) -> {
            int cmp;
            switch (sortBy.toLowerCase()) {
                case "happiness" -> cmp = Integer.compare(a.getHappiness(), b.getHappiness());
                case "hungerlevel" -> cmp = Integer.compare(a.getHungerLevel(), b.getHungerLevel());
                case "name" -> cmp = a.getName().compareToIgnoreCase(b.getName());
                default -> cmp = Long.compare(a.getId() == null ? Long.MAX_VALUE : a.getId(),
                        b.getId() == null ? Long.MAX_VALUE : b.getId());
            }
            return order.equalsIgnoreCase("desc") ? -cmp : cmp;
        });

        ///  pagination, protection against out of range problems
        int from = Math.min(offset, petsList.size());
        int to = Math.min(from + limit, petsList.size());
        return petsList.subList(from, to);
    }

    /**
     * GET /api/pets/{id} -> get one specific pet
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
     * PUT /api/pets/{id}/feed -> feed: Decrease hunger level, not below 0
     **/
    @PUT
    @Path("/{id}/feed")
    public Response feed(@PathParam("id") Long id,
                         @Valid @BeanParam AmountParams p) {
        PetDto updated = pet.feedPet(id, p.amount);
        if (updated == null) throw new NotFoundException("Pet not found");
        return Response.noContent().build();
    }

    /**
     * PUT /api/pets/{id}/play -> play: Increase happiness level, not above 100
     **/
    @PUT
    @Path("/{id}/play")
    public Response play(@PathParam("id") Long id,
                         @Valid @BeanParam AmountParams p) {
        PetDto updated = pet.playWithPet(id, p.amount);
        if (updated == null) throw new NotFoundException("Pet not found");
        return Response.noContent().build();
    }

    /**
     * DELETE /api/pets/{id} -> release one pet
     **/
    @DELETE
    @Path("/{id}")
    public Response release(@PathParam("id") Long id) {
        boolean removed = pet.releasePet(id);
        if (!removed) throw new NotFoundException("Pet not found");
        return Response.noContent().build();
    }


}
