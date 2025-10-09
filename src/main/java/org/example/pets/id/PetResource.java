package org.example.pets.id;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
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
}
