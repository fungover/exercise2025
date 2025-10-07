package org.example.pets;


import jakarta.inject.Inject;
import jakarta.ws.rs.*;
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

    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public PetDTO addPet(PetDTO petDTO) {
        return petService.addPet(petDTO);
    }

}