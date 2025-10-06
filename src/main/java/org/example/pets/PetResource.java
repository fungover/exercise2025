package org.example.pets;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.Collection;


@Path("/pets")
@Produces(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class PetResource {

    private PetService petService;

    public PetResource(){}

    @Inject
    public PetResource(PetService petService) {
        this.petService = petService;
    }
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public PetDTO adoptPet(@Valid PetDTO pet) {
        return petService.adoptPet(pet);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Collection<PetDTO> getPets(){
        return petService.getAllPets();
    }

    @GET
    @Path("{id}")
    public PetDTO getPet(@PathParam("id") long id){
        return petService.getPet(id);
    }

    @PUT
    @Path("{id}/feed")
    public PetDTO feedPet(@PathParam("id") long id){
        return petService.feedPet(id);
    }
    @PUT
    @Path("{id}/play")
    public PetDTO playWithPet(@PathParam("id") long id){
        return petService.playWithPet(id);
    }

    @DELETE
    @Path("{id}")
    public PetDTO deletePet(@PathParam("id") long id){
        return petService.deletePet(id);
    }

}
