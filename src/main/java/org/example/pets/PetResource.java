package org.example.pets;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.example.Hello;

/*
🐼Implement a JAX-RS resource class:
POST /pets → Adopt a new pet
GET /pets → List all pets
GET /pets/{id} → View pet status
PUT /pets/{id}/feed → Feed the pet (reduce hunger)
PUT /pets/{id}/play → Play with the pet (increase happiness)
DELETE /pets/{id} → Release the pet
*/

@Path("hello")
public class PetResource {


    @GET
    @Produces({ MediaType.APPLICATION_JSON })
    public Hello hello(@QueryParam("name") String name) {
        if ((name == null) || name.trim().isEmpty()) {
            name = "pets";
        }

        return new Hello(name);
    }
}
