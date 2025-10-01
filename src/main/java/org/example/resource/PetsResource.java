package org.example.resource;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.example.dto.PetDTO;
import org.example.dto.PetResponseDTO;
import org.example.service.api.PetsService;

import java.util.List;

@Path("pets")
public class PetsResource {
    private final PetsService petsService;

    @Inject
    public PetsResource(PetsService petsService) {
        this.petsService = petsService;
    }

    @POST
    @Consumes({ MediaType.APPLICATION_JSON })
    @Produces({ MediaType.APPLICATION_JSON })
    public Response addPet(@Valid PetDTO petDTO) {
        PetDTO createdPet = petsService.addPet(petDTO);

        return Response.status(Response.Status.CREATED)
                .entity(new PetResponseDTO("Successfully added pet", createdPet))
                .build();
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

    @PUT
    @Path("{id}/feed")
    @Produces({ MediaType.APPLICATION_JSON })
    public Response feedPet(@PathParam("id") Long id) {
        PetDTO fedPet = petsService.feedPet(id);
        return Response.ok(new PetResponseDTO("Pet has been fed!", fedPet))
                .build();
    }

    @PUT
    @Path("{id}/play")
    @Produces({ MediaType.APPLICATION_JSON })
    public Response playWithPet(@PathParam("id") Long id) {
        PetDTO playedWithPet = petsService.playWithPet(id);
        return Response.ok(new PetResponseDTO("Pet has been played with", playedWithPet))
                .build();
    }

    @DELETE
    @Path("{id}")
    @Produces({ MediaType.APPLICATION_JSON })
    public Response deletePet(@PathParam("id") Long id) {
        petsService.deletePet(id);
        return Response.ok(new PetResponseDTO("Pet has successfully been removed"))
                .build();
    }
}
