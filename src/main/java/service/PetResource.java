package service;

import dto.PetDTO;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import service.PetService;

import java.util.Collection;

@Path("/pets")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PetResource {

    @Inject
    private PetService petService;

    @POST
    public Response adoptPet(@Valid PetDTO pet) {
        PetDTO created = petService.adoptPet(pet);
        return Response.status(Response.Status.CREATED).entity(created).build();
    }

    @GET
    public Collection<PetDTO> getAllPets() {
        return petService.getAllPets();
    }

    @GET
    @Path("/{id}")
    public PetDTO getPet(@PathParam("id") Long id) {
        PetDTO pet = petService.getPet(id);
        //NotFoundException returns 404.
        if (pet == null) throw new NotFoundException("Pet not found");
        return pet;
    }

    @PUT
    @Path("/{id}/feed")
    public PetDTO feedPet(@PathParam("id") Long id) {
        PetDTO pet = petService.feedPet(id);
        if (pet == null) throw new NotFoundException("Pet not found");
        return pet;
    }

    @PUT
    @Path("/{id}/play")
    public PetDTO playPet(@PathParam("id") Long id) {
        PetDTO pet = petService.playWithPet(id);
        if (pet == null) throw new NotFoundException("Pet not found");
        return pet;
    }

    @DELETE
    @Path("/{id}")
    public Response releasePet(@PathParam("id") Long id) {
        petService.releasePet(id);
        return Response.noContent().build();
    }
}

