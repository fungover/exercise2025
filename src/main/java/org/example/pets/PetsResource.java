package org.example.pets;


import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;

import java.util.List;

@Path("pets")
public class PetsResource {

    PetService petService;

    public PetsResource() {

    }

    @Inject
    public PetsResource(PetService petService) {
        this.petService = petService;
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public List<PetDTO> getAllPets() {
        return petService.getAllPets();
    }

}