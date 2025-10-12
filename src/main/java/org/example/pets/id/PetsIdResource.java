package org.example.pets.id;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.example.pets.PetDTO;
import org.jboss.logging.Logger;

@Path("/pets/{id}")
public class PetResource {

    PetService petService;

    Logger logger = Logger.getLogger(PetResource.class);

    public PetResource() {}

    @Inject
    public PetResource(PetService petService) {
        this.petService = petService;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public PetDTO getPetById(@PathParam("id") int id) {
        logger.infov("Getting pet with id: {0}", id);
        return petService.getPetById(id);
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public void releasePetById(@PathParam("id") int id) {
        logger.infov("Giving away {0}", petService.getPetById(id).name(),
                " to a new happy family...");
        petService.releasePet(id);
    }
}
