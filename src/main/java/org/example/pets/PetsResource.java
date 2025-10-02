package org.example.pets;


import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;

@Path("pets")
public class PetsResource {

    @GET
    @Produces({ MediaType.APPLICATION_JSON })
    public Pets pets(@QueryParam("pets") String pets) {
        if ((pets == null) || pets.trim().isEmpty()) {
            pets = "Generic pet";
        }

        return new Pets(pets);
    }

}
