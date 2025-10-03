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
@Consumes(MediaType.APPLICATION_JSON)
public class PetResource {

    @Inject
    private PetService petService;

    // POST /pets. Adopt a new pet.
    // Input: PetDTO (validtated with @Valid)
    @POST
    public Response adoptPet(@Valid PetDTO pet) {
        Long id = petService.addPet(pet);

        // return 201 Created with the new ID in the response body
        return Response.status(Response.Status.CREATED)
                .entity("{\"id\": " + id + "}")
                .build();
    }

    // GET /pets. List all pets.
    // Output: JSON array of all PetDTOs
    @GET
    public Collection<PetDTO> getAllPets() {
        return petService.getAllPets();
    }

    // GET /pets/{id}. Get a specific pet by ID.
    // Output: PetDTO if found, 404 Not Found if missing
    @GET
    @Path("/{id}")
    public Response getPetById(@PathParam("id") Long id) {
        PetDTO pet = petService.getPetById(id);
        if (pet == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"error\": \"Pet not found\"}")
                    .build();
        }
        return Response.ok(pet).build();
    }
}
