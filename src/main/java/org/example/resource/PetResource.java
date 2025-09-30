package org.example.resource;


import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import org.example.dto.PetDTO;
import org.example.service.PetService;

import java.net.URI;

@Path("/pets")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PetResource {

    @Inject
    PetService petService;

    @Context
    UriInfo uriInfo;

    @POST public Response adoptPet(@Valid PetDTO pet) {
        PetDTO created = petService.adoptPet(pet);
        URI location = uriInfo.getAbsolutePathBuilder()
                              .path(Long.toString(created.getId()))
                              .build();
        return Response.created(location)
                       .entity(created)
                       .build();

    }

}
