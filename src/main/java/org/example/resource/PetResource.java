package org.example.resource;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import org.example.dto.PetDTO;
import org.example.service.PetService;

import java.net.URI;

@Path("/pets") // Base path for all endpoints in this resource, e.g., /api/pets.
@Produces(MediaType.APPLICATION_JSON) // All methods will be sent as JSON responses
@Consumes(MediaType.APPLICATION_JSON) // All incoming requests are expected to be in JSON format.
public class PetResource {

    @Inject
    private PetService petService; // CDI: Injecting the service to handle business logic.

    @POST
    public Response create(@Valid PetDTO pet, @Context UriInfo uriInfo) { // @Valid triggers validation based on annotations in PetDTO
        PetDTO created = petService.createPet(pet);

        URI location = uriInfo.getAbsolutePathBuilder()
                .path(String.valueOf(created.getId())) // adds the new pet ID on the end of the current path.
                .build();

        return Response.created(location)
                .entity(created) // 201 Created with the created pet in the response body
                .build();
    }

    @GET
    public Response getAll(
            @QueryParam("offset") @DefaultValue("0") @Min(0) int offset, // offset for pagination, default is 0
            @QueryParam("limit") @DefaultValue("10") @Min(1) @Max(100) int limit, // limit for pagination, default is 10
            @QueryParam("species") String species, // optional species filter
            @QueryParam("sortBy") @DefaultValue("id") String sortBy, // field to sort by, default is "id"
            @QueryParam("order") @DefaultValue("asc") String order // sort order, default is ascending
    ) {
        return Response.ok(
                petService.searchPets(offset, limit, species, sortBy, order) // Call service to get filtered, sorted, paginated pets
        ).build(); // 200 OK with the list of pets in the response body

    }


    @GET
    @Path("/{id}") // Path parameter for pet ID, e.g., /api/pets/1
    public Response getById(@PathParam("id") Long id) {
        return petService.getPetById(id) // Call service to get pet by ID
                .map(p -> Response.ok(p).build()) // If found, return 200 OK with pet in body
                .orElseThrow(() -> new NotFoundException("Pet with ID " + id + " does not exist.")); // If not found, throw 404 Not Found
    }

    @PUT
    @Path("/{id}/feed") // Path for feeding a pet, e.g., /api/pets/1/feed
    public Response feed(@PathParam("id") Long id) {
        return petService.feedPet(id) // Call service to feed the pet
                .map(p -> Response.ok(p).build()) // If found and fed, return 200 OK with updated pet in body
                .orElseThrow(() -> new NotFoundException("Pet with ID " + id + " does not exist.")); // If not found, throw 404 Not Found
    }

    @PUT
    @Path("/{id}/play") // Path for playing with a pet, e.g., /api/pets/1/play
    public Response play(@PathParam("id") Long id) {
        return petService.playWithPet(id) // Call service to play with the pet
                .map(p -> Response.ok(p).build()) // If found and played with, return 200 OK with updated pet in body
                .orElseThrow(() -> new NotFoundException("Pet with ID " + id + " does not exist.")); // If not found, throw 404 Not Found
    }

    @DELETE
    @Path("/{id}") // Path for deleting a pet, e.g., /api/pets/1
    public Response delete(@PathParam("id") Long id) {
        boolean deleted = petService.deletePet(id); // Call service to delete the pet
        if (!deleted) {
            throw new NotFoundException("Pet with ID " + id + " does not exist."); // If not found, throw 404 Not Found
        }
        return Response.noContent().build(); // 204 No Content on successful deletion
    }
}
