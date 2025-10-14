package org.example.pets;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.example.Hello;

import java.util.ArrayList;
import java.util.List;

/*
🐼Implement a JAX-RS resource class:
POST /pets → Adopt a new pet
GET /pets → List all pets
GET /pets/{id} → View pet status
PUT /pets/{id}/feed → Feed the pet (reduce hunger)
PUT /pets/{id}/play → Play with the pet (increase happiness)
DELETE /pets/{id} → Release the pet
*/

@Path("pets")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PetResource {

    @Inject
    private PetService petService;

    // GET /api/pets show all pets
    @GET
    public List <PetDTO> listAll(){
        return petService.getAllPets();
    }

    // POST /api/pets  add new pet
    @POST
    public Response addPet(@Valid PetDTO pet) {
        PetDTO savedPet = petService.addPet(pet);
        return Response.status(Response.Status.CREATED).entity(savedPet).build();
    }

    @GET
    @Path("{id}")
    public PetDTO getPet(@PathParam("id") Long id) {
        return petService.getPetById(id);
    }
}
