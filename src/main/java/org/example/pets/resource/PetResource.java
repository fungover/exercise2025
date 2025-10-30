package org.example.pets.resource;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import org.example.pets.dto.PetDTO;
import org.example.pets.service.PetService;

import java.net.URI;
import java.util.List;

@Path("/pets")
@Produces( { "application/json" })
@Consumes( { "application/json" })
public class PetResource {

    @Inject
    PetService petService;

    @POST
        public Response adopt(@Valid PetDTO pet, @Context UriInfo uriInfo) {
        PetDTO created = petService.adopt(pet);
        URI location = uriInfo.getAbsolutePathBuilder().path(created.getId().toString()).build();
        return Response.created(location).entity(created).build();
    }

    @GET
        public Response list() {
        List<PetDTO> all = petService.list();
        return Response.ok(all).build();
        }

    @GET
    @Path("{id : \\d+}")
        public Response find(@PathParam("id") long id) {
        return petService.find(id)
                .map(petDTO -> Response.ok(petDTO).build())
                .orElseGet(() -> Response.status(Response.Status.NOT_FOUND).build());
    }

    @PUT
    @Path("{id: \\d+}/feed")
        public Response feed(@PathParam("id") long id, @QueryParam("amount") @DefaultValue("10") int amount) {
        PetDTO updated = petService.feed(id, amount);
        return Response.ok(updated).build();
    }

    @PUT
    @Path("{id: \\d+}/play")
        public Response play(@PathParam("id") long id, @QueryParam("amount") @DefaultValue("10") int amount) {
            PetDTO updated = petService.play(id, amount);
            return Response.ok(updated).build();
    }

    @DELETE
    @Path("{id: \\d+}")
    public Response delete(@PathParam("id") long id) {
        petService.release(id);
        return Response.noContent().build();
    }
}
