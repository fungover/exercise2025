package org.example.resource;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.example.dto.PetDTO;
import org.example.service.PetService;

import java.net.URI;

@Path("/pets")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PetResource {

    @Inject
    private PetService petService;

    @POST
    public Response create(@Valid PetDTO pet) {
        PetDTO created = petService.createPet(pet);
        return Response.created(URI.create("/api/pets/" + created.getId()))
                .entity(created)
                .build();
    }

    @GET
    public Response getAll() {
        return Response.ok(petService.getAllPets()).build();
    }


}
