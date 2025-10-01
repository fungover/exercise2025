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
import java.util.List;

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

    @GET public Response listPets(@QueryParam("offset") Integer offset,
                                  @QueryParam("limit") Integer limit,
                                  @QueryParam("species") String species,
                                  @QueryParam("sortBy") String sortBy,
                                  @QueryParam("order") String order) {
        List<PetDTO> list = petService.listPets(offset, limit, species, sortBy,
          order);
        return Response.ok(list)
                       .build();
    }

    @GET @Path("{id}") public Response getPet(@PathParam("id") long id) {
        return Response.ok(petService.getPet(id))
                       .build();
    }

    @PUT @Path("{id}/feed") public Response feedPet(@PathParam("id") long id,
                                                    @QueryParam("amount")
                                                    @DefaultValue("10") int amount) {
        return Response.ok(petService.feedPet(id, amount))
                       .build();
    }

    @PUT @Path("{id}/play") public Response playPet(@PathParam("id") long id,
                                                    @QueryParam("amount")
                                                    @DefaultValue("10") int amount) {
        return Response.ok(petService.playWithPet(id, amount))
                       .build();
    }

    @DELETE @Path("{id}") public Response releasePet(@PathParam("id") long id) {
        petService.releasePet(id);
        return Response.noContent()
                       .build();
    }

}
