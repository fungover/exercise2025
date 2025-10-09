package org.example.pets;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
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


    private static List<PetDTO> pets = new ArrayList<>();

    // GET /api/pets → lista alla djur
    @GET
    public List<PetDTO> listAll() {
        return pets;
    }

    // POST /api/pets → lägg till nytt djur
    @POST
    public PetDTO addPet(PetDTO pet) {
        pets.add(pet);
        return pet;
    }
}
