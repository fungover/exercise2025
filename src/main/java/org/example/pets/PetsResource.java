package org.example.pets;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
import org.jboss.logging.Logger;

@Path("pets")
public class PetsResource {

    private PetsService petsService;
    private static final Logger logger = Logger.getLogger(PetsResource.class);

    public PetsResource() {}

    @Inject
    public PetsResource(PetsService petsService) {
        this.petsService = petsService;
    }

    @Path("{id}")
    public PetIdResource pet(@PathParam("id") int id) {
        return new PetIdResource(petsService, id);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response adoptAPet(@Valid PetDTO petDTO) {
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

    public static class PetIdResource {
        private final PetsService petsService;
        private final int id;

        public PetIdResource(PetsService petsService, int id) {
            this.petsService = petsService;
            this.id = id;
        }

        @GET
        @Produces(MediaType.APPLICATION_JSON)
        public PetDTO getPetById() {
            logger.infov("Getting pet with id: {0}", id);
            return petsService.getPetById(id);
        }

        @PUT
        @Path("feed")
        @Consumes(MediaType.APPLICATION_JSON)
        public Response feedPetById() {
            petsService.feedPetById(id);
            logger.infov("Feeding pet with id: {0}", id);
            return Response.ok()
                    .build();
        }

        @PUT
        @Path("play")
        @Consumes(MediaType.APPLICATION_JSON)
        public Response playWithPetById() {
            petsService.playWithPetById(id);
            logger.infov("Playing with pet with id: {0}", id);
            return Response.ok()
                    .build();
        }

        @DELETE
        @Produces(MediaType.APPLICATION_JSON)
        public Response releasePetById() {
            String name = petsService.getPetById(id).name();
            petsService.releasePetById(id);
            logger.infov("Giving away {0}", name +
                    ", to a new happy family...");
            return Response.ok()
                    .build();
        }
    }
}
