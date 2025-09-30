package org.example.resource;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.example.dto.PetDTO;
import org.example.service.api.PetsService;

import java.util.List;

@Path("pets")
public class PetResource {

    @Inject
    private PetsService petsService;

    @POST
    @Consumes({ MediaType.APPLICATION_JSON })
    @Produces({ MediaType.APPLICATION_JSON })
    public PetDTO addPet(PetDTO petDTO) {
        return petsService.addPet(petDTO);
    }

    @GET
    @Produces({ MediaType.APPLICATION_JSON })
    public List<PetDTO> getPets() {
        return petsService.getAllPets();
    }

    @GET
    @Path("{id}")
    @Produces({ MediaType.APPLICATION_JSON })
    public PetDTO getPetById(@PathParam("id") Long id) {
        return petsService.getPetById(id);
    }
}
