package org.example.resource;

import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import org.example.DTO.PetDTO;
import org.example.service.PetService;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;

import jakarta.ws.rs.*;

@Path("/pets")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)

public class PetResource {

    @Inject
    private PetService service;

    @Context
    UriInfo uriInfo;

    @POST
    public Response adopt(@Valid PetDTO pet) {
        PetDTO created = service.adoptPet(pet);
        URI location = uriInfo.getAbsolutePathBuilder().path(String.valueOf(created.getId())).build();
        return Response.created(location).entity(created).build();
    }

    @GET
    public List<PetDTO> getPets() {
        return service.listAllPets();
    }

    @GET
    @Path("{id}")
    public PetDTO getPetByID(@PathParam("id") long id) {
        return service.getPetWithID(id);
    }

    @PUT
    @Path("{id}/feed")
    public PetDTO feedPet(@PathParam("id") long id, @QueryParam("amount") int amount) {
        if  (amount < 0) throw new BadRequestException("Amount must be greater than zero");
        return service.feedPet(id, amount);
    }

    @PUT
    @Path("{id}/play")
    public PetDTO playPet(@PathParam("id") long id, @QueryParam("amount") int amount) {
        if (amount < 0) throw new BadRequestException("Amount must be greater than zero");
        return service.play(id, amount);
    }

    @DELETE
    @Path("{id}")
    public Response deletePet(@PathParam("id") long id) {
        service.deletePet(id);
        return Response.noContent().build();
    }
}
