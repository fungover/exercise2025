package org.example;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
import org.jboss.logging.Logger;

@Path("pets")
public class PetsResource {

    PetsService petsService;

    Logger logger = Logger.getLogger(PetsResource.class);

    public PetsResource() {}

    @Inject
    public PetsResource(PetsService petsService) {
        this.petsService = petsService;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createPet(PetDTO petDTO) {
        logger.infov("Adopted pet: {0}", petDTO.name());
        return Response.ok()
                .entity(new PetsResource(petsService))
                .build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<PetDTO> getPets() {
        logger.infov("Getting all adopted pets");
        return petsService.getPets();
    }
}
