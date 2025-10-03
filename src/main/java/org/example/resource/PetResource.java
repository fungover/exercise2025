package org.example.resource;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.example.dto.PetDTO;
import org.example.service.PetService;

import java.util.Collection;

/**
 * PetResource is the REST API layer for pets.
 * IT exposes PetService functioanlity through HTTP
 *
 * Base path: /api/pets
 */
@Path("pets")
@Produces(MediaType.APPLICATION_JSON)
public class PetResource {

    @Inject
    private PetService petService;

    // POST /pets. Adopt a new pet.
    // Input: PetDTO (validtated with @Valid)
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response adoptPet(@Valid PetDTO pet) {
        Long id = petService.addPet(pet);

        // return 201 Created with the new ID in the response body
        return Response.status(Response.Status.CREATED)
                .entity("{\"id\": " + id + "}")
                .build();
    }

    // GET /pets. List all pets with optional pagination (offset and limit)
    // Example: /pets?offset=0&limit=10
    @GET
    public Response getAllPets(
            @QueryParam("offset") @DefaultValue("0") int offset,
            @QueryParam("limit") @DefaultValue("10") int limit) {
        // Convert pets from map to list for easier slicing
        var allPets = petService.getAllPets().stream().toList();

        // Calculate sublist boundaries
        int fromIndex = Math.max(0, offset);
        int toIndex = (limit < 0) ? allPets.size() : Math.min(allPets.size(), offset + limit);

        // Handle case: offset > size
        if (fromIndex > allPets.size()) {
            return Response.ok(java.util.Collections.emptyList()).build();
        }

        var paginated = allPets.subList(fromIndex, toIndex);
        return Response.ok(paginated).build();
    }

    // GET /pets/{id}. Get a specific pet by ID.
    // Output: PetDTO if found, 404 Not Found if missing
    @GET
    @Path("/{id}")
    public Response getPetById(@PathParam("id") Long id) {
        PetDTO pet = petService.getPetById(id);
        if (pet == null) {
            throw new NotFoundException();
        }
        return Response.ok(pet).build();
    }

    // PUT /pets/{id}/feed. Feed a pet to decrease hunger level.
    // Returns 200 OK if the pet was updated. 404 if not found.
    @PUT
    @Path("/{id}/feed")
    public Response feedPet(@PathParam("id") Long id) {
        PetDTO pet = petService.getPetById(id);
        if (pet == null) {
            throw new NotFoundException();
        }

        petService.feedPet(id);
        return Response.ok(petService.getPetById(id)).build(); // return updated pet
    }

    // PUT /pets/{id}/play. Play with a pet to increase happiness.
    // Returns 200 OK if the pet was updated. 404 if not found.
    @PUT
    @Path("/{id}/play")
    public Response playWithPet(@PathParam("id") Long id) {
        PetDTO pet = petService.getPetById(id);
        if (pet == null) {
            throw new NotFoundException();
        }

        petService.playWithPet(id);
        return Response.ok(petService.getPetById(id)).build(); // return updated pet
    }

    // DELETE /pets/{id}. Release a pet (remove from memory)
    // Returns 204 No Content if successful. 404 if not found.
    @DELETE
    @Path("/{id}")
    public Response deletePet(@PathParam("id") Long id) {
        boolean removed = petService.deletePet(id);
        if (!removed) {
            throw new NotFoundException();
        }
        return Response.noContent().build();
    }
}
