package org.example.pets;


import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

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
    public List<PetDTO> getAllPets(
            @QueryParam("species") String species,
            @QueryParam("sortBy") String sortBy,
            @QueryParam("order") String order,
            @QueryParam("offset") Integer offset,
            @QueryParam("limit") Integer limit) {
        return petService.getAllPets(species, sortBy, order, offset, limit);
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public PetDTO getPetById(@PathParam("id") Long id) {
        return petService.getPetById(id);
    }

    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response addPet(@Valid PetDTO petDTO) {
        PetDTO createdPet = petService.addPet(petDTO);
        return Response
                .status(Response.Status.CREATED)
                .entity(createdPet)
                .build();
    }

    @PUT
    @Path("{id}/feed")
    @Produces({MediaType.APPLICATION_JSON})
    public PetDTO feedPet(@PathParam("id") Long id) {
        return petService.feedPet(id);
    }

    @PUT
    @Path("{id}/play")
    @Produces({MediaType.APPLICATION_JSON})
    public PetDTO playWithPet(@PathParam("id") Long id) {
        return petService.playWithPet(id);
    }

    @DELETE
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response deletePet(@PathParam("id") Long id) {
        petService.deletePet(id);
        return Response
                .noContent()
                .build();
    }

}