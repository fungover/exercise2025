package org.example;

import jakarta.inject.Singleton;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

import java.util.UUID;

@Path("/pets")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Singleton
public class PetResource {
    private final PetService petService = new PetService();

    @POST
    public Response newPet(@Valid PetDTO pet, @Context UriInfo uriInfo) {
        long uuid = UUID.randomUUID().getMostSignificantBits();
        petService.addPet(uuid ,pet);

        UriBuilder builder = uriInfo.getAbsolutePathBuilder().path(Long.toString(uuid));
        return Response.created(builder.build()).entity(pet).build();
    }
}
