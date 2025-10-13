package org.example.pets;

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
    public Response adoptAPet(PetDTO petDTO) {
        petsService.adoptPet(petDTO);
        logger.infov("Adopted pet: {0}", petDTO.name());
        return Response.ok()
                .build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<PetDTO> getPets() {
        logger.infov("Getting all adopted pets");
        return petsService.getPets();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public PetDTO getPetById(@PathParam("id") int id) {
        logger.infov("Getting pet with id: {0}", id);
        return petsService.getPetById(id);
    }

    @PUT
    @Path("{id}/feed")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response feedPetById(@PathParam("id") int id) {
        petsService.feedPetById(id);
        logger.infov("Feeding pet with id: {0}", id);
        return Response.ok()
                .build();
    }

    @PUT
    @Path("{id}/play")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response playWithPetById(@PathParam("id") int id) {
        petsService.playWithPetById(id);
        logger.infov("Playing with pet with id: {0}", id);
        return Response.ok()
                .build();
    }

    @DELETE
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response releasePetById(@PathParam("id") int id) {
        petsService.releasePetById(id);
        logger.infov("Giving away {0}", petsService.getPetById(id).name(),
                " to a new happy family...");
        return Response.ok()
                .build();
    }
}
