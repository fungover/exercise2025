package org.example.pets;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.Collection;
import java.util.List;


@Path("/pets")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class PetResource {

    private PetService petService;

    public PetResource(){}

    @Inject
    public PetResource(PetService petService) {
        this.petService = petService;
    }
    @POST
    public Response adoptPet(@Valid PetDTO pet) {
        System.out.println("Adopting pet: " + pet.getName());
        PetDTO saved = petService.adoptPet(pet);
        return Response.status(Response.Status.CREATED).entity(saved).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllPets(){
        System.out.println("In get all pets");
        List<PetDTO> pets = petService.getAllPets();
        if(pets.isEmpty()){
            throw new NotFoundException();
        }
        return Response.ok(pets).build();
    }

    @GET
    @Path("/{id}")
    public Response getPet(@PathParam("id") long id){
        System.out.println("In getPet: "+id);
        PetDTO pet = petService.getPet(id);
        System.out.println(pet);
        if(pet == null){
            throw new NotFoundException();
        }
        return Response.ok(pet).build();
    }

    @PUT
    @Path("/{id}/feed")
    public Response feedPet(@PathParam("id") long id){
        System.out.println("In feed pet");
        var pet = petService.getPet(id);
        if(pet == null){
            throw new NotFoundException();
        }
        petService.feedPet(id);
        System.out.println("Feeding pet: " + pet.getName() + " " + pet.getHungerLevel());
        return Response.ok(pet).build();
    }
    @PUT
    @Path("{id}/play")
    public Response playWithPet(@PathParam("id") long id){
        var pet = petService.getPet(id);
         if(pet == null){
            throw new NotFoundException();
        }
        petService.playWithPet(id);
        return Response.ok(pet).build();
    }

    @DELETE
    @Path("{id}")
    public Response deletePet(@PathParam("id") long id){
        var pet = petService.getPet(id);
        if(pet == null){
            throw new NotFoundException();
        }
        petService.deletePet(id);
        return Response.ok(pet).build();
    }

}
