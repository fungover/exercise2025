package org.example.petadoptionservice.resource;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriBuilder;
import org.example.petadoptionservice.dto.PetDTO;
import org.example.petadoptionservice.service.PetService;

import java.net.URI;
import java.util.List;

@Path("/pets")
@RequestScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PetResource {
    @Inject
    private PetService petService;
    @POST
    public Response adopt(@Valid PetDTO pet) {
        PetDTO adoptedPet = petService.adopt(pet);
        URI uri = UriBuilder.fromPath("/{id}").build(adoptedPet.id());
        return Response.created(uri).entity(adoptedPet).build();
    }

    @GET
    public List<PetDTO> listAll() {
        return petService.listAll();
    }

    @GET
    @Path("/{id}")
    public PetDTO getById(@PathParam("id") Long id) {
        return petService.getById(id);
    }

    @PUT
    @Path("/{id}/feed")
    public PetDTO feed(@PathParam("id") Long id) {
        return petService.feed(id);
    }

    @PUT
    @Path("/{id}/play")
    public PetDTO play(@PathParam("id") Long id) {
        return petService.play(id);
    }

    @DELETE
    @Path("/{id}")
    public Response release(@PathParam("id") Long id) {
        petService.release(id);
        return Response.noContent().build();
    }


}

